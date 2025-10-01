package com.tuorg.acquistoazioni.service;

import com.tuorg.acquistoazioni.domain.entity.*;
import com.tuorg.acquistoazioni.domain.repo.*;
import com.tuorg.acquistoazioni.exception.*;
import com.tuorg.acquistoazioni.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.*;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AcquistoService {

  private final AgenteRepository agenteRepo;
  private final TitoloRepository titoloRepo;
  private final MovimentoRepository movimentoRepo;
  private final MovimentoTitoloRepository mvtTitoloRepo;
  private final MovimentoTitoloAgenteRepository mvtTitoloAgRepo;

  @Transactional
  public AcquistoResponse registraAcquisto(AcquistoRequest req) {
    if (req.getImportoTotale().signum() <= 0) throw new BusinessException("Importo totale deve essere > 0");
    if (req.getQuantitaTotale() <= 0) throw new BusinessException("Quantità totale deve essere > 0");

    Titolo titolo = titoloRepo.findById(req.getTitoloId())
        .orElseThrow(() -> new NotFoundException("Titolo non trovato"));
    LocalDate data = req.getDataAcquisto();
    if (data.isBefore(titolo.getDataEmissione()))
      throw new BusinessException("Data acquisto precedente alla data di emissione del titolo");

    List<Agente> tutti = agenteRepo.findAll();
    List<Agente> attivi = tutti.stream().filter(a -> a.isAttivoAlla(data)).toList();
    if (attivi.isEmpty()) throw new BusinessException("Nessun agente attivo alla data");

    Map<Long, BigDecimal> saldi = new HashMap<>();
    BigDecimal saldoTotale = BigDecimal.ZERO;
    for (Agente a : attivi) {
      BigDecimal s = movimentoRepo.saldoByAgente(a.getId());
      if (s.signum() > 0) {
        saldi.put(a.getId(), s);
        saldoTotale = saldoTotale.add(s);
      }
    }
    if (saldoTotale.compareTo(req.getImportoTotale()) < 0)
      throw new BusinessException("Saldo totale insufficiente");

    MovimentoTitolo mvtTitolo = mvtTitoloRepo.save(
      MovimentoTitolo.builder()
        .titolo(titolo)
        .dataInvestimento(data)
        .quantita(req.getQuantitaTotale())
        .importoAcquisto(req.getImportoTotale())
        .build()
    );

    List<AcquistoResponse.RipartoItem> riparti = new ArrayList<>();
    BigDecimal importoRim = req.getImportoTotale();
    int qtyRim = req.getQuantitaTotale();

    var ord = saldi.entrySet().stream()
      .sorted((e1,e2)-> e2.getValue().compareTo(e1.getValue()))
      .toList();

    for (int i=0; i<ord.size(); i++) {
      var e = ord.get(i);
      Long agenteId = e.getKey();
      BigDecimal quota = req.getImportoTotale()
        .multiply(e.getValue()).divide(saldoTotale, 2, RoundingMode.DOWN);
      int qty = (int)Math.floor(req.getQuantitaTotale()
        * e.getValue().doubleValue() / saldoTotale.doubleValue());

      if (i == ord.size()-1) { quota = importoRim; qty = qtyRim; }
      importoRim = importoRim.subtract(quota);
      qtyRim -= qty;

      Agente agenteRef = new Agente(); agenteRef.setId(agenteId);

      Movimento mov = movimentoRepo.save(
        Movimento.builder()
          .agente(agenteRef)
          .data(data)
          .importo(quota.negate())  // débito
          .build()
      );

      mvtTitoloAgRepo.save(
        MovimentoTitoloAgente.builder()
          .movimentoTitolo(mvtTitolo)
          .agente(agenteRef)
          .quantita(qty)
          .importoAcquisto(quota)
          .idMovimento(mov.getId())
          .build()
      );

      riparti.add(new AcquistoResponse.RipartoItem(agenteId, qty, quota, mov.getId()));
    }

    return new AcquistoResponse(
      mvtTitolo.getId(), titolo.getId(), data,
      req.getQuantitaTotale(), req.getImportoTotale(), riparti
    );
  }
}

package com.tuorg.acquistoazioni.service;

import com.tuorg.acquistoazioni.domain.entity.Agente;
import com.tuorg.acquistoazioni.domain.entity.Titolo;
import com.tuorg.acquistoazioni.domain.repo.*;
import com.tuorg.acquistoazioni.exception.BusinessException;
import com.tuorg.acquistoazioni.exception.NotFoundException;
import com.tuorg.acquistoazioni.web.dto.AcquistoRequest;
import com.tuorg.acquistoazioni.web.dto.AcquistoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AcquistoServicePreview {

  private final AgenteRepository agenteRepo;
  private final TitoloRepository titoloRepo;
  private final MovimentoRepository movimentoRepo;

  /** Calcula la previsualización SIN persistir nada. */
  public AcquistoResponse calcolaPreview(AcquistoRequest req) {
    if (req.getImportoTotale().signum() <= 0) throw new BusinessException("Importo totale deve essere > 0");
    if (req.getQuantitaTotale() <= 0) throw new BusinessException("Quantità totale deve essere > 0");

    Titolo titolo = titoloRepo.findById(req.getTitoloId())
        .orElseThrow(() -> new NotFoundException("Titolo non trovato"));
    LocalDate data = req.getDataAcquisto();
    if (data.isBefore(titolo.getDataEmissione()))
      throw new BusinessException("Data acquisto precedente alla data di emissione del titolo");

    List<Agente> attivi = agenteRepo.findAll().stream().filter(a -> a.isAttivoAlla(data)).toList();
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

      riparti.add(new AcquistoResponse.RipartoItem(agenteId, qty, quota, null));
    }

    return new AcquistoResponse(
      null, titolo.getId(), data,
      req.getQuantitaTotale(), req.getImportoTotale(), riparti
    );
  }
}

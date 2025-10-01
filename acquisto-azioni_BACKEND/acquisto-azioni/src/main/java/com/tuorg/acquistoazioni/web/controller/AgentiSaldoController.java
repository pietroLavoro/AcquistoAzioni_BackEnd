package com.tuorg.acquistoazioni.web.controller;

import com.tuorg.acquistoazioni.domain.entity.Agente;
import com.tuorg.acquistoazioni.domain.repo.AgenteRepository;
import com.tuorg.acquistoazioni.domain.repo.MovimentoRepository;
import com.tuorg.acquistoazioni.web.dto.AgenteSaldoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/agenti")
@RequiredArgsConstructor
public class AgentiSaldoController {

  private final AgenteRepository agenteRepo;
  private final MovimentoRepository movimentoRepo;

  @GetMapping("/saldi")
  public List<AgenteSaldoDto> saldi(@RequestParam(value = "data", required = false) String dataIso) {
    LocalDate data = (dataIso != null && !dataIso.isBlank()) ? LocalDate.parse(dataIso) : LocalDate.now();
    List<AgenteSaldoDto> res = new ArrayList<>();
    for (Agente a : agenteRepo.findAll()) {
      if (!a.isAttivoAlla(data)) continue;
      BigDecimal saldo = movimentoRepo.saldoByAgente(a.getId());
      res.add(new AgenteSaldoDto(a.getId(), a.getCodiceFiscale(), saldo, 0));
    }
    return res;
  }
}

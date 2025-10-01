package com.tuorg.acquistoazioni.web.controller;

import com.tuorg.acquistoazioni.domain.entity.Agente;
import com.tuorg.acquistoazioni.domain.repo.AgenteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/agenti")
@RequiredArgsConstructor
public class AgentiController {

  private final AgenteRepository repo;

  @GetMapping
  public List<Agente> list() { return repo.findAll(); }
}

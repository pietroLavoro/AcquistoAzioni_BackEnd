package com.tuorg.acquistoazioni.web.controller;

import com.tuorg.acquistoazioni.domain.entity.Titolo;
import com.tuorg.acquistoazioni.domain.repo.TitoloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/titoli")
@RequiredArgsConstructor
public class TitoliController {

  private final TitoloRepository repo;

  @GetMapping
  public List<Titolo> list() { return repo.findAll(); }
}

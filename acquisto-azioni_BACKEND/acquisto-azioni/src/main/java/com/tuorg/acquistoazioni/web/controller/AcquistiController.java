package com.tuorg.acquistoazioni.web.controller;

import com.tuorg.acquistoazioni.service.AcquistoService;
import com.tuorg.acquistoazioni.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/acquisti")
@RequiredArgsConstructor
public class AcquistiController {

  private final AcquistoService acquistoService;

  @PostMapping
  public ResponseEntity<AcquistoResponse> acquista(@Validated @RequestBody AcquistoRequest req) {
    var resp = acquistoService.registraAcquisto(req);
    return ResponseEntity.status(HttpStatus.CREATED).body(resp);
  }
}

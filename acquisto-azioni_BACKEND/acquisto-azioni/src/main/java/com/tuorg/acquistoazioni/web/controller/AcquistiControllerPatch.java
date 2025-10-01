package com.tuorg.acquistoazioni.web.controller;

import com.tuorg.acquistoazioni.service.AcquistoService;
import com.tuorg.acquistoazioni.service.AcquistoServicePreview;
import com.tuorg.acquistoazioni.web.dto.AcquistoRequest;
import com.tuorg.acquistoazioni.web.dto.AcquistoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/acquisti")
@RequiredArgsConstructor
public class AcquistiControllerPatch {

  private final AcquistoService acquistoService;
  private final AcquistoServicePreview previewService;

  @PostMapping("/preview")
  public ResponseEntity<AcquistoResponse> preview(@Valid @RequestBody AcquistoRequest req) {
    var resp = previewService.calcolaPreview(req);
    return ResponseEntity.status(HttpStatus.OK).body(resp);
  }
}

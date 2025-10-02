package com.tuorg.acquistoazioni.web.controller;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Profile("dev") // sólo disponible con perfil dev
public class AdminController {

    private final Flyway flyway;

    @PostMapping("/reset")
    public ResponseEntity<Void> reset() {
        flyway.clean();   // ¡Borra el esquema!
        flyway.migrate(); // Reconstruye desde V1__... V2__... etc.
        return ResponseEntity.noContent().build();
    }
}

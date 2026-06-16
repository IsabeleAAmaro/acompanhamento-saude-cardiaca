package com.saudecardiaca.backend.controllers;

import com.saudecardiaca.backend.dtos.RegistroSaudeRequest;
import com.saudecardiaca.backend.dtos.RelatorioSaudeResponse;
import com.saudecardiaca.backend.models.RegistroSaude;
import com.saudecardiaca.backend.services.RegistroSaudeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/saude")
public class RegistroSaudeController {

    private final RegistroSaudeService registroSaudeService;

    public RegistroSaudeController(RegistroSaudeService registroSaudeService) {
        this.registroSaudeService = registroSaudeService;
    }

    @PostMapping("/registros")
    public ResponseEntity<RegistroSaude> registrarMetricas(@Valid @RequestBody RegistroSaudeRequest request) {
        RegistroSaude novoRegistro = registroSaudeService.registrarMetricas(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoRegistro);
    }

    @GetMapping("/relatorios/{usuarioId}")
    public ResponseEntity<RelatorioSaudeResponse> obterRelatorio(@PathVariable Long usuarioId) {
        try {
            RelatorioSaudeResponse relatorio = registroSaudeService.gerarRelatorio(usuarioId);
        return ResponseEntity.ok(relatorio);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
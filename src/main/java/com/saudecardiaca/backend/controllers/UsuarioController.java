package com.saudecardiaca.backend.controllers;

import com.saudecardiaca.backend.dtos.UsuarioRequest;
import com.saudecardiaca.backend.dtos.UsuarioResponse;
import com.saudecardiaca.backend.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse response = usuarioService.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
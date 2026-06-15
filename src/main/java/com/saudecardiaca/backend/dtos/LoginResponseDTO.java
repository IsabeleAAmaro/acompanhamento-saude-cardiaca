package com.saudecardiaca.backend.dtos;

public record LoginResponseDTO(
    String mensagem,
    String nomeUsuario
) {}
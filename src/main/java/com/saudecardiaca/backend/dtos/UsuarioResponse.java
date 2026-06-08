package com.saudecardiaca.backend.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioResponse {

    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private String sexo;
    private String paisResidencia;
}
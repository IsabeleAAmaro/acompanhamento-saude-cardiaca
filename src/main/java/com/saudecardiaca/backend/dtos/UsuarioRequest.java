package com.saudecardiaca.backend.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioRequest {

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotBlank(message = "O sobrenome é obrigatório")
    private String sobrenome;

    @NotBlank(message = "O e-mail é obrigatório")
    @Email(message = "Formato de e-mail inválido")
    private String email;

    private String telefone;

    @NotBlank(message = "A senha é obrigatória")
    private String senha;

    @NotBlank(message = "A confirmação de senha é obrigatória")
    private String confirmarSenha;

    @NotNull(message = "A data de nascimento é obrigatória")
    private LocalDate dataNascimento;

    private String sexo;

    private String paisResidencia;
}
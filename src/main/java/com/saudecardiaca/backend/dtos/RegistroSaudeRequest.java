package com.saudecardiaca.backend.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegistroSaudeRequest {

    @NotNull(message = "O ID do usuário é obrigatório")
    private Long usuarioId;

    private String pressaoArterial;
    private Integer frequenciaCardiaca;
    private Double oxigenacaoSangue;
    private Double pesoCorporal;
    private String sintomas;
}
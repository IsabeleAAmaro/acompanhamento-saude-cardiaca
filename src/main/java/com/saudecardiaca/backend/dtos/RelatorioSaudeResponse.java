package com.saudecardiaca.backend.dtos;

import com.saudecardiaca.backend.models.RegistroSaude;
import lombok.Data;

import java.util.List;

@Data
public class RelatorioSaudeResponse {

    private Long usuarioId;
    private int totalRegistros;
    private Double mediaFrequenciaCardiaca;
    private Double mediaOxigenacao;
    private List<String> alertas;
    private List<RegistroSaude> historicoDetalhado;
}
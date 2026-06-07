package com.saudecardiaca.backend.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "registros_saude")
public class RegistroSaude {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime dataRegistro;

    private String pressaoArterial;

    private Integer frequenciaCardiaca;

    private Double oxigenacaoSangue;

    private Double pesoCorporal;
    private String sintomas;
}
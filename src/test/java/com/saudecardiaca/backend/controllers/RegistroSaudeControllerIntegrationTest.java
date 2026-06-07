package com.saudecardiaca.backend.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class RegistroSaudeControllerIntegrationTest {

    @Autowired
    private WebApplicationContext contextoDaAplicacao;

    private MockMvc mockMvc;

    @BeforeEach
    void configurarMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.contextoDaAplicacao).build();
    }

    @Test
    void deveFazerOFluxoCompletoDeSalvarMetricas() throws Exception {

        String jsonRequest = """
                {
                  "usuarioId": 1,
                  "pressaoArterial": "120/80",
                  "frequenciaCardiaca": 75,
                  "oxigenacaoSangue": 98.5,
                  "pesoCorporal": 70.2,
                  "sintomas": "Nenhum"
                }
                """;

        mockMvc.perform(post("/api/saude/registros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.frequenciaCardiaca").value(75));
    }
}
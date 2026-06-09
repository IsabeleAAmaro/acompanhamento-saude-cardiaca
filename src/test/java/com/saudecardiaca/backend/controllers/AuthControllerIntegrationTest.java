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
class AuthControllerIntegrationTest {

    @Autowired
    private WebApplicationContext contextoDaAplicacao;

    private MockMvc mockMvc;

    @BeforeEach
    void configurarMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.contextoDaAplicacao).build();
    }

    @Test
    void deveRealizarLoginComSucesso() throws Exception {
        // Usuário inserido pelo TesteDataLoader
        String jsonRequest = """
                {
                  "email": "paciente@teste.com",
                  "senha": "senha123"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Login realizado com sucesso!"))
                .andExpect(jsonPath("$.nomeUsuario").value("Paciente"));
    }

    @Test
    void deveRetornarNaoAutorizadoParaCredenciaisInvalidas() throws Exception {
        String jsonRequest = """
                {
                  "email": "paciente@teste.com",
                  "senha": "senhaErrada"
                }
                """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isUnauthorized());
    }
}
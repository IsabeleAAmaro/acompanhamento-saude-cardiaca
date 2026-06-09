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
class UsuarioControllerIntegrationTest {

    @Autowired
    private WebApplicationContext contextoDaAplicacao;

    private MockMvc mockMvc;

    @BeforeEach
    void configurarMockMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.contextoDaAplicacao).build();
    }

    // ---------------------------------------------------------------
    // Cenário 1: registro com dados válidos (201 Created)
    // ---------------------------------------------------------------
    @Test
    void deveRegistrarUsuarioComSucesso() throws Exception {
        // Usamos timestamp no e-mail para evitar conflito com o TesteDataLoader
        String emailUnico = "teste_" + System.currentTimeMillis() + "@email.com";

        String jsonRequest = """
                {
                  "nome": "Carlos",
                  "sobrenome": "Cardoso",
                  "email": "%s",
                  "telefone": "21988880000",
                  "senha": "senha123",
                  "confirmarSenha": "senha123",
                  "dataNascimento": "1988-07-15",
                  "sexo": "Masculino",
                  "paisResidencia": "Brasil"
                }
                """.formatted(emailUnico);

        mockMvc.perform(post("/api/usuarios/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.email").value(emailUnico))
                .andExpect(jsonPath("$.nome").value("Carlos"));
    }

    // ---------------------------------------------------------------
    // Cenário 2: campo obrigatório ausente (400 Bad Request)
    // ---------------------------------------------------------------
    @Test
    void deveRetornar400QuandoNomeEstiverAusente() throws Exception {
        String jsonRequest = """
                {
                  "sobrenome": "Cardoso",
                  "email": "semname@email.com",
                  "senha": "senha123",
                  "confirmarSenha": "senha123",
                  "dataNascimento": "1988-07-15"
                }
                """;

        mockMvc.perform(post("/api/usuarios/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    // ---------------------------------------------------------------
    // Cenário 3: e-mail inválido (400 Bad Request)
    // ---------------------------------------------------------------
    @Test
    void deveRetornar400QuandoEmailForInvalido() throws Exception {
        String jsonRequest = """
                {
                  "nome": "Ana",
                  "sobrenome": "Silva",
                  "email": "emailsemarroba",
                  "senha": "senha123",
                  "confirmarSenha": "senha123",
                  "dataNascimento": "1990-01-01"
                }
                """;

        mockMvc.perform(post("/api/usuarios/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }
}
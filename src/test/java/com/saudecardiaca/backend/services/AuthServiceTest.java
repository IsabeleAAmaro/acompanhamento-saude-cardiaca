package com.saudecardiaca.backend.services;

import com.saudecardiaca.backend.dtos.LoginRequestDTO;
import com.saudecardiaca.backend.dtos.LoginResponseDTO;
import com.saudecardiaca.backend.models.Usuario;
import com.saudecardiaca.backend.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    void deveAutenticarComSucesso() {
        LoginRequestDTO request = new LoginRequestDTO("teste@teste.com", "senha123");
        Usuario usuarioMock = new Usuario();
        usuarioMock.setEmail("teste@teste.com");
        usuarioMock.setSenha("senha123");
        usuarioMock.setNome("Teste");

        when(usuarioRepository.findByEmail("teste@teste.com")).thenReturn(Optional.of(usuarioMock));

        LoginResponseDTO response = authService.autenticar(request);

        assertNotNull(response);
        assertEquals("Login realizado com sucesso!", response.mensagem());
        assertEquals("Teste", response.nomeUsuario());
    }

    @Test
    void deveLancarExcecaoQuandoSenhaIncorreta() {
        LoginRequestDTO request = new LoginRequestDTO("teste@teste.com", "senhaErrada");
        Usuario usuarioMock = new Usuario();
        usuarioMock.setEmail("teste@teste.com");
        usuarioMock.setSenha("senha123");

        when(usuarioRepository.findByEmail("teste@teste.com")).thenReturn(Optional.of(usuarioMock));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.autenticar(request);
        });
        assertEquals("E-mail ou senha inválidos", exception.getMessage());
    }
}
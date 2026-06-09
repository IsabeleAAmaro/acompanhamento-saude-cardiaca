package com.saudecardiaca.backend.services;

import com.saudecardiaca.backend.dtos.UsuarioRequest;
import com.saudecardiaca.backend.dtos.UsuarioResponse;
import com.saudecardiaca.backend.models.Usuario;
import com.saudecardiaca.backend.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    // ---------------------------------------------------------------
    // Cenário 1: registro bem sucedido
    // ---------------------------------------------------------------
    @Test
    void deveRegistrarUsuarioComSucesso() {
        UsuarioRequest request = criarRequestValido();

        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(false);

        Usuario usuarioSalvo = new Usuario();
        usuarioSalvo.setId(1L);
        usuarioSalvo.setNome(request.getNome());
        usuarioSalvo.setSobrenome(request.getSobrenome());
        usuarioSalvo.setEmail(request.getEmail());
        usuarioSalvo.setTelefone(request.getTelefone());
        usuarioSalvo.setSenha(request.getSenha());
        usuarioSalvo.setDataNascimento(request.getDataNascimento());
        usuarioSalvo.setSexo(request.getSexo());
        usuarioSalvo.setPaisResidencia(request.getPaisResidencia());

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuarioSalvo);

        UsuarioResponse response = usuarioService.registrar(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Maria", response.getNome());
        assertEquals("silva@email.com", response.getEmail());

        verify(usuarioRepository, times(1)).existsByEmail("silva@email.com");
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    // ---------------------------------------------------------------
    // Cenário 2: senhas não coincidem (deve lançar exceção)
    // ---------------------------------------------------------------
    @Test
    void deveLancarExcecaoQuandoSenhasNaoCoincidem() {
        UsuarioRequest request = criarRequestValido();
        request.setConfirmarSenha("senhaErrada");

        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.registrar(request)
        );

        assertEquals("As senhas não coincidem.", excecao.getMessage());

        // Garante que o banco não foi consultado nem gravado
        verify(usuarioRepository, never()).existsByEmail(any());
        verify(usuarioRepository, never()).save(any());
    }

    // ---------------------------------------------------------------
    // Cenário 3: e-mail já cadastrado (deve lançar exceção)
    // ---------------------------------------------------------------
    @Test
    void deveLancarExcecaoQuandoEmailJaCadastrado() {
        UsuarioRequest request = criarRequestValido();

        when(usuarioRepository.existsByEmail(request.getEmail())).thenReturn(true);

        IllegalArgumentException excecao = assertThrows(
                IllegalArgumentException.class,
                () -> usuarioService.registrar(request)
        );

        assertEquals("Já existe uma conta com este e-mail.", excecao.getMessage());

        verify(usuarioRepository, times(1)).existsByEmail("silva@email.com");
        verify(usuarioRepository, never()).save(any());
    }

    // ---------------------------------------------------------------
    // Helper
    // ---------------------------------------------------------------
    private UsuarioRequest criarRequestValido() {
        UsuarioRequest request = new UsuarioRequest();
        request.setNome("Maria");
        request.setSobrenome("Silva");
        request.setEmail("silva@email.com");
        request.setTelefone("21999990000");
        request.setSenha("senha123");
        request.setConfirmarSenha("senha123");
        request.setDataNascimento(LocalDate.of(1995, 3, 10));
        request.setSexo("Feminino");
        request.setPaisResidencia("Brasil");
        return request;
    }
}
package com.saudecardiaca.backend.services;

import com.saudecardiaca.backend.dtos.LoginRequestDTO;
import com.saudecardiaca.backend.dtos.LoginResponseDTO;
import com.saudecardiaca.backend.models.Usuario;
import com.saudecardiaca.backend.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;

    // Construtor para injeção de dependência (dispensa o @Autowired e facilita testes)
    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public LoginResponseDTO autenticar(LoginRequestDTO loginRequest) {
        // 1. Busca o usuário pelo e-mail
        Usuario usuario = usuarioRepository.findByEmail(loginRequest.email())
                .orElseThrow(() -> new RuntimeException("E-mail ou senha inválidos"));

        // 2. Valida a senha (Em produção usaríamos BCrypt, aqui comparamos texto plano para agilizar)
        if (!usuario.getSenha().equals(loginRequest.senha())) {
            throw new RuntimeException("E-mail ou senha inválidos");
        }

        // 3. Retorna o DTO de sucesso se as credenciais estiverem corretas
        return new LoginResponseDTO("Login realizado com sucesso!", usuario.getNome());
    }
}
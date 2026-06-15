package com.saudecardiaca.backend.services;

import com.saudecardiaca.backend.dtos.UsuarioRequest;
import com.saudecardiaca.backend.dtos.UsuarioResponse;
import com.saudecardiaca.backend.models.Usuario;
import com.saudecardiaca.backend.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponse registrar(UsuarioRequest request) {
        if (!request.getSenha().equals(request.getConfirmarSenha())) {
            throw new IllegalArgumentException("As senhas não coincidem.");
        }

        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Já existe uma conta com este e-mail.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(request.getNome());
        usuario.setSobrenome(request.getSobrenome());
        usuario.setEmail(request.getEmail());
        usuario.setTelefone(request.getTelefone());
        usuario.setSenha(request.getSenha());
        usuario.setDataNascimento(request.getDataNascimento());
        usuario.setSexo(request.getSexo());
        usuario.setPaisResidencia(request.getPaisResidencia());

        Usuario salvo = usuarioRepository.save(usuario);

        UsuarioResponse response = new UsuarioResponse();
        response.setId(salvo.getId());
        response.setNome(salvo.getNome());
        response.setSobrenome(salvo.getSobrenome());
        response.setEmail(salvo.getEmail());
        response.setTelefone(salvo.getTelefone());
        response.setDataNascimento(salvo.getDataNascimento());
        response.setSexo(salvo.getSexo());
        response.setPaisResidencia(salvo.getPaisResidencia());

        return response;
    }
}
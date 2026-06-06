package com.saudecardiaca.backend.services;

import com.saudecardiaca.backend.dtos.RegistroSaudeRequest;
import com.saudecardiaca.backend.models.RegistroSaude;
import com.saudecardiaca.backend.models.Usuario;
import com.saudecardiaca.backend.repositories.RegistroSaudeRepository;
import com.saudecardiaca.backend.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegistroSaudeService {

    private final RegistroSaudeRepository registroRepository;
    private final UsuarioRepository usuarioRepository;

    public RegistroSaudeService(RegistroSaudeRepository registroRepository, UsuarioRepository usuarioRepository) {
        this.registroRepository = registroRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public RegistroSaude registrarMetricas(RegistroSaudeRequest request) {
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));

        // 2. Prepara o registro
        RegistroSaude registro = new RegistroSaude();
        registro.setUsuario(usuario);
        registro.setDataRegistro(LocalDateTime.now());
        registro.setPressaoArterial(request.getPressaoArterial());
        registro.setFrequenciaCardiaca(request.getFrequenciaCardiaca());
        registro.setOxigenacaoSangue(request.getOxigenacaoSangue());
        registro.setPesoCorporal(request.getPesoCorporal());
        registro.setSintomas(request.getSintomas());

        // 3. Salva no banco
        return registroRepository.save(registro);
    }
}
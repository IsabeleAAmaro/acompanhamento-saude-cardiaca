package com.saudecardiaca.backend.services;

import com.saudecardiaca.backend.dtos.RegistroSaudeRequest;
import com.saudecardiaca.backend.dtos.RelatorioSaudeResponse;
import com.saudecardiaca.backend.models.RegistroSaude;
import com.saudecardiaca.backend.models.Usuario;
import com.saudecardiaca.backend.repositories.RegistroSaudeRepository;
import com.saudecardiaca.backend.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public RelatorioSaudeResponse gerarRelatorio(Long usuarioId) {
        // 1. Busca todos os registros do usuário, ordenados do mais recente pro mais antigo
        List<RegistroSaude> registros = registroRepository.findByUsuarioIdOrderByDataRegistroDesc(usuarioId);

        if (registros.isEmpty()) {
            throw new IllegalArgumentException("Nenhum registro encontrado para elaborar o relatório.");
        }

        // 2. Calcula as médias usando Java Streams
        Double mediaBpm = registros.stream()
                .filter(r -> r.getFrequenciaCardiaca() != null)
                .mapToInt(RegistroSaude::getFrequenciaCardiaca)
                .average()
                .orElse(0.0);

        Double mediaOxi = registros.stream()
                .filter(r -> r.getOxigenacaoSangue() != null)
                .mapToDouble(RegistroSaude::getOxigenacaoSangue)
                .average()
                .orElse(0.0);

        // 3. Monta o relatório final
        RelatorioSaudeResponse relatorio = new RelatorioSaudeResponse();
        relatorio.setUsuarioId(usuarioId);
        relatorio.setTotalRegistros(registros.size());
        relatorio.setMediaFrequenciaCardiaca(Math.round(mediaBpm * 100.0) / 100.0);
        relatorio.setMediaOxigenacao(Math.round(mediaOxi * 100.0) / 100.0);
        relatorio.setHistoricoDetalhado(registros);

        return relatorio;
    }
}
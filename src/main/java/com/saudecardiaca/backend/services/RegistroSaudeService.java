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

        RegistroSaude registro = new RegistroSaude();
        registro.setUsuario(usuario);
        registro.setDataRegistro(LocalDateTime.now());
        registro.setPressaoArterial(request.getPressaoArterial());
        registro.setFrequenciaCardiaca(request.getFrequenciaCardiaca());
        registro.setOxigenacaoSangue(request.getOxigenacaoSangue());
        registro.setPesoCorporal(request.getPesoCorporal());
        registro.setSintomas(request.getSintomas());

        return registroRepository.save(registro);
    }

    public RelatorioSaudeResponse gerarRelatorio(Long usuarioId) {
        List<RegistroSaude> registros = registroRepository.findByUsuarioIdOrderByDataRegistroDesc(usuarioId);

        if (registros.isEmpty()) {
            throw new IllegalArgumentException("Nenhum registro encontrado para elaborar o relatório.");
        }

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

        java.util.List<String> alertas = new java.util.ArrayList<>();
        RegistroSaude ultimoRegistro = registros.get(0); // Pega a medição mais recente

        if (ultimoRegistro.getFrequenciaCardiaca() != null && ultimoRegistro.getFrequenciaCardiaca() > 100) {
            alertas.add("Atenção: Sua última frequência cardíaca está elevada (> 100 bpm). Considere repousar.");
        }

        if (ultimoRegistro.getOxigenacaoSangue() != null && ultimoRegistro.getOxigenacaoSangue() < 95.0) {
            alertas.add("Alerta: Nível de oxigenação no sangue abaixo do recomendado (< 95%). Procure orientação médica.");
        }

        if (ultimoRegistro.getPressaoArterial() != null && ultimoRegistro.getPressaoArterial().contains("/")) {
            try {
                String[] partesPressao = ultimoRegistro.getPressaoArterial().split("/");
                int pressaoSistolica = Integer.parseInt(partesPressao[0]);

                if (pressaoSistolica >= 140) {
                    alertas.add("Atenção: Sua pressão sistólica está alta (>= 140). Monitore com frequência.");
                }
            } catch (Exception e) {
            }
        }

        RelatorioSaudeResponse relatorio = new RelatorioSaudeResponse();
        relatorio.setUsuarioId(usuarioId);
        relatorio.setTotalRegistros(registros.size());
        relatorio.setMediaFrequenciaCardiaca(Math.round(mediaBpm * 100.0) / 100.0);
        relatorio.setMediaOxigenacao(Math.round(mediaOxi * 100.0) / 100.0);
        relatorio.setAlertas(alertas);
        relatorio.setHistoricoDetalhado(registros);

        return relatorio;
    }
}
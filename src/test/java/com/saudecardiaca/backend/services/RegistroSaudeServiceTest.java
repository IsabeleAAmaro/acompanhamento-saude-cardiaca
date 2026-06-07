package com.saudecardiaca.backend.services;

import com.saudecardiaca.backend.dtos.RegistroSaudeRequest;
import com.saudecardiaca.backend.models.RegistroSaude;
import com.saudecardiaca.backend.models.Usuario;
import com.saudecardiaca.backend.repositories.RegistroSaudeRepository;
import com.saudecardiaca.backend.repositories.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegistroSaudeServiceTest {

    @Mock
    private RegistroSaudeRepository registroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private RegistroSaudeService registroSaudeService;

    @Test
    void deveRegistrarMetricasComSucesso() {
        RegistroSaudeRequest request = new RegistroSaudeRequest();
        request.setUsuarioId(1L);
        request.setPressaoArterial("120/80");

        Usuario usuarioMock = new Usuario();
        usuarioMock.setId(1L);

        RegistroSaude registroSalvoMock = new RegistroSaude();
        registroSalvoMock.setId(10L);
        registroSalvoMock.setPressaoArterial("120/80");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
        when(registroRepository.save(any(RegistroSaude.class))).thenReturn(registroSalvoMock);

        RegistroSaude resultado = registroSaudeService.registrarMetricas(request);

        assertNotNull(resultado);
        assertEquals("120/80", resultado.getPressaoArterial());

        verify(usuarioRepository, times(1)).findById(1L);
        verify(registroRepository, times(1)).save(any(RegistroSaude.class));
    }


    @Test
    void deveGerarRelatorioComMediasEAlertas() {
        Long usuarioId = 1L;

        RegistroSaude registroRecente = new RegistroSaude();
        registroRecente.setFrequenciaCardiaca(110);
        registroRecente.setOxigenacaoSangue(93.0);
        registroRecente.setPressaoArterial("145/90");

        RegistroSaude registroAntigo = new RegistroSaude();
        registroAntigo.setFrequenciaCardiaca(70);
        registroAntigo.setOxigenacaoSangue(99.0);
        registroAntigo.setPressaoArterial("120/80");

        when(registroRepository.findByUsuarioIdOrderByDataRegistroDesc(usuarioId))
                .thenReturn(java.util.List.of(registroRecente, registroAntigo));

        var resultado = registroSaudeService.gerarRelatorio(usuarioId);

        assertNotNull(resultado);
        assertEquals(2, resultado.getTotalRegistros());

        assertEquals(90.0, resultado.getMediaFrequenciaCardiaca());

        assertEquals(96.0, resultado.getMediaOxigenacao());

        assertEquals(3, resultado.getAlertas().size());
        assertTrue(resultado.getAlertas().get(0).contains("elevada"));
        assertTrue(resultado.getAlertas().get(1).contains("abaixo do recomendado"));
        assertTrue(resultado.getAlertas().get(2).contains("pressão sistólica está alta"));
    }
}
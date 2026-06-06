package com.saudecardiaca.backend.config;

import com.saudecardiaca.backend.models.Usuario;
import com.saudecardiaca.backend.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class TesteDataLoader {

    @Bean
    CommandLineRunner iniciarBancoDeDados(UsuarioRepository usuarioRepository) {
        return args -> {
            // Só cria o usuário falso se o banco estiver vazio
            if (usuarioRepository.count() == 0) {
                Usuario usuarioFalso = new Usuario();
                usuarioFalso.setNome("Paciente");
                usuarioFalso.setSobrenome("Teste");
                usuarioFalso.setEmail("paciente@teste.com");
                usuarioFalso.setSenha("senha123");
                usuarioFalso.setDataNascimento(LocalDate.of(1990, 5, 20));
                usuarioFalso.setSexo("Masculino");
                usuarioFalso.setPaisResidencia("Brasil");

                usuarioRepository.save(usuarioFalso);
                System.out.println("✅ Usuário falso criado com sucesso! O ID dele é: " + usuarioFalso.getId());
            }
        };
    }
}
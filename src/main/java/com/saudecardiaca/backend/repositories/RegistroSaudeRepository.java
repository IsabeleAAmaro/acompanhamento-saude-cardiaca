package com.saudecardiaca.backend.repositories;

import com.saudecardiaca.backend.models.RegistroSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegistroSaudeRepository extends JpaRepository<RegistroSaude, Long> {
    List<RegistroSaude> findByUsuarioIdOrderByDataRegistroDesc(Long usuarioId);
}
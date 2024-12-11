package com.orcamentos.kaspper.repository;

import com.orcamentos.kaspper.model.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacaoRepository extends JpaRepository<Notificacao, Long> {
    List<Notificacao> findByUsuarioId(Long idUsuario);
}

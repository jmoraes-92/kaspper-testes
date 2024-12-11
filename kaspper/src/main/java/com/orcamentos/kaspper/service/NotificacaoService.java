package com.orcamentos.kaspper.service;

import com.orcamentos.kaspper.model.Notificacao;
import com.orcamentos.kaspper.model.Usuario;
import com.orcamentos.kaspper.repository.NotificacaoRepository;
import com.orcamentos.kaspper.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Notificacao enviarNotificacao(Long idUsuario, String mensagem) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElseThrow(() -> new IllegalArgumentException("Usuário com ID " + idUsuario + " não encontrado."));

        Notificacao notificacao = new Notificacao();
        notificacao.setUsuario(usuario);
        notificacao.setMensagem(mensagem);
        notificacao.setDataEnvio(LocalDateTime.now());
        notificacao.setVisualizada(false);

        return notificacaoRepository.save(notificacao);
    }

    public Notificacao marcarComoVisualizada(Long idNotificacao) {
        Notificacao notificacao = notificacaoRepository.findById(idNotificacao)
            .orElseThrow(() -> new IllegalArgumentException("Notificação não encontrada."));
        notificacao.setVisualizada(true);
        return notificacaoRepository.save(notificacao);
    }

    public List<Notificacao> listarNotificacoesPorUsuario(Long idUsuario) {
        return notificacaoRepository.findByUsuarioId(idUsuario);
    }
}

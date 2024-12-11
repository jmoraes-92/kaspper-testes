package com.orcamentos.kaspper.controller;

import com.orcamentos.kaspper.dto.NotificacaoRequestDTO;
import com.orcamentos.kaspper.model.Notificacao;
import com.orcamentos.kaspper.service.NotificacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @PostMapping
    public ResponseEntity<Notificacao> enviarNotificacao(@RequestBody NotificacaoRequestDTO notificacaoRequestDTO) {
        Notificacao notificacao = notificacaoService.enviarNotificacao(
            notificacaoRequestDTO.getIdUsuario(),
            notificacaoRequestDTO.getMensagem()
        );
        return ResponseEntity.ok(notificacao);
    }

    @PatchMapping("/{id}/visualizar")
    public ResponseEntity<Notificacao> marcarComoVisualizada(@PathVariable Long id) {
        Notificacao notificacao = notificacaoService.marcarComoVisualizada(id);
        return ResponseEntity.ok(notificacao);
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Notificacao>> listarNotificacoesPorUsuario(@PathVariable Long idUsuario) {
        List<Notificacao> notificacoes = notificacaoService.listarNotificacoesPorUsuario(idUsuario);
        return ResponseEntity.ok(notificacoes);
    }
}

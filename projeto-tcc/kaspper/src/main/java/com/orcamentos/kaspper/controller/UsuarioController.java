package com.orcamentos.kaspper.controller;

import com.orcamentos.kaspper.model.Notificacao;
import com.orcamentos.kaspper.model.Usuario;
import com.orcamentos.kaspper.service.NotificacaoService;
import com.orcamentos.kaspper.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuários", description = "Endpoints para gerenciar usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private NotificacaoService notificacaoService;

    @GetMapping
    @Operation(summary = "Listar todos os usuários", description = "Retorna uma lista paginada de usuários.")
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Usuario> salvarUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.salvar(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuario);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirUsuario(@PathVariable Long id) {
        usuarioService.excluirUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idUsuario}/notificar")
    public ResponseEntity<Notificacao> enviarNotificacao(@PathVariable Long idUsuario, @RequestBody String mensagem) {
        Notificacao notificacao = notificacaoService.enviarNotificacao(idUsuario, mensagem);
        return ResponseEntity.ok(notificacao);
    }

    @PutMapping("/notificacoes/{id}/visualizar")
    public ResponseEntity<Notificacao> visualizarNotificacao(@PathVariable Long id) {
        Notificacao notificacao = notificacaoService.marcarComoVisualizada(id);
        return ResponseEntity.ok(notificacao);
    }

    @GetMapping("/{idUsuario}/notificacoes")
    public ResponseEntity<List<Notificacao>> listarNotificacoes(@PathVariable Long idUsuario) {
        List<Notificacao> notificacoes = notificacaoService.listarNotificacoesPorUsuario(idUsuario);
        return ResponseEntity.ok(notificacoes);
    }
}

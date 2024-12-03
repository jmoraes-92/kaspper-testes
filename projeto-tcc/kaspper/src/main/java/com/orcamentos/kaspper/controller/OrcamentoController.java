package com.orcamentos.kaspper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orcamentos.kaspper.dto.OrcamentoRequestDTO;
import com.orcamentos.kaspper.exception.ResourceNotFoundException;
import com.orcamentos.kaspper.model.Demanda;
import com.orcamentos.kaspper.model.Orcamento;
import com.orcamentos.kaspper.service.DemandaService;
import com.orcamentos.kaspper.service.OrcamentoService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.Optional;

@RestController
@RequestMapping("/api/orcamentos")
public class OrcamentoController {

	@Autowired
	private OrcamentoService orcamentoService;
	@Autowired
	private DemandaService demandaService;

	@GetMapping
	public ResponseEntity<List<Orcamento>> listarTodos() {
		return ResponseEntity.ok(orcamentoService.listarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Orcamento> buscarPorId(@PathVariable Long id) {
		return orcamentoService.buscarPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Orcamento> salvar(@RequestBody Long id_demanda) {
		// Buscar a demanda pelo ID
		Demanda demanda = demandaService.buscarPorId(id_demanda)
				.orElseThrow(() -> new ResourceNotFoundException("Demanda com ID " + id_demanda + " não encontrada."));

		// Gerar o orçamento com base na demanda e suas tarefas
		Orcamento novoOrcamento = orcamentoService.gerarOrcamento(demanda);

		return ResponseEntity.ok(novoOrcamento);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		orcamentoService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}

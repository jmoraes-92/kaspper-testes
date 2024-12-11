package com.orcamentos.kaspper.controller;

import com.orcamentos.kaspper.dto.TarefaRequestDTO;
import com.orcamentos.kaspper.model.Demanda;
import com.orcamentos.kaspper.model.Tarefa;
import com.orcamentos.kaspper.model.enums.StatusTarefa;
import com.orcamentos.kaspper.service.DemandaService;
import com.orcamentos.kaspper.service.TarefaService;
import java.util.Optional;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tarefas")
public class TarefaController {

	@Autowired
	private TarefaService tarefaService;

	@Autowired
	private DemandaService demandaService;

	@PostMapping
	public ResponseEntity<Tarefa> salvar(@RequestBody TarefaRequestDTO tarefaRequest) {
		Long idDemanda = tarefaRequest.getid_demanda();
		Optional<Demanda> demandaOptional = demandaService.buscarPorId(idDemanda);
		if (!demandaOptional.isPresent()) {
			throw new IllegalArgumentException("Demanda com ID " + idDemanda + " não encontrada.");
		}

		Demanda demanda = demandaOptional.get();
		Tarefa tarefa = new Tarefa();
		tarefa.setDemanda(demanda);
		tarefa.setDescricao(tarefaRequest.getDescricao());
		tarefa.setResponsavel(tarefaRequest.getResponsavel());
		tarefa.setStatus(StatusTarefa.valueOf(tarefaRequest.getStatus().toUpperCase()));

		return ResponseEntity.ok(tarefaService.salvar(tarefa));
	}

	@GetMapping
	public ResponseEntity<List<Tarefa>> listarTarefas() {
		List<Tarefa> tarefas = tarefaService.listarTodos();
		return ResponseEntity.ok(tarefas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Tarefa> buscarPorId(@PathVariable Long id) {
		Tarefa tarefa = tarefaService.buscarPorId(id)
				.orElseThrow(() -> new IllegalArgumentException("Tarefa com ID " + id + " não encontrada."));
		return ResponseEntity.ok(tarefa);
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<Tarefa> atualizarStatus(@PathVariable Long id, @RequestParam String status) {
		StatusTarefa novoStatus = StatusTarefa.valueOf(status.toUpperCase());
		Tarefa tarefa = tarefaService.atualizarStatus(id, novoStatus)
				.orElseThrow(() -> new IllegalArgumentException("Tarefa com ID " + id + " não encontrada."));
		return ResponseEntity.ok(tarefa);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		tarefaService.deletar(id);
		return ResponseEntity.noContent().build();
	}
}

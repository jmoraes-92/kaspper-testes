package com.orcamentos.kaspper.controller;

import com.orcamentos.kaspper.dto.DemandaRequestDTO;
import com.orcamentos.kaspper.exception.ResourceNotFoundException;
import com.orcamentos.kaspper.model.Demanda;
import com.orcamentos.kaspper.model.Tarefa;
import com.orcamentos.kaspper.model.enums.Prioridade;
import com.orcamentos.kaspper.model.enums.StatusDemanda;
import com.orcamentos.kaspper.service.DemandaService;
import com.orcamentos.kaspper.service.TarefaService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandas")
public class DemandaController {

	@Autowired
	private DemandaService demandaService;

	@Autowired
	private TarefaService tarefaService; // Injeção do serviço de tarefas para buscar tarefas existentes.

	private boolean isValidStatus(String status) {
		try {
			StatusDemanda.valueOf(status.toUpperCase());
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	@PostMapping("/criar-com-tarefas")
	public ResponseEntity<Demanda> criarDemandaComTarefas(@RequestBody DemandaRequestDTO demandaRequestDTO) {
		Demanda demanda = new Demanda();
		demanda.setDescricao(demandaRequestDTO.getDescricao());

		if (!isValidStatus(demandaRequestDTO.getStatus())) {
			throw new IllegalArgumentException("Status inválido: " + demandaRequestDTO.getStatus());
		}

		// Verificar comprimento do status
		if (demandaRequestDTO.getStatus().length() > 20) {
			throw new IllegalArgumentException("Status inválido ou muito longo.");
		}

		// Converter os valores String para os enums correspondentes
		demanda.setPrioridade(Prioridade.valueOf(demandaRequestDTO.getPrioridade().toUpperCase()));
		demanda.setStatus(StatusDemanda.valueOf(demandaRequestDTO.getStatus().toUpperCase()));
		demanda.setCliente(demandaRequestDTO.getCliente()); // Mapeando cliente do DTO para a entidade

		// Buscar tarefas existentes pelos IDs fornecidos
		if (demandaRequestDTO.getIdsTarefasExistentes() != null
				&& !demandaRequestDTO.getIdsTarefasExistentes().isEmpty()) {
			List<Tarefa> tarefasExistentes = tarefaService
					.buscarTarefasPorIds(demandaRequestDTO.getIdsTarefasExistentes());
			demanda.getTarefas().addAll(tarefasExistentes); // Associar tarefas existentes à demanda
		}

		// Criar novas tarefas, se fornecidas
		Demanda novaDemanda = demandaService.criarDemandaComTarefas(demanda, demandaRequestDTO.getTarefas());

		return ResponseEntity.ok(novaDemanda);
	}

	@GetMapping
	public ResponseEntity<List<Demanda>> listarDemandas() {
		List<Demanda> demandas = demandaService.listarTodasDemandas();
		return ResponseEntity.ok(demandas);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Demanda> buscarPorId(@PathVariable Long id) {
		Optional<Demanda> demandaOptional = demandaService.buscarPorId(id);
		if (!demandaOptional.isPresent()) {
			throw new ResourceNotFoundException("Demanda com ID " + id + " não encontrada.");
		}

		Demanda demanda = demandaOptional.get();
		return ResponseEntity.ok(demanda);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Demanda> atualizarDemanda(@PathVariable Long id,
			@RequestBody DemandaRequestDTO demandaRequestDTO) {
		Demanda demanda = demandaService.atualizarDemanda(id, demandaRequestDTO);
		return ResponseEntity.ok(demanda);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarDemanda(@PathVariable Long id) {
		demandaService.deletarDemanda(id);
		return ResponseEntity.noContent().build();
	}
}

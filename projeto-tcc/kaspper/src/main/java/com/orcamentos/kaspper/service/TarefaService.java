package com.orcamentos.kaspper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.orcamentos.kaspper.model.Tarefa;
import com.orcamentos.kaspper.model.enums.StatusTarefa;
import com.orcamentos.kaspper.repository.TarefaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

	@Autowired
	private TarefaRepository tarefaRepository;

	public Tarefa salvar(Tarefa tarefa) {
		if (tarefa.getDemanda() == null) {
			throw new IllegalArgumentException("A demanda é obrigatória para salvar a tarefa.");
		}
		return tarefaRepository.save(tarefa);
	}

	public List<Tarefa> listarTodos() {
		return tarefaRepository.findAll();
	}

	public Optional<Tarefa> buscarPorId(Long id) {
		return tarefaRepository.findById(id).or(() -> {
			throw new IllegalArgumentException("Tarefa com ID " + id + " não encontrada.");
		});
	}
	
	public List<Tarefa> buscarTarefasPorIds(List<Long> ids) {
		return tarefaRepository.findAllById(ids);
	}

	public Optional<Tarefa> atualizarStatus(Long id, StatusTarefa status) {
		return tarefaRepository.findById(id).map(tarefa -> {
			tarefa.setStatus(status);
			return tarefaRepository.save(tarefa);
		}).or(() -> {
			throw new IllegalArgumentException("Tarefa com ID " + id + " não encontrada.");
		});
	}

	public void deletar(Long id) {
		if (!tarefaRepository.existsById(id)) {
			throw new IllegalArgumentException("Tarefa com ID " + id + " não encontrada.");
		}
		tarefaRepository.deleteById(id);
	}
}

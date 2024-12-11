package com.orcamentos.kaspper.service;

import com.orcamentos.kaspper.dto.DemandaRequestDTO;
import com.orcamentos.kaspper.exception.ResourceNotFoundException;
import com.orcamentos.kaspper.model.Cliente;
import com.orcamentos.kaspper.model.Demanda;
import com.orcamentos.kaspper.model.Tarefa;
import com.orcamentos.kaspper.model.enums.Prioridade;
import com.orcamentos.kaspper.model.enums.StatusDemanda;
import com.orcamentos.kaspper.model.enums.StatusTarefa;
import com.orcamentos.kaspper.repository.ClienteRepository;
import com.orcamentos.kaspper.repository.DemandaRepository;
import com.orcamentos.kaspper.repository.TarefaRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemandaService {

	@Autowired
	private DemandaRepository demandaRepository;

	@Autowired
	private TarefaRepository tarefaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	/**
	 * Cria uma nova demanda com tarefas associadas.
	 */
	@Transactional
	public Demanda criarDemandaComTarefas(Demanda demanda, List<Tarefa> tarefasBase) {
		if (demanda.getCliente() == null || demanda.getCliente().getId() == null) {
			throw new IllegalArgumentException("Cliente is required to save a Demanda.");
		}

		Cliente cliente = clienteRepository.findById(demanda.getCliente().getId()).orElseThrow(
				() -> new ResourceNotFoundException("Cliente not found with id: " + demanda.getCliente().getId()));

		demanda.setCliente(cliente);
		Demanda savedDemanda = demandaRepository.save(demanda);

		// Gerar e salvar tarefas associadas à demanda
		for (Tarefa tarefaBase : tarefasBase) {
			Tarefa tarefa = new Tarefa();
			tarefa.setDemanda(savedDemanda);
			tarefa.setDescricao(tarefaBase.getDescricao());
			tarefa.setResponsavel(tarefaBase.getResponsavel());
			tarefa.setStatus(StatusTarefa.PENDENTE);
			tarefaRepository.save(tarefa);
		}

		return savedDemanda;
	}

	/**
	 * Salva uma nova demanda a partir de um DTO de solicitação.
	 *
	 * @param demandaRequestDTO Dados da solicitação.
	 * @return A demanda salva.
	 */
	public Demanda salvar(DemandaRequestDTO demandaRequestDTO) {
		Demanda demanda = new Demanda();
		demanda.setDescricao(demandaRequestDTO.getDescricao());
		demanda.setPrioridade(Prioridade.valueOf(demandaRequestDTO.getPrioridade().toUpperCase()));
		demanda.setStatus(StatusDemanda.valueOf(demandaRequestDTO.getStatus().toUpperCase()));

		List<Tarefa> tarefasBase = demandaRequestDTO.getTarefas();
		return criarDemandaComTarefas(demanda, tarefasBase);
	}

	/**
	 * Lista todas as demandas cadastradas.
	 *
	 * @return Lista de demandas.
	 */
	public List<Demanda> listarTodasDemandas() {
		return demandaRepository.findAll();
	}

	/**
	 * Busca uma demanda pelo ID.
	 *
	 * @param id O ID da demanda.
	 * @return A demanda encontrada.
	 */
	public Optional<Demanda> buscarPorId(Long id) {
		return demandaRepository.findById(id);
	}

	/**
	 * Atualiza uma demanda existente com base no ID e no DTO fornecido.
	 *
	 * @param id                O ID da demanda a ser atualizada.
	 * @param demandaRequestDTO Os novos dados para atualizar a demanda.
	 * @return A demanda atualizada.
	 */
	public Demanda atualizarDemanda(Long id, DemandaRequestDTO demandaRequestDTO) {
		// Buscar a demanda pelo ID
		Optional<Demanda> demandaOptional = buscarPorId(id);

		// Verificar se a demanda existe
		if (!demandaOptional.isPresent()) {
			throw new ResourceNotFoundException("Demanda com ID " + id + " não encontrada.");
		}

		// Obter o valor do Optional
		Demanda demandaExistente = demandaOptional.get();

		// Atualizar os campos da demanda
		demandaExistente.setDescricao(demandaRequestDTO.getDescricao());
		demandaExistente.setPrioridade(Prioridade.valueOf(demandaRequestDTO.getPrioridade().toUpperCase()));
		demandaExistente.setStatus(StatusDemanda.valueOf(demandaRequestDTO.getStatus().toUpperCase()));

		// Salvar e retornar a demanda atualizada
		return demandaRepository.save(demandaExistente);
	}

	/**
	 * Deleta uma demanda pelo ID.
	 *
	 * @param id O ID da demanda a ser deletada.
	 */
	public void deletarDemanda(Long id) {
		Optional<Demanda> demandaOptional = buscarPorId(id);
		if (!demandaOptional.isPresent()) {
			throw new ResourceNotFoundException("Demanda com ID " + id + " não encontrada.");
		}

		Demanda demanda = demandaOptional.get();
		demandaRepository.delete(demanda);
	}

}

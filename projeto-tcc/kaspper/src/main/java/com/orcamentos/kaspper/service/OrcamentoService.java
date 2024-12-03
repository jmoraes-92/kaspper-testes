package com.orcamentos.kaspper.service;

import com.orcamentos.kaspper.exception.ResourceNotFoundException;
import com.orcamentos.kaspper.model.Demanda;
import com.orcamentos.kaspper.model.Orcamento;
import com.orcamentos.kaspper.model.Tarefa;
import com.orcamentos.kaspper.model.enums.Prioridade;
import com.orcamentos.kaspper.repository.OrcamentoRepository;
import com.orcamentos.kaspper.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrcamentoService {

	@Autowired
	private OrcamentoRepository orcamentoRepository;

	@Autowired
	private TarefaRepository tarefaRepository;

	public Orcamento gerarOrcamento(Demanda demanda) {
		// Buscar tarefas relacionadas à demanda
		List<Tarefa> tarefas = tarefaRepository.findByDemanda(demanda);

		// Calcular custo estimado baseado na prioridade
		BigDecimal custoEstimado = calcularCustoEstimado(demanda.getPrioridade(), tarefas.size());

		// Calcular prazo estimado com base na quantidade de tarefas
		int prazoEstimado = calcularPrazoEstimado(tarefas.size());

		// Criar o orçamento
		Orcamento orcamento = new Orcamento();
		orcamento.setDemanda(demanda);
		orcamento.setValor(custoEstimado);
		orcamento.setPrazoEstimado(prazoEstimado);
		orcamento.setDataGeracao(LocalDateTime.now());
		orcamento.setObservacoes("Orçamento gerado automaticamente.");

		// Salvar no banco de dados
		return orcamentoRepository.save(orcamento);
	}

	private BigDecimal calcularCustoEstimado(Prioridade prioridade, int quantidadeTarefas) {
		// Base de custo por tarefa
		BigDecimal baseCusto = BigDecimal.valueOf(100.00); // R$ 100,00 por tarefa

		// Ajustar custo com base na prioridade
		switch (prioridade) {
		case ALTA:
			return baseCusto.multiply(BigDecimal.valueOf(quantidadeTarefas)).multiply(BigDecimal.valueOf(1.5));
		case MEDIA:
			return baseCusto.multiply(BigDecimal.valueOf(quantidadeTarefas)).multiply(BigDecimal.valueOf(1.2));
		case BAIXA:
			return baseCusto.multiply(BigDecimal.valueOf(quantidadeTarefas));
		default:
			throw new IllegalArgumentException("Prioridade inválida.");
		}
	}

	private int calcularPrazoEstimado(int quantidadeTarefas) {
		// Prazo estimado é de 2 dias por tarefa
		return quantidadeTarefas * 2;
	}

	public Orcamento salvar(Orcamento orcamento) {
		return orcamentoRepository.save(orcamento);
	}

	public Optional<Orcamento> buscarPorId(Long id) {
		return orcamentoRepository.findById(id);
	}

	public List<Orcamento> listarTodos() {
		return orcamentoRepository.findAll();
	}

	public void excluir(Long id) {
		if (!orcamentoRepository.existsById(id)) {
			throw new ResourceNotFoundException("Orçamento com ID " + id + " não encontrado.");
		}
		orcamentoRepository.deleteById(id);
	}
}

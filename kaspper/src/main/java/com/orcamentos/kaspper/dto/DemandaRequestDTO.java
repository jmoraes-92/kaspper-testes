package com.orcamentos.kaspper.dto;

import java.util.List;

import com.orcamentos.kaspper.model.Cliente;
import com.orcamentos.kaspper.model.Tarefa;

public class DemandaRequestDTO {
    private String descricao; // Descrição da demanda
    private String prioridade; // Prioridade da demanda (ALTA, MEDIA, BAIXA)
    private String status; // Status da demanda (ABERTA, EM_PROGRESSO, FECHADA)
    private Cliente cliente; // Cliente associado à demanda
    private Long idCliente; // ID do cliente associado (opcional)
    private List<Tarefa> tarefas; // Novas tarefas associadas à demanda
    private List<Long> idsTarefasExistentes; // IDs das tarefas já existentes a serem associadas


    // Getters e Setters

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    public List<Long> getIdsTarefasExistentes() {
        return idsTarefasExistentes;
    }

    public void setIdsTarefasExistentes(List<Long> idsTarefasExistentes) {
        this.idsTarefasExistentes = idsTarefasExistentes;
    }
}

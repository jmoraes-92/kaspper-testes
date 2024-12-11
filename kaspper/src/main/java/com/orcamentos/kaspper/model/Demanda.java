package com.orcamentos.kaspper.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.orcamentos.kaspper.model.enums.Prioridade;
import com.orcamentos.kaspper.model.enums.StatusDemanda;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "demandas")
public class Demanda {
    
    @ManyToOne
    private Orcamento orcamento;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demanda", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridade", nullable = false)
    private Prioridade prioridade;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusDemanda status;

    @Column(name = "data_criacao", nullable = false)
    private LocalDateTime dataCriacao;
    
	@JsonManagedReference // Indica que esta é a referência "gerenciada"
    @OneToMany(mappedBy = "demanda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarefa> tarefas = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.dataCriacao = LocalDateTime.now();
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public StatusDemanda getStatus() {
        return status;
    }

    public void setStatus(StatusDemanda status) {
        this.status = status;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }

    // Métodos utilitários para manipular o relacionamento

    public void addTarefa(Tarefa tarefa) {
        this.tarefas.add(tarefa);
        tarefa.setDemanda(this); // Define a demanda na tarefa para manter consistência
    }

    public void removeTarefa(Tarefa tarefa) {
        this.tarefas.remove(tarefa);
        tarefa.setDemanda(null); // Remove a associação de demanda na tarefa
    }

    /**
     * Método `orElseThrow` funcional para validação em Optional.
     */
    public static Demanda orElseThrow(Demanda demanda, RuntimeException exception) {
        if (demanda == null) {
            throw exception;
        }
        return demanda;
    }
}

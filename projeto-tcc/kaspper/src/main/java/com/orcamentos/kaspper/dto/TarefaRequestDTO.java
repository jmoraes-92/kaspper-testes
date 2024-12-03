package com.orcamentos.kaspper.dto;

public class TarefaRequestDTO {
    private String descricao;
    private String responsavel;
    private String status;
    private Long id_demanda;

    // Getters e Setters
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getid_demanda() {
        return id_demanda;
    }

    public void setIdDemanda(Long id_demanda) {
        this.id_demanda = id_demanda;
    }
}

package com.orcamentos.kaspper.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrcamentoRequestDTO {

	@JsonProperty("id_demanda")
	private Long id_demanda;

	public Long getIdDemanda() {
		return id_demanda;
	}

	public void setIdDemanda(Long id_demanda) {
		this.id_demanda = id_demanda;
	}

	@Override
	public String toString() {
		return "OrcamentoRequestDTO{" + "id_demanda=" + id_demanda + '}';
	}
}

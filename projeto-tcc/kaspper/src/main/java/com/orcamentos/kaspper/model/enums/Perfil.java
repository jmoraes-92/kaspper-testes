package com.orcamentos.kaspper.model.enums;

public enum Perfil {
	ADMIN, GESTOR;

	public String getDescricao() {
		switch (this) {
		case ADMIN:
			return "Administrador";
		case GESTOR:
			return "Gestor";
		default:
			return "Desconhecido";
		}
	}

}

package com.orcamentos.kaspper.model.enums;

public enum StatusTarefa {
    PENDENTE,
    EM_ANDAMENTO,
    FINALIZADA;

    public static StatusTarefa fromString(String value) {
        for (StatusTarefa status : values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + value);
    }
}

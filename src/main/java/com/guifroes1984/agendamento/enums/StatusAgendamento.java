package com.guifroes1984.agendamento.enums;

public enum StatusAgendamento {
	
    AGENDADO("AGENDADO", "Agendado"),
    CONFIRMADO("CONFIRMADO", "Confirmado"),
    REALIZADO("REALIZADO", "Realizado"),
    CANCELADO("CANCELADO", "Cancelado"),
    FALTOU("FALTOU", "Faltou");

    private final String codigo;
    private final String descricao;

    StatusAgendamento(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusAgendamento fromCodigo(String codigo) {
        for (StatusAgendamento status : StatusAgendamento.values()) {
            if (status.getCodigo().equalsIgnoreCase(codigo)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de status inválido: " + codigo);
    }

    public static boolean isValid(String codigo) {
        for (StatusAgendamento status : StatusAgendamento.values()) {
            if (status.getCodigo().equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }
}

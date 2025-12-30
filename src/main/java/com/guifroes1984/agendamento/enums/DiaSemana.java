package com.guifroes1984.agendamento.enums;

public enum DiaSemana {
    SEGUNDA_FEIRA(1, "Segunda-feira"),
    TERCA_FEIRA(2, "Terça-feira"),
    QUARTA_FEIRA(3, "Quarta-feira"),
    QUINTA_FEIRA(4, "Quinta-feira"),
    SEXTA_FEIRA(5, "Sexta-feira"),
    SABADO(6, "Sábado"),
    DOMINGO(7, "Domingo");

    private final int codigo;
    private final String descricao;

    DiaSemana(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

}

package com.guifroes1984.agendamento.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guifroes1984.agendamento.model.Profissional;

public class EstatisticasProfissionalResponseDTO {
    
    private Profissional profissional;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;
    
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFim;
    
    private Long totalAtendimentos;
    private Long atendimentosConfirmados;
    private Long atendimentosRealizados;
    private Long atendimentosCancelados;
    private BigDecimal faturamentoTotal;
    private BigDecimal faturamentoRealizado;
    private Double taxaOcupacao;

    public EstatisticasProfissionalResponseDTO() {
    }

    public EstatisticasProfissionalResponseDTO(Profissional profissional, LocalDate dataInicio, LocalDate dataFim,
                                              Long totalAtendimentos, Long atendimentosConfirmados,
                                              Long atendimentosRealizados, Long atendimentosCancelados,
                                              BigDecimal faturamentoTotal, BigDecimal faturamentoRealizado) {
        this.profissional = profissional;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.totalAtendimentos = totalAtendimentos;
        this.atendimentosConfirmados = atendimentosConfirmados;
        this.atendimentosRealizados = atendimentosRealizados;
        this.atendimentosCancelados = atendimentosCancelados;
        this.faturamentoTotal = faturamentoTotal;
        this.faturamentoRealizado = faturamentoRealizado;
        this.taxaOcupacao = calcularTaxaOcupacao();
    }

    public Profissional getProfissional() {
        return profissional;
    }

    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public Long getTotalAtendimentos() {
        return totalAtendimentos;
    }

    public void setTotalAtendimentos(Long totalAtendimentos) {
        this.totalAtendimentos = totalAtendimentos;
        this.taxaOcupacao = calcularTaxaOcupacao();
    }

    public Long getAtendimentosConfirmados() {
        return atendimentosConfirmados;
    }

    public void setAtendimentosConfirmados(Long atendimentosConfirmados) {
        this.atendimentosConfirmados = atendimentosConfirmados;
    }

    public Long getAtendimentosRealizados() {
        return atendimentosRealizados;
    }

    public void setAtendimentosRealizados(Long atendimentosRealizados) {
        this.atendimentosRealizados = atendimentosRealizados;
    }

    public Long getAtendimentosCancelados() {
        return atendimentosCancelados;
    }

    public void setAtendimentosCancelados(Long atendimentosCancelados) {
        this.atendimentosCancelados = atendimentosCancelados;
    }

    public BigDecimal getFaturamentoTotal() {
        return faturamentoTotal;
    }

    public void setFaturamentoTotal(BigDecimal faturamentoTotal) {
        this.faturamentoTotal = faturamentoTotal;
    }

    public BigDecimal getFaturamentoRealizado() {
        return faturamentoRealizado;
    }

    public void setFaturamentoRealizado(BigDecimal faturamentoRealizado) {
        this.faturamentoRealizado = faturamentoRealizado;
    }

    public Double getTaxaOcupacao() {
        return taxaOcupacao;
    }

    public void setTaxaOcupacao(Double taxaOcupacao) {
        this.taxaOcupacao = taxaOcupacao;
    }

    private Double calcularTaxaOcupacao() {
        if (totalAtendimentos == null || totalAtendimentos == 0) {
            return 0.0;
        }
        
        long diasUteis = calcularDiasUteis(dataInicio, dataFim);
        if (diasUteis == 0) {
            return 0.0;
        }

        double horasDisponiveis = diasUteis * 8.0;
        double horasAgendadas = totalAtendimentos.doubleValue();
        
        return Math.min(100.0, (horasAgendadas / horasDisponiveis) * 100.0);
    }
    
    private long calcularDiasUteis(LocalDate inicio, LocalDate fim) {
        long dias = 0;
        LocalDate data = inicio;
        
        while (!data.isAfter(fim)) {
            int diaSemana = data.getDayOfWeek().getValue();

            if (diaSemana >= 1 && diaSemana <= 5) {
                dias++;
            }
            data = data.plusDays(1);
        }
        
        return dias;
    }
}

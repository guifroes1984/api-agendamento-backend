package com.guifroes1984.agendamento.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RelatorioFinanceiroResponseDTO {

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataInicio;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFim;

	private BigDecimal valorTotalAgendado;
	private BigDecimal valorTotalRealizado;
	private BigDecimal valorTotalCancelado;
	private Long quantidadeServicos;

	public RelatorioFinanceiroResponseDTO() {
	}

	public RelatorioFinanceiroResponseDTO(LocalDate dataInicio, LocalDate dataFim, BigDecimal valorTotalAgendado,
			BigDecimal valorTotalRealizado, BigDecimal valorTotalCancelado, Long quantidadeServicos) {
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.valorTotalAgendado = valorTotalAgendado;
		this.valorTotalRealizado = valorTotalRealizado;
		this.valorTotalCancelado = valorTotalCancelado;
		this.quantidadeServicos = quantidadeServicos;
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

	public BigDecimal getValorTotalAgendado() {
		return valorTotalAgendado;
	}

	public void setValorTotalAgendado(BigDecimal valorTotalAgendado) {
		this.valorTotalAgendado = valorTotalAgendado;
	}

	public BigDecimal getValorTotalRealizado() {
		return valorTotalRealizado;
	}

	public void setValorTotalRealizado(BigDecimal valorTotalRealizado) {
		this.valorTotalRealizado = valorTotalRealizado;
	}

	public BigDecimal getValorTotalCancelado() {
		return valorTotalCancelado;
	}

	public void setValorTotalCancelado(BigDecimal valorTotalCancelado) {
		this.valorTotalCancelado = valorTotalCancelado;
	}

	public Long getQuantidadeServicos() {
		return quantidadeServicos;
	}

	public void setQuantidadeServicos(Long quantidadeServicos) {
		this.quantidadeServicos = quantidadeServicos;
	}

}

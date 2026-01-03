package com.guifroes1984.agendamento.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class RelatorioAgendamentosResponseDTO {

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataInicio;

	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataFim;

	private Long totalAgendamentos;
	private Long agendamentosConfirmados;
	private Long agendamentosRealizados;
	private Long agendamentosCancelados;
	private Long clientesFaltaram;

	public RelatorioAgendamentosResponseDTO() {
	}

	public RelatorioAgendamentosResponseDTO(LocalDate dataInicio, LocalDate dataFim, Long totalAgendamentos,
			Long agendamentosConfirmados, Long agendamentosRealizados, Long agendamentosCancelados,
			Long clientesFaltaram) {
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.totalAgendamentos = totalAgendamentos;
		this.agendamentosConfirmados = agendamentosConfirmados;
		this.agendamentosRealizados = agendamentosRealizados;
		this.agendamentosCancelados = agendamentosCancelados;
		this.clientesFaltaram = clientesFaltaram;
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

	public Long getTotalAgendamentos() {
		return totalAgendamentos;
	}

	public void setTotalAgendamentos(Long totalAgendamentos) {
		this.totalAgendamentos = totalAgendamentos;
	}

	public Long getAgendamentosConfirmados() {
		return agendamentosConfirmados;
	}

	public void setAgendamentosConfirmados(Long agendamentosConfirmados) {
		this.agendamentosConfirmados = agendamentosConfirmados;
	}

	public Long getAgendamentosRealizados() {
		return agendamentosRealizados;
	}

	public void setAgendamentosRealizados(Long agendamentosRealizados) {
		this.agendamentosRealizados = agendamentosRealizados;
	}

	public Long getAgendamentosCancelados() {
		return agendamentosCancelados;
	}

	public void setAgendamentosCancelados(Long agendamentosCancelados) {
		this.agendamentosCancelados = agendamentosCancelados;
	}

	public Long getClientesFaltaram() {
		return clientesFaltaram;
	}

	public void setClientesFaltaram(Long clientesFaltaram) {
		this.clientesFaltaram = clientesFaltaram;
	}

}

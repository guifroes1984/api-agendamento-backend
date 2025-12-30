package com.guifroes1984.agendamento.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AgendamentoRequestDTO {

	@NotNull(message = "Cliente é obrigatório")
	private Long clienteId;

	@NotNull(message = "Profissional é obrigatório")
	private Long profissionalId;

	@NotNull(message = "Serviço é obrigatório")
	private Long servicoId;

	@NotNull(message = "Data e hora de início é obrigatória")
	@Future(message = "Data e hora de início deve ser futura")
	private LocalDateTime dataHoraInicio;

	@Size(max = 1000, message = "Observações não pode exceder 1000 caracteres")
	private String observacoes;

	public AgendamentoRequestDTO() {
	}

	public AgendamentoRequestDTO(Long clienteId, Long profissionalId, Long servicoId, LocalDateTime dataHoraInicio,
			String observacoes) {
		this.clienteId = clienteId;
		this.profissionalId = profissionalId;
		this.servicoId = servicoId;
		this.dataHoraInicio = dataHoraInicio;
		this.observacoes = observacoes;
	}

	public Long getClienteId() {
		return clienteId;
	}

	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}

	public Long getProfissionalId() {
		return profissionalId;
	}

	public void setProfissionalId(Long profissionalId) {
		this.profissionalId = profissionalId;
	}

	public Long getServicoId() {
		return servicoId;
	}

	public void setServicoId(Long servicoId) {
		this.servicoId = servicoId;
	}

	public LocalDateTime getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

}

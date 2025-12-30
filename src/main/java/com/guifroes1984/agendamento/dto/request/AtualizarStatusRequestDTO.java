package com.guifroes1984.agendamento.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AtualizarStatusRequestDTO {

	@NotBlank(message = "Status é obrigatório")
	private String status;

	@Size(max = 500, message = "Motivo não pode exceder 500 caracteres")
	private String motivo;

	public AtualizarStatusRequestDTO() {
	}

	public AtualizarStatusRequestDTO(String status, String motivo) {
		this.status = status;
		this.motivo = motivo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

}

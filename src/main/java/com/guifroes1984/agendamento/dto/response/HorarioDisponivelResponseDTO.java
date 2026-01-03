package com.guifroes1984.agendamento.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class HorarioDisponivelResponseDTO {

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataHoraInicio;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataHoraFim;

	private Integer duracaoMinutos;
	private BigDecimal preco;
	private Long profissionalId;
	private String profissionalNome;

	public HorarioDisponivelResponseDTO() {
	}

	public HorarioDisponivelResponseDTO(LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, Integer duracaoMinutos,
			BigDecimal preco) {
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraFim = dataHoraFim;
		this.duracaoMinutos = duracaoMinutos;
		this.preco = preco;
	}

	public LocalDateTime getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
	}

	public LocalDateTime getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(LocalDateTime dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
	}

	public Integer getDuracaoMinutos() {
		return duracaoMinutos;
	}

	public void setDuracaoMinutos(Integer duracaoMinutos) {
		this.duracaoMinutos = duracaoMinutos;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Long getProfissionalId() {
		return profissionalId;
	}

	public void setProfissionalId(Long profissionalId) {
		this.profissionalId = profissionalId;
	}

	public String getProfissionalNome() {
		return profissionalNome;
	}

	public void setProfissionalNome(String profissionalNome) {
		this.profissionalNome = profissionalNome;
	}

}

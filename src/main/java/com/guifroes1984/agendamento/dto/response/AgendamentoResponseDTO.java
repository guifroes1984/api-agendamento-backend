package com.guifroes1984.agendamento.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guifroes1984.agendamento.enums.StatusAgendamento;

public class AgendamentoResponseDTO {

	private Long id;
	private ClienteSimplesResponseDTO cliente;
	private ProfissionalSimplesResponseDTO profissional;
	private ServicoSimplesResponseDTO servico;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataHoraInicio;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataHoraFim;

	private StatusAgendamento status;
	private BigDecimal valorCobrado;
	private String observacoes;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataCancelamento;

	private String motivoCancelamento;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataCriacao;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataAtualizacao;

	public AgendamentoResponseDTO() {
	}

	public AgendamentoResponseDTO(Long id, ClienteSimplesResponseDTO cliente,
			ProfissionalSimplesResponseDTO profissional, ServicoSimplesResponseDTO servico,
			LocalDateTime dataHoraInicio, LocalDateTime dataHoraFim, StatusAgendamento status, BigDecimal valorCobrado,
			String observacoes, LocalDateTime dataCancelamento, String motivoCancelamento, LocalDateTime dataCriacao,
			LocalDateTime dataAtualizacao) {
		this.id = id;
		this.cliente = cliente;
		this.profissional = profissional;
		this.servico = servico;
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraFim = dataHoraFim;
		this.status = status;
		this.valorCobrado = valorCobrado;
		this.observacoes = observacoes;
		this.dataCancelamento = dataCancelamento;
		this.motivoCancelamento = motivoCancelamento;
		this.dataCriacao = dataCriacao;
		this.dataAtualizacao = dataAtualizacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ClienteSimplesResponseDTO getCliente() {
		return cliente;
	}

	public void setCliente(ClienteSimplesResponseDTO cliente) {
		this.cliente = cliente;
	}

	public ProfissionalSimplesResponseDTO getProfissional() {
		return profissional;
	}

	public void setProfissional(ProfissionalSimplesResponseDTO profissional) {
		this.profissional = profissional;
	}

	public ServicoSimplesResponseDTO getServico() {
		return servico;
	}

	public void setServico(ServicoSimplesResponseDTO servico) {
		this.servico = servico;
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

	public StatusAgendamento getStatus() {
		return status;
	}

	public void setStatus(StatusAgendamento status) {
		this.status = status;
	}

	public BigDecimal getValorCobrado() {
		return valorCobrado;
	}

	public void setValorCobrado(BigDecimal valorCobrado) {
		this.valorCobrado = valorCobrado;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public LocalDateTime getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(LocalDateTime dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

}

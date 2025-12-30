package com.guifroes1984.agendamento.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.guifroes1984.agendamento.enums.StatusAgendamento;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "TBL_AGENDAMENTOS")
public class Agendamento {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Cliente é obrigatório")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id", nullable = false)
	private Cliente cliente;

	@NotNull(message = "Profissional é obrigatório")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profissional_id", nullable = false)
	private Profissional profissional;

	@NotNull(message = "Serviço é obrigatório")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "servico_id", nullable = false)
	private Servico servico;

	@NotNull(message = "Data e hora de início é obrigatória")
	@Column(name = "data_hora_inicio", nullable = false)
	private LocalDateTime dataHoraInicio;

	@NotNull(message = "Data e hora de fim é obrigatória")
	@Column(name = "data_hora_fim", nullable = false)
	private LocalDateTime dataHoraFim;

	@NotNull(message = "Status é obrigatório")
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private StatusAgendamento status = StatusAgendamento.AGENDADO;

	@Column(name = "valor_cobrado", precision = 10, scale = 2)
	private BigDecimal valorCobrado;

	@Column(name = "observacoes", length = 1000)
	private String observacoes;

	@Column(name = "data_cancelamento")
	private LocalDateTime dataCancelamento;

	@Column(name = "motivo_cancelamento", length = 500)
	private String motivoCancelamento;

	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;

	@Column(name = "data_atualizacao")
	private LocalDateTime dataAtualizacao;

	public Agendamento() {
		this.dataCriacao = LocalDateTime.now();
		this.dataAtualizacao = LocalDateTime.now();
	}

	public Agendamento(Cliente cliente, Profissional profissional, Servico servico, LocalDateTime dataHoraInicio,
			LocalDateTime dataHoraFim) {
		this.cliente = cliente;
		this.profissional = profissional;
		this.servico = servico;
		this.dataHoraInicio = dataHoraInicio;
		this.dataHoraFim = dataHoraFim;
		this.valorCobrado = servico != null ? servico.getPreco() : null;
		this.status = StatusAgendamento.AGENDADO;
		this.dataCriacao = LocalDateTime.now();
		this.dataAtualizacao = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		atualizarDataModificacao();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		atualizarDataModificacao();
	}

	public Profissional getProfissional() {
		return profissional;
	}

	public void setProfissional(Profissional profissional) {
		this.profissional = profissional;
		atualizarDataModificacao();
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
		atualizarDataModificacao();
	}

	public LocalDateTime getDataHoraInicio() {
		return dataHoraInicio;
	}

	public void setDataHoraInicio(LocalDateTime dataHoraInicio) {
		this.dataHoraInicio = dataHoraInicio;
		atualizarDataModificacao();
	}

	public LocalDateTime getDataHoraFim() {
		return dataHoraFim;
	}

	public void setDataHoraFim(LocalDateTime dataHoraFim) {
		this.dataHoraFim = dataHoraFim;
		atualizarDataModificacao();
	}

	public StatusAgendamento getStatus() {
		return status;
	}

	public void setStatus(StatusAgendamento status) {
		this.status = status;
		atualizarDataModificacao();
	}

	public BigDecimal getValorCobrado() {
		return valorCobrado;
	}

	public void setValorCobrado(BigDecimal valorCobrado) {
		this.valorCobrado = valorCobrado;
		atualizarDataModificacao();
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
		atualizarDataModificacao();
	}

	public LocalDateTime getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(LocalDateTime dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
		atualizarDataModificacao();
	}

	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}

	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
		atualizarDataModificacao();
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

	private void atualizarDataModificacao() {
		this.dataAtualizacao = LocalDateTime.now();
	}

	public LocalDateTime calcularDataHoraFim() {
		if (this.dataHoraInicio != null && this.servico != null) {
			return this.dataHoraInicio.plusMinutes(this.servico.getDuracaoMinutos());
		}
		return null;
	}

	public void calcularEConfigurarDataHoraFim() {
		LocalDateTime dataHoraFimCalculada = calcularDataHoraFim();
		if (dataHoraFimCalculada != null) {
			this.dataHoraFim = dataHoraFimCalculada;
		}
	}

	public void confirmar() {
		this.status = StatusAgendamento.CONFIRMADO;
		atualizarDataModificacao();
	}

	public void realizar() {
		this.status = StatusAgendamento.REALIZADO;
		atualizarDataModificacao();
	}

	public void cancelar(String motivo) {
		this.status = StatusAgendamento.CANCELADO;
		this.motivoCancelamento = motivo;
		this.dataCancelamento = LocalDateTime.now();
		atualizarDataModificacao();
	}

	public void marcarComoFaltou() {
		this.status = StatusAgendamento.FALTOU;
		atualizarDataModificacao();
	}

	public boolean estaNoHorarioTrabalho() {
		if (profissional == null || dataHoraInicio == null || dataHoraFim == null) {
			return false;
		}

		LocalTime horaInicio = dataHoraInicio.toLocalTime();
		LocalTime horaFim = dataHoraFim.toLocalTime();

		return !horaInicio.isBefore(profissional.getHoraInicio()) && !horaFim.isAfter(profissional.getHoraFim());
	}

	public boolean estaDisponivelNoDia() {
		if (profissional == null || dataHoraInicio == null) {
			return false;
		}

		int diaSemana = dataHoraInicio.getDayOfWeek().getValue();
		return profissional.estaDisponivelNoDiaPorCodigo(diaSemana);
	}

	public boolean profissionalPodeExecutarServico() {
		if (profissional == null || servico == null) {
			return false;
		}
		return profissional.podeExecutarServico(servico);
	}

}

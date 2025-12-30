package com.guifroes1984.agendamento.dto.response;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ProfissionalResponseDTO {

	private Long id;
	private String nome;
	private String telefone;
	private String email;
	private LocalTime horaInicio;
	private LocalTime horaFim;
	private Boolean ativo;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataCadastro;

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
	private LocalDateTime dataAtualizacao;

	private Set<ServicoSimplesResponseDTO> especialidades;
	private Set<Integer> diasDisponiveis;

	public ProfissionalResponseDTO() {
	}

	public ProfissionalResponseDTO(Long id, String nome, String telefone, String email, LocalTime horaInicio,
			LocalTime horaFim, Boolean ativo, LocalDateTime dataCadastro, LocalDateTime dataAtualizacao,
			Set<ServicoSimplesResponseDTO> especialidades, Set<Integer> diasDisponiveis) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.ativo = ativo;
		this.dataCadastro = dataCadastro;
		this.dataAtualizacao = dataAtualizacao;
		this.especialidades = especialidades;
		this.diasDisponiveis = diasDisponiveis;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(LocalTime horaFim) {
		this.horaFim = horaFim;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Set<ServicoSimplesResponseDTO> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(Set<ServicoSimplesResponseDTO> especialidades) {
		this.especialidades = especialidades;
	}

	public Set<Integer> getDiasDisponiveis() {
		return diasDisponiveis;
	}

	public void setDiasDisponiveis(Set<Integer> diasDisponiveis) {
		this.diasDisponiveis = diasDisponiveis;
	}

}

package com.guifroes1984.agendamento.dto.response;

import java.time.LocalTime;

public class ProfissionalSimplesResponseDTO {
	private Long id;
	private String nome;
	private String telefone;
	private String email;
	private LocalTime horaInicio;
	private LocalTime horaFim;

	// Construtor
	public ProfissionalSimplesResponseDTO() {
	}

	public ProfissionalSimplesResponseDTO(Long id, String nome, String telefone, String email, LocalTime horaInicio,
			LocalTime horaFim) {
		this.id = id;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
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

}

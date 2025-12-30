package com.guifroes1984.agendamento.dto.request;

import java.time.LocalTime;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProfissionalRequestDTO {

	@NotBlank(message = "Nome é obrigatório")
	@Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
	private String nome;

	@NotBlank(message = "Telefone é obrigatório")
	@Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", message = "Telefone inválido. Use o formato (11) 99999-9999")
	private String telefone;

	@NotBlank(message = "Email é obrigatório")
	@Email(message = "Email inválido")
	private String email;

	@NotNull(message = "Horário de início é obrigatório")
	private LocalTime horaInicio;

	@NotNull(message = "Horário de fim é obrigatório")
	private LocalTime horaFim;

	@NotNull(message = "Status é obrigatório")
	private Boolean ativo;

	@NotNull(message = "Especialidades são obrigatórias")
	@Size(min = 1, message = "Pelo menos uma especialidade deve ser informada")
	private Set<Long> especialidadesIds;

	@NotNull(message = "Dias disponíveis são obrigatórios")
	@Size(min = 1, message = "Pelo menos um dia disponível deve ser informado")
	private Set<Integer> diasDisponiveis;

	public ProfissionalRequestDTO() {
		this.ativo = true;
	}

	public ProfissionalRequestDTO(String nome, String telefone, String email, LocalTime horaInicio, LocalTime horaFim,
			Boolean ativo, Set<Long> especialidadesIds, Set<Integer> diasDisponiveis) {
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.ativo = ativo != null ? ativo : true;
		this.especialidadesIds = especialidadesIds;
		this.diasDisponiveis = diasDisponiveis;
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

	public Set<Long> getEspecialidadesIds() {
		return especialidadesIds;
	}

	public void setEspecialidadesIds(Set<Long> especialidadesIds) {
		this.especialidadesIds = especialidadesIds;
	}

	public Set<Integer> getDiasDisponiveis() {
		return diasDisponiveis;
	}

	public void setDiasDisponiveis(Set<Integer> diasDisponiveis) {
		this.diasDisponiveis = diasDisponiveis;
	}

}

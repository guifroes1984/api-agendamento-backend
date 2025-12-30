package com.guifroes1984.agendamento.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import com.guifroes1984.agendamento.enums.DiaSemana;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "TBL_PROFISSIONAIS")
public class Profissional {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Nome é obrigatório")
	@Column(nullable = false)
	private String nome;

	@NotBlank(message = "Telefone é obrigatório")
	@Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$", message = "Telefone inválido")
	@Column(nullable = false, unique = true)
	private String telefone;

	@NotBlank(message = "Email é obrigatório")
	@jakarta.validation.constraints.Email(message = "Email inválido")
	@Column(nullable = false, unique = true)
	private String email;

	@NotNull(message = "Horário de início é obrigatório")
	@Column(name = "hora_inicio", nullable = false)
	private LocalTime horaInicio;

	@NotNull(message = "Horário de fim é obrigatório")
	@Column(name = "hora_fim", nullable = false)
	private LocalTime horaFim;

	@NotNull(message = "Status é obrigatório")
	@Column(nullable = false)
	private Boolean ativo = true;

	@Column(name = "data_cadastro")
	private LocalDateTime dataCadastro;

	@Column(name = "data_atualizacao")
	private LocalDateTime dataAtualizacao;

	@ManyToMany
	@JoinTable(name = "TBL_PROFISSIONAL_SERVICOS", joinColumns = @JoinColumn(name = "profissional_id"), inverseJoinColumns = @JoinColumn(name = "servico_id"))
	private Set<Servico> especialidades = new HashSet<>();

	@ElementCollection
	@CollectionTable(name = "TBL_PROFISSIONAL_DIAS_DISPONIVEIS", joinColumns = @JoinColumn(name = "profissional_id"))
	@Column(name = "dia_semana")
	private Set<Integer> diasDisponiveis = new HashSet<>();

	public Profissional() {
		this.dataCadastro = LocalDateTime.now();
		this.dataAtualizacao = LocalDateTime.now();
	}

	public Profissional(String nome, String telefone, String email, LocalTime horaInicio, LocalTime horaFim) {
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.ativo = true;
		this.dataCadastro = LocalDateTime.now();
		this.dataAtualizacao = LocalDateTime.now();
	}

	// Getters e Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
		atualizarDataModificacao();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
		atualizarDataModificacao();
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
		atualizarDataModificacao();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		atualizarDataModificacao();
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
		atualizarDataModificacao();
	}

	public LocalTime getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(LocalTime horaFim) {
		this.horaFim = horaFim;
		atualizarDataModificacao();
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
		atualizarDataModificacao();
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

	public Set<Servico> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(Set<Servico> especialidades) {
		this.especialidades = especialidades;
		atualizarDataModificacao();
	}

	public Set<Integer> getDiasDisponiveis() {
		return diasDisponiveis;
	}

	public void setDiasDisponiveis(Set<Integer> diasDisponiveis) {
		this.diasDisponiveis = diasDisponiveis;
		atualizarDataModificacao();
	}

	private void atualizarDataModificacao() {
		this.dataAtualizacao = LocalDateTime.now();
	}

	public void adicionarEspecialidade(Servico servico) {
		this.especialidades.add(servico);
		atualizarDataModificacao();
	}

	public void removerEspecialidade(Servico servico) {
		this.especialidades.remove(servico);
		atualizarDataModificacao();
	}

	public void adicionarDiaDisponivel(DiaSemana dia) {
		this.diasDisponiveis.add(dia.getCodigo());
		atualizarDataModificacao();
	}

	public void removerDiaDisponivel(DiaSemana dia) {
		this.diasDisponiveis.remove(dia.getCodigo());
		atualizarDataModificacao();
	}

	public void adicionarDiaDisponivelPorCodigo(Integer codigoDia) {
		this.diasDisponiveis.add(codigoDia);
		atualizarDataModificacao();
	}

	public void removerDiaDisponivelPorCodigo(Integer codigoDia) {
		this.diasDisponiveis.remove(codigoDia);
		atualizarDataModificacao();
	}

	public boolean estaDisponivelNoDiaPorCodigo(Integer codigoDia) {
		if (codigoDia == null || this.diasDisponiveis == null || this.diasDisponiveis.isEmpty()) {
			return false;
		}
		return this.diasDisponiveis.contains(codigoDia);
	}

	public boolean estaDisponivelNoDia(DiaSemana dia) {
		if (dia == null || this.diasDisponiveis == null || this.diasDisponiveis.isEmpty()) {
			return false;
		}
		return this.diasDisponiveis.contains(dia.getCodigo());
	}

	public boolean podeExecutarServico(Servico servico) {
		if (servico == null || this.especialidades == null || this.especialidades.isEmpty()) {
			return false;
		}
		return this.especialidades.contains(servico);
	}

	public void ativar() {
		this.ativo = true;
		atualizarDataModificacao();
	}

	public void desativar() {
		this.ativo = false;
		atualizarDataModificacao();
	}

}

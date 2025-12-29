package com.guifroes1984.agendamento.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "TBL_SERVICOS")
public class Servico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Nome do serviço é obrigatório")
	@Column(nullable = false, unique = true)
	private String nome;

	@NotBlank(message = "Descrição é obrigatória")
	@Column(nullable = false, length = 500)
	private String descricao;

	@NotNull(message = "Duração é obrigatória")
	@Min(value = 15, message = "Duração mínima é 15 minutos")
	@Column(nullable = false)
	private Integer duracaoMinutos;

	@NotNull(message = "Preço é obrigatório")
	@Positive(message = "Preço deve ser maior que zero")
	@Column(nullable = false, precision = 10, scale = 2)
	private BigDecimal preco;

	@NotNull(message = "Status é obrigatório")
	@Column(nullable = false)
	private Boolean ativo = true;

	@Column(name = "data_cadastro")
	private LocalDateTime dataCadastro;

	@Column(name = "data_atualizacao")
	private LocalDateTime dataAtualizacao;

	public Servico() {
		this.dataCadastro = LocalDateTime.now();
		this.dataAtualizacao = LocalDateTime.now();
	}

	public Servico(String nome, String descricao, Integer duracaoMinutos, BigDecimal preco) {
		this.nome = nome;
		this.descricao = descricao;
		this.duracaoMinutos = duracaoMinutos;
		this.preco = preco;
		this.ativo = true;
		this.dataCadastro = LocalDateTime.now();
		this.dataAtualizacao = LocalDateTime.now();
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

}

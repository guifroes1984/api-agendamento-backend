package com.guifroes1984.agendamento.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ServicoRequestDTO {

	@NotBlank(message = "Nome do serviço é obrigatório")
	private String nome;

	@NotBlank(message = "Descrição é obrigatória")
	private String descricao;

	@NotNull(message = "Duração é obrigatória")
	@Min(value = 15, message = "Duração mínima é 15 minutos")
	private Integer duracaoMinutos;

	@NotNull(message = "Preço é obrigatório")
	@Positive(message = "Preço deve ser maior que zero")
	private BigDecimal preco;

	@NotNull(message = "Status é obrigatório")
	private Boolean ativo;

	// Construtor padrão
	public ServicoRequestDTO() {
		this.ativo = true;
	}

	public ServicoRequestDTO(String nome, String descricao, Integer duracaoMinutos, BigDecimal preco, Boolean ativo) {
		this.nome = nome;
		this.descricao = descricao;
		this.duracaoMinutos = duracaoMinutos;
		this.preco = preco;
		this.ativo = ativo != null ? ativo : true;
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

}

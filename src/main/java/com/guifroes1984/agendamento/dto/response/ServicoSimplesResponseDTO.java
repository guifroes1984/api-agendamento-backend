package com.guifroes1984.agendamento.dto.response;

import java.math.BigDecimal;

public class ServicoSimplesResponseDTO {
	private Long id;
	private String nome;
	private String descricao;
	private Integer duracaoMinutos;
	private BigDecimal preco;

	// Construtor
	public ServicoSimplesResponseDTO() {
	}

	public ServicoSimplesResponseDTO(Long id, String nome, String descricao, Integer duracaoMinutos, BigDecimal preco) {
		this.id = id;
		this.nome = nome;
		this.descricao = descricao;
		this.duracaoMinutos = duracaoMinutos;
		this.preco = preco;
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

}

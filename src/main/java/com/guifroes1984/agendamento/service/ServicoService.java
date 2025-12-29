package com.guifroes1984.agendamento.service;

import java.util.List;

import com.guifroes1984.agendamento.dto.request.ServicoRequestDTO;
import com.guifroes1984.agendamento.dto.response.ServicoResponseDTO;

public interface ServicoService {
	ServicoResponseDTO criar(ServicoRequestDTO servicoDTO);

	ServicoResponseDTO buscarPorId(Long id);

	ServicoResponseDTO buscarPorNomeExato(String nome);

	List<ServicoResponseDTO> buscarPorNome(String nome);

	List<ServicoResponseDTO> listarTodos();

	List<ServicoResponseDTO> listarAtivos();

	List<ServicoResponseDTO> buscarPorTermo(String termo);

	ServicoResponseDTO atualizar(Long id, ServicoRequestDTO servicoDTO);

	void excluir(Long id);

	ServicoResponseDTO ativar(Long id);

	ServicoResponseDTO desativar(Long id);

	boolean existePorNome(String nome);

	boolean existePorId(Long id);
}

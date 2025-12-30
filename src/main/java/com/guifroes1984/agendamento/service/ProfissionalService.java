package com.guifroes1984.agendamento.service;

import java.util.List;

import com.guifroes1984.agendamento.dto.request.ProfissionalRequestDTO;
import com.guifroes1984.agendamento.dto.response.ProfissionalResponseDTO;

public interface ProfissionalService {
	ProfissionalResponseDTO criar(ProfissionalRequestDTO profissionalDTO);

	ProfissionalResponseDTO buscarPorId(Long id);

	ProfissionalResponseDTO buscarPorEmail(String email);

	List<ProfissionalResponseDTO> listarTodos();

	List<ProfissionalResponseDTO> listarAtivos();

	List<ProfissionalResponseDTO> buscarPorNome(String nome);

	List<ProfissionalResponseDTO> buscarPorTermo(String termo);

	List<ProfissionalResponseDTO> buscarPorServico(Long servicoId);

	ProfissionalResponseDTO atualizar(Long id, ProfissionalRequestDTO profissionalDTO);

	void excluir(Long id);

	ProfissionalResponseDTO ativar(Long id);

	ProfissionalResponseDTO desativar(Long id);

	boolean existePorEmail(String email);

	boolean existePorTelefone(String telefone);
}

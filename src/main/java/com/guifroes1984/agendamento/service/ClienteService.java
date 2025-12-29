package com.guifroes1984.agendamento.service;

import java.util.List;

import com.guifroes1984.agendamento.dto.request.ClienteRequestDTO;
import com.guifroes1984.agendamento.dto.response.ClienteResponseDTO;

public interface ClienteService {
	ClienteResponseDTO criar(ClienteRequestDTO clienteDTO);

	ClienteResponseDTO buscarPorId(Long id);

	List<ClienteResponseDTO> listarTodos();

	List<ClienteResponseDTO> buscarPorNome(String nome);

	List<ClienteResponseDTO> buscarPorTermo(String termo);

	ClienteResponseDTO atualizar(Long id, ClienteRequestDTO clienteDTO);

	void excluir(Long id);

	boolean existePorEmail(String email);

	boolean existePorTelefone(String telefone);
}

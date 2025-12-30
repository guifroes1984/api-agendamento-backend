package com.guifroes1984.agendamento.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.guifroes1984.agendamento.dto.request.AgendamentoRequestDTO;
import com.guifroes1984.agendamento.dto.request.AtualizarStatusRequestDTO;
import com.guifroes1984.agendamento.dto.response.AgendamentoResponseDTO;

public interface AgendamentoService {
	AgendamentoResponseDTO criar(AgendamentoRequestDTO agendamentoDTO);

	AgendamentoResponseDTO buscarPorId(Long id);

	List<AgendamentoResponseDTO> listarTodos();

	List<AgendamentoResponseDTO> listarPorCliente(Long clienteId);

	List<AgendamentoResponseDTO> listarPorProfissional(Long profissionalId);

	List<AgendamentoResponseDTO> listarPorData(LocalDate data);

	List<AgendamentoResponseDTO> listarPorClienteEData(Long clienteId, LocalDate data);

	List<AgendamentoResponseDTO> listarPorProfissionalEData(Long profissionalId, LocalDate data);

	List<AgendamentoResponseDTO> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim);

	List<AgendamentoResponseDTO> listarPorStatus(String status);

	AgendamentoResponseDTO atualizar(Long id, AgendamentoRequestDTO agendamentoDTO);

	AgendamentoResponseDTO reagendar(Long id, LocalDateTime novaDataHora);

	AgendamentoResponseDTO atualizarStatus(Long id, AtualizarStatusRequestDTO statusDTO);

	void cancelar(Long id, String motivo);

	void excluir(Long id);

	boolean verificarDisponibilidade(Long profissionalId, LocalDateTime dataHoraInicio, Integer duracaoMinutos);

	boolean verificarDisponibilidadeParaAtualizacao(Long agendamentoId, Long profissionalId,
			LocalDateTime dataHoraInicio, Integer duracaoMinutos);
}

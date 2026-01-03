package com.guifroes1984.agendamento.service;

import java.time.LocalDate;
import java.util.List;

import com.guifroes1984.agendamento.dto.response.AgendamentoResponseDTO;

public interface HistoricoService {
	List<AgendamentoResponseDTO> obterHistoricoCliente(Long clienteId, LocalDate dataInicio, LocalDate dataFim);

	List<AgendamentoResponseDTO> obterAgendamentosFuturosCliente(Long clienteId);

	List<AgendamentoResponseDTO> obterAgendamentosFuturosProfissional(Long profissionalId);

	List<AgendamentoResponseDTO> obterAgendamentosDoDia(LocalDate data);

	List<AgendamentoResponseDTO> obterAgendamentosPorStatusEData(String status, LocalDate data);
}

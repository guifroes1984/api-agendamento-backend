package com.guifroes1984.agendamento.service;

import java.time.LocalDate;
import java.util.List;

import com.guifroes1984.agendamento.dto.response.EstatisticasProfissionalResponseDTO;
import com.guifroes1984.agendamento.dto.response.RelatorioAgendamentosResponseDTO;
import com.guifroes1984.agendamento.dto.response.RelatorioFinanceiroResponseDTO;

public interface RelatorioService {
	RelatorioAgendamentosResponseDTO gerarRelatorioAgendamentos(LocalDate dataInicio, LocalDate dataFim);

	RelatorioFinanceiroResponseDTO gerarRelatorioFinanceiro(LocalDate dataInicio, LocalDate dataFim);

	EstatisticasProfissionalResponseDTO gerarEstatisticasProfissional(Long profissionalId, LocalDate dataInicio,
			LocalDate dataFim);

	List<Object[]> obterServicosMaisPopulares(LocalDate dataInicio, LocalDate dataFim, Integer limite);

	List<Object[]> obterClientesMaisAtivos(LocalDate dataInicio, LocalDate dataFim, Integer limite);

	List<Object[]> obterHorariosMaisOcupados(LocalDate dataInicio, LocalDate dataFim);
}

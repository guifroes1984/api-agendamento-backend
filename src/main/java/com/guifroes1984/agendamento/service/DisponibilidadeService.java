package com.guifroes1984.agendamento.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.guifroes1984.agendamento.dto.response.HorarioDisponivelResponseDTO;

public interface DisponibilidadeService {
	List<HorarioDisponivelResponseDTO> verificarDisponibilidadeProfissional(Long profissionalId, LocalDate data,
			Long servicoId);

	List<HorarioDisponivelResponseDTO> verificarDisponibilidadeProfissionaisPorServico(LocalDate data, Long servicoId);

	boolean verificarDisponibilidadeInstantanea(Long profissionalId, LocalDateTime dataHoraInicio,
			Integer duracaoMinutos);

	List<LocalDateTime> sugerirHorariosDisponiveis(Long profissionalId, LocalDate data, Long servicoId,
			Integer quantidadeHorarios);
}

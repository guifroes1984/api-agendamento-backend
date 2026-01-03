package com.guifroes1984.agendamento.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guifroes1984.agendamento.dto.response.HorarioDisponivelResponseDTO;
import com.guifroes1984.agendamento.service.DisponibilidadeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/disponibilidade")
@Tag(name = "Disponibilidade", description = "API para verificação de disponibilidade de horários")
public class DisponibilidadeController {

	@Autowired
	private DisponibilidadeService disponibilidadeService;

	@GetMapping("/profissional/{profissionalId}")
	@Operation(summary = "Verificar disponibilidade de horários de um profissional")
	public ResponseEntity<List<HorarioDisponivelResponseDTO>> verificarDisponibilidadeProfissional(
			@PathVariable Long profissionalId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data, @RequestParam Long servicoId) {

		List<HorarioDisponivelResponseDTO> horariosDisponiveis = disponibilidadeService
				.verificarDisponibilidadeProfissional(profissionalId, data, servicoId);

		return ResponseEntity.ok(horariosDisponiveis);
	}

	@GetMapping("/servico/{servicoId}")
	@Operation(summary = "Verificar disponibilidade de todos profissionais para um serviço")
	public ResponseEntity<List<HorarioDisponivelResponseDTO>> verificarDisponibilidadePorServico(
			@PathVariable Long servicoId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

		List<HorarioDisponivelResponseDTO> horariosDisponiveis = disponibilidadeService
				.verificarDisponibilidadeProfissionaisPorServico(data, servicoId);

		return ResponseEntity.ok(horariosDisponiveis);
	}

	@GetMapping("/verificar-instantanea")
	@Operation(summary = "Verificação instantânea de disponibilidade")
	public ResponseEntity<Boolean> verificarDisponibilidadeInstantanea(@RequestParam Long profissionalId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataHoraInicio,
			@RequestParam Integer duracaoMinutos) {

		boolean disponivel = disponibilidadeService.verificarDisponibilidadeInstantanea(profissionalId, dataHoraInicio,
				duracaoMinutos);

		return ResponseEntity.ok(disponivel);
	}

	@GetMapping("/sugerir-horarios")
	@Operation(summary = "Sugerir horários disponíveis")
	public ResponseEntity<List<LocalDateTime>> sugerirHorariosDisponiveis(@RequestParam Long profissionalId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data, @RequestParam Long servicoId,
			@RequestParam(defaultValue = "5") Integer quantidade) {

		List<LocalDateTime> horariosSugeridos = disponibilidadeService.sugerirHorariosDisponiveis(profissionalId, data,
				servicoId, quantidade);

		return ResponseEntity.ok(horariosSugeridos);
	}
}

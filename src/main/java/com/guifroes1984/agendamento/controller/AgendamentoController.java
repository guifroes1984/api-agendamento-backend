package com.guifroes1984.agendamento.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guifroes1984.agendamento.dto.request.AgendamentoRequestDTO;
import com.guifroes1984.agendamento.dto.request.AtualizarStatusRequestDTO;
import com.guifroes1984.agendamento.dto.response.AgendamentoResponseDTO;
import com.guifroes1984.agendamento.service.AgendamentoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/agendamentos")
@Tag(name = "Agendamentos", description = "API para gerenciamento de agendamentos")
public class AgendamentoController {

	@Autowired
	private AgendamentoService agendamentoService;

	@PostMapping
	@Operation(summary = "Criar um novo agendamento")
	public ResponseEntity<AgendamentoResponseDTO> criar(@Valid @RequestBody AgendamentoRequestDTO agendamentoDTO) {
		AgendamentoResponseDTO agendamentoCriado = agendamentoService.criar(agendamentoDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(agendamentoCriado);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar agendamento por ID")
	public ResponseEntity<AgendamentoResponseDTO> buscarPorId(@PathVariable Long id) {
		AgendamentoResponseDTO agendamento = agendamentoService.buscarPorId(id);
		return ResponseEntity.ok(agendamento);
	}

	@GetMapping
	@Operation(summary = "Listar todos os agendamentos")
	public ResponseEntity<List<AgendamentoResponseDTO>> listarTodos() {
		List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarTodos();
		return ResponseEntity.ok(agendamentos);
	}

	@GetMapping("/cliente/{clienteId}")
	@Operation(summary = "Listar agendamentos por cliente")
	public ResponseEntity<List<AgendamentoResponseDTO>> listarPorCliente(@PathVariable Long clienteId) {
		List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarPorCliente(clienteId);
		return ResponseEntity.ok(agendamentos);
	}

	@GetMapping("/profissional/{profissionalId}")
	@Operation(summary = "Listar agendamentos por profissional")
	public ResponseEntity<List<AgendamentoResponseDTO>> listarPorProfissional(@PathVariable Long profissionalId) {
		List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarPorProfissional(profissionalId);
		return ResponseEntity.ok(agendamentos);
	}

	@GetMapping("/data")
	@Operation(summary = "Listar agendamentos por data")
	public ResponseEntity<List<AgendamentoResponseDTO>> listarPorData(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
		List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarPorData(data);
		return ResponseEntity.ok(agendamentos);
	}

	@GetMapping("/cliente/{clienteId}/data")
	@Operation(summary = "Listar agendamentos por cliente e data")
	public ResponseEntity<List<AgendamentoResponseDTO>> listarPorClienteEData(@PathVariable Long clienteId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
		List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarPorClienteEData(clienteId, data);
		return ResponseEntity.ok(agendamentos);
	}

	@GetMapping("/profissional/{profissionalId}/data")
	@Operation(summary = "Listar agendamentos por profissional e data")
	public ResponseEntity<List<AgendamentoResponseDTO>> listarPorProfissionalEData(@PathVariable Long profissionalId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
		List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarPorProfissionalEData(profissionalId, data);
		return ResponseEntity.ok(agendamentos);
	}

	@GetMapping("/periodo")
	@Operation(summary = "Listar agendamentos por período")
	public ResponseEntity<List<AgendamentoResponseDTO>> listarPorPeriodo(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
		List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarPorPeriodo(inicio, fim);
		return ResponseEntity.ok(agendamentos);
	}

	@GetMapping("/status/{status}")
	@Operation(summary = "Listar agendamentos por status")
	public ResponseEntity<List<AgendamentoResponseDTO>> listarPorStatus(@PathVariable String status) {
		List<AgendamentoResponseDTO> agendamentos = agendamentoService.listarPorStatus(status);
		return ResponseEntity.ok(agendamentos);
	}

	@GetMapping("/disponibilidade")
	@Operation(summary = "Verificar disponibilidade de profissional")
	public ResponseEntity<Boolean> verificarDisponibilidade(@RequestParam Long profissionalId,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataHoraInicio,
			@RequestParam Integer duracaoMinutos) {
		boolean disponivel = agendamentoService.verificarDisponibilidade(profissionalId, dataHoraInicio,
				duracaoMinutos);
		return ResponseEntity.ok(disponivel);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar agendamento")
	public ResponseEntity<AgendamentoResponseDTO> atualizar(@PathVariable Long id,
			@Valid @RequestBody AgendamentoRequestDTO agendamentoDTO) {
		AgendamentoResponseDTO agendamentoAtualizado = agendamentoService.atualizar(id, agendamentoDTO);
		return ResponseEntity.ok(agendamentoAtualizado);
	}

	@PatchMapping("/{id}/reagendar")
	@Operation(summary = "Reagendar agendamento")
	public ResponseEntity<AgendamentoResponseDTO> reagendar(@PathVariable Long id,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime novaDataHora) {
		AgendamentoResponseDTO agendamentoReagendado = agendamentoService.reagendar(id, novaDataHora);
		return ResponseEntity.ok(agendamentoReagendado);
	}

	@PatchMapping("/{id}/status")
	@Operation(summary = "Atualizar status do agendamento")
	public ResponseEntity<AgendamentoResponseDTO> atualizarStatus(@PathVariable Long id,
			@Valid @RequestBody AtualizarStatusRequestDTO statusDTO) {
		AgendamentoResponseDTO agendamentoAtualizado = agendamentoService.atualizarStatus(id, statusDTO);
		return ResponseEntity.ok(agendamentoAtualizado);
	}

	@DeleteMapping("/{id}/cancelar")
	@Operation(summary = "Cancelar agendamento")
	public ResponseEntity<Void> cancelar(@PathVariable Long id, @RequestParam(required = false) String motivo) {
		agendamentoService.cancelar(id, motivo != null ? motivo : "Cancelado pelo cliente");
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Excluir agendamento (apenas status AGENDADO)")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		agendamentoService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}

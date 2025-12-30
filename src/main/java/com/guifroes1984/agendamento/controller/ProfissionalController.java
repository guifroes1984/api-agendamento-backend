package com.guifroes1984.agendamento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.guifroes1984.agendamento.dto.request.ProfissionalRequestDTO;
import com.guifroes1984.agendamento.dto.response.ProfissionalResponseDTO;
import com.guifroes1984.agendamento.service.ProfissionalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/profissionais")
@Tag(name = "Profissionais", description = "API para gerenciamento de profissionais")
public class ProfissionalController {

	@Autowired
	private ProfissionalService profissionalService;

	@PostMapping
	@Operation(summary = "Criar um novo profissional")
	public ResponseEntity<ProfissionalResponseDTO> criar(@Valid @RequestBody ProfissionalRequestDTO profissionalDTO) {
		ProfissionalResponseDTO profissionalCriado = profissionalService.criar(profissionalDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(profissionalCriado);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar profissional por ID")
	public ResponseEntity<ProfissionalResponseDTO> buscarPorId(@PathVariable Long id) {
		ProfissionalResponseDTO profissional = profissionalService.buscarPorId(id);
		return ResponseEntity.ok(profissional);
	}

	@GetMapping("/email/{email}")
	@Operation(summary = "Buscar profissional por email")
	public ResponseEntity<ProfissionalResponseDTO> buscarPorEmail(@PathVariable String email) {
		ProfissionalResponseDTO profissional = profissionalService.buscarPorEmail(email);
		return ResponseEntity.ok(profissional);
	}

	@GetMapping
	@Operation(summary = "Listar todos os profissionais")
	public ResponseEntity<List<ProfissionalResponseDTO>> listarTodos() {
		List<ProfissionalResponseDTO> profissionais = profissionalService.listarTodos();
		return ResponseEntity.ok(profissionais);
	}

	@GetMapping("/ativos")
	@Operation(summary = "Listar profissionais ativos")
	public ResponseEntity<List<ProfissionalResponseDTO>> listarAtivos() {
		List<ProfissionalResponseDTO> profissionais = profissionalService.listarAtivos();
		return ResponseEntity.ok(profissionais);
	}

	@GetMapping("/buscar-nome")
	@Operation(summary = "Buscar profissionais por nome")
	public ResponseEntity<List<ProfissionalResponseDTO>> buscarPorNome(@RequestParam String nome) {
		List<ProfissionalResponseDTO> profissionais = profissionalService.buscarPorNome(nome);
		return ResponseEntity.ok(profissionais);
	}

	@GetMapping("/buscar")
	@Operation(summary = "Buscar profissionais ativos por termo")
	public ResponseEntity<List<ProfissionalResponseDTO>> buscarPorTermo(@RequestParam String termo) {
		List<ProfissionalResponseDTO> profissionais = profissionalService.buscarPorTermo(termo);
		return ResponseEntity.ok(profissionais);
	}

	@GetMapping("/servico/{servicoId}")
	@Operation(summary = "Buscar profissionais por serviço")
	public ResponseEntity<List<ProfissionalResponseDTO>> buscarPorServico(@PathVariable Long servicoId) {
		List<ProfissionalResponseDTO> profissionais = profissionalService.buscarPorServico(servicoId);
		return ResponseEntity.ok(profissionais);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar profissional")
	public ResponseEntity<ProfissionalResponseDTO> atualizar(@PathVariable Long id,
			@Valid @RequestBody ProfissionalRequestDTO profissionalDTO) {
		ProfissionalResponseDTO profissionalAtualizado = profissionalService.atualizar(id, profissionalDTO);
		return ResponseEntity.ok(profissionalAtualizado);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Excluir profissional")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		profissionalService.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/ativar")
	@Operation(summary = "Ativar profissional")
	public ResponseEntity<ProfissionalResponseDTO> ativar(@PathVariable Long id) {
		ProfissionalResponseDTO profissionalAtivado = profissionalService.ativar(id);
		return ResponseEntity.ok(profissionalAtivado);
	}

	@PatchMapping("/{id}/desativar")
	@Operation(summary = "Desativar profissional")
	public ResponseEntity<ProfissionalResponseDTO> desativar(@PathVariable Long id) {
		ProfissionalResponseDTO profissionalDesativado = profissionalService.desativar(id);
		return ResponseEntity.ok(profissionalDesativado);
	}
}

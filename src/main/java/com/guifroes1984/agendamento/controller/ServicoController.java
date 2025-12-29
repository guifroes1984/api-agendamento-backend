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

import com.guifroes1984.agendamento.dto.request.ServicoRequestDTO;
import com.guifroes1984.agendamento.dto.response.ServicoResponseDTO;
import com.guifroes1984.agendamento.service.ServicoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/servicos")
@Tag(name = "Serviços", description = "API para gerenciamento de serviços")
public class ServicoController {

	@Autowired
	private ServicoService servicoService;

	@PostMapping
	@Operation(summary = "Criar um novo serviço")
	public ResponseEntity<ServicoResponseDTO> criar(@Valid @RequestBody ServicoRequestDTO servicoDTO) {
		ServicoResponseDTO servicoCriado = servicoService.criar(servicoDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(servicoCriado);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar serviço por ID")
	public ResponseEntity<ServicoResponseDTO> buscarPorId(@PathVariable Long id) {
		ServicoResponseDTO servico = servicoService.buscarPorId(id);
		return ResponseEntity.ok(servico);
	}

	@GetMapping("/nome/{nome}")
    @Operation(summary = "Buscar serviço por nome exato")
    public ResponseEntity<ServicoResponseDTO> buscarPorNomeExato(@PathVariable String nome) {
        ServicoResponseDTO servico = servicoService.buscarPorNomeExato(nome);
        return ResponseEntity.ok(servico);
    }

    @GetMapping("/buscar-nome")
    @Operation(summary = "Buscar serviços por nome (contém)")
    public ResponseEntity<List<ServicoResponseDTO>> buscarPorNome(@RequestParam String nome) {
        List<ServicoResponseDTO> servicos = servicoService.buscarPorNome(nome);
        return ResponseEntity.ok(servicos);
    }

	@GetMapping
	@Operation(summary = "Listar todos os serviços")
	public ResponseEntity<List<ServicoResponseDTO>> listarTodos() {
		List<ServicoResponseDTO> servicos = servicoService.listarTodos();
		return ResponseEntity.ok(servicos);
	}

	@GetMapping("/ativos")
	@Operation(summary = "Listar serviços ativos")
	public ResponseEntity<List<ServicoResponseDTO>> listarAtivos() {
		List<ServicoResponseDTO> servicos = servicoService.listarAtivos();
		return ResponseEntity.ok(servicos);
	}

	@GetMapping("/buscar")
	@Operation(summary = "Buscar serviços por termo")
	public ResponseEntity<List<ServicoResponseDTO>> buscarPorTermo(@RequestParam String termo) {
		List<ServicoResponseDTO> servicos = servicoService.buscarPorTermo(termo);
		return ResponseEntity.ok(servicos);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar serviço")
	public ResponseEntity<ServicoResponseDTO> atualizar(@PathVariable Long id,
			@Valid @RequestBody ServicoRequestDTO servicoDTO) {
		ServicoResponseDTO servicoAtualizado = servicoService.atualizar(id, servicoDTO);
		return ResponseEntity.ok(servicoAtualizado);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Excluir serviço")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		servicoService.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}/ativar")
	@Operation(summary = "Ativar serviço")
	public ResponseEntity<ServicoResponseDTO> ativar(@PathVariable Long id) {
		ServicoResponseDTO servicoAtivado = servicoService.ativar(id);
		return ResponseEntity.ok(servicoAtivado);
	}

	@PatchMapping("/{id}/desativar")
	@Operation(summary = "Desativar serviço")
	public ResponseEntity<ServicoResponseDTO> desativar(@PathVariable Long id) {
		ServicoResponseDTO servicoDesativado = servicoService.desativar(id);
		return ResponseEntity.ok(servicoDesativado);
	}
}

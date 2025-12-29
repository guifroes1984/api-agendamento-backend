package com.guifroes1984.agendamento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guifroes1984.agendamento.dto.request.ClienteRequestDTO;
import com.guifroes1984.agendamento.dto.response.ClienteResponseDTO;
import com.guifroes1984.agendamento.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "API para gerenciamento de clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PostMapping
	@Operation(summary = "Criar um novo cliente")
	public ResponseEntity<ClienteResponseDTO> criar(@Valid @RequestBody ClienteRequestDTO clienteDTO) {
		ClienteResponseDTO clienteCriado = clienteService.criar(clienteDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(clienteCriado);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar cliente por ID")
	public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
		ClienteResponseDTO cliente = clienteService.buscarPorId(id);
		return ResponseEntity.ok(cliente);
	}

	@GetMapping
	@Operation(summary = "Listar todos os clientes")
	public ResponseEntity<List<ClienteResponseDTO>> listarTodos() {
		List<ClienteResponseDTO> clientes = clienteService.listarTodos();
		return ResponseEntity.ok(clientes);
	}

	@GetMapping("/buscar/nome")
	@Operation(summary = "Buscar clientes por nome")
	public ResponseEntity<List<ClienteResponseDTO>> buscarPorNome(@RequestParam String nome) {
		List<ClienteResponseDTO> clientes = clienteService.buscarPorNome(nome);
		return ResponseEntity.ok(clientes);
	}

	@GetMapping("/buscar/termo")
	@Operation(summary = "Buscar clientes por termo (nome, email ou telefone)")
	public ResponseEntity<List<ClienteResponseDTO>> buscarPorTermo(@RequestParam String termo) {
		List<ClienteResponseDTO> clientes = clienteService.buscarPorTermo(termo);
		return ResponseEntity.ok(clientes);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Atualizar cliente")
	public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id,
			@Valid @RequestBody ClienteRequestDTO clienteDTO) {
		ClienteResponseDTO clienteAtualizado = clienteService.atualizar(id, clienteDTO);
		return ResponseEntity.ok(clienteAtualizado);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Excluir cliente")
	public ResponseEntity<Void> excluir(@PathVariable Long id) {
		clienteService.excluir(id);
		return ResponseEntity.noContent().build();
	}
}

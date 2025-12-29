package com.guifroes1984.agendamento.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guifroes1984.agendamento.dto.request.ClienteRequestDTO;
import com.guifroes1984.agendamento.dto.response.ClienteResponseDTO;
import com.guifroes1984.agendamento.model.Cliente;
import com.guifroes1984.agendamento.repository.ClienteRepository;
import com.guifroes1984.agendamento.service.ClienteService;

import jakarta.transaction.Transactional;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	@Transactional
	public ClienteResponseDTO criar(ClienteRequestDTO clienteDTO) {

		if (clienteRepository.existsByEmail(clienteDTO.getEmail())) {
			throw new RuntimeException("Email já cadastrado");
		}

		if (clienteRepository.existsByTelefone(clienteDTO.getTelefone())) {
			throw new RuntimeException("Telefone já cadastrado");
		}

		Cliente cliente = new Cliente();
		cliente.setNome(clienteDTO.getNome());
		cliente.setTelefone(clienteDTO.getTelefone());
		cliente.setEmail(clienteDTO.getEmail());

		Cliente clienteSalvo = clienteRepository.save(cliente);
		return toResponseDTO(clienteSalvo);
	}

	@Override
	public ClienteResponseDTO buscarPorId(Long id) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
		return toResponseDTO(cliente);
	}

	@Override
	public List<ClienteResponseDTO> listarTodos() {
		return clienteRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
	}

	@Override
	public List<ClienteResponseDTO> buscarPorNome(String nome) {
		return clienteRepository.findByNomeContainingIgnoreCase(nome).stream().map(this::toResponseDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<ClienteResponseDTO> buscarPorTermo(String termo) {
		return clienteRepository.findAll().stream()
				.filter(c -> c.getNome().toLowerCase().contains(termo.toLowerCase())
						|| c.getEmail().toLowerCase().contains(termo.toLowerCase()) || c.getTelefone().contains(termo))
				.map(this::toResponseDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public ClienteResponseDTO atualizar(Long id, ClienteRequestDTO clienteDTO) {
		Cliente cliente = clienteRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

		// Validação de email único
		if (!cliente.getEmail().equals(clienteDTO.getEmail())
				&& clienteRepository.existsByEmail(clienteDTO.getEmail())) {
			throw new RuntimeException("Email já cadastrado");
		}

		// Validação de telefone único
		if (!cliente.getTelefone().equals(clienteDTO.getTelefone())
				&& clienteRepository.existsByTelefone(clienteDTO.getTelefone())) {
			throw new RuntimeException("Telefone já cadastrado");
		}

		cliente.setNome(clienteDTO.getNome());
		cliente.setTelefone(clienteDTO.getTelefone());
		cliente.setEmail(clienteDTO.getEmail());

		Cliente clienteAtualizado = clienteRepository.save(cliente);
		return toResponseDTO(clienteAtualizado);
	}

	@Override
	@Transactional
	public void excluir(Long id) {
		if (!clienteRepository.existsById(id)) {
			throw new RuntimeException("Cliente não encontrado");
		}
		clienteRepository.deleteById(id);
	}

	@Override
	public boolean existePorEmail(String email) {
		return clienteRepository.existsByEmail(email);
	}

	@Override
	public boolean existePorTelefone(String telefone) {
		return clienteRepository.existsByTelefone(telefone);
	}

	private ClienteResponseDTO toResponseDTO(Cliente cliente) {
		return new ClienteResponseDTO(cliente.getId(), cliente.getNome(), cliente.getTelefone(), cliente.getEmail(),
				cliente.getDataCadastro());
	}
}

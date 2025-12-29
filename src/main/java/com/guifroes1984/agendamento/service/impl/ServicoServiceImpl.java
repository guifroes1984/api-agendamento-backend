package com.guifroes1984.agendamento.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guifroes1984.agendamento.dto.request.ServicoRequestDTO;
import com.guifroes1984.agendamento.dto.response.ServicoResponseDTO;
import com.guifroes1984.agendamento.exception.BusinessException;
import com.guifroes1984.agendamento.model.Servico;
import com.guifroes1984.agendamento.repository.ServicoRepository;
import com.guifroes1984.agendamento.service.ServicoService;

import jakarta.transaction.Transactional;

@Service
public class ServicoServiceImpl implements ServicoService {

	@Autowired
	private ServicoRepository servicoRepository;

	@Override
	@Transactional
	public ServicoResponseDTO criar(ServicoRequestDTO servicoDTO) {
		validarServicoAntesDeCriar(servicoDTO);

		Servico servico = new Servico();
		servico.setNome(servicoDTO.getNome());
		servico.setDescricao(servicoDTO.getDescricao());
		servico.setDuracaoMinutos(servicoDTO.getDuracaoMinutos());
		servico.setPreco(servicoDTO.getPreco());
		servico.setAtivo(servicoDTO.getAtivo() != null ? servicoDTO.getAtivo() : true);

		Servico servicoSalvo = servicoRepository.save(servico);
		return toResponseDTO(servicoSalvo);
	}

	@Override
	public ServicoResponseDTO buscarPorId(Long id) {
		Servico servico = servicoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + id));
		return toResponseDTO(servico);
	}

	@Override
    public ServicoResponseDTO buscarPorNomeExato(String nome) {
        Servico servico = servicoRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com nome: " + nome));
        return toResponseDTO(servico);
    }

    @Override
    public List<ServicoResponseDTO> buscarPorNome(String nome) {
        return servicoRepository.findByNomeContainingIgnoreCase(nome).stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

	@Override
	public List<ServicoResponseDTO> listarTodos() {
		return servicoRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
	}

	@Override
	public List<ServicoResponseDTO> listarAtivos() {
		return servicoRepository.findByAtivoTrue().stream().map(this::toResponseDTO).collect(Collectors.toList());
	}

	@Override
	public List<ServicoResponseDTO> buscarPorTermo(String termo) {
		return servicoRepository.buscarAtivosPorTermo(termo).stream().map(this::toResponseDTO)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public ServicoResponseDTO atualizar(Long id, ServicoRequestDTO servicoDTO) {
		Servico servico = servicoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + id));

		validarServicoAntesDeAtualizar(servico, servicoDTO);

		servico.setNome(servicoDTO.getNome());
		servico.setDescricao(servicoDTO.getDescricao());
		servico.setDuracaoMinutos(servicoDTO.getDuracaoMinutos());
		servico.setPreco(servicoDTO.getPreco());

		if (servicoDTO.getAtivo() != null) {
			servico.setAtivo(servicoDTO.getAtivo());
		}

		Servico servicoAtualizado = servicoRepository.save(servico);
		return toResponseDTO(servicoAtualizado);
	}

	@Override
	@Transactional
	public void excluir(Long id) {
		Servico servico = servicoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + id));

		// Verificar se o serviço está sendo usado em agendamentos futuros
		// (Esta verificação será implementada no Commit 4)

		servicoRepository.delete(servico);
	}

	@Override
	@Transactional
	public ServicoResponseDTO ativar(Long id) {
	    Servico servico = servicoRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + id));
	    
	    servico.setAtivo(true);
	    servico.setDataAtualizacao(java.time.LocalDateTime.now());
	    Servico servicoAtualizado = servicoRepository.save(servico);
	    return toResponseDTO(servicoAtualizado);
	}

	@Override
	@Transactional
	public ServicoResponseDTO desativar(Long id) {
	    Servico servico = servicoRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + id));
	    
	    servico.setAtivo(false);
	    servico.setDataAtualizacao(java.time.LocalDateTime.now());
	    Servico servicoAtualizado = servicoRepository.save(servico);
	    return toResponseDTO(servicoAtualizado);
	}

	@Override
	public boolean existePorNome(String nome) {
		return servicoRepository.existsByNome(nome);
	}

	@Override
	public boolean existePorId(Long id) {
		return servicoRepository.existsById(id);
	}

	private void validarServicoAntesDeCriar(ServicoRequestDTO servicoDTO) {
		if (servicoRepository.existsByNome(servicoDTO.getNome())) {
			throw new BusinessException("Já existe um serviço cadastrado com este nome");
		}

		if (servicoDTO.getDuracaoMinutos() < 15) {
			throw new BusinessException("Duração mínima do serviço é 15 minutos");
		}

		if (servicoDTO.getPreco().compareTo(java.math.BigDecimal.ZERO) <= 0) {
			throw new BusinessException("Preço do serviço deve ser maior que zero");
		}
	}

	private void validarServicoAntesDeAtualizar(Servico servico, ServicoRequestDTO servicoDTO) {
		if (!servico.getNome().equals(servicoDTO.getNome())
				&& servicoRepository.existsByNomeAndIdNot(servicoDTO.getNome(), servico.getId())) {
			throw new BusinessException("Já existe um serviço cadastrado com este nome");
		}

		if (servicoDTO.getDuracaoMinutos() < 15) {
			throw new BusinessException("Duração mínima do serviço é 15 minutos");
		}

		if (servicoDTO.getPreco().compareTo(java.math.BigDecimal.ZERO) <= 0) {
			throw new BusinessException("Preço do serviço deve ser maior que zero");
		}
	}

	private ServicoResponseDTO toResponseDTO(Servico servico) {
		return new ServicoResponseDTO(servico.getId(), servico.getNome(), servico.getDescricao(),
				servico.getDuracaoMinutos(), servico.getPreco(), servico.getAtivo(), servico.getDataCadastro(),
				servico.getDataAtualizacao());
	}
}

package com.guifroes1984.agendamento.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guifroes1984.agendamento.dto.request.AgendamentoRequestDTO;
import com.guifroes1984.agendamento.dto.request.AtualizarStatusRequestDTO;
import com.guifroes1984.agendamento.dto.response.AgendamentoResponseDTO;
import com.guifroes1984.agendamento.dto.response.ClienteSimplesResponseDTO;
import com.guifroes1984.agendamento.dto.response.ProfissionalSimplesResponseDTO;
import com.guifroes1984.agendamento.dto.response.ServicoSimplesResponseDTO;
import com.guifroes1984.agendamento.enums.StatusAgendamento;
import com.guifroes1984.agendamento.exception.BusinessException;
import com.guifroes1984.agendamento.model.Agendamento;
import com.guifroes1984.agendamento.model.Cliente;
import com.guifroes1984.agendamento.model.Profissional;
import com.guifroes1984.agendamento.model.Servico;
import com.guifroes1984.agendamento.repository.AgendamentoRepository;
import com.guifroes1984.agendamento.repository.ClienteRepository;
import com.guifroes1984.agendamento.repository.ProfissionalRepository;
import com.guifroes1984.agendamento.repository.ServicoRepository;
import com.guifroes1984.agendamento.service.AgendamentoService;

import jakarta.transaction.Transactional;

@Service
public class AgendamentoServiceImpl implements AgendamentoService {

	@Autowired
	private AgendamentoRepository agendamentoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ProfissionalRepository profissionalRepository;

	@Autowired
	private ServicoRepository servicoRepository;

	@Override
	@Transactional
	public AgendamentoResponseDTO criar(AgendamentoRequestDTO agendamentoDTO) {

		Cliente cliente = clienteRepository.findById(agendamentoDTO.getClienteId()).orElseThrow(
				() -> new RuntimeException("Cliente não encontrado com ID: " + agendamentoDTO.getClienteId()));

		Profissional profissional = profissionalRepository.findById(agendamentoDTO.getProfissionalId())
				.orElseThrow(() -> new RuntimeException(
						"Profissional não encontrado com ID: " + agendamentoDTO.getProfissionalId()));

		Servico servico = servicoRepository.findById(agendamentoDTO.getServicoId()).orElseThrow(
				() -> new RuntimeException("Serviço não encontrado com ID: " + agendamentoDTO.getServicoId()));

		validarAgendamento(cliente, profissional, servico, agendamentoDTO.getDataHoraInicio(), null);

		Agendamento agendamento = new Agendamento();
		agendamento.setCliente(cliente);
		agendamento.setProfissional(profissional);
		agendamento.setServico(servico);
		agendamento.setDataHoraInicio(agendamentoDTO.getDataHoraInicio());
		agendamento.calcularEConfigurarDataHoraFim();
		agendamento.setObservacoes(agendamentoDTO.getObservacoes());
		agendamento.setValorCobrado(servico.getPreco());
		agendamento.setStatus(StatusAgendamento.AGENDADO);

		Agendamento agendamentoSalvo = agendamentoRepository.save(agendamento);
		return toResponseDTO(agendamentoSalvo);
	}

	@Override
	public AgendamentoResponseDTO buscarPorId(Long id) {
		Agendamento agendamento = agendamentoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Agendamento não encontrado com ID: " + id));
		return toResponseDTO(agendamento);
	}

	@Override
	public List<AgendamentoResponseDTO> listarTodos() {
		return agendamentoRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
	}

	@Override
	public List<AgendamentoResponseDTO> listarPorCliente(Long clienteId) {
		return agendamentoRepository.findByClienteId(clienteId).stream().map(this::toResponseDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<AgendamentoResponseDTO> listarPorProfissional(Long profissionalId) {
		return agendamentoRepository.findByProfissionalId(profissionalId).stream().map(this::toResponseDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<AgendamentoResponseDTO> listarPorData(LocalDate data) {
		return agendamentoRepository.findByData(data).stream().map(this::toResponseDTO).collect(Collectors.toList());
	}

	@Override
	public List<AgendamentoResponseDTO> listarPorClienteEData(Long clienteId, LocalDate data) {
		return agendamentoRepository.findByClienteAndData(clienteId, data).stream().map(this::toResponseDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<AgendamentoResponseDTO> listarPorProfissionalEData(Long profissionalId, LocalDate data) {
		return agendamentoRepository.findByProfissionalAndData(profissionalId, data).stream().map(this::toResponseDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<AgendamentoResponseDTO> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
		return agendamentoRepository.findByPeriodo(inicio, fim).stream().map(this::toResponseDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<AgendamentoResponseDTO> listarPorStatus(String status) {
		StatusAgendamento statusEnum = StatusAgendamento.fromCodigo(status);
		return agendamentoRepository.findByStatus(statusEnum).stream().map(this::toResponseDTO)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public AgendamentoResponseDTO atualizar(Long id, AgendamentoRequestDTO agendamentoDTO) {
		Agendamento agendamento = agendamentoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Agendamento não encontrado com ID: " + id));

		if (!agendamento.getStatus().equals(StatusAgendamento.AGENDADO)
				&& !agendamento.getStatus().equals(StatusAgendamento.CONFIRMADO)) {
			throw new BusinessException("Agendamento não pode ser alterado no status: " + agendamento.getStatus());
		}

		if (!agendamento.getCliente().getId().equals(agendamentoDTO.getClienteId())) {
			Cliente cliente = clienteRepository.findById(agendamentoDTO.getClienteId()).orElseThrow(
					() -> new RuntimeException("Cliente não encontrado com ID: " + agendamentoDTO.getClienteId()));
			agendamento.setCliente(cliente);
		}

		if (!agendamento.getProfissional().getId().equals(agendamentoDTO.getProfissionalId())) {
			Profissional profissional = profissionalRepository.findById(agendamentoDTO.getProfissionalId())
					.orElseThrow(() -> new RuntimeException(
							"Profissional não encontrado com ID: " + agendamentoDTO.getProfissionalId()));
			agendamento.setProfissional(profissional);
		}

		if (!agendamento.getServico().getId().equals(agendamentoDTO.getServicoId())) {
			Servico servico = servicoRepository.findById(agendamentoDTO.getServicoId()).orElseThrow(
					() -> new RuntimeException("Serviço não encontrado com ID: " + agendamentoDTO.getServicoId()));
			agendamento.setServico(servico);
		}

		if (!agendamento.getDataHoraInicio().equals(agendamentoDTO.getDataHoraInicio())) {
			validarAgendamento(agendamento.getCliente(), agendamento.getProfissional(), agendamento.getServico(),
					agendamentoDTO.getDataHoraInicio(), id);
			agendamento.setDataHoraInicio(agendamentoDTO.getDataHoraInicio());
			agendamento.calcularEConfigurarDataHoraFim();
		}

		agendamento.setObservacoes(agendamentoDTO.getObservacoes());

		Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamento);
		return toResponseDTO(agendamentoAtualizado);
	}

	@Override
	@Transactional
	public AgendamentoResponseDTO reagendar(Long id, LocalDateTime novaDataHora) {
		Agendamento agendamento = agendamentoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Agendamento não encontrado com ID: " + id));

		if (!agendamento.getStatus().equals(StatusAgendamento.AGENDADO)
				&& !agendamento.getStatus().equals(StatusAgendamento.CONFIRMADO)) {
			throw new BusinessException("Agendamento não pode ser reagendado no status: " + agendamento.getStatus());
		}

		if (novaDataHora.isBefore(LocalDateTime.now())) {
			throw new BusinessException("Nova data/hora deve ser futura");
		}

		validarAgendamento(agendamento.getCliente(), agendamento.getProfissional(), agendamento.getServico(),
				novaDataHora, id);

		agendamento.setDataHoraInicio(novaDataHora);
		agendamento.calcularEConfigurarDataHoraFim();

		Agendamento agendamentoReagendado = agendamentoRepository.save(agendamento);
		return toResponseDTO(agendamentoReagendado);
	}

	@Override
	@Transactional
	public AgendamentoResponseDTO atualizarStatus(Long id, AtualizarStatusRequestDTO statusDTO) {
		Agendamento agendamento = agendamentoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Agendamento não encontrado com ID: " + id));

		StatusAgendamento novoStatus = StatusAgendamento.fromCodigo(statusDTO.getStatus());

		validarTransicaoStatus(agendamento.getStatus(), novoStatus);

		agendamento.setStatus(novoStatus);

		if (novoStatus.equals(StatusAgendamento.CANCELADO)) {
			agendamento.setDataCancelamento(LocalDateTime.now());
			agendamento.setMotivoCancelamento(statusDTO.getMotivo());
		}

		Agendamento agendamentoAtualizado = agendamentoRepository.save(agendamento);
		return toResponseDTO(agendamentoAtualizado);
	}

	@Override
	@Transactional
	public void cancelar(Long id, String motivo) {
		Agendamento agendamento = agendamentoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Agendamento não encontrado com ID: " + id));

		if (agendamento.getStatus().equals(StatusAgendamento.CANCELADO)
				|| agendamento.getStatus().equals(StatusAgendamento.FALTOU)
				|| agendamento.getStatus().equals(StatusAgendamento.REALIZADO)) {
			throw new BusinessException("Agendamento não pode ser cancelado no status: " + agendamento.getStatus());
		}

		if (agendamento.getDataHoraInicio().isBefore(LocalDateTime.now())) {
			throw new BusinessException("Não é possível cancelar um agendamento que já passou");
		}

		agendamento.cancelar(motivo);
		agendamentoRepository.save(agendamento);
	}

	@Override
	@Transactional
	public void excluir(Long id) {
		Agendamento agendamento = agendamentoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Agendamento não encontrado com ID: " + id));

		if (!agendamento.getStatus().equals(StatusAgendamento.AGENDADO)) {
			throw new BusinessException("Só é possível excluir agendamentos com status AGENDADO");
		}

		agendamentoRepository.delete(agendamento);
	}

	@Override
	public boolean verificarDisponibilidade(Long profissionalId, LocalDateTime dataHoraInicio, Integer duracaoMinutos) {
		Profissional profissional = profissionalRepository.findById(profissionalId)
				.orElseThrow(() -> new RuntimeException("Profissional não encontrado com ID: " + profissionalId));

		LocalDateTime dataHoraFim = dataHoraInicio.plusMinutes(duracaoMinutos);

		if (!profissionalEstaDisponivelNoHorario(profissional, dataHoraInicio, dataHoraFim)) {
			return false;
		}

		if (!profissionalEstaDisponivelNoDia(profissional, dataHoraInicio)) {
			return false;
		}

		List<Agendamento> conflitos = agendamentoRepository.findConflitosProfissional(profissionalId, dataHoraInicio,
				dataHoraFim);

		return conflitos.isEmpty();
	}

	@Override
	public boolean verificarDisponibilidadeParaAtualizacao(Long agendamentoId, Long profissionalId,
			LocalDateTime dataHoraInicio, Integer duracaoMinutos) {
		Profissional profissional = profissionalRepository.findById(profissionalId)
				.orElseThrow(() -> new RuntimeException("Profissional não encontrado com ID: " + profissionalId));

		LocalDateTime dataHoraFim = dataHoraInicio.plusMinutes(duracaoMinutos);

		if (!profissionalEstaDisponivelNoHorario(profissional, dataHoraInicio, dataHoraFim)) {
			return false;
		}

		if (!profissionalEstaDisponivelNoDia(profissional, dataHoraInicio)) {
			return false;
		}

		boolean existeConflito = agendamentoRepository.existsConflitoProfissional(profissionalId, dataHoraInicio,
				dataHoraFim, agendamentoId);

		return !existeConflito;
	}

	private void validarAgendamento(Cliente cliente, Profissional profissional, Servico servico,
			LocalDateTime dataHoraInicio, Long agendamentoId) {

		if (dataHoraInicio.isBefore(LocalDateTime.now())) {
			throw new BusinessException("Data e hora de início deve ser futura");
		}

		if (!profissional.getAtivo()) {
			throw new BusinessException("Profissional não está ativo");
		}

		if (!servico.getAtivo()) {
			throw new BusinessException("Serviço não está ativo");
		}

		if (!profissional.podeExecutarServico(servico)) {
			throw new BusinessException("Profissional não está qualificado para executar este serviço");
		}

		LocalDateTime dataHoraFim = dataHoraInicio.plusMinutes(servico.getDuracaoMinutos());

		if (!profissionalEstaDisponivelNoHorario(profissional, dataHoraInicio, dataHoraFim)) {
			throw new BusinessException("Profissional não está disponível neste horário");
		}

		if (!profissionalEstaDisponivelNoDia(profissional, dataHoraInicio)) {
			throw new BusinessException("Profissional não trabalha neste dia da semana");
		}

		if (agendamentoId == null) {
			List<Agendamento> conflitosProfissional = agendamentoRepository
					.findConflitosProfissional(profissional.getId(), dataHoraInicio, dataHoraFim);
			if (!conflitosProfissional.isEmpty()) {
				throw new BusinessException("Profissional já tem um agendamento neste horário");
			}
		} else {
			boolean existeConflitoProfissional = agendamentoRepository.existsConflitoProfissional(profissional.getId(),
					dataHoraInicio, dataHoraFim, agendamentoId);
			if (existeConflitoProfissional) {
				throw new BusinessException("Profissional já tem um agendamento neste horário");
			}
		}

		if (agendamentoId == null) {
			List<Agendamento> conflitosCliente = agendamentoRepository.findConflitosCliente(cliente.getId(),
					dataHoraInicio, dataHoraFim);
			if (!conflitosCliente.isEmpty()) {
				throw new BusinessException("Cliente já tem um agendamento neste horário");
			}
		} else {
			boolean existeConflitoCliente = agendamentoRepository.existsConflitoCliente(cliente.getId(), dataHoraInicio,
					dataHoraFim, agendamentoId);
			if (existeConflitoCliente) {
				throw new BusinessException("Cliente já tem um agendamento neste horário");
			}
		}
	}

	private boolean profissionalEstaDisponivelNoHorario(Profissional profissional, LocalDateTime dataHoraInicio,
			LocalDateTime dataHoraFim) {
		if (profissional == null || dataHoraInicio == null || dataHoraFim == null) {
			return false;
		}

		java.time.LocalTime horaInicio = dataHoraInicio.toLocalTime();
		java.time.LocalTime horaFim = dataHoraFim.toLocalTime();

		return !horaInicio.isBefore(profissional.getHoraInicio()) && !horaFim.isAfter(profissional.getHoraFim());
	}

	private boolean profissionalEstaDisponivelNoDia(Profissional profissional, LocalDateTime dataHoraInicio) {
		if (profissional == null || dataHoraInicio == null) {
			return false;
		}

		int diaSemana = dataHoraInicio.getDayOfWeek().getValue();
		return profissional.estaDisponivelNoDiaPorCodigo(diaSemana);
	}

	private void validarTransicaoStatus(StatusAgendamento statusAtual, StatusAgendamento novoStatus) {
		
		if (statusAtual.equals(StatusAgendamento.CANCELADO)) {
			throw new BusinessException("Não é possível alterar status de um agendamento CANCELADO");
		}

		if (statusAtual.equals(StatusAgendamento.FALTOU)) {
			throw new BusinessException("Não é possível alterar status de um agendamento em que o cliente FALTOU");
		}

		if (statusAtual.equals(StatusAgendamento.REALIZADO)) {
			throw new BusinessException("Não é possível alterar status de um agendamento REALIZADO");
		}

		// Transições permitidas:
		// AGENDADO -> CONFIRMADO, CANCELADO
		// CONFIRMADO -> REALIZADO, CANCELADO, FALTOU
		if (statusAtual.equals(StatusAgendamento.AGENDADO) && !novoStatus.equals(StatusAgendamento.CONFIRMADO)
				&& !novoStatus.equals(StatusAgendamento.CANCELADO)) {
			throw new BusinessException("Transição de status inválida: " + statusAtual + " -> " + novoStatus);
		}

		if (statusAtual.equals(StatusAgendamento.CONFIRMADO) && !novoStatus.equals(StatusAgendamento.REALIZADO)
				&& !novoStatus.equals(StatusAgendamento.CANCELADO) && !novoStatus.equals(StatusAgendamento.FALTOU)) {
			throw new BusinessException("Transição de status inválida: " + statusAtual + " -> " + novoStatus);
		}
	}

	private AgendamentoResponseDTO toResponseDTO(Agendamento agendamento) {
		ClienteSimplesResponseDTO clienteDTO = new ClienteSimplesResponseDTO(agendamento.getCliente().getId(),
				agendamento.getCliente().getNome(), agendamento.getCliente().getTelefone(),
				agendamento.getCliente().getEmail());

		ProfissionalSimplesResponseDTO profissionalDTO = new ProfissionalSimplesResponseDTO(
				agendamento.getProfissional().getId(), agendamento.getProfissional().getNome(),
				agendamento.getProfissional().getTelefone(), agendamento.getProfissional().getEmail(),
				agendamento.getProfissional().getHoraInicio(), agendamento.getProfissional().getHoraFim());

		ServicoSimplesResponseDTO servicoDTO = new ServicoSimplesResponseDTO(agendamento.getServico().getId(),
				agendamento.getServico().getNome(), agendamento.getServico().getDescricao(),
				agendamento.getServico().getDuracaoMinutos(), agendamento.getServico().getPreco());

		return new AgendamentoResponseDTO(agendamento.getId(), clienteDTO, profissionalDTO, servicoDTO,
				agendamento.getDataHoraInicio(), agendamento.getDataHoraFim(), agendamento.getStatus(),
				agendamento.getValorCobrado(), agendamento.getObservacoes(), agendamento.getDataCancelamento(),
				agendamento.getMotivoCancelamento(), agendamento.getDataCriacao(), agendamento.getDataAtualizacao());
	}

}

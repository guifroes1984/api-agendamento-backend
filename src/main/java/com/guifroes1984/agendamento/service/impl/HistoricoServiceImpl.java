package com.guifroes1984.agendamento.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guifroes1984.agendamento.dto.response.AgendamentoResponseDTO;
import com.guifroes1984.agendamento.dto.response.ClienteSimplesResponseDTO;
import com.guifroes1984.agendamento.dto.response.ProfissionalSimplesResponseDTO;
import com.guifroes1984.agendamento.dto.response.ServicoSimplesResponseDTO;
import com.guifroes1984.agendamento.enums.StatusAgendamento;
import com.guifroes1984.agendamento.model.Agendamento;
import com.guifroes1984.agendamento.repository.AgendamentoRepository;
import com.guifroes1984.agendamento.service.HistoricoService;

@Service
public class HistoricoServiceImpl implements HistoricoService {

	@Autowired
	private AgendamentoRepository agendamentoRepository;

	@Override
	public List<AgendamentoResponseDTO> obterHistoricoCliente(Long clienteId, LocalDate dataInicio, LocalDate dataFim) {
		LocalDateTime inicio = dataInicio.atStartOfDay();
		LocalDateTime fim = dataFim.atTime(23, 59, 59);

		List<Agendamento> agendamentos = agendamentoRepository.findByClienteId(clienteId);

		return agendamentos.stream()
				.filter(a -> !a.getDataHoraInicio().isBefore(inicio) && !a.getDataHoraInicio().isAfter(fim))
				.sorted((a1, a2) -> a2.getDataHoraInicio().compareTo(a1.getDataHoraInicio()))
				.map(this::toResponseDTO).collect(Collectors.toList());
	}

	@Override
	public List<AgendamentoResponseDTO> obterAgendamentosFuturosCliente(Long clienteId) {
		LocalDateTime hoje = LocalDate.now().atStartOfDay();

		List<Agendamento> agendamentos = agendamentoRepository.findByClienteId(clienteId);

		return agendamentos.stream()
				.filter(a -> a.getDataHoraInicio().isAfter(hoje) && !a.getStatus().equals(StatusAgendamento.CANCELADO)
						&& !a.getStatus().equals(StatusAgendamento.FALTOU))
				.sorted((a1, a2) -> a1.getDataHoraInicio().compareTo(a2.getDataHoraInicio())).map(this::toResponseDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<AgendamentoResponseDTO> obterAgendamentosFuturosProfissional(Long profissionalId) {
		LocalDateTime hoje = LocalDate.now().atStartOfDay();

		List<Agendamento> agendamentos = agendamentoRepository.findByProfissionalId(profissionalId);

		return agendamentos.stream()
				.filter(a -> a.getDataHoraInicio().isAfter(hoje) && !a.getStatus().equals(StatusAgendamento.CANCELADO)
						&& !a.getStatus().equals(StatusAgendamento.FALTOU))
				.sorted((a1, a2) -> a1.getDataHoraInicio().compareTo(a2.getDataHoraInicio())).map(this::toResponseDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<AgendamentoResponseDTO> obterAgendamentosDoDia(LocalDate data) {
		LocalDateTime inicio = data.atStartOfDay();
		LocalDateTime fim = data.atTime(23, 59, 59);

		List<Agendamento> todosAgendamentos = agendamentoRepository.findAll();

		return todosAgendamentos.stream()
				.filter(a -> !a.getDataHoraInicio().isBefore(inicio) && !a.getDataHoraInicio().isAfter(fim)
						&& !a.getStatus().equals(StatusAgendamento.CANCELADO))
				.sorted((a1, a2) -> a1.getDataHoraInicio().compareTo(a2.getDataHoraInicio())).map(this::toResponseDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<AgendamentoResponseDTO> obterAgendamentosPorStatusEData(String status, LocalDate data) {
		LocalDateTime inicio = data.atStartOfDay();
		LocalDateTime fim = data.atTime(23, 59, 59);

		StatusAgendamento statusEnum = StatusAgendamento.fromCodigo(status);
		List<Agendamento> todosAgendamentos = agendamentoRepository.findAll();

		return todosAgendamentos.stream()
				.filter(a -> a.getStatus().equals(statusEnum) && !a.getDataHoraInicio().isBefore(inicio)
						&& !a.getDataHoraInicio().isAfter(fim))
				.sorted((a1, a2) -> a1.getDataHoraInicio().compareTo(a2.getDataHoraInicio())).map(this::toResponseDTO)
				.collect(Collectors.toList());
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

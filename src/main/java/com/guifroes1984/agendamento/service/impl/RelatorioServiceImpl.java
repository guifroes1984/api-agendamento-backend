package com.guifroes1984.agendamento.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guifroes1984.agendamento.dto.response.EstatisticasProfissionalResponseDTO;
import com.guifroes1984.agendamento.dto.response.RelatorioAgendamentosResponseDTO;
import com.guifroes1984.agendamento.dto.response.RelatorioFinanceiroResponseDTO;
import com.guifroes1984.agendamento.model.Profissional;
import com.guifroes1984.agendamento.repository.AgendamentoRepository;
import com.guifroes1984.agendamento.repository.ProfissionalRepository;
import com.guifroes1984.agendamento.service.RelatorioService;

@Service
public class RelatorioServiceImpl implements RelatorioService {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Override
    public RelatorioAgendamentosResponseDTO gerarRelatorioAgendamentos(LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        
        List<Object[]> resultado = agendamentoRepository.obterEstatisticasAgendamentos(inicio, fim);
        
        if (resultado.isEmpty()) {
            return new RelatorioAgendamentosResponseDTO(dataInicio, dataFim, 0L, 0L, 0L, 0L, 0L);
        }
        
        Object[] stats = resultado.get(0);
        Long totalAgendamentos = ((Number) stats[0]).longValue();
        Long confirmados = ((Number) stats[1]).longValue();
        Long realizados = ((Number) stats[2]).longValue();
        Long cancelados = ((Number) stats[3]).longValue();
        Long faltas = ((Number) stats[4]).longValue();
        
        return new RelatorioAgendamentosResponseDTO(
                dataInicio, dataFim, totalAgendamentos, confirmados, realizados, cancelados, faltas);
    }

    @Override
    public RelatorioFinanceiroResponseDTO gerarRelatorioFinanceiro(LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        
        List<Object[]> resultado = agendamentoRepository.obterEstatisticasFinanceiras(inicio, fim);
        
        if (resultado.isEmpty()) {
            return new RelatorioFinanceiroResponseDTO(dataInicio, dataFim, 
                    BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, 0L);
        }
        
        Object[] stats = resultado.get(0);
        BigDecimal valorTotal = (BigDecimal) stats[0];
        BigDecimal valorRealizado = (BigDecimal) stats[1];
        BigDecimal valorCancelado = (BigDecimal) stats[2];
        Long quantidadeServicos = ((Number) stats[3]).longValue();
        
        return new RelatorioFinanceiroResponseDTO(
                dataInicio, dataFim, valorTotal, valorRealizado, valorCancelado, quantidadeServicos);
    }

    @Override
    public EstatisticasProfissionalResponseDTO gerarEstatisticasProfissional(Long profissionalId, LocalDate dataInicio, LocalDate dataFim) {
        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado com ID: " + profissionalId));
        
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        
        List<Object[]> resultado = agendamentoRepository.obterEstatisticasProfissional(profissionalId, inicio, fim);
        
        if (resultado.isEmpty()) {
            return new EstatisticasProfissionalResponseDTO(profissional, dataInicio, dataFim, 
                    0L, 0L, 0L, 0L, BigDecimal.ZERO, BigDecimal.ZERO);
        }
        
        Object[] stats = resultado.get(0);
        Long totalAtendimentos = ((Number) stats[0]).longValue();
        Long confirmados = ((Number) stats[1]).longValue();
        Long realizados = ((Number) stats[2]).longValue();
        Long cancelados = ((Number) stats[3]).longValue();
        BigDecimal faturamentoTotal = (BigDecimal) stats[4];
        BigDecimal faturamentoRealizado = (BigDecimal) stats[5];
        
        return new EstatisticasProfissionalResponseDTO(
                profissional, dataInicio, dataFim, totalAtendimentos, confirmados, 
                realizados, cancelados, faturamentoTotal, faturamentoRealizado);
    }

    @Override
    public List<Object[]> obterServicosMaisPopulares(LocalDate dataInicio, LocalDate dataFim, Integer limite) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        List<Object[]> todosServicos = agendamentoRepository.obterServicosMaisSolicitados(inicio, fim);

        return todosServicos.stream()
                .limit(limite != null ? limite : 10)
                .collect(Collectors.toList());
    }

    @Override
    public List<Object[]> obterClientesMaisAtivos(LocalDate dataInicio, LocalDate dataFim, Integer limite) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        List<Object[]> todosClientes = agendamentoRepository.obterClientesMaisAtivos(inicio, fim);

        return todosClientes.stream()
                .limit(limite != null ? limite : 10)
                .collect(Collectors.toList());
    }

    @Override
    public List<Object[]> obterHorariosMaisOcupados(LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        return agendamentoRepository.obterHorariosMaisOcupados(inicio, fim);
    }
}

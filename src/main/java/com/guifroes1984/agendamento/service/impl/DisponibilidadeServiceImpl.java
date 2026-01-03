package com.guifroes1984.agendamento.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guifroes1984.agendamento.dto.response.HorarioDisponivelResponseDTO;
import com.guifroes1984.agendamento.model.Agendamento;
import com.guifroes1984.agendamento.model.Profissional;
import com.guifroes1984.agendamento.model.Servico;
import com.guifroes1984.agendamento.repository.AgendamentoRepository;
import com.guifroes1984.agendamento.repository.ProfissionalRepository;
import com.guifroes1984.agendamento.repository.ServicoRepository;
import com.guifroes1984.agendamento.service.DisponibilidadeService;

@Service
public class DisponibilidadeServiceImpl implements DisponibilidadeService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    private static final int INTERVALO_PADRAO_MINUTOS = 15;

    @Override
    public List<HorarioDisponivelResponseDTO> verificarDisponibilidadeProfissional(
            Long profissionalId, LocalDate data, Long servicoId) {
        
        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado com ID: " + profissionalId));
        
        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + servicoId));

        if (!profissional.podeExecutarServico(servico)) {
            throw new IllegalArgumentException("Profissional não está qualificado para este serviço");
        }

        if (!profissionalTrabalhaNoDia(profissional, data)) {
            return new ArrayList<>();
        }
        
        List<HorarioDisponivelResponseDTO> horariosDisponiveis = new ArrayList<>();
        LocalTime horaAtual = profissional.getHoraInicio();
        LocalTime horaFim = profissional.getHoraFim();

        List<Agendamento> agendamentosDoDia = agendamentoRepository.findByProfissionalAndData(profissionalId, data);
        
        while (horaAtual.plusMinutes(servico.getDuracaoMinutos()).isBefore(horaFim) ||
               horaAtual.plusMinutes(servico.getDuracaoMinutos()).equals(horaFim)) {
            
            LocalDateTime dataHoraInicio = data.atTime(horaAtual);
            LocalDateTime dataHoraFim = dataHoraInicio.plusMinutes(servico.getDuracaoMinutos());
            
            boolean disponivel = true;

            for (Agendamento agendamento : agendamentosDoDia) {
                if (!agendamento.getStatus().toString().equals("CANCELADO") &&
                    !agendamento.getStatus().toString().equals("FALTOU")) {
                    
                    if (dataHoraInicio.isBefore(agendamento.getDataHoraFim()) &&
                        dataHoraFim.isAfter(agendamento.getDataHoraInicio())) {
                        disponivel = false;
                        break;
                    }
                }
            }
            
            if (disponivel) {

                HorarioDisponivelResponseDTO horario = new HorarioDisponivelResponseDTO(
                        dataHoraInicio,
                        dataHoraFim,
                        servico.getDuracaoMinutos(),
                        servico.getPreco()
                );

                horario.setProfissionalId(profissional.getId());
                horario.setProfissionalNome(profissional.getNome());
                
                horariosDisponiveis.add(horario);
            }
            
            horaAtual = horaAtual.plusMinutes(INTERVALO_PADRAO_MINUTOS);
        }
        
        return horariosDisponiveis;
    }

    @Override
    public List<HorarioDisponivelResponseDTO> verificarDisponibilidadeProfissionaisPorServico(
            LocalDate data, Long servicoId) {
        
        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + servicoId));
        
        List<Profissional> profissionais = profissionalRepository.buscarPorServico(servicoId);
        List<HorarioDisponivelResponseDTO> todosHorarios = new ArrayList<>();
        
        for (Profissional profissional : profissionais) {
            if (profissional.getAtivo() && profissionalTrabalhaNoDia(profissional, data)) {
                List<HorarioDisponivelResponseDTO> horariosProfissional = 
                        verificarDisponibilidadeProfissional(profissional.getId(), data, servicoId);

                horariosProfissional.forEach(horario -> {
                    horario.setProfissionalId(profissional.getId());
                    horario.setProfissionalNome(profissional.getNome());
                });
                
                todosHorarios.addAll(horariosProfissional);
            }
        }

        return todosHorarios.stream()
                .sorted((h1, h2) -> h1.getDataHoraInicio().compareTo(h2.getDataHoraInicio()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean verificarDisponibilidadeInstantanea(
            Long profissionalId, LocalDateTime dataHoraInicio, Integer duracaoMinutos) {
        
        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado com ID: " + profissionalId));
        
        LocalDateTime dataHoraFim = dataHoraInicio.plusMinutes(duracaoMinutos);

        if (!profissionalEstaDisponivelNoHorario(profissional, dataHoraInicio, dataHoraFim)) {
            return false;
        }

        if (!profissionalTrabalhaNoDia(profissional, dataHoraInicio.toLocalDate())) {
            return false;
        }

        List<Agendamento> conflitos = agendamentoRepository.findConflitosProfissional(
                profissionalId, dataHoraInicio, dataHoraFim);
        
        return conflitos.isEmpty();
    }

    @Override
    public List<LocalDateTime> sugerirHorariosDisponiveis(
            Long profissionalId, LocalDate data, Long servicoId, Integer quantidadeHorarios) {
        
        Profissional profissional = profissionalRepository.findById(profissionalId)
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado com ID: " + profissionalId));
        
        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado com ID: " + servicoId));
        
        List<LocalDateTime> horariosSugeridos = new ArrayList<>();
        LocalDate dataAtual = data;
        int horariosEncontrados = 0;
        int diasVerificados = 0;
        int maxDiasVerificados = 30;
        
        while (horariosEncontrados < quantidadeHorarios && diasVerificados < maxDiasVerificados) {
        	
            if (profissionalTrabalhaNoDia(profissional, dataAtual)) {
                List<HorarioDisponivelResponseDTO> horariosDisponiveis = 
                        verificarDisponibilidadeProfissional(profissionalId, dataAtual, servicoId);
                
                for (HorarioDisponivelResponseDTO horario : horariosDisponiveis) {
                    horariosSugeridos.add(horario.getDataHoraInicio());
                    horariosEncontrados++;
                    
                    if (horariosEncontrados >= quantidadeHorarios) {
                        break;
                    }
                }
            }
            
            dataAtual = dataAtual.plusDays(1);
            diasVerificados++;
        }
        
        return horariosSugeridos;
    }

    private boolean profissionalTrabalhaNoDia(Profissional profissional, LocalDate data) {
        if (profissional == null || data == null) {
            return false;
        }

        int diaSemana = data.getDayOfWeek().getValue();

        return profissional.estaDisponivelNoDiaPorCodigo(diaSemana);
    }
    
    private boolean profissionalEstaDisponivelNoHorario(Profissional profissional, 
                                                       LocalDateTime dataHoraInicio, 
                                                       LocalDateTime dataHoraFim) {
        if (profissional == null || dataHoraInicio == null || dataHoraFim == null) {
            return false;
        }
        
        LocalTime horaInicio = dataHoraInicio.toLocalTime();
        LocalTime horaFim = dataHoraFim.toLocalTime();
        
        return !horaInicio.isBefore(profissional.getHoraInicio()) &&
               !horaFim.isAfter(profissional.getHoraFim());
    }
}

package com.guifroes1984.agendamento.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guifroes1984.agendamento.dto.response.EstatisticasProfissionalResponseDTO;
import com.guifroes1984.agendamento.dto.response.RelatorioAgendamentosResponseDTO;
import com.guifroes1984.agendamento.dto.response.RelatorioFinanceiroResponseDTO;
import com.guifroes1984.agendamento.service.RelatorioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/relatorios")
@Tag(name = "Relatórios", description = "API para geração de relatórios e estatísticas")
public class RelatorioController {

    @Autowired
    private RelatorioService relatorioService;

    @GetMapping("/agendamentos")
    @Operation(summary = "Gerar relatório de agendamentos")
    public ResponseEntity<RelatorioAgendamentosResponseDTO> gerarRelatorioAgendamentos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        
        RelatorioAgendamentosResponseDTO relatorio = 
                relatorioService.gerarRelatorioAgendamentos(dataInicio, dataFim);
        
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/financeiro")
    @Operation(summary = "Gerar relatório financeiro")
    public ResponseEntity<RelatorioFinanceiroResponseDTO> gerarRelatorioFinanceiro(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        
        RelatorioFinanceiroResponseDTO relatorio = 
                relatorioService.gerarRelatorioFinanceiro(dataInicio, dataFim);
        
        return ResponseEntity.ok(relatorio);
    }

    @GetMapping("/profissional/{profissionalId}")
    @Operation(summary = "Gerar estatísticas do profissional")
    public ResponseEntity<EstatisticasProfissionalResponseDTO> gerarEstatisticasProfissional(
            @PathVariable Long profissionalId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        
        EstatisticasProfissionalResponseDTO estatisticas = 
                relatorioService.gerarEstatisticasProfissional(profissionalId, dataInicio, dataFim);
        
        return ResponseEntity.ok(estatisticas);
    }

    @GetMapping("/servicos-populares")
    @Operation(summary = "Obter serviços mais populares")
    public ResponseEntity<List<Object[]>> obterServicosMaisPopulares(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(defaultValue = "10") Integer limite) {
        
        List<Object[]> servicos = relatorioService.obterServicosMaisPopulares(dataInicio, dataFim, limite);
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/clientes-ativos")
    @Operation(summary = "Obter clientes mais ativos")
    public ResponseEntity<List<Object[]>> obterClientesMaisAtivos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(defaultValue = "10") Integer limite) {
        
        List<Object[]> clientes = relatorioService.obterClientesMaisAtivos(dataInicio, dataFim, limite);
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/horarios-ocupados")
    @Operation(summary = "Obter horários mais ocupados")
    public ResponseEntity<List<Object[]>> obterHorariosMaisOcupados(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        
        List<Object[]> horarios = relatorioService.obterHorariosMaisOcupados(dataInicio, dataFim);
        return ResponseEntity.ok(horarios);
    }
}

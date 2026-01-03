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

import com.guifroes1984.agendamento.dto.response.AgendamentoResponseDTO;
import com.guifroes1984.agendamento.service.HistoricoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/historico")
@Tag(name = "Histórico", description = "API para consulta de histórico e agendamentos")
public class HistoricoController {

    @Autowired
    private HistoricoService historicoService;

    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Obter histórico completo do cliente")
    public ResponseEntity<List<AgendamentoResponseDTO>> obterHistoricoCliente(
            @PathVariable Long clienteId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        
        List<AgendamentoResponseDTO> historico = 
                historicoService.obterHistoricoCliente(clienteId, dataInicio, dataFim);
        
        return ResponseEntity.ok(historico);
    }

    @GetMapping("/cliente/{clienteId}/futuros")
    @Operation(summary = "Obter agendamentos futuros do cliente")
    public ResponseEntity<List<AgendamentoResponseDTO>> obterAgendamentosFuturosCliente(@PathVariable Long clienteId) {
        List<AgendamentoResponseDTO> agendamentos = 
                historicoService.obterAgendamentosFuturosCliente(clienteId);
        
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/profissional/{profissionalId}/futuros")
    @Operation(summary = "Obter agendamentos futuros do profissional")
    public ResponseEntity<List<AgendamentoResponseDTO>> obterAgendamentosFuturosProfissional(@PathVariable Long profissionalId) {
        List<AgendamentoResponseDTO> agendamentos = 
                historicoService.obterAgendamentosFuturosProfissional(profissionalId);
        
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/dia")
    @Operation(summary = "Obter agendamentos do dia")
    public ResponseEntity<List<AgendamentoResponseDTO>> obterAgendamentosDoDia(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        
        List<AgendamentoResponseDTO> agendamentos = historicoService.obterAgendamentosDoDia(data);
        return ResponseEntity.ok(agendamentos);
    }

    @GetMapping("/status/{status}/data")
    @Operation(summary = "Obter agendamentos por status e data")
    public ResponseEntity<List<AgendamentoResponseDTO>> obterAgendamentosPorStatusEData(
            @PathVariable String status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        
        List<AgendamentoResponseDTO> agendamentos = 
                historicoService.obterAgendamentosPorStatusEData(status, data);
        
        return ResponseEntity.ok(agendamentos);
    }
}

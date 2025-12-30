package com.guifroes1984.agendamento.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.guifroes1984.agendamento.enums.StatusAgendamento;
import com.guifroes1984.agendamento.model.Agendamento;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
    
    List<Agendamento> findByClienteId(Long clienteId);
    
    List<Agendamento> findByProfissionalId(Long profissionalId);
    
    List<Agendamento> findByServicoId(Long servicoId);
    
    List<Agendamento> findByStatus(StatusAgendamento status);
    
    @Query("SELECT a FROM Agendamento a WHERE DATE(a.dataHoraInicio) = :data")
    List<Agendamento> findByData(@Param("data") LocalDate data);
    
    @Query("SELECT a FROM Agendamento a WHERE a.cliente.id = :clienteId AND DATE(a.dataHoraInicio) = :data")
    List<Agendamento> findByClienteAndData(@Param("clienteId") Long clienteId, @Param("data") LocalDate data);
    
    @Query("SELECT a FROM Agendamento a WHERE a.profissional.id = :profissionalId AND DATE(a.dataHoraInicio) = :data")
    List<Agendamento> findByProfissionalAndData(@Param("profissionalId") Long profissionalId, @Param("data") LocalDate data);

    @Query("SELECT a FROM Agendamento a WHERE a.profissional.id = :profissionalId " +
           "AND a.dataHoraInicio < :dataHoraFim " +
           "AND a.dataHoraFim > :dataHoraInicio " +
           "AND a.status NOT IN ('CANCELADO', 'FALTOU')")
    List<Agendamento> findConflitosProfissional(
            @Param("profissionalId") Long profissionalId,
            @Param("dataHoraInicio") LocalDateTime dataHoraInicio,
            @Param("dataHoraFim") LocalDateTime dataHoraFim);
    
    @Query("SELECT a FROM Agendamento a WHERE a.cliente.id = :clienteId " +
           "AND a.dataHoraInicio < :dataHoraFim " +
           "AND a.dataHoraFim > :dataHoraInicio " +
           "AND a.status NOT IN ('CANCELADO', 'FALTOU')")
    List<Agendamento> findConflitosCliente(
            @Param("clienteId") Long clienteId,
            @Param("dataHoraInicio") LocalDateTime dataHoraInicio,
            @Param("dataHoraFim") LocalDateTime dataHoraFim);
    
    @Query("SELECT a FROM Agendamento a WHERE a.dataHoraInicio >= :inicio AND a.dataHoraInicio <= :fim")
    List<Agendamento> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);
    
    @Query("SELECT COUNT(a) > 0 FROM Agendamento a WHERE a.profissional.id = :profissionalId " +
           "AND a.dataHoraInicio < :dataHoraFim " +
           "AND a.dataHoraFim > :dataHoraInicio " +
           "AND a.id <> :agendamentoId " +
           "AND a.status NOT IN ('CANCELADO', 'FALTOU')")
    boolean existsConflitoProfissional(
            @Param("profissionalId") Long profissionalId,
            @Param("dataHoraInicio") LocalDateTime dataHoraInicio,
            @Param("dataHoraFim") LocalDateTime dataHoraFim,
            @Param("agendamentoId") Long agendamentoId);
    
    @Query("SELECT COUNT(a) > 0 FROM Agendamento a WHERE a.cliente.id = :clienteId " +
           "AND a.dataHoraInicio < :dataHoraFim " +
           "AND a.dataHoraFim > :dataHoraInicio " +
           "AND a.id <> :agendamentoId " +
           "AND a.status NOT IN ('CANCELADO', 'FALTOU')")
    boolean existsConflitoCliente(
            @Param("clienteId") Long clienteId,
            @Param("dataHoraInicio") LocalDateTime dataHoraInicio,
            @Param("dataHoraFim") LocalDateTime dataHoraFim,
            @Param("agendamentoId") Long agendamentoId);
}

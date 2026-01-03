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
	List<Agendamento> findByProfissionalAndData(@Param("profissionalId") Long profissionalId,
			@Param("data") LocalDate data);

	@Query("SELECT a FROM Agendamento a WHERE a.profissional.id = :profissionalId "
			+ "AND a.dataHoraInicio < :dataHoraFim " + "AND a.dataHoraFim > :dataHoraInicio "
			+ "AND a.status NOT IN ('CANCELADO', 'FALTOU')")
	List<Agendamento> findConflitosProfissional(@Param("profissionalId") Long profissionalId,
			@Param("dataHoraInicio") LocalDateTime dataHoraInicio, @Param("dataHoraFim") LocalDateTime dataHoraFim);

	@Query("SELECT a FROM Agendamento a WHERE a.cliente.id = :clienteId " + "AND a.dataHoraInicio < :dataHoraFim "
			+ "AND a.dataHoraFim > :dataHoraInicio " + "AND a.status NOT IN ('CANCELADO', 'FALTOU')")
	List<Agendamento> findConflitosCliente(@Param("clienteId") Long clienteId,
			@Param("dataHoraInicio") LocalDateTime dataHoraInicio, @Param("dataHoraFim") LocalDateTime dataHoraFim);

	@Query("SELECT a FROM Agendamento a WHERE a.dataHoraInicio >= :inicio AND a.dataHoraInicio <= :fim")
	List<Agendamento> findByPeriodo(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

	@Query("SELECT COUNT(a) > 0 FROM Agendamento a WHERE a.profissional.id = :profissionalId "
			+ "AND a.dataHoraInicio < :dataHoraFim " + "AND a.dataHoraFim > :dataHoraInicio "
			+ "AND a.id <> :agendamentoId " + "AND a.status NOT IN ('CANCELADO', 'FALTOU')")
	boolean existsConflitoProfissional(@Param("profissionalId") Long profissionalId,
			@Param("dataHoraInicio") LocalDateTime dataHoraInicio, @Param("dataHoraFim") LocalDateTime dataHoraFim,
			@Param("agendamentoId") Long agendamentoId);

	@Query("SELECT COUNT(a) > 0 FROM Agendamento a WHERE a.cliente.id = :clienteId "
			+ "AND a.dataHoraInicio < :dataHoraFim " + "AND a.dataHoraFim > :dataHoraInicio "
			+ "AND a.id <> :agendamentoId " + "AND a.status NOT IN ('CANCELADO', 'FALTOU')")
	boolean existsConflitoCliente(@Param("clienteId") Long clienteId,
			@Param("dataHoraInicio") LocalDateTime dataHoraInicio, @Param("dataHoraFim") LocalDateTime dataHoraFim,
			@Param("agendamentoId") Long agendamentoId);

	@Query("SELECT " + "COUNT(a) as totalAgendamentos, "
			+ "SUM(CASE WHEN a.status = 'CONFIRMADO' THEN 1 ELSE 0 END) as confirmados, "
			+ "SUM(CASE WHEN a.status = 'REALIZADO' THEN 1 ELSE 0 END) as realizados, "
			+ "SUM(CASE WHEN a.status = 'CANCELADO' THEN 1 ELSE 0 END) as cancelados, "
			+ "SUM(CASE WHEN a.status = 'FALTOU' THEN 1 ELSE 0 END) as faltas " + "FROM Agendamento a "
			+ "WHERE a.dataHoraInicio >= :inicio AND a.dataHoraInicio <= :fim")
	List<Object[]> obterEstatisticasAgendamentos(@Param("inicio") LocalDateTime inicio,
			@Param("fim") LocalDateTime fim);

	@Query("SELECT " + "SUM(a.valorCobrado) as valorTotal, "
			+ "SUM(CASE WHEN a.status = 'REALIZADO' THEN a.valorCobrado ELSE 0 END) as valorRealizado, "
			+ "SUM(CASE WHEN a.status = 'CANCELADO' THEN a.valorCobrado ELSE 0 END) as valorCancelado, "
			+ "COUNT(a) as quantidadeServicos " + "FROM Agendamento a "
			+ "WHERE a.dataHoraInicio >= :inicio AND a.dataHoraInicio <= :fim")
	List<Object[]> obterEstatisticasFinanceiras(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

	@Query("SELECT " + "COUNT(a) as totalAtendimentos, "
			+ "SUM(CASE WHEN a.status = 'CONFIRMADO' THEN 1 ELSE 0 END) as confirmados, "
			+ "SUM(CASE WHEN a.status = 'REALIZADO' THEN 1 ELSE 0 END) as realizados, "
			+ "SUM(CASE WHEN a.status = 'CANCELADO' THEN 1 ELSE 0 END) as cancelados, "
			+ "SUM(a.valorCobrado) as faturamentoTotal, "
			+ "SUM(CASE WHEN a.status = 'REALIZADO' THEN a.valorCobrado ELSE 0 END) as faturamentoRealizado "
			+ "FROM Agendamento a " + "WHERE a.profissional.id = :profissionalId "
			+ "AND a.dataHoraInicio >= :inicio AND a.dataHoraInicio <= :fim")
	List<Object[]> obterEstatisticasProfissional(@Param("profissionalId") Long profissionalId,
			@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

	@Query("SELECT c.nome, COUNT(a) as quantidadeAgendamentos, SUM(a.valorCobrado) as valorTotal "
			+ "FROM Agendamento a JOIN a.cliente c " + "WHERE a.dataHoraInicio >= :inicio AND a.dataHoraInicio <= :fim "
			+ "AND a.status NOT IN ('CANCELADO', 'FALTOU') " + "GROUP BY c.id, c.nome "
			+ "ORDER BY quantidadeAgendamentos DESC")
	List<Object[]> obterClientesMaisAtivos(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

	@Query("SELECT s.nome, COUNT(a) as quantidade, SUM(a.valorCobrado) as faturamento "
			+ "FROM Agendamento a JOIN a.servico s " + "WHERE a.dataHoraInicio >= :inicio AND a.dataHoraInicio <= :fim "
			+ "AND a.status NOT IN ('CANCELADO', 'FALTOU') " + "GROUP BY s.id, s.nome " + "ORDER BY quantidade DESC")
	List<Object[]> obterServicosMaisSolicitados(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

	@Query("SELECT " + "HOUR(a.dataHoraInicio) as hora, " + "DAYNAME(a.dataHoraInicio) as diaSemana, "
			+ "COUNT(a) as quantidadeAgendamentos " + "FROM Agendamento a "
			+ "WHERE a.dataHoraInicio >= :inicio AND a.dataHoraInicio <= :fim "
			+ "AND a.status NOT IN ('CANCELADO', 'FALTOU') "
			+ "GROUP BY HOUR(a.dataHoraInicio), DAYNAME(a.dataHoraInicio) " + "ORDER BY quantidadeAgendamentos DESC")
	List<Object[]> obterHorariosMaisOcupados(@Param("inicio") LocalDateTime inicio, @Param("fim") LocalDateTime fim);

	@Query("SELECT a FROM Agendamento a " + "WHERE a.dataHoraInicio >= :inicio AND a.dataHoraInicio <= :fim "
			+ "AND a.cliente.id = :clienteId " + "ORDER BY a.dataHoraInicio DESC")
	List<Agendamento> findHistoricoCliente(@Param("clienteId") Long clienteId, @Param("inicio") LocalDateTime inicio,
			@Param("fim") LocalDateTime fim);

	@Query("SELECT a FROM Agendamento a " + "WHERE a.profissional.id = :profissionalId " + "AND a.status = 'AGENDADO' "
			+ "AND a.dataHoraInicio >= :hoje " + "ORDER BY a.dataHoraInicio ASC")
	List<Agendamento> findAgendamentosFuturosProfissional(@Param("profissionalId") Long profissionalId,
			@Param("hoje") LocalDateTime hoje);
}

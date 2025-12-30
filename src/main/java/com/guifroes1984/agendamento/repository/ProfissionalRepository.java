package com.guifroes1984.agendamento.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.guifroes1984.agendamento.model.Profissional;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

	Optional<Profissional> findByEmail(String email);

	Optional<Profissional> findByTelefone(String telefone);

	List<Profissional> findByAtivoTrue();

	List<Profissional> findByAtivo(Boolean ativo);

	List<Profissional> findByNomeContainingIgnoreCase(String nome);

	@Query("SELECT p FROM Profissional p WHERE p.ativo = true "
			+ "AND LOWER(p.nome) LIKE LOWER(CONCAT('%', :termo, '%'))")
	List<Profissional> buscarAtivosPorTermo(@Param("termo") String termo);

	@Query("SELECT p FROM Profissional p JOIN p.especialidades s WHERE s.id = :servicoId AND p.ativo = true")
	List<Profissional> buscarPorServico(@Param("servicoId") Long servicoId);

	@Query("SELECT p FROM Profissional p WHERE p.ativo = true "
			+ "AND :horaInicio >= p.horaInicio AND :horaFim <= p.horaFim")
	List<Profissional> buscarDisponiveisPorHorario(@Param("horaInicio") LocalTime horaInicio,
			@Param("horaFim") LocalTime horaFim);

	@Query("SELECT COUNT(p) > 0 FROM Profissional p WHERE p.email = :email")
	boolean existsByEmail(@Param("email") String email);

	@Query("SELECT COUNT(p) > 0 FROM Profissional p WHERE p.telefone = :telefone")
	boolean existsByTelefone(@Param("telefone") String telefone);

	@Query("SELECT COUNT(p) > 0 FROM Profissional p WHERE p.email = :email AND p.id <> :id")
	boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Long id);

	@Query("SELECT COUNT(p) > 0 FROM Profissional p WHERE p.telefone = :telefone AND p.id <> :id")
	boolean existsByTelefoneAndIdNot(@Param("telefone") String telefone, @Param("id") Long id);
}

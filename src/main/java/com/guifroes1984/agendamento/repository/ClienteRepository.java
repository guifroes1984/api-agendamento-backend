package com.guifroes1984.agendamento.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.guifroes1984.agendamento.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	Optional<Cliente> findByEmail(String email);

	Optional<Cliente> findByTelefone(String telefone);

	List<Cliente> findByNomeContainingIgnoreCase(String nome);

	@Query("SELECT c FROM Cliente c WHERE LOWER(c.nome) LIKE LOWER(CONCAT('%', :termo, '%')) "
			+ "OR LOWER(c.email) LIKE LOWER(CONCAT('%', :termo, '%')) " + "OR c.telefone LIKE CONCAT('%', :termo, '%')")
	List<Cliente> buscarPorTermo(@Param("termo") String termo);

	boolean existsByEmail(String email);

	boolean existsByTelefone(String telefone);
}

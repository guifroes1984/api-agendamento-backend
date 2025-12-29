package com.guifroes1984.agendamento.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.guifroes1984.agendamento.model.Servico;

@Repository
public interface ServicoRepository extends JpaRepository<Servico, Long> {

    Optional<Servico> findByNome(String nome);

    List<Servico> findByNomeContainingIgnoreCase(String nome);
    
    List<Servico> findByAtivoTrue();
    
    List<Servico> findByAtivo(Boolean ativo);
    
    @Query("SELECT s FROM Servico s WHERE s.ativo = true AND LOWER(s.nome) LIKE LOWER(CONCAT('%', :termo, '%'))")
    List<Servico> buscarAtivosPorTermo(@Param("termo") String termo);
    
    boolean existsByNome(String nome);
    
    @Query("SELECT COUNT(s) > 0 FROM Servico s WHERE s.nome = :nome AND s.id <> :id")
    boolean existsByNomeAndIdNot(@Param("nome") String nome, @Param("id") Long id);
}

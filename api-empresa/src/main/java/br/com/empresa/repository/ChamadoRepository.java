package br.com.empresa.repository;

import br.com.empresa.entity.Chamado;
import br.com.empresa.enums.Prioridade;
import br.com.empresa.enums.StatusAtendimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChamadoRepository extends JpaRepository<Chamado,Long> {
    Optional<Chamado> findByProtocolo(String protocolo);
    List<Chamado> findByStatus(StatusAtendimento status);
    Page<Chamado> findByStatus(StatusAtendimento status, Pageable pageable);
    long countByStatus(StatusAtendimento status);
    long countByPrioridade(Prioridade prioridade);
    @Query("SELECT COUNT(c) FROM Chamado c WHERE c.status = :status AND c.dataInicial BETWEEN :inicio AND :fim")
    long countByStatusAndPeriodo(@Param("status") StatusAtendimento status,
                                 @Param("inicio") LocalDate inicio,
                                 @Param("fim") LocalDate fim);

    @Query("SELECT COUNT(c) FROM Chamado c WHERE c.prioridade = :prioridade AND c.dataInicial BETWEEN :inicio AND :fim")
    long countByPrioridadeAndPeriodo(@Param("prioridade") Prioridade prioridade,
                                     @Param("inicio") LocalDate inicio,
                                     @Param("fim") LocalDate fim);

    @Query("SELECT COUNT(c) FROM Chamado c WHERE c.dataInicial BETWEEN :inicio AND :fim")
    long countByPeriodo(@Param("inicio") LocalDate inicio,
                        @Param("fim") LocalDate fim);
}

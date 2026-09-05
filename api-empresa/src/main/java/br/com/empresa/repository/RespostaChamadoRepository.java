package br.com.empresa.repository;

import br.com.empresa.entity.RespostaChamado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RespostaChamadoRepository extends JpaRepository<RespostaChamado,Long> {

    List<RespostaChamado> findByChamadoId(Long id);
}

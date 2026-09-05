package br.com.empresa.repository;

import br.com.empresa.dto.ClienteDTO;
import br.com.empresa.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
   Optional<Cliente> findByCpf(String cpf);
}

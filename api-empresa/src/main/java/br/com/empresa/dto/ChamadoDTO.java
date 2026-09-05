package br.com.empresa.dto;

import br.com.empresa.entity.Cliente;
import br.com.empresa.enums.Prioridade;
import br.com.empresa.enums.Setor;
import br.com.empresa.enums.StatusAtendimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChamadoDTO {

    private Long id;
    private String protocolo;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private String assunto;
    private Setor setor;
    private Prioridade prioridade;
    private String descricao;
    private StatusAtendimento status;
    private Cliente cliente;
}

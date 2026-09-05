package br.com.empresa.dto;


import br.com.empresa.entity.Chamado;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespostaRequisicaoDTO {

    private Long id;
    private String descricao;
    private LocalDateTime dataCriacao;
    private Chamado chamado;
}

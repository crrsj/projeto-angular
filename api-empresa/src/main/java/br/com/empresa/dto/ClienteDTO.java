package br.com.empresa.dto;

import br.com.empresa.entity.Chamado;
import br.com.empresa.entity.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Long id;
    private String nome;
    @JsonProperty("cpfOuCnpj")
    private String cpf;
    private String telefone;
    private String email;
    private Endereco endereco;
    private Chamado chamado;
}

package br.com.empresa.dto;


import br.com.empresa.entity.Cliente;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {
    private String cep;
    private String logradouro;
    private String bairro;
    @JsonProperty("localidade")
    private String cidade;
    private String estado;
    private String uf;
    private String numero;
    private String complemento;
    private Cliente cliente;
}

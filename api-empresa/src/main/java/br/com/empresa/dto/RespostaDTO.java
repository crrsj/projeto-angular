package br.com.empresa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespostaDTO {
    @NotBlank(message = "A descrição da resposta é obrigatória.")
    private String descricao;

}

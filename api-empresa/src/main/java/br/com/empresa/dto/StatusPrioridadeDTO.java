package br.com.empresa.dto;


import br.com.empresa.enums.Prioridade;
import br.com.empresa.enums.StatusAtendimento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusPrioridadeDTO {

    private  Long id;
    private StatusAtendimento status;
    private Prioridade prioridade;
}

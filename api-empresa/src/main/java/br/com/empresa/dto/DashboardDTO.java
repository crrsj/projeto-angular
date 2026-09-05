package br.com.empresa.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardDTO {

    private long totalAbertos;
    private long totalEmAndamento;
    private long totalFechados;
    private long totalPrioridadeBaixa;
    private long totalPrioridadeMedia;
    private long totalPrioridadeAlta;
    private long totalChamados;

}

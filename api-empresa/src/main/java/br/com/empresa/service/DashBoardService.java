package br.com.empresa.service;


import br.com.empresa.dto.DashboardDTO;
import br.com.empresa.enums.Prioridade;
import br.com.empresa.enums.StatusAtendimento;
import br.com.empresa.repository.ChamadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashBoardService {

    private final ChamadoRepository chamadoRepository;

    public DashboardDTO obterDadosDashboard() {
        return DashboardDTO.builder()
                .totalAbertos(chamadoRepository.countByStatus(StatusAtendimento.ABERTO))
                .totalEmAndamento(chamadoRepository.countByStatus(StatusAtendimento.EM_ANDAMENTO))
                .totalFechados(chamadoRepository.countByStatus(StatusAtendimento.FECHADO))
                .totalPrioridadeBaixa(chamadoRepository.countByPrioridade(Prioridade.BAIXA))
                .totalPrioridadeMedia(chamadoRepository.countByPrioridade(Prioridade.MEDIA))
                .totalPrioridadeAlta(chamadoRepository.countByPrioridade(Prioridade.ALTA))
                .totalChamados(chamadoRepository.count())
                .build();
    }
}

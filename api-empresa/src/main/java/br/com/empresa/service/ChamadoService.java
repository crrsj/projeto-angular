package br.com.empresa.service;


import br.com.empresa.dto.AtualizarChamadoDTO;
import br.com.empresa.dto.ChamadoDTO;
import br.com.empresa.dto.DashboardDTO;
import br.com.empresa.dto.StatusPrioridadeDTO;
import br.com.empresa.entity.Chamado;
import br.com.empresa.enums.Prioridade;
import br.com.empresa.enums.StatusAtendimento;
import br.com.empresa.exceptions.ChamadoNaoEncontrado;
import br.com.empresa.exceptions.ClienteNaoEncntrado;
import br.com.empresa.repository.ChamadoRepository;
import br.com.empresa.repository.ClienteRepository;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.UnitValue;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import com.itextpdf.layout.element.Table;

@Service
@RequiredArgsConstructor
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;
    private final ModelMapper modelMapper;
    private final ClienteRepository clienteRepository;


    @Transactional
    public ChamadoDTO salvarChamado(Long clienteId, ChamadoDTO chamadoDTO) {
        var cliente = clienteRepository.findById(clienteId).orElseThrow(() -> new ClienteNaoEncntrado("Cliente não encontrado."));
        var chamado = modelMapper.map(chamadoDTO, Chamado.class);
        chamado.setDataInicial(LocalDate.now()); // Seta a data atual
        chamado.setDataFinal(null);
        chamado.setStatus(StatusAtendimento.ABERTO);
        chamado.setCliente(cliente);
        var novoChamado = chamadoRepository.save(chamado);
        return modelMapper.map(novoChamado, ChamadoDTO.class);
    }

    public Page<ChamadoDTO> listarChamados(Pageable pageable) {
        return chamadoRepository.findAll(pageable).
                map(chamados -> modelMapper.map(chamados, ChamadoDTO.class));
    }

    public Chamado buscarPorIdOuLancarExcessao(Long id) {
        return chamadoRepository.findById(id).orElseThrow(() -> new ChamadoNaoEncontrado("Chamado não encontrado."));
    }

    public ChamadoDTO buscarChamadoPorId(Long id) {
        var chamado = buscarPorIdOuLancarExcessao(id);
        return modelMapper.map(chamado, ChamadoDTO.class);
    }



    public ChamadoDTO buscarPorProtocolo(String protocolo) {
        var chamado = chamadoRepository.findByProtocolo(protocolo).
                orElseThrow(() -> new ChamadoNaoEncontrado("Chamado não encontrado."));
        return modelMapper.map(chamado, ChamadoDTO.class);

    }

    public Page<ChamadoDTO> buscarPorStatus(StatusAtendimento status, Pageable pageable) {
        return chamadoRepository.findByStatus(status, pageable)
                .map(chamado -> modelMapper.map(chamado, ChamadoDTO.class));
    }

    public Page<ChamadoDTO> listarAbertos(Pageable pageable) {
        return buscarPorStatus(StatusAtendimento.ABERTO, pageable);
    }

    public Page<ChamadoDTO> listarEmAndamento(Pageable pageable) {
        return buscarPorStatus(StatusAtendimento.EM_ANDAMENTO, pageable);
    }

    public Page<ChamadoDTO> listarFechados(Pageable pageable) {
        return buscarPorStatus(StatusAtendimento.FECHADO, pageable);
    }

    @Transactional
    public AtualizarChamadoDTO atualizarChamado(Long id, AtualizarChamadoDTO atualizarChamadoDTO) {
        var chamado = buscarPorIdOuLancarExcessao(id);
        modelMapper.map(atualizarChamadoDTO, chamado);
        var chamadoAtualizado = chamadoRepository.save(chamado);
        return modelMapper.map(chamado, AtualizarChamadoDTO.class);
    }

    @Transactional
    public StatusPrioridadeDTO atualizarStatus(Long id, StatusPrioridadeDTO status) {
        var chamado = buscarPorIdOuLancarExcessao(id);
        modelMapper.map(status, chamado);
        // Regra de negócio para gerenciamento automático da dataFinal
        if (chamado.getStatus() == StatusAtendimento.FECHADO) {
            chamado.setDataFinal(LocalDate.now());
        } else {
            // Se o chamado for reaberto ou alterado para outro status, limpa a data final
            chamado.setDataFinal(null);
        }
        var statusAtualizado = chamadoRepository.save(chamado);
        return modelMapper.map(statusAtualizado, StatusPrioridadeDTO.class);
    }

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

    public ByteArrayInputStream gerarRelatorioPdf(LocalDate inicio, LocalDate fim) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        long abertos = chamadoRepository.countByStatusAndPeriodo(StatusAtendimento.ABERTO, inicio, fim);
        long emAndamento = chamadoRepository.countByStatusAndPeriodo(StatusAtendimento.EM_ANDAMENTO, inicio, fim);
        long fechados = chamadoRepository.countByStatusAndPeriodo(StatusAtendimento.FECHADO, inicio, fim);

        long baixa = chamadoRepository.countByPrioridadeAndPeriodo(Prioridade.BAIXA, inicio, fim);
        long media = chamadoRepository.countByPrioridadeAndPeriodo(Prioridade.MEDIA, inicio, fim);
        long alta = chamadoRepository.countByPrioridadeAndPeriodo(Prioridade.ALTA, inicio, fim);

        long total = chamadoRepository.countByPeriodo(inicio, fim);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Cabeçalho
            document.add(new Paragraph("Relatório Consolidado de Chamados")
                    .setBold().setFontSize(18));
            document.add(new Paragraph("Período: " + inicio.format(formatter) + " até " + fim.format(formatter))
                    .setFontSize(12));
            document.add(new Paragraph("\n"));

            // Tabela de Métricas
            Table table = new Table(UnitValue.createPercentArray(new float[]{50, 50})).useAllAvailableWidth();

            table.addHeaderCell("Métrica").setBold();
            table.addHeaderCell("Quantidade").setBold();

            table.addCell("Chamados Abertos");
            table.addCell(String.valueOf(abertos));

            table.addCell("Chamados Em Andamento");
            table.addCell(String.valueOf(emAndamento));

            table.addCell("Chamados Fechados");
            table.addCell(String.valueOf(fechados));

            table.addCell("Prioridade Baixa");
            table.addCell(String.valueOf(baixa));

            table.addCell("Prioridade Média");
            table.addCell(String.valueOf(media));

            table.addCell("Prioridade Alta");
            table.addCell(String.valueOf(alta));

            table.addCell("Total de Chamados no Período").setBold();
            table.addCell(String.valueOf(total)).setBold();

            document.add(table);
            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar o relatório PDF", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
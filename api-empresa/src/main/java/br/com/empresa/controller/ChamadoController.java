package br.com.empresa.controller;


import br.com.empresa.dto.AtualizarChamadoDTO;
import br.com.empresa.dto.ChamadoDTO;
import br.com.empresa.dto.DashboardDTO;
import br.com.empresa.enums.StatusAtendimento;
import br.com.empresa.service.ChamadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;

import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping("/api/chamados")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ChamadoController {

    private final ChamadoService chamadoService;

    @PostMapping("/{clienteId}")
    public ResponseEntity<ChamadoDTO>salvarChamado(@PathVariable Long clienteId, @RequestBody ChamadoDTO  chamadoDTO){
        return ResponseEntity.status(HttpStatus.CREATED).
                body(chamadoService.salvarChamado(clienteId,chamadoDTO));
    }


    @GetMapping
    public ResponseEntity<Page<ChamadoDTO>>listarChamados(@PageableDefault(page = 0, size = 10, sort = "id",
            direction = Sort.Direction.DESC)Pageable pageable){
        Page<ChamadoDTO>chamado = chamadoService.listarChamados(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(chamado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChamadoDTO>buscarChamadoPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).
                body(chamadoService.buscarChamadoPorId(id));
    }

    @GetMapping("/protocolo")
    public ResponseEntity<ChamadoDTO>buscarPorProtocolo(@RequestParam String protocolo){
        return ResponseEntity.status(HttpStatus.OK).
                body(chamadoService.buscarPorProtocolo(protocolo));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<ChamadoDTO>> buscarPorStatus(
            @PathVariable StatusAtendimento status,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ChamadoDTO> page = chamadoService.buscarPorStatus(status, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/abertos")
    public ResponseEntity<Page<ChamadoDTO>> listarAbertos(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ChamadoDTO> page = chamadoService.listarAbertos(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/em-andamento")
    public ResponseEntity<Page<ChamadoDTO>> listarEmAndamento(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ChamadoDTO> page = chamadoService.listarEmAndamento(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/fechados")
    public ResponseEntity<Page<ChamadoDTO>> listarFechados(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        Page<ChamadoDTO> page = chamadoService.listarFechados(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> obterDashboard() {
        return ResponseEntity.status(HttpStatus.OK).body(chamadoService.obterDadosDashboard());
    }


    @GetMapping("/relatorio-pdf")
    public ResponseEntity<InputStreamResource> gerarRelatorioPdf(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {

        ByteArrayInputStream pdfStream = chamadoService.gerarRelatorioPdf(inicio, fim);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=relatorio-chamados.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(pdfStream));
    }


    @PatchMapping("/{id}")
    public ResponseEntity<AtualizarChamadoDTO>atualizarChamados(@PathVariable Long id, @RequestBody AtualizarChamadoDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(chamadoService.atualizarChamado(id,dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void>excluirChamado(@PathVariable Long id){
        chamadoService.excluirChamado(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

package br.com.empresa.controller;


import br.com.empresa.dto.RespostaDTO;
import br.com.empresa.dto.RespostaRequisicaoDTO;
import br.com.empresa.service.RespostaChamadoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/respostas")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class RespostaController {

    private final RespostaChamadoService service;

    @PostMapping("/{id}/respostas")
    public ResponseEntity<RespostaRequisicaoDTO> adicionarResposta(
            @PathVariable Long id,
            @Valid @RequestBody RespostaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.adicionarResposta(id, dto));
    }

    @GetMapping("/{id}/respostas")
    public ResponseEntity<List<RespostaRequisicaoDTO>> listarRespostas(@PathVariable Long id) {
        List<RespostaRequisicaoDTO> respostas = service.listarRespostasDoChamado(id);
        return ResponseEntity.ok(respostas);
    }
}

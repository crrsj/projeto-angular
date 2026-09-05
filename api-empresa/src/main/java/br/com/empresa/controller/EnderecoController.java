package br.com.empresa.controller;


import br.com.empresa.dto.EnderecoDTO;
import br.com.empresa.repository.EnderecoRepository;
import br.com.empresa.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/endereco")
@RequiredArgsConstructor
public class EnderecoController {


    private final EnderecoService enderecoService;
/*
    @PostMapping("/{clienteId}")
    public ResponseEntity<EnderecoDTO>salvarEndereco(@PathVariable long clienteId,
                                                     @RequestBody EnderecoDTO enderecoDTO){
        return ResponseEntity.status(HttpStatus.CREATED).
                body(enderecoService.salvarEndereco(clienteId,enderecoDTO));
    }
*/
    @GetMapping("/cep")
    public ResponseEntity<EnderecoDTO>buscarPorCel(@RequestParam String cep){
        return ResponseEntity.status(HttpStatus.OK).body(enderecoService.buscarPorCep(cep));
    }

}

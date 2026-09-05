package br.com.empresa.controller;


import br.com.empresa.dto.ClienteDTO;

import br.com.empresa.service.ClienteService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteDTO>salvarCliente(@RequestBody ClienteDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.salvarCliente(dto));
    }

    @GetMapping
    public ResponseEntity<Page<ClienteDTO>>listarClientes( @PageableDefault(page = 0, size = 10, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable){
        Page<ClienteDTO>cliente = clienteService.listarClientes(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(cliente);
 }

     @GetMapping("/{id}")
     public ResponseEntity<ClienteDTO>cuscarPorId(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscarPorId(id));
 }
     @PatchMapping("/{id}")
     public ResponseEntity<ClienteDTO>atualizarCliente(@PathVariable Long id,@RequestBody ClienteDTO clienteDTO){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.atualizarCliente(id,clienteDTO));
 }

     @DeleteMapping("/{id}")
     public ResponseEntity<Void>excluirClientes(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
     }

     @GetMapping("/cpf")
     public ResponseEntity<ClienteDTO>buscarPorCpf(@RequestParam String cpf){
        return ResponseEntity.status(HttpStatus.OK).body(clienteService.buscarPorCpf(cpf));
     }
}

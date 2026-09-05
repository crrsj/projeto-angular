package br.com.empresa.service;

import br.com.empresa.dto.ClienteDTO;

import br.com.empresa.dto.EnderecoDTO;
import br.com.empresa.entity.Cliente;
import br.com.empresa.entity.Endereco;
import br.com.empresa.exceptions.ClienteNaoEncontrado;
import br.com.empresa.repository.ClienteRepository;
import br.com.empresa.viacep.ViaCepClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    private final ViaCepClient viaCepClient;
/*

    @Transactional
    public ClienteDTO salvarCliente(ClienteDTO dto){
     var cliente = modelMapper.map(dto, Cliente.class);
     var novoCliente = clienteRepository.save(cliente);
     return modelMapper.map(novoCliente, ClienteDTO.class);


  }

  */

    @Transactional
    public ClienteDTO salvarCliente(ClienteDTO clienteDTO) {
        if (clienteDTO.getEndereco() == null) {
            throw new IllegalArgumentException("Os dados de endereço são obrigatórios.");
        }

        // 1. Mapeia a entidade Cliente
        var cliente = modelMapper.map(clienteDTO, Cliente.class);
        var enderecoDTO = clienteDTO.getEndereco();

        // 2. Valida e higieniza o CEP enviado
        if (enderecoDTO.getCep() == null || enderecoDTO.getCep().isBlank()) {
            throw new IllegalArgumentException("O CEP é obrigatório.");
        }
        String cepLimpo = enderecoDTO.getCep().replaceAll("\\D", "");

        if (cepLimpo.length() != 8) {
            throw new IllegalArgumentException("CEP inválido. Deve conter 8 dígitos.");
        }

        // 3. Consulta a API do ViaCEP
        EnderecoDTO viaCep = viaCepClient.buscarEnderecoPorCep(cepLimpo);

        // 4. Monta a entidade Endereco mesclando dados da API e do DTO (número e complemento)
        var endereco = modelMapper.map(enderecoDTO, Endereco.class);
        endereco.setCep(cepLimpo);
        endereco.setLogradouro(viaCep.getLogradouro());
        endereco.setBairro(viaCep.getBairro());
        endereco.setCidade(viaCep.getCidade()); // Ajuste se no ViaCEP o campo for localidade
        endereco.setUf(viaCep.getUf());
        endereco.setEstado(viaCep.getEstado());

        // 5. Estabelece o relacionamento bi-direcional
        endereco.setCliente(cliente);
        cliente.setEndereco(endereco);

        // 6. Salva o cliente (com CascadeType.ALL na entidade Cliente, o Endereco é salvo automaticamente)
        var clienteSalvo = clienteRepository.save(cliente);

        return modelMapper.map(clienteSalvo, ClienteDTO.class);
    }

  public Page<ClienteDTO>listarClientes(Pageable pageable){
        return clienteRepository.findAll(pageable)
                .map(clientes->modelMapper.map(clientes, ClienteDTO.class));
  }

  public Cliente buscarPorIdOuLancarExcessao(Long id){
        return clienteRepository.findById(id)
                .orElseThrow(()->new ClienteNaoEncontrado("Cliente não encontrado."));
  }

  public ClienteDTO buscarPorId(Long id){
        var cliente = buscarPorIdOuLancarExcessao(id);
        return modelMapper.map(cliente, ClienteDTO.class);
  }



  @Transactional
  public ClienteDTO atualizarCliente(Long id, ClienteDTO clienteDTO){
        var cliente = buscarPorIdOuLancarExcessao(id);
        modelMapper.map(clienteDTO,cliente);
        var clienteSalvo = clienteRepository.save(cliente);
        return modelMapper.map(clienteSalvo, ClienteDTO.class);
  }

  @Transactional
  public void excluirCliente(Long id){
        var cliente = buscarPorIdOuLancarExcessao((id));
        clienteRepository.delete(cliente);
  }


  public ClienteDTO buscarPorCpf(String cpf){
        var cliente  = clienteRepository.findByCpf(cpf).orElseThrow(()->new ClienteNaoEncontrado("Cliente não encontrado."));
        return modelMapper.map(cliente, ClienteDTO.class);
  }
}

package br.com.empresa.service;

import br.com.empresa.dto.ClienteDTO;
import br.com.empresa.dto.EnderecoDTO;
import br.com.empresa.entity.Cliente;
import br.com.empresa.entity.Endereco;
import br.com.empresa.exceptions.ClienteNaoEncontrado;
import br.com.empresa.repository.ClienteRepository;
import br.com.empresa.repository.EnderecoRepository;
import br.com.empresa.viacep.ViaCepClient;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final ViaCepClient viaCepClient;
    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;
    private final EnderecoRepository enderecoRepository;

    public EnderecoDTO buscarPorCep(String cep) {
        return viaCepClient.buscarEnderecoPorCep(cep);

    }

/*
    @Transactional
    public EnderecoDTO salvarEndereco(Long clienteId,EnderecoDTO enderecoDTO){
        // 1. Busca o cliente
        var cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNaoEncontrado("Cliente não encontrado."));

        // 2. Valida e higieniza o CEP enviado no DTO
        if (enderecoDTO.getCep() == null || enderecoDTO.getCep().isBlank()) {
            throw new IllegalArgumentException("O CEP é obrigatório.");
        }
        String cepLimpo = enderecoDTO.getCep().replaceAll("\\D", "");

        if (cepLimpo.length() != 8) {
            throw new IllegalArgumentException("CEP inválido. Deve conter 8 dígitos.");
        }

        // 3. Consulta a API do ViaCEP
        EnderecoDTO viaCep = viaCepClient.buscarEnderecoPorCep(cepLimpo);

        // 4. Mapeia os dados do DTO (que traz número e complemento informados pelo usuário)
        var endereco = modelMapper.map(enderecoDTO, Endereco.class);

        // 5. Preenche/sobrescreve os dados com o retorno do ViaCEP
        endereco.setCep(cepLimpo);
        endereco.setLogradouro(viaCep.getLogradouro());
        endereco.setBairro(viaCep.getBairro());
        endereco.setCidade(viaCep.getCidade()); // Certifique-se de que no EnderecoResponse usou @JsonProperty("localidade")
        endereco.setUf(viaCep.getUf());
        endereco.setEstado(viaCep.getEstado());

        // 6. Vincula ambos os lados do relacionamento OneToOne
        endereco.setCliente(cliente);
        cliente.setEndereco(endereco);

        // 7. Salva o endereço (o Hibernate reflete a chave no cliente via Cascade/OrphanRemoval)
        var enderecoNovo = enderecoRepository.save(endereco);

        return modelMapper.map(enderecoNovo, EnderecoDTO.class);
    }

*/




}

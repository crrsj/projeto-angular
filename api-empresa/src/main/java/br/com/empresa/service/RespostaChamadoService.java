package br.com.empresa.service;


import br.com.empresa.dto.RespostaDTO;
import br.com.empresa.dto.RespostaRequisicaoDTO;
import br.com.empresa.entity.Chamado;
import br.com.empresa.entity.RespostaChamado;
import br.com.empresa.exceptions.ChamadoNaoEncontrado;
import br.com.empresa.repository.ChamadoRepository;
import br.com.empresa.repository.RespostaChamadoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RespostaChamadoService {

    private final RespostaChamadoRepository repository;
    private final ModelMapper modelMapper;
    private final ChamadoRepository chamadoRepository;



    @Transactional
    public RespostaRequisicaoDTO adicionarResposta(Long chamadoId, RespostaDTO dto) {
        Chamado chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new ChamadoNaoEncontrado("Chamado não encontrado com o ID: " + chamadoId));

        var resposta = new RespostaChamado();
        resposta.setDescricao(dto.getDescricao());
        resposta.setChamado(chamado);
        // Salva diretamente a nova resposta no banco
        var  respostaSalva = repository.save(resposta);

        return modelMapper.map(respostaSalva, RespostaRequisicaoDTO.class);
    }

    public List<RespostaRequisicaoDTO> listarRespostasDoChamado(Long chamadoId) {
        if (!chamadoRepository.existsById(chamadoId)) {
            throw new ChamadoNaoEncontrado("Chamado não encontrado com o ID: " + chamadoId);
        }
        List<RespostaChamado> respostas = repository.findByChamadoId(chamadoId);
        return respostas.stream()
                .map(r -> modelMapper.map(r, RespostaRequisicaoDTO.class))
                .collect(Collectors.toList());
    }
}

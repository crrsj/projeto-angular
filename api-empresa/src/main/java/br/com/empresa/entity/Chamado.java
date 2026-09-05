package br.com.empresa.entity;


import br.com.empresa.enums.Prioridade;
import br.com.empresa.enums.Setor;
import br.com.empresa.enums.StatusAtendimento;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tb_chamados")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, updatable = false)
    private String protocolo;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private String assunto;
    @Enumerated(EnumType.STRING)
    private Setor setor;
    @Enumerated(EnumType.STRING)
    private Prioridade prioridade;
    @Column(columnDefinition = "TEXT")
    private String descricao;
    private StatusAtendimento status;
    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    @JsonIgnore
    private Cliente cliente;
    @OneToMany(mappedBy = "chamado", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<RespostaChamado> resposta= new ArrayList<>();


    @PrePersist
    private void gerarProtocolo() {
        if (this.protocolo == null) {
            // Pega a data/hora atual no formato AAAAMMDDHHMMSS
            String dataHora = java.time.LocalDateTime.now()
                    .format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));

            // Gera 4 dígitos numéricos aleatórios para evitar duplicidade em requisições simultâneas
            int numeroAleatorio = java.util.concurrent.ThreadLocalRandom.current().nextInt(1000, 9999);

            // Junta tudo em uma única sequência numérica
            this.protocolo = dataHora + numeroAleatorio;
            // Exemplo de resultado: 202609012205308492
        }
    }
}

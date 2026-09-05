package br.com.empresa.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_respostas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespostaChamado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name = "chamado_id", nullable = false)
    @JsonIgnore
    private Chamado chamado;
}

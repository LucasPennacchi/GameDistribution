package com.gamedistribution.gamedistribution.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * Representa a entidade JOGO no banco de dados.
 */
@Entity
@Table(name = "JOGO")
@Getter
@Setter
@ToString(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Jogo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jogo")
    private Long id;

    @Column(name = "titulo", nullable = false, unique = true, length = 255)
    private String titulo;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "data_lancamento")
    private LocalDate dataLancamento;

    /** Relacionamento N:1. É o lado do Many, mas não é ignorado na serialização. */
    @ManyToOne
    @JoinColumn(name = "id_editora", nullable = false)
    private Editora editora;

    /** Relacionamento N:1. É o lado do Many, mas não é ignorado na serialização. */
    @ManyToOne
    @JoinColumn(name = "id_genero", nullable = false)
    private Genero genero;

    /** Relacionamento 1:N com ChaveAtivacao. Ignorado para evitar ciclos de serialização. */
    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ChaveAtivacao> chaves;

    /** Relacionamento N:M (via tabela de associação). Ignorado para evitar ciclos. */
    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<JogoPublicadora> publicacoes;
}
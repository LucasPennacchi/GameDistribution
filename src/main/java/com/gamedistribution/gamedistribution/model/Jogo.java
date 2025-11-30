package com.gamedistribution.gamedistribution.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * Representa a entidade JOGO no banco de dados.
 * Mapeia a tabela 'JOGO' e contém informações detalhadas sobre um jogo digital.
 */
@Entity
@Table(name = "JOGO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Jogo {

    /**
     * Chave primária da entidade Jogo.
     * Mapeia a coluna 'id_jogo' e é auto-incrementada.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jogo")
    private Long id;

    /**
     * Título do jogo. Campo obrigatório.
     */
    @Column(name = "titulo", nullable = false, unique = true, length = 255)
    private String titulo;

    /**
     * Descrição detalhada do jogo.
     */
    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    /**
     * Preço de venda do jogo. Campo obrigatório.
     */
    @Column(name = "preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;

    /**
     * Data de lançamento do jogo.
     */
    @Column(name = "data_lancamento")
    private LocalDate dataLancamento;

    /**
     * Relacionamento N:1 com a entidade Editora.
     * Mapeia a chave estrangeira 'id_editora'.
     */
    @ManyToOne
    @JoinColumn(name = "id_editora", nullable = false)
    private Editora editora;

    /**
     * Relacionamento N:1 com a entidade Genero.
     * Mapeia a chave estrangeira 'id_genero'.
     */
    @ManyToOne
    @JoinColumn(name = "id_genero", nullable = false)
    private Genero genero;

    /**
     * Relacionamento Um-para-Muitos (1:N) com ChaveAtivacao.
     * @JsonIgnore impede que a serialização de Jogo tente carregar todas as chaves (Lazy Loading).
     */
    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<ChaveAtivacao> chaves;

    /**
     * Relacionamento Muitos-para-Muitos (N:N) com Publicadora, mapeado via JogoPublicadora.
     * @JsonIgnore impede que a serialização tente carregar todos os relacionamentos M:N.
     */
    @OneToMany(mappedBy = "jogo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<JogoPublicadora> publicacoes;
}
package com.gamedistribution.gamedistribution.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;

/**
 * Representa a entidade GENERO (categoria) no banco de dados.
 * Mapeia a tabela 'GENERO'.
 */
@Entity
@Table(name = "GENERO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genero {

    /**
     * Chave primária da entidade Genero. Mapeia a coluna 'id_genero'.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero")
    private Long id;

    /**
     * Nome do gênero (ex: Ação, RPG). Campo obrigatório e único.
     */
    @Column(name = "nome", nullable = false, unique = true, length = 50)
    private String nome;

    /**
     * Relacionamento 1:N com Jogo.
     * @JsonIgnore impede a serialização recursiva Jogo -> Genero -> [Lista de Jogos].
     */
    @OneToMany(mappedBy = "genero", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Jogo> jogos;
}
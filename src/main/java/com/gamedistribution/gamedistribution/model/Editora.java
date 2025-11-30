package com.gamedistribution.gamedistribution.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;

/**
 * Representa a entidade EDITORA (desenvolvedora/criadora de jogos) no banco de dados.
 * Mapeia a tabela 'EDITORA'.
 */
@Entity
@Table(name = "EDITORA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Editora {

    /**
     * Chave primária da entidade Editora. Mapeia a coluna 'id_editora'.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_editora")
    private Long id;

    /**
     * Nome da editora. Campo obrigatório e único.
     */
    @Column(name = "nome", nullable = false, unique = true, length = 100)
    private String nome;

    /**
     * Localização da sede da editora.
     */
    @Column(name = "sede", length = 100)
    private String sede;

    /**
     * Relacionamento 1:N com Jogo.
     * @JsonIgnore impede a serialização recursiva Jogo -> Editora -> [Lista de Jogos].
     */
    @OneToMany(mappedBy = "editora", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Jogo> jogos;
}
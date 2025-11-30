package com.gamedistribution.gamedistribution.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;

/**
 * Representa a entidade PUBLICADORA (empresa de divulgação) no banco de dados.
 * Mapeia a tabela 'PUBLICADORA'.
 */
@Entity
@Table(name = "PUBLICADORA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publicadora {

    /**
     * Chave primária da entidade Publicadora. Mapeia a coluna 'id_publicadora'.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_publicadora")
    private Long id;

    /**
     * Nome da publicadora. Campo obrigatório e único.
     */
    @Column(name = "nome", nullable = false, unique = true, length = 100)
    private String nome;

    /**
     * Email de contato principal da publicadora.
     */
    @Column(name = "contato_email", length = 150)
    private String contatoEmail;

    /**
     * Relacionamento 1:N com JogoPublicadora.
     * @JsonIgnore impede a serialização recursiva quando buscamos uma lista de Jogos.
     */
    @OneToMany(mappedBy = "publicadora", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<JogoPublicadora> publicacoes;
}
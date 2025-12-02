package com.gamedistribution.gamedistribution.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Set;

/**
 * Representa a entidade GENERO (categoria) no banco de dados.
 */
@Entity
@Table(name = "GENERO")
@Getter
@Setter
@ToString(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Genero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_genero")
    private Long id;

    @Column(name = "nome", nullable = false, unique = true, length = 50)
    private String nome;

    /** Relacionamento 1:N com Jogo. Ignorado para evitar ciclos de serialização. */
    @OneToMany(mappedBy = "genero", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Jogo> jogos;
}
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
 * Representa a entidade PUBLICADORA (empresa de divulgação) no banco de dados.
 */
@Entity
@Table(name = "PUBLICADORA")
@Getter
@Setter
@ToString(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Publicadora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_publicadora")
    private Long id;

    @Column(name = "nome", nullable = false, unique = true, length = 100)
    private String nome;

    @Column(name = "contato_email", length = 150)
    private String contatoEmail;

    /**
     * Relacionamento 1:N com JogoPublicadora. Ignorado para evitar ciclos de serialização.
     */
    @OneToMany(mappedBy = "publicadora", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<JogoPublicadora> publicacoes;
}
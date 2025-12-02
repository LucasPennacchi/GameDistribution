package com.gamedistribution.gamedistribution.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.Set;

/**
 * Representa a entidade CLIENTE no banco de dados.
 * Mapeia a tabela 'CLIENTE'.
 */
@Entity
@Table(name = "CLIENTE")
@Getter
@Setter
@ToString(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    /**
     * Chave primária da entidade Cliente. Mapeia a coluna 'id_cliente'.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id;

    /**
     * Nome completo do cliente. Campo obrigatório.
     */
    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    /**
     * Email do cliente. Campo obrigatório e único.
     */
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    /**
     * Data de nascimento do cliente.
     */
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    /**
     * Relacionamento 1:N com Compra.
     * @JsonIgnore impede o ciclo recursivo Cliente -> Compra -> Cliente.
     */
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Compra> compras;
}
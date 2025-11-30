package com.gamedistribution.gamedistribution.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

/**
 * Representa a entidade CHAVE_ATIVACAO no banco de dados.
 * Mapeia a tabela 'CHAVE_ATIVACAO'.
 */
@Entity
@Table(name = "CHAVE_ATIVACAO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChaveAtivacao {

    /**
     * Chave primária: O código de ativação em si.
     */
    @Id
    @Column(name = "chave", length = 50)
    private String chave;

    /**
     * Data e hora em que a chave foi gerada no sistema. Campo obrigatório.
     */
    @Column(name = "data_geracao", nullable = false)
    private LocalDateTime dataGeracao;

    /**
     * Data e hora em que o cliente resgatou a chave (pode ser nulo).
     */
    @Column(name = "data_resgate")
    private LocalDateTime dataResgate;

    /**
     * Flag que indica se a chave já foi utilizada/resgatada.
     */
    @Column(name = "resgatada", nullable = false)
    private Boolean resgatada = false;

    /**
     * Relacionamento N:1 com a entidade Jogo.
     * Mapeia a chave estrangeira 'id_jogo'.
     */
    @ManyToOne
    @JoinColumn(name = "id_jogo", nullable = false)
    private Jogo jogo;
}
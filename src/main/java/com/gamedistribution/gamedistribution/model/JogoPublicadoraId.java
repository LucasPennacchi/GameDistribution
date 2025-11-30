package com.gamedistribution.gamedistribution.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

/**
 * Classe auxiliar para representar a chave primária composta da entidade JogoPublicadora.
 * Essencial para mapear o relacionamento N:N entre Jogo e Publicadora.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JogoPublicadoraId implements Serializable {

    /**
     * ID do Jogo, parte da chave primária.
     */
    private Long idJogo;

    /**
     * ID da Publicadora, parte da chave primária.
     */
    private Long idPublicadora;
}
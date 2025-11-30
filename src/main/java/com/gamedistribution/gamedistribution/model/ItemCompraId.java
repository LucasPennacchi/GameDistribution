package com.gamedistribution.gamedistribution.model;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.io.Serializable;

/**
 * Classe auxiliar para representar a chave primária composta da entidade ItemCompra.
 * É essencial para mapear o relacionamento N:N com atributos.
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCompraId implements Serializable {

    /**
     * ID da Compra, parte da chave primária.
     */
    private Long idCompra;

    /**
     * ID do Jogo, parte da chave primária.
     */
    private Long idJogo;
}
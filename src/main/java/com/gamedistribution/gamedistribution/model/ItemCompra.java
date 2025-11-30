package com.gamedistribution.gamedistribution.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Representa a entidade ITEM_COMPRA no banco de dados (item de linha da compra).
 * Mapeia a tabela 'ITEM_COMPRA', que é uma tabela de associação N:N.
 */
@Entity
@Table(name = "ITEM_COMPRA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCompra {

    /**
     * Chave primária composta.
     */
    @EmbeddedId
    private ItemCompraId id = new ItemCompraId();

    /**
     * Relacionamento M:1 com Compra. Mapeado pelo 'idCompra' na chave composta.
     * @JsonIgnore impede o ciclo recursivo Compra -> ItemCompra -> Compra.
     */
    @ManyToOne
    @MapsId("idCompra")
    @JoinColumn(name = "id_compra", nullable = false)
    @JsonIgnore // ESSENCIAL para quebrar o ciclo
    private Compra compra;

    /**
     * Relacionamento M:1 com Jogo. Mapeado pelo 'idJogo' na chave composta.
     */
    @ManyToOne
    @MapsId("idJogo")
    @JoinColumn(name = "id_jogo", nullable = false)
    private Jogo jogo;

    /**
     * Preço pelo qual o item foi vendido no momento da compra.
     */
    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;
}
package com.gamedistribution.gamedistribution.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ITEM_COMPRA")
@Getter
@Setter
@ToString(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ItemCompra {

    @EmbeddedId
    private ItemCompraId id = new ItemCompraId();

    @ManyToOne
    // @MapsId diz: "Use o valor de 'id.idCompra' para mapear este relacionamento"
    @MapsId("idCompra")
    @JoinColumn(name = "id_compra", nullable = false) // Força o nome da coluna física
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private Compra compra;

    @ManyToOne
    // @MapsId diz: "Use o valor de 'id.idJogo' para mapear este relacionamento"
    @MapsId("idJogo")
    @JoinColumn(name = "id_jogo", nullable = false) // Força o nome da coluna física
    @EqualsAndHashCode.Exclude
    private Jogo jogo;

    @Column(name = "preco_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal precoUnitario;
}
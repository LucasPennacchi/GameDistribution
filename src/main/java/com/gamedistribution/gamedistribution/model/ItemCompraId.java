package com.gamedistribution.gamedistribution.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemCompraId implements Serializable {

    // Garante que o Hibernate leia/escreva na coluna correta do banco
    @Column(name = "id_compra")
    private Long idCompra;

    @Column(name = "id_jogo")
    private Long idJogo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCompraId that = (ItemCompraId) o;
        return Objects.equals(idCompra, that.idCompra) &&
                Objects.equals(idJogo, that.idJogo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCompra, idJogo);
    }
}
package com.gamedistribution.gamedistribution.repository;

import com.gamedistribution.gamedistribution.model.ItemCompra;
import com.gamedistribution.gamedistribution.model.ItemCompraId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade ItemCompra (associação N:N entre Compra e Jogo).
 * A chave primária é composta (ItemCompraId).
 */
@Repository
public interface ItemCompraRepository extends JpaRepository<ItemCompra, ItemCompraId> {
}
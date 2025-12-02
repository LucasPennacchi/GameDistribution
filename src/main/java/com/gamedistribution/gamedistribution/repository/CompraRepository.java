package com.gamedistribution.gamedistribution.repository;

import com.gamedistribution.gamedistribution.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositório para a entidade Compra.
 */
@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    // ----------------------------------------------------------------------------------
    // CONSULTA COMPLEXA 2: Junções Múltiplas (JOINs)
    // Etapa 5, item 1 do roteiro.
    // ----------------------------------------------------------------------------------

    /**
     * Busca o histórico de compras detalhado de um cliente específico.
     * Usa LEFT JOIN FETCH para carregar a Compra, seus Itens e os Jogos associados
     * em uma única consulta, evitando o problema de N+1 (múltiplas consultas desnecessárias).
     *
     * @param clienteId O ID do cliente.
     * @return Uma lista de objetos Compra.
     */
    @Query("SELECT DISTINCT c FROM Compra c LEFT JOIN FETCH c.itensDaCompra ic LEFT JOIN FETCH ic.jogo j WHERE c.cliente.id = :clienteId ORDER BY c.dataCompra DESC")
    List<Compra> findHistoricoDetalhadoByClienteId(@Param("clienteId") Long clienteId);


    // ----------------------------------------------------------------------------------
    // MÉTODO AUXILIAR: Busca por ID do Cliente (Para listar no Service)
    // ----------------------------------------------------------------------------------

    /**
     * Busca todas as compras feitas por um cliente específico, utilizando o ID do Cliente.
     * Esta é uma convenção padrão do Spring Data JPA: findBy[Nome do Campo Relacionado]Id.
     * @param clienteId O ID do cliente.
     * @return Uma lista de compras.
     */
    List<Compra> findByClienteId(Long clienteId);
}
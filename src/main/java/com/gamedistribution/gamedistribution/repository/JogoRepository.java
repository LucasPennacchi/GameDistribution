package com.gamedistribution.gamedistribution.repository;

import com.gamedistribution.gamedistribution.model.Jogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repositório para a entidade Jogo.
 */
@Repository
public interface JogoRepository extends JpaRepository<Jogo, Long> {

    /**
     * Busca um jogo pelo seu título exato.
     * @param titulo O título do jogo a ser buscado.
     * @return O objeto Jogo encontrado, ou null se não for encontrado.
     */
    Jogo findByTitulo(String titulo);

    // ----------------------------------------------------------------------------------
    // CONSULTA COMPLEXA 1: Consultas Agregadas (GROUP BY, COUNT) e Ordenação (ORDER BY)
    // Etapa 5, itens 3 e 5 do roteiro.
    // ----------------------------------------------------------------------------------

    /**
     * Lista todos os jogos ordenados de forma decrescente pelo número total de vezes que foram comprados.
     * Implementa a lógica para o filtro "Ordenar por maior número de compras" do frontend.
     *
     * @param limit O número máximo de resultados a serem retornados (e.g., Top 10).
     * @return Uma lista de objetos Jogo ordenados pelo total de compras.
     */
    @Query(value = "SELECT j FROM Jogo j JOIN ItemCompra ic ON j.id = ic.jogo.id GROUP BY j.id ORDER BY COUNT(ic.jogo.id) DESC LIMIT :limit")
    List<Jogo> findTopJogosByTotalCompras(int limit);
}
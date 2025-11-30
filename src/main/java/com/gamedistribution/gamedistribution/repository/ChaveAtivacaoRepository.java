package com.gamedistribution.gamedistribution.repository;

import com.gamedistribution.gamedistribution.model.ChaveAtivacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade ChaveAtivacao.
 * A chave primária é do tipo String (o próprio código de ativação).
 */
@Repository
public interface ChaveAtivacaoRepository extends JpaRepository<ChaveAtivacao, String> {

    /**
     * Busca chaves de ativação que ainda não foram resgatadas para um determinado ID de jogo.
     * @param idJogo O ID do jogo.
     * @param resgatada O status de resgate (deve ser 'false' para chaves disponíveis).
     * @return Uma lista de chaves de ativação disponíveis.
     */
    ChaveAtivacao findTopByJogoIdAndResgatada(Long idJogo, Boolean resgatada);
}
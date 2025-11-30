package com.gamedistribution.gamedistribution.repository;

import com.gamedistribution.gamedistribution.model.JogoPublicadora;
import com.gamedistribution.gamedistribution.model.JogoPublicadoraId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade JogoPublicadora (associação N:N entre Jogo e Publicadora).
 * A chave primária é composta (JogoPublicadoraId).
 */
@Repository
public interface JogoPublicadoraRepository extends JpaRepository<JogoPublicadora, JogoPublicadoraId> {
}
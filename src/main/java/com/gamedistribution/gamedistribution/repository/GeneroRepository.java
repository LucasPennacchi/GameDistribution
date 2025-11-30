package com.gamedistribution.gamedistribution.repository;

import com.gamedistribution.gamedistribution.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Genero (categoria do jogo).
 * Permite operações CRUD básicas e busca por nome.
 */
@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {

    /**
     * Busca um gênero pelo seu nome.
     * @param nome O nome do gênero.
     * @return O objeto Genero encontrado, ou null.
     */
    Genero findByNome(String nome);
}
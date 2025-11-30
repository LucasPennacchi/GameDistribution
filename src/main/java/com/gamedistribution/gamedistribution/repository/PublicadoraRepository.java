package com.gamedistribution.gamedistribution.repository;

import com.gamedistribution.gamedistribution.model.Publicadora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Publicadora.
 */
@Repository
public interface PublicadoraRepository extends JpaRepository<Publicadora, Long> {

    /**
     * Busca uma publicadora pelo seu nome.
     * @param nome O nome da publicadora.
     * @return O objeto Publicadora encontrado.
     */
    Publicadora findByNome(String nome);
}
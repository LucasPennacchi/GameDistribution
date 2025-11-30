package com.gamedistribution.gamedistribution.repository;

import com.gamedistribution.gamedistribution.model.Editora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Editora (desenvolvedora/criadora).
 * Permite operações CRUD básicas e busca por nome.
 */
@Repository
public interface EditoraRepository extends JpaRepository<Editora, Long> {

    /**
     * Busca uma editora pelo seu nome.
     * @param nome O nome da editora.
     * @return O objeto Editora encontrado, ou null.
     */
    Editora findByNome(String nome);
}
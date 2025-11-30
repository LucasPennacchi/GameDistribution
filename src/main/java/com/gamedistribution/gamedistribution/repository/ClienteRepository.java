package com.gamedistribution.gamedistribution.repository;

import com.gamedistribution.gamedistribution.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para a entidade Cliente.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    /**
     * Busca um cliente pelo seu email.
     * @param email O email do cliente a ser buscado.
     * @return O objeto Cliente encontrado, ou null.
     */
    Cliente findByEmail(String email);
}
package com.gamedistribution.gamedistribution.service;

import com.gamedistribution.gamedistribution.model.Genero;
import com.gamedistribution.gamedistribution.repository.GeneroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Serviço responsável pela lógica de negócios da entidade Genero.
 */
@Service
public class GeneroService {

    private final GeneroRepository generoRepository;

    /**
     * Injeção de dependência do Repositório de Genero.
     * @param generoRepository Repositório de Genero.
     */
    @Autowired
    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    /**
     * Retorna a lista completa de Gêneros.
     * @return Uma lista de todos os gêneros.
     */
    public List<Genero> listarTodos() {
        return generoRepository.findAll();
    }
}
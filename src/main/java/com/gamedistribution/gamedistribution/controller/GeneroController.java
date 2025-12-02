package com.gamedistribution.gamedistribution.controller;

import com.gamedistribution.gamedistribution.model.Genero;
import com.gamedistribution.gamedistribution.service.GeneroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

/**
 * Controller REST responsável por gerenciar operações relacionadas à entidade Genero.
 * Mapeado para a URI '/api/generos'.
 */
@RestController
@RequestMapping("/api/generos")
public class GeneroController {

    private final GeneroService generoService;

    /**
     * Injeção de dependência do serviço de Genero.
     * @param generoService Serviço com a lógica de negócios para Genero.
     */
    @Autowired
    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    /**
     * Endpoint para listar todos os gêneros.
     * Mapeia requisições GET para '/api/generos'.
     * @return Lista de todos os gêneros com status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Genero>> listarTodos() {
        // Usa o serviço para buscar todos os gêneros no banco de dados
        List<Genero> generos = generoService.listarTodos();
        return ResponseEntity.ok(generos);
    }
}
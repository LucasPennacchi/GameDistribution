package com.gamedistribution.gamedistribution.controller;

import com.gamedistribution.gamedistribution.model.Publicadora;
import com.gamedistribution.gamedistribution.service.PublicadoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller REST para listar a entidade Publicadora.
 */
@RestController
@RequestMapping("/api/publicadoras")
public class PublicadoraController {

    private final PublicadoraService publicadoraService;

    @Autowired
    public PublicadoraController(PublicadoraService publicadoraService) {
        this.publicadoraService = publicadoraService;
    }

    /**
     * Endpoint para listar todas as publicadoras.
     * Mapeia requisições GET para '/api/publicadoras'.
     */
    @GetMapping
    public ResponseEntity<List<Publicadora>> listarTodas() {
        return ResponseEntity.ok(publicadoraService.listarTodas());
    }
}
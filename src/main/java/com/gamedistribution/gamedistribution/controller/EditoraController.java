package com.gamedistribution.gamedistribution.controller;

import com.gamedistribution.gamedistribution.model.Editora;
import com.gamedistribution.gamedistribution.service.EditoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller REST para listar a entidade Editora.
 */
@RestController
@RequestMapping("/api/editoras")
public class EditoraController {

    private final EditoraService editoraService;

    @Autowired
    public EditoraController(EditoraService editoraService) {
        this.editoraService = editoraService;
    }

    /**
     * Endpoint para listar todas as editoras.
     * Mapeia requisições GET para '/api/editoras'.
     */
    @GetMapping
    public ResponseEntity<List<Editora>> listarTodas() {
        return ResponseEntity.ok(editoraService.listarTodas());
    }
}
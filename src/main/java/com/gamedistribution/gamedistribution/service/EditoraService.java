package com.gamedistribution.gamedistribution.service;

import com.gamedistribution.gamedistribution.model.Editora;
import com.gamedistribution.gamedistribution.repository.EditoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Serviço de acesso a dados e lógica simples para a entidade Editora.
 */
@Service
public class EditoraService {

    private final EditoraRepository editoraRepository;

    @Autowired
    public EditoraService(EditoraRepository editoraRepository) {
        this.editoraRepository = editoraRepository;
    }

    /**
     * Retorna a lista completa de Editoras.
     */
    public List<Editora> listarTodas() {
        return editoraRepository.findAll();
    }
}
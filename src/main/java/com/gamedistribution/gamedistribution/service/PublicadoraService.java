package com.gamedistribution.gamedistribution.service;

import com.gamedistribution.gamedistribution.model.Publicadora;
import com.gamedistribution.gamedistribution.repository.PublicadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Serviço de acesso a dados e lógica simples para a entidade Publicadora.
 */
@Service
public class PublicadoraService {

    private final PublicadoraRepository publicadoraRepository;

    @Autowired
    public PublicadoraService(PublicadoraRepository publicadoraRepository) {
        this.publicadoraRepository = publicadoraRepository;
    }

    /**
     * Retorna a lista completa de Publicadoras.
     */
    public List<Publicadora> listarTodas() {
        return publicadoraRepository.findAll();
    }
}
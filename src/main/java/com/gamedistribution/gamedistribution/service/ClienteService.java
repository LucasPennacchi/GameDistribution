package com.gamedistribution.gamedistribution.service;

import com.gamedistribution.gamedistribution.model.Cliente;
import com.gamedistribution.gamedistribution.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela lógica de negócios da entidade Cliente.
 */
@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Injeção de dependência do repositório de Cliente.
     * @param clienteRepository Repositório de Cliente.
     */
    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Salva ou atualiza um cliente.
     * @param cliente O objeto Cliente.
     * @return O objeto Cliente salvo.
     */
    public Cliente salvar(Cliente cliente) {
        // Implementar lógica de validação de email, senha (futura), etc.
        return clienteRepository.save(cliente);
    }

    /**
     * Busca um cliente pelo seu ID.
     * @param id O ID do cliente.
     * @return Um Optional contendo o cliente, se encontrado.
     */
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    /**
     * Lista todos os clientes.
     * @return Uma lista de todos os clientes.
     */
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    // ... Aqui seria o local para implementar a consulta de histórico de compras do cliente
    //     que exige JOINs múltiplas, como discutido anteriormente.
}
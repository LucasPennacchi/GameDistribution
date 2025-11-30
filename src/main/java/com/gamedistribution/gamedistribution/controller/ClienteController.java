package com.gamedistribution.gamedistribution.controller;

import com.gamedistribution.gamedistribution.model.Cliente;
import com.gamedistribution.gamedistribution.model.Compra;
import com.gamedistribution.gamedistribution.service.ClienteService;
import com.gamedistribution.gamedistribution.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * Controller REST responsável por gerenciar operações relacionadas à entidade Cliente.
 * Mapeado para a URI '/api/clientes'.
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final CompraService compraService; // Para consultas de histórico

    /**
     * Injeção de dependência dos serviços.
     * @param clienteService Serviço com a lógica de negócios para Cliente.
     * @param compraService Serviço para manipular compras e histórico.
     */
    @Autowired
    public ClienteController(ClienteService clienteService, CompraService compraService) {
        this.clienteService = clienteService;
        this.compraService = compraService;
    }

    /**
     * Endpoint para cadastrar um novo cliente (Etapa 4 - Tela de cadastro).
     * Mapeia requisições POST para '/api/clientes'.
     * @param cliente O objeto Cliente enviado no corpo da requisição.
     * @return O Cliente salvo com o status HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
        Cliente novoCliente = clienteService.salvar(cliente);
        return new ResponseEntity<>(novoCliente, HttpStatus.CREATED);
    }

    /**
     * Endpoint para buscar o histórico de compras de um cliente (Etapa 5 - Consulta Complexa).
     * Mapeia requisições GET para '/api/clientes/{clienteId}/historico'.
     * @param clienteId O ID do cliente.
     * @return Lista de compras do cliente ou 404 (Not Found).
     */
    @GetMapping("/{clienteId}/historico")
    public ResponseEntity<List<Compra>> buscarHistoricoCompras(@PathVariable Long clienteId) {
        // Valida se o cliente existe primeiro
        if (clienteService.buscarPorId(clienteId).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Busca o histórico usando a lógica de JOINs no CompraService
        List<Compra> historico = compraService.buscarHistoricoCompras(clienteId);

        if (historico.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(historico, HttpStatus.OK);
    }

    /**
     * Endpoint para listar todos os clientes.
     * Mapeia requisições GET para '/api/clientes'.
     */
    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.listarTodos());
    }
}
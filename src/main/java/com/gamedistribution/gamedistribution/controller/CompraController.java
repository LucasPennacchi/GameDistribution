package com.gamedistribution.gamedistribution.controller;

import com.gamedistribution.gamedistribution.model.Compra;
import com.gamedistribution.gamedistribution.dto.CompraRequest;
import com.gamedistribution.gamedistribution.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controller REST para gerenciar a entidade Compra e consultas complexas de histórico.
 * Mapeado para a URI base '/api/compras'.
 */
@RestController
@RequestMapping("/api/compras")
public class CompraController {

    private final CompraService compraService;

    /**
     * Injeção de dependência do serviço de Compra.
     * @param compraService Serviço com a lógica de negócios para Compra.
     */
    @Autowired
    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    // ----------------------------------------------------
    // CONSULTA COMPLEXA: Histórico Detalhado (Solução do Erro 404/500)
    // ----------------------------------------------------

    /**
     * Endpoint para buscar o histórico detalhado de compras de um cliente específico.
     * Mapeado para a URI '/api/compras/historico-cliente/{clienteId}'.
     * (Etapa 5 - Junções Múltiplas)
     * * NOTA: Este método assume que o cliente existe (se o ID for Long, ele existe ou não).
     * Se o histórico estiver vazio, ele retorna 204 NO CONTENT, e não 404.
     * @param clienteId O ID do cliente.
     * @return Lista de compras detalhadas ou 204.
     */
    @GetMapping("/historico-cliente/{clienteId}")
    public ResponseEntity<List<Compra>> buscarHistoricoCompras(@PathVariable Long clienteId) {
        try {
            List<Compra> historico = compraService.buscarHistoricoCompras(clienteId);

            if (historico == null || historico.isEmpty()) {
                // Retorna 204 se a consulta rodou mas não achou resultados (melhor que 404)
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

            // Se a lista não está vazia, o Spring Boot a serializa.
            return ResponseEntity.ok(historico);

        } catch (Exception e) {
            // Se uma exceção ocorrer, vamos logá-la completamente.
            System.err.println("Erro FATAL ao buscar histórico: " + e.getMessage());
            e.printStackTrace();
            // Retornamos 500 para análise.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ----------------------------------------------------
    // CADASTRO: Realizar Compra (POST /api/compras)
    // ----------------------------------------------------

    /**
     * Endpoint para simular a compra de um jogo por um cliente.
     * Mapeado para POST '/api/compras'.
     * @param request DTO contendo clienteId e jogoId.
     * @return A Compra criada com status 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Compra> realizarCompra(@RequestBody CompraRequest request) {
        try {
            // Chama a lógica de negócio (criação de compra, itens e chaves)
            Compra novaCompra = compraService.realizarCompra(request.getClienteId(), request.getJogoId());

            // Retorna 201 Created
            return new ResponseEntity<>(novaCompra, HttpStatus.CREATED);

        } catch (RuntimeException e) {
            // Trata erros de "Cliente não encontrado" ou "Jogo não encontrado" do Service
            System.err.println("Erro ao realizar compra: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // ----------------------------------------------------
    // LISTAGEM GERAL (GET /api/compras)
    // ----------------------------------------------------

    /**
     * Endpoint para listar todas as compras (visualização administrativa).
     * @return Lista de todas as compras.
     */
    @GetMapping
    public ResponseEntity<List<Compra>> listarTodas() {
        List<Compra> compras = compraService.listarTodas();
        if (compras.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(compras);
    }
}
package com.gamedistribution.gamedistribution.service;

import com.gamedistribution.gamedistribution.model.Compra;
import com.gamedistribution.gamedistribution.model.Cliente;
import com.gamedistribution.gamedistribution.repository.CompraRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Serviço responsável pela lógica de negócios da entidade Compra.
 * Lida com o processo de checkout e consultas de transações.
 */
@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final ClienteService clienteService;

    /**
     * Injeção de dependência.
     * @param compraRepository Repositório de Compra.
     * @param clienteService Serviço de Cliente.
     */
    @Autowired
    public CompraService(CompraRepository compraRepository, ClienteService clienteService) {
        this.compraRepository = compraRepository;
        this.clienteService = clienteService;
    }

    /**
     * Busca uma compra pelo seu ID.
     * @param id O ID da compra.
     * @return Um Optional contendo a compra, se encontrada.
     */
    public Optional<Compra> buscarPorId(Long id) {
        return compraRepository.findById(id);
    }

    /**
     * Executa a transação de compra (simplificado).
     * @param clienteId ID do cliente.
     * @param itens Map<JogoId, Quantidade> dos itens.
     * @return O objeto Compra finalizado.
     */
    @Transactional
    public Compra realizarCompra(Long clienteId, Map<Long, Integer> itens) {
        Cliente cliente = clienteService.buscarPorId(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        // Lógica real de compra (cálculo de valor, geração de chaves, etc.)
        Compra novaCompra = new Compra();
        novaCompra.setCliente(cliente);

        return compraRepository.save(novaCompra);
    }

    /**
     * Consulta Complexa (Junções Múltiplas): Busca o histórico detalhado de compras de um cliente.
     * Implementa a consulta que utiliza JOINs e carrega entidades relacionadas (jogos, itens).
     *
     * @param clienteId O ID do cliente.
     * @return Uma lista de objetos Compra com os detalhes do jogo carregados.
     */
    public List<Compra> buscarHistoricoCompras(Long clienteId) {
        return compraRepository.findHistoricoDetalhadoByClienteId(clienteId);
    }
}
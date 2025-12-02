package com.gamedistribution.gamedistribution.service;

import com.gamedistribution.gamedistribution.model.Compra;
import com.gamedistribution.gamedistribution.model.Cliente;
import com.gamedistribution.gamedistribution.model.Jogo;
import com.gamedistribution.gamedistribution.repository.CompraRepository;
import com.gamedistribution.gamedistribution.repository.ClienteRepository;
import com.gamedistribution.gamedistribution.repository.JogoRepository;
import com.gamedistribution.gamedistribution.dto.CompraRequest;
import jakarta.transaction.Transactional; // ESSENCIAL para Lazy Loading e consistência
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.Optional;

/**
 * Serviço responsável pela lógica de negócios da entidade Compra.
 * Inclui o processo de transação e a consulta complexa de histórico.
 */
@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final ClienteRepository clienteRepository;
    private final JogoRepository jogoRepository;

    /**
     * Injeção de dependência dos repositórios.
     */
    @Autowired
    public CompraService(
            CompraRepository compraRepository,
            ClienteRepository clienteRepository,
            JogoRepository jogoRepository)
    {
        this.compraRepository = compraRepository;
        this.clienteRepository = clienteRepository;
        this.jogoRepository = jogoRepository;
    }

    /**
     * Retorna a lista completa de Compras.
     */
    public List<Compra> listarTodas() {
        return compraRepository.findAll();
    }

    /**
     * Busca uma compra pelo seu ID.
     */
    public Optional<Compra> buscarPorId(Long id) {
        return compraRepository.findById(id);
    }

    // ----------------------------------------------------
    // CONSULTA COMPLEXA: Histórico Detalhado (Solução do Erro 500)
    // ----------------------------------------------------

    /**
     * Busca o histórico detalhado de compras de um cliente específico.
     * A anotação @Transactional é crucial aqui para evitar o LazyInitializationException
     * e o ConcurrentModificationException durante a serialização do JSON
     * ao carregar os Itens de Compra e Jogos (Fetch Join).
     *
     * @param clienteId O ID do cliente.
     * @return Uma lista de objetos Compra detalhados.
     */
    @Transactional
    public List<Compra> buscarHistoricoCompras(Long clienteId) {
        return compraRepository.findHistoricoDetalhadoByClienteId(clienteId);
    }

    // ----------------------------------------------------
    // ESQUELETO DE TRANSAÇÃO: Realizar Compra (POST)
    // ----------------------------------------------------

    /**
     * Simula a transação de compra, criação da Compra e ativação de chave.
     * NOTA: Esta é uma simulação básica. A implementação completa incluiria
     * a criação da entidade ItemCompra e a persistência da ChaveAtivacao.
     *
     * @param clienteId ID do cliente.
     * @param jogoId ID do jogo comprado.
     * @return A nova entidade Compra criada.
     * @throws RuntimeException se Cliente ou Jogo não for encontrado.
     */
    @Transactional
    public Compra realizarCompra(Long clienteId, Long jogoId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + clienteId));

        Jogo jogo = jogoRepository.findById(jogoId)
                .orElseThrow(() -> new RuntimeException("Jogo não encontrado com ID: " + jogoId));

        // 1. Cria a Compra (Transação)
        Compra novaCompra = new Compra();
        novaCompra.setCliente(cliente);
        novaCompra.setDataCompra(LocalDateTime.now());
        novaCompra.setValorTotal(jogo.getPreco());

        // 2. Simulação de Chave de Ativação
        String chaveAtivacao = UUID.randomUUID().toString().toUpperCase().substring(0, 15);
        System.out.println("### CHAVE GERADA ###: " + jogo.getTitulo() + " (Código: " + chaveAtivacao + ")");
        // Em um sistema real, você salvaria a entidade ChaveAtivacao aqui.

        // 3. Persistência
        return compraRepository.save(novaCompra);
    }
}
package com.gamedistribution.gamedistribution.service;

import com.gamedistribution.gamedistribution.model.Jogo;
import com.gamedistribution.gamedistribution.repository.EditoraRepository;
import com.gamedistribution.gamedistribution.repository.JogoRepository;
import com.gamedistribution.gamedistribution.repository.JogoPublicadoraRepository;
import jakarta.transaction.Transactional; // Importação essencial para Lazy Loading
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors; // Importação essencial para criar lista mutável

/**
 * Serviço responsável pela lógica de negócios da entidade Jogo.
 * Inclui operações CRUD e consultas personalizadas, gerenciando as consultas complexas.
 */
@Service
public class JogoService {

    private final JogoRepository jogoRepository;
    private final EditoraRepository editoraRepository;
    private final JogoPublicadoraRepository jogoPublicadoraRepository;

    /**
     * Construtor com injeção de dependência dos repositórios necessários.
     */
    @Autowired
    public JogoService(JogoRepository jogoRepository, EditoraRepository editoraRepository, JogoPublicadoraRepository jogoPublicadoraRepository) {
        this.jogoRepository = jogoRepository;
        this.editoraRepository = editoraRepository;
        this.jogoPublicadoraRepository = jogoPublicadoraRepository;
    }

    /**
     * Salva ou atualiza um jogo no banco de dados.
     */
    public Jogo salvar(Jogo jogo) {
        return jogoRepository.save(jogo);
    }

    /**
     * Busca um jogo pelo seu ID.
     */
    public Optional<Jogo> buscarPorId(Long id) {
        return jogoRepository.findById(id);
    }

    /**
     * Lista todos os jogos no catálogo.
     */
    public List<Jogo> listarTodos() {
        return jogoRepository.findAll();
    }

    /**
     * Consulta Complexa 1: Retorna todos os jogos de uma Editora (Criador).
     * Nota: Este método pode ser otimizado no futuro com uma @Query no Repositório.
     */
    @Transactional
    public List<Jogo> buscarPorEditora(Long editoraId) {
        return editoraRepository.findById(editoraId)
                .map(editora -> jogoRepository.findAll()
                        .stream()
                        .filter(jogo -> jogo.getEditora() != null && jogo.getEditora().getId().equals(editoraId))
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    /**
     * Consulta Complexa (Agregada e Ordenação): Lista o Top N jogos ordenados pelo total de compras.
     * Usa o método customizado findTopJogosByTotalCompras implementado no JogoRepository.
     */
    public List<Jogo> buscarTopJogosMaisVendidos(int limite) {
        return jogoRepository.findTopJogosByTotalCompras(limite);
    }

    /**
     * NOVO MÉTODO (CORRIGIDO): Consulta unificada para jogos, aplicando filtragem e ordenação.
     * Resolve o erro de UnsupportedOperationException ao usar .collect(Collectors.toList()).
     * @param idGenero O ID do Gênero (opcional).
     * @param ordenacao A string de ordenação (ex: precoAsc, lancamentoDesc).
     * @return Lista de jogos filtrados e ordenados.
     */
    @Transactional // ESSENCIAL: Garante que as relações Lazy (como getGenero()) possam ser acessadas
    public List<Jogo> buscarEOrdenar(Long idGenero, String ordenacao) {

        // Inicia com todos os jogos
        List<Jogo> jogos = jogoRepository.findAll()
                .stream()
                // 1. Coleta para uma lista MUTÁVEL para permitir o método .sort()
                .collect(Collectors.toList());

        // 2. Filtrar por Gênero
        if (idGenero != null) {
            jogos = jogos.stream()
                    .filter(j -> j.getGenero() != null && j.getGenero().getId().equals(idGenero))
                    // Coleta para uma lista MUTÁVEL após a filtragem
                    .collect(Collectors.toList());
        }

        // 3. Aplicar Ordenação
        switch (ordenacao) {
            case "precoDesc":
                jogos.sort((a, b) -> b.getPreco().compareTo(a.getPreco()));
                break;
            case "precoAsc":
                jogos.sort((a, b) -> a.getPreco().compareTo(b.getPreco()));
                break;
            case "lancamentoDesc":
                jogos.sort((a, b) -> b.getDataLancamento().compareTo(a.getDataLancamento()));
                break;
            // Se o frontend tiver um caso para 'mais_vendidos', ele chamará o método buscarTopJogosMaisVendidos diretamente no Controller.
        }

        return jogos;
    }
}
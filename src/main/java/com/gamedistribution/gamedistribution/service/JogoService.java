package com.gamedistribution.gamedistribution.service;

import com.gamedistribution.gamedistribution.model.Jogo;
import com.gamedistribution.gamedistribution.repository.EditoraRepository;
import com.gamedistribution.gamedistribution.repository.JogoRepository;
import com.gamedistribution.gamedistribution.repository.JogoPublicadoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela lógica de negócios da entidade Jogo.
 * Inclui operações CRUD e consultas personalizadas.
 */
@Service
public class JogoService {

    private final JogoRepository jogoRepository;
    private final EditoraRepository editoraRepository;
    private final JogoPublicadoraRepository jogoPublicadoraRepository;

    /**
     * Injeção de dependência dos repositórios necessários.
     * @param jogoRepository Repositório de Jogo.
     * @param editoraRepository Repositório de Editora.
     * @param jogoPublicadoraRepository Repositório de JogoPublicadora.
     */
    @Autowired
    public JogoService(JogoRepository jogoRepository, EditoraRepository editoraRepository, JogoPublicadoraRepository jogoPublicadoraRepository) {
        this.jogoRepository = jogoRepository;
        this.editoraRepository = editoraRepository;
        this.jogoPublicadoraRepository = jogoPublicadoraRepository;
    }

    /**
     * Salva ou atualiza um jogo no banco de dados.
     * @param jogo O objeto Jogo a ser persistido.
     * @return O objeto Jogo salvo.
     */
    public Jogo salvar(Jogo jogo) {
        return jogoRepository.save(jogo);
    }

    /**
     * Busca um jogo pelo seu ID.
     * @param id O ID do jogo.
     * @return Um Optional contendo o jogo, se encontrado.
     */
    public Optional<Jogo> buscarPorId(Long id) {
        return jogoRepository.findById(id);
    }

    /**
     * Lista todos os jogos no catálogo.
     * @return Uma lista de todos os jogos.
     */
    public List<Jogo> listarTodos() {
        return jogoRepository.findAll();
    }

    /**
     * Consulta Complexa 1: Retorna todos os jogos de uma Editora (Criador).
     * @param editoraId O ID da Editora.
     * @return Lista de jogos associados à Editora.
     */
    public List<Jogo> buscarPorEditora(Long editoraId) {
        return editoraRepository.findById(editoraId)
                .map(editora -> jogoRepository.findAll()
                        .stream()
                        .filter(jogo -> jogo.getEditora().getId().equals(editoraId))
                        .toList())
                .orElse(List.of());
    }

    /**
     * Consulta Complexa (Agregada e Ordenação): Lista o Top N jogos ordenados pelo total de compras.
     * Usa o método customizado implementado no JogoRepository.
     * @param limite O número máximo de jogos a serem retornados (ex: 10).
     * @return Uma lista de objetos Jogo ordenados pelo total de compras.
     */
    public List<Jogo> buscarTopJogosMaisVendidos(int limite) {
        return jogoRepository.findTopJogosByTotalCompras(limite);
    }
}
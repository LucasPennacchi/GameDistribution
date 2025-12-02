package com.gamedistribution.gamedistribution.controller;

import com.gamedistribution.gamedistribution.model.Jogo;
import com.gamedistribution.gamedistribution.service.JogoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * Controller REST responsável por gerenciar operações relacionadas à entidade Jogo.
 * Mapeado para a URI '/api/jogos'.
 */
@RestController
@RequestMapping("/api/jogos")
public class JogoController {

    private final JogoService jogoService;

    /**
     * Injeção de dependência do serviço de Jogo.
     * @param jogoService Serviço com a lógica de negócios para Jogo.
     */
    @Autowired
    public JogoController(JogoService jogoService) {
        this.jogoService = jogoService;
    }

    /**
     * Endpoint para cadastrar um novo jogo (Etapa 4 - Tela de cadastro).
     * Mapeia requisições POST para '/api/jogos'.
     * @param jogo O objeto Jogo enviado no corpo da requisição.
     * @return O Jogo salvo com o status HTTP 201 (Created).
     */
    @PostMapping
    public ResponseEntity<Jogo> cadastrarJogo(@RequestBody Jogo jogo) {
        Jogo novoJogo = jogoService.salvar(jogo);
        return new ResponseEntity<>(novoJogo, HttpStatus.CREATED);
    }

    /**
     * Endpoint para buscar um jogo pelo ID.
     * Mapeia requisições GET para '/api/jogos/{id}'.
     * @param id O ID do jogo a ser buscado.
     * @return O Jogo encontrado com status 200, ou 404 (Not Found).
     */
    @GetMapping("/{id}")
    public ResponseEntity<Jogo> buscarPorId(@PathVariable Long id) {
        Optional<Jogo> jogo = jogoService.buscarPorId(id);
        return jogo.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint para listar todos os jogos.
     * Mapeia requisições GET para '/api/jogos'.
     * @return Lista de todos os jogos com status 200 (OK).
     */
    @GetMapping
    public ResponseEntity<List<Jogo>> listarTodos() {
        List<Jogo> jogos = jogoService.listarTodos();
        return new ResponseEntity<>(jogos, HttpStatus.OK);
    }

    /**
     * Endpoint para atualizar um jogo existente (Etapa 4 - Tela de atualização).
     * Mapeia requisições PUT para '/api/jogos/{id}'.
     * @param id O ID do jogo a ser atualizado.
     * @param jogoDetalhes Os novos dados do jogo.
     * @return O Jogo atualizado ou 404 (Not Found).
     */
    @PutMapping("/{id}")
    public ResponseEntity<Jogo> atualizarJogo(@PathVariable Long id, @RequestBody Jogo jogoDetalhes) {
        Optional<Jogo> jogoAtualizado = jogoService.buscarPorId(id)
                .map(jogo -> {
                    // ... (lógica de atualização)
                    return jogoService.salvar(jogo);
                });

        return jogoAtualizado.map(jogo -> new ResponseEntity<>(jogo, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Endpoint para buscar jogos por Editora (Etapa 5 - Consulta Complexa).
     * Mapeia requisições GET para '/api/jogos/editora/{editoraId}'.
     * @param editoraId O ID da editora.
     * @return Lista de jogos daquela editora.
     */
    @GetMapping("/editora/{editoraId}")
    public ResponseEntity<List<Jogo>> buscarPorEditora(@PathVariable Long editoraId) {
        List<Jogo> jogos = jogoService.buscarPorEditora(editoraId);
        if (jogos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(jogos, HttpStatus.OK);
    }

    /**
     * NOVO ENDPOINT DE CONSULTA COMPLEXA: Retorna o Top N jogos mais vendidos.
     * Mapeia requisições GET para '/api/jogos/mais-vendidos?limite=N'.
     * @param limite O número máximo de resultados (default é 10).
     * @return Lista de jogos ordenados por número de compras.
     */
    @GetMapping("/mais-vendidos")
    public ResponseEntity<List<Jogo>> buscarTopJogosMaisVendidos(@RequestParam(defaultValue = "10") int limite) {
        List<Jogo> topJogos = jogoService.buscarTopJogosMaisVendidos(limite);
        if (topJogos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(topJogos, HttpStatus.OK);
    }

    /**
     * ENDPOINT DE CONSULTA UNIFICADA: Suporta filtragem por gênero e ordenação.
     * Mapeia requisições GET para '/api/jogos/filtrar'.
     * @param idGenero O ID do Gênero (opcional).
     * @param ordenacao A string de ordenação (ex: precoAsc, lancamentoDesc).
     * @return Lista de jogos filtrados e ordenados.
     */
    @GetMapping("/filtrar")
    public ResponseEntity<List<Jogo>> buscarEOrdenarJogos(
            @RequestParam(required = false) Long idGenero,
            @RequestParam(defaultValue = "precoAsc") String ordenacao) {

        // Se o frontend solicitar o filtro de "mais_vendidos", redirecione para o endpoint customizado
        if (ordenacao.equals("mais_vendidos")) {
            // Assumindo que você quer o Top 10
            return ResponseEntity.ok(jogoService.buscarTopJogosMaisVendidos(10));
        }

        List<Jogo> jogos = jogoService.buscarEOrdenar(idGenero, ordenacao);

        if (jogos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok(jogos);
    }
}
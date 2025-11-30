const API_BASE_URL = 'http://localhost:8080/api';

// --- 1. Funções de Carregamento de Dados Básicos ---

/**
 * Função genérica para carregar dados e preencher tabelas simples.
 */
async function carregarTabela(endpoint, tabelaId, colunas) {
    try {
        const response = await fetch(`${API_BASE_URL}/${endpoint}`);
        if (!response.ok) throw new Error(`Status: ${response.status}`);
        const dados = await response.json();

        const tbody = document.getElementById(tabelaId).querySelector('tbody');
        tbody.innerHTML = '';

        dados.forEach(item => {
            const row = tbody.insertRow();
            colunas.forEach(coluna => {
                const cell = row.insertCell();
                let valor = item[coluna];

                // Simplifica a exibição de objetos aninhados (útil para Editora.nome)
                if (coluna.includes('.')) {
                    valor = coluna.split('.').reduce((obj, key) => (obj && obj[key] !== undefined) ? obj[key] : '', item);
                }

                cell.textContent = valor || '-';
            });
        });
    } catch (error) {
        console.error(`Erro ao carregar ${endpoint}:`, error);
        alert(`Erro ao carregar dados de ${endpoint}. Verifique se o Spring Boot está rodando.`);
    }
}

// --- 2. Lógica de Consultas Complexas (Jogos) ---

/**
 * Carrega a tabela de jogos, aplicando filtros de categoria e ordenação complexa.
 */
async function carregarJogos() {
    const consultaTipo = document.getElementById('consulta-jogos').value;
    const filtroCategoria = document.getElementById('filtro-categoria').value;
    const tbody = document.getElementById('tabela-jogos').querySelector('tbody');
    tbody.innerHTML = '';
    let url = `${API_BASE_URL}/jogos`;

    // 2.1. Define o endpoint com base na consulta complexa
    if (consultaTipo === 'mais_vendidos') {
        url = `${API_BASE_URL}/jogos/mais-vendidos?limite=5`;
    }

    try {
        const response = await fetch(url);
        let jogos = await response.json();

        if (jogos.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6">Nenhum jogo encontrado.</td></tr>';
            return;
        }

        // 2.2. Preenche o filtro de categorias na primeira execução
        preencherFiltroCategorias(jogos);

        // 2.3. Filtra no frontend por Categoria (para simplificar o backend)
        if (filtroCategoria && filtroCategoria !== '') {
            // Se a consulta for 'mais_vendidos', os jogos já estão ordenados pelo backend.
            jogos = jogos.filter(jogo => jogo.genero.id === parseInt(filtroCategoria));
        }

        // 2.4. Renderiza a Tabela
        jogos.forEach(jogo => {
            const row = tbody.insertRow();
            row.insertCell().textContent = jogo.id;
            row.insertCell().textContent = jogo.titulo;
            row.insertCell().textContent = `R$ ${jogo.preco.toFixed(2)}`;
            row.insertCell().textContent = jogo.dataLancamento;
            row.insertCell().textContent = jogo.editora.nome;
            row.insertCell().textContent = jogo.genero.nome;
        });

    } catch (error) {
        console.error('Erro ao carregar jogos:', error);
    }
}

/**
 * Preenche o dropdown de Gêneros.
 */
function preencherFiltroCategorias(jogos) {
    const select = document.getElementById('filtro-categoria');
    if (select.options.length > 1) return;

    const generos = {};
    jogos.forEach(jogo => {
        if (jogo.genero) {
            generos[jogo.genero.id] = jogo.genero.nome;
        }
    });

    select.innerHTML = '<option value="">Mostrar Todos</option>';
    for (const id in generos) {
        const option = document.createElement('option');
        option.value = id;
        option.textContent = generos[id];
        select.appendChild(option);
    }
}

// --- 3. Lógica de Consulta Complexa (Histórico de Compras) ---

/**
 * Busca e exibe o histórico detalhado de compras de um cliente (JOINs Múltiplas).
 */
async function buscarHistorico() {
    const clienteId = document.getElementById('cliente-id-input').value;
    const statusDiv = document.getElementById('historico-status');
    const tbody = document.getElementById('tabela-historico').querySelector('tbody');
    tbody.innerHTML = '';
    statusDiv.textContent = '';

    if (!clienteId) {
        statusDiv.textContent = 'Por favor, insira um ID de cliente.';
        return;
    }

    try {
        const url = `${API_BASE_URL}/clientes/${clienteId}/historico`;
        const response = await fetch(url);

        if (response.status === 404) {
            statusDiv.textContent = `Erro: Cliente com ID ${clienteId} não encontrado.`;
            return;
        }
        if (response.status === 204) {
            statusDiv.textContent = `Cliente encontrado, mas não possui histórico de compras.`;
            return;
        }
        if (!response.ok) throw new Error(`Status: ${response.status}`);

        const historico = await response.json();

        // O Spring Boot retorna uma lista de Compra. Precisamos iterar pelos ItemCompra de cada Compra.
        historico.forEach(compra => {
            // O nome do Set é `itemCompras` (definido no modelo Compra)
            compra.itemCompras.forEach(item => {
                const row = tbody.insertRow();
                row.insertCell().textContent = compra.id;
                // Formata a data
                row.insertCell().textContent = new Date(compra.dataCompra).toLocaleString();
                // Acesso aos detalhes do Jogo
                row.insertCell().textContent = item.jogo ? item.jogo.titulo : 'Jogo Removido';
                row.insertCell().textContent = `R$ ${item.precoUnitario.toFixed(2)}`;
            });
        });

    } catch (error) {
        statusDiv.textContent = `Erro ao buscar histórico: ${error.message}`;
        console.error('Erro de histórico:', error);
    }
}


// --- 4. Inicialização ---

window.onload = function() {
    // Carrega a tabela de jogos e preenche os filtros
    carregarJogos();

    // Carrega as tabelas de visualização básica
    carregarTabela('clientes', 'tabela-clientes', ['id', 'nome', 'email', 'dataNascimento']);
    carregarTabela('editoras', 'tabela-editoras', ['id', 'nome', 'sede']);
    carregarTabela('publicadoras', 'tabela-publicadoras', ['id', 'nome', 'contatoEmail']);
};
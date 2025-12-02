const API_BASE_URL = 'http://localhost:8080/api';

// --- Função de Navegação ---

/**
 * Simula o processo de logout e redireciona para a tela de login.
 */
function fazerLogout() {
    // Simula a limpeza de qualquer token/sessão local
    localStorage.removeItem('userToken'); 
    console.log("Usuário deslogado. Redirecionando para login.html...");
    
    window.location.href = 'login.html'
}


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
            
            row.classList.add('table-row-bg');
            
            colunas.forEach(coluna => {
                const cell = row.insertCell();
                cell.classList.add('px-4', 'py-2', 'text-sm'); 
                
                let valor = item[coluna];

                
                if (coluna.includes('.')) {
                    valor = coluna.split('.').reduce((obj, key) => (obj && obj[key] !== undefined) ? obj[key] : '', item);
                }
                
                
                if (coluna === 'dataNascimento' && valor) {
                    valor = new Date(valor).toLocaleDateString('pt-BR');
                }

                cell.textContent = valor || '-';
            });
        });
    } catch (error) {
        console.error(`Erro ao carregar ${endpoint}:`, error);
        
    }
}

// --- 2. Lógica de Consultas Complexas (Jogos) ---

/**
 * Carrega a tabela de jogos, aplicando filtros de categoria e ordenação complexa.
 */
async function carregarJogos() {
    const generoId = document.getElementById('genero-select').value;
    const ordenacao = document.getElementById('ordenacao-select').value;
    const tbody = document.getElementById('tabela-jogos').querySelector('tbody');
    tbody.innerHTML = '<tr><td colspan="6" class="text-center py-4">Carregando jogos...</td></tr>';

    let url = `${API_BASE_URL}/jogos/filtrar?`;

    if (generoId) {
        url += `idGenero=${generoId}&`;
    }
    

    url += `ordenacao=${ordenacao}`;

    try {
        const response = await fetch(url);
        if (!response.ok) throw new Error(`Status: ${response.status}`);
        const jogos = await response.json();

        
        tbody.innerHTML = ''; 

        if (jogos.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6" class="text-center py-4 text-yellow-400">Nenhum jogo encontrado com os filtros aplicados.</td></tr>';
            return;
        }

        jogos.forEach(jogo => {
            const row = tbody.insertRow();
            row.classList.add('table-row-bg'); 
            row.insertCell().textContent = jogo.id;
            row.insertCell().textContent = jogo.titulo;
            row.insertCell().textContent = `R$ ${jogo.preco.toFixed(2)}`;
            row.insertCell().textContent = new Date(jogo.dataLancamento).toLocaleDateString();
            // Acesso aos atributos aninhados (Editora e Genero)
            row.insertCell().textContent = jogo.editora ? jogo.editora.nome : 'N/A';
            row.insertCell().textContent = jogo.genero ? jogo.genero.nome : 'N/A';

            
            Array.from(row.cells).forEach(cell => cell.classList.add('px-4', 'py-2', 'text-sm'));
        });

    } catch (error) {
        tbody.innerHTML = `<tr><td colspan="6" class="text-center py-4 text-red-500">Erro ao carregar jogos: ${error.message}. Verifique o Spring Boot.</td></tr>`;
        console.error('Erro de jogos:', error);
    }
}

/**
 * Preenche o dropdown de gêneros no início.
 */
async function preencherFiltroGeneros() {
    try {
        const response = await fetch(`${API_BASE_URL}/generos`);
        if (!response.ok) throw new Error(`Status: ${response.status}`);
        const generos = await response.json();

        const select = document.getElementById('genero-select');
        // Mantém a opção "Todos"
        select.innerHTML = '<option value="">Todos os Gêneros</option>';

        generos.forEach(genero => {
            const option = document.createElement('option');
            option.value = genero.id;
            option.textContent = genero.nome;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Erro ao preencher gêneros:', error);
    }
}


// --- 3. Lógica de Consultas Complexas (Histórico) ---

/**
 * Busca o histórico de compras de um cliente específico.
 */
async function buscarHistorico() {
    const clienteId = document.getElementById('cliente-id-input').value;
    const tbody = document.getElementById('tabela-historico').querySelector('tbody');
    const statusDiv = document.getElementById('historico-status');
    
    tbody.innerHTML = '';
    statusDiv.textContent = '';
    
    if (!clienteId || clienteId < 1) {
        statusDiv.textContent = 'Por favor, insira um ID de Cliente válido.';
        return;
    }

    // Indica carregamento
    tbody.innerHTML = '<tr><td colspan="4" class="text-center py-4">Buscando histórico de compras...</td></tr>';


    try {
        const response = await fetch(`${API_BASE_URL}/compras/historico-cliente/${clienteId}`);

        if (response.status === 204) { // Adicionar o tratamento para 204
            statusDiv.textContent = `Cliente encontrado, mas não possui histórico de compras.`;
            tbody.innerHTML = '<tr><td colspan="4" class="text-center py-4 text-yellow-400">Nenhuma compra registrada para este cliente.</td></tr>';
            return;
        }

        if (response.status === 404) {
            statusDiv.textContent = `Erro: Cliente ou recurso não encontrado.`;
            return;
        }

        if (!response.ok) throw new Error(`Status: ${response.status}`);

        const historico = await response.json();
        
        tbody.innerHTML = '';
        
        if (historico.length === 0) {
            tbody.innerHTML = '<tr><td colspan="4" class="text-center py-4 text-yellow-400">Nenhuma compra registrada para este cliente.</td></tr>';
            return;
        }


        // O Spring Boot retorna uma lista de Compra. Precisamos iterar pelos ItemCompra de cada Compra.
        historico.forEach(compra => {

            (compra.itensDaCompra || []).forEach(item => {
                const row = tbody.insertRow();
                row.classList.add('table-row-bg');
                row.insertCell().textContent = compra.id;

                // Formata a data
                row.insertCell().textContent = new Date(compra.dataCompra).toLocaleString('pt-BR');

                // Acesso aos detalhes do Jogo
                row.insertCell().textContent = item.jogo ? item.jogo.titulo : 'Jogo Removido';
                row.insertCell().textContent = `R$ ${item.precoUnitario.toFixed(2)}`;

                // Adiciona classes para estilo de célula
                Array.from(row.cells).forEach(cell => cell.classList.add('px-4', 'py-2', 'text-sm'));
            });
        });

    } catch (error) {
        statusDiv.textContent = `Erro ao buscar histórico: ${error.message}`;
        console.error('Erro de histórico:', error);
    }
}


// --- 4. Inicialização ---

window.onload = function() {
    // 1. Preenche o filtro de gêneros
    preencherFiltroGeneros();

    // 2. Carrega a tabela de jogos com os filtros iniciais
    carregarJogos();

    // 3. Carrega as tabelas de visualização básica
    carregarTabela('clientes', 'tabela-clientes', ['id', 'nome', 'email', 'dataNascimento']);
    carregarTabela('editoras', 'tabela-editoras', ['id', 'nome', 'sede']);
    carregarTabela('publicadoras', 'tabela-publicadoras', ['id', 'nome', 'contatoEmail']);
};
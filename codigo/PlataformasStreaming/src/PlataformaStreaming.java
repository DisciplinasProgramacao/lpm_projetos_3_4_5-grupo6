import java.io.IOException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.NameNotFoundException;

import Exceptions.SenhaFracaException;
import Exceptions.SenhaIncorretaException;

public class PlataformaStreaming {
    private String nome;
    private HashMap<Integer, Midia> midias;
    private HashMap<String, Cliente> clientes;
    private Cliente clienteAtual;

    /**
     * Cria uma nova instância da plataforma de streaming.
     *
     * @param nome O nome da plataforma.
     * @throws IllegalArgumentException se o nome for nulo ou vazio.
     */
    public PlataformaStreaming(String nome) throws IllegalArgumentException {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome da plataforma não pode ser nulo ou vazio.");
        }

        this.nome = nome;
        midias = new HashMap<>();
        clientes = new HashMap<>();
    }
    // #region Métodos

    /**
     * Tenta autenticar um usuário com um nome de usuário e senha fornecidos.
     *
     * @param nomeUsuario o nome de usuário do cliente a ser autenticado
     * @param senha       a senha do cliente a ser autenticado
     * @return o objeto Cliente correspondente ao nome de usuário fornecido se a
     *         autenticação for bem sucedida
     * @throws NameNotFoundException   se o nome de usuário fornecido não
     *                                 corresponder a nenhum cliente registrado
     * @throws SenhaIncorretaException se a senha fornecida não coincidir com a
     *                                 senha do cliente correspondente ao nome de
     *                                 usuário fornecido
     */
    public Cliente login(String nomeUsuario, String senha) throws NameNotFoundException, SenhaIncorretaException {
        Cliente cliente = clientes.get(nomeUsuario);
        if (cliente == null) {
            throw new NameNotFoundException("Usuário não encontrado.");
        }
        if (!cliente.senhaCorreta(senha)) {
            throw new SenhaIncorretaException("Senha incorreta");
        }
        this.clienteAtual = cliente;
        return cliente;
    }

    /**
     * Adiciona uma mídia ao banco de dados da plataforma.
     *
     * @param novMidia A série a ser adicionada.
     */
    public void adicionarSerie(Midia novMidia) {
        if (!midias.containsValue(novMidia))
            this.midias.put(novMidia.getId(), novMidia);
    }

    /**
     * Adiciona um cliente ao banco de dados da plataforma.
     *
     * @param cliente O cliente a ser adicionado.
     */
    public void adicionarCliente(Cliente cliente) {
        this.clientes.put(cliente.getLogin(), cliente);
    }

    public void cadastrarCliente(String nome, String login, String senha)
            throws IllegalArgumentException, SenhaFracaException {
        Cliente cliente = new Cliente(nome, login, senha);
        cliente.salvar();
        clientes.put(login, cliente);
    }

    /**
     * Filtra as mídias por gênero.
     *
     * @param genero O gênero das mídias a serem filtradas.
     * @return Uma lista contendo todas as mídias que pertencem ao gênero
     *         especificado.
     */
    public List<Midia> filtrarPorGenero(String genero) {
        FiltroGenero filtro = new FiltroGenero();
        List<Midia> arrayList = new ArrayList<>(midias.values());
        return filtro.comparar(arrayList, genero);
    }

    /**
     * Filtra as mídias por idioma.
     *
     * @param idioma O idioma das mídias a serem filtradas.
     * @return Uma lista contendo todas as mídias que pertencem ao idioma
     *         especificado.
     */
    public List<Midia> filtrarPorIdioma(String idioma) {
        FiltroIdioma filtro = new FiltroIdioma();
        List<Midia> arrayList = new ArrayList<>(midias.values());
        return filtro.comparar(arrayList, idioma);
    }

    /**
     * Filtra somente as séries por quantidade de episódios.
     *
     * @param quantEpisodios A quantidade de episódios das séries a serem filtradas.
     * @return Uma lista contendo todas as séries que contém a quantidade de
     *         episódios especificada.
     */
    public List<Midia> filtrarPorQtdEpisodios(int quantEpisodios) {
        FiltroTotalEp filtro = new FiltroTotalEp();
        List<Midia> arrayList = new ArrayList<>(midias.values());
        return filtro.comparar(arrayList, Integer.toString(quantEpisodios));
    }

    /**
     *
     * Registra a audiência de uma midia para o cliente atual.
     *
     * @param novaMidia A midia para a qual a audiência será registrada.
     * @throws InvalidParameterSpecException
     */
    public void registrarAudiencia(Midia novaMidia) throws InvalidParameterSpecException {
        if (this.clienteAtual != null) {
            this.clienteAtual.registrarAudiencia(novaMidia);
        }
    }

    /**
     * Realiza o logoff do cliente atual.
     * O cliente atual será definido como null.
     */
    public void logoff() {
        this.clienteAtual = null;
    }

    /**
     *
     * Carrega as séries a partir de um arquivo CSV.
     *
     * @throws IOException se ocorrer um erro durante a leitura do arquivo
     */
    public void carregarSeries() throws IOException {
        // id;nome;data
        String path = "assets/Series.csv";
        String[] todasSeries = Util.lerArquivo(path).split(System.lineSeparator());

        for (String SerieCSV : todasSeries) {
            String[] serie = SerieCSV.split(";");
            int id = Integer.parseInt(serie[0]);
            String nome = serie[1];
            String data = serie[2];
            String idioma = Util.gerarNovoIdioma();
            int ep = Util.gerarTotalEp();
            String genero = Util.gerarNovoGenero();
            Midia novaSerie = new Serie(id, nome, idioma, genero, ep, data);
            midias.put(id, novaSerie);
        }
    }

    public void cadastrarSerie(String id, String nome, String dataLancamento) {

    }

    /**
     *
     * Carrega os filmes a partir de um arquivo CSV.
     *
     * @throws IOException se ocorrer um erro durante a leitura do arquivo
     */
    public void carregarFilmes() throws IOException {
        // id;nome;data;duracao
        String path = "assets/Filmes.csv";
        String[] todosFilmes = Util.lerArquivo(path).split(System.lineSeparator());

        for (String FilmeCSV : todosFilmes) {
            String[] filme = FilmeCSV.split(";");
            int id = Integer.parseInt(filme[0]);
            String nome = filme[1];
            String data = filme[2];
            int duracao = Integer.parseInt(filme[3]);
            String idioma = Util.gerarNovoIdioma();
            String genero = Util.gerarNovoGenero();
            Midia novoFilme = new Filme(id, nome, idioma, genero, duracao, data);
            midias.put(id, novoFilme);
        }
    }

    /**
     *
     * Carrega os espectadores a partir de um arquivo CSV.
     *
     * @throws IOException              se ocorrer um erro durante a leitura do
     *                                  arquivo
     * @throws IllegalArgumentException se os dados do cliente forem inválidos
     * @throws SenhaFracaException      se a senha do cliente for considerada fraca
     */
    public void carregarEspectadores() throws IOException, IllegalArgumentException, SenhaFracaException {
        // nome;login;senha
        String path = "assets/Espectadores.csv";
        String[] todosClientes = Util.lerArquivo(path).split(System.lineSeparator());

        for (String ClienteCSV : todosClientes) {
            String[] cliente = ClienteCSV.split(";");
            String nome = cliente[0];
            String login = cliente[1];
            String senha = cliente[2];
            Cliente novoCliente = new Cliente(nome, login, senha);
            clientes.put(login, novoCliente);

        }
    }

    /**
     * Carrega os dados de audiência a partir de um arquivo CSV.
     *
     * @throws IOException                   se ocorrer um erro de entrada/saída ao
     *                                       ler o arquivo.
     * @throws InvalidParameterSpecException
     */
    public void carregarAudiencia() throws IOException, InvalidParameterSpecException {
        // user;?;idMidia
        String path = "assets/Audiencia.csv";
        String[] todosOsDados = Util.lerArquivo(path).split(System.lineSeparator());

        for (String AudienciaCSV : todosOsDados) {
            String[] audiencia = AudienciaCSV.split(";");
            String usuario = audiencia[0];
            String algumaCoisa = audiencia[1];
            int id = Integer.parseInt(audiencia[2]);
            Midia midiaNoMapa;
            Cliente clienteNoMapa;

            if (midias.containsKey(id)) {
                midiaNoMapa = midias.get(id);
            } else {
                throw new IOException("Mídia não encontrada");
            }

            if (clientes.containsKey(usuario)) {
                clienteNoMapa = clientes.get(usuario);
                clienteNoMapa.registrarAudiencia(midiaNoMapa);
            } else {
                throw new IOException("Usuário não encontrado");
            }
        }
    }

    /**
     * Busca uma mídia pelo nome.
     *
     * @param referencia O nome da série a ser buscada.
     * @return A série encontrada ou null caso não seja encontrada.
     */
    public Midia buscar(String referencia) {
        for (HashMap.Entry<Integer, Midia> entry : this.midias.entrySet()) {
            Midia midia = entry.getValue();
            if (midia.getNome().equals(referencia)) {
                return midia;
            }
        }
        return null;
    }

    /**
     * Registra uma avaliação de um usuário para uma determinada mídia.
     *
     * @param userLogin O login do usuário que está fazendo a avaliação.
     * @param midiaId   O ID da mídia que está sendo avaliada.
     * @param avaliacao A avaliação atribuída à mídia.
     * @throws IOException Se ocorrer um erro durante o processo de registro da
     *                     avaliação.
     */
    public void registrarAvaliacao(String userLogin, int midiaId, int avaliacao) throws IOException {
        String path_avaliacoes = "assets/Avaliacoes.csv";
        StringBuilder auxCSV = new StringBuilder();
        auxCSV.append(userLogin);
        auxCSV.append(";");
        auxCSV.append(midiaId);
        auxCSV.append(";");
        auxCSV.append(avaliacao);

        DAO dao = new DAO();
        dao.salvar(path_avaliacoes, auxCSV.toString());
    }

    /**
     *
     * Adiciona uma mídia à lista do cliente para ser vista posteriormente.
     *
     * @param novaMidia A nova mídia a ser adicionada.
     */
    public void adicionarNaListaParaVer(Midia novaMidia) {
        clienteAtual.adicionarNaLista(novaMidia);
    }

    public List<Midia> listarMidia() {
        List<Midia> copia = new ArrayList<>(midias.values());
        return copia;
    }

    public String getCurrentUserId() {
        if (clienteAtual == null) {
            return "Ada2"; // para fins de teste
        } else {
            return this.clienteAtual.getLogin();
        }

    }

}

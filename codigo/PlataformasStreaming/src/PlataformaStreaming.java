import java.io.IOException;
import java.security.InvalidParameterException;
import java.security.spec.InvalidParameterSpecException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.naming.NameNotFoundException;

import Exceptions.SenhaFracaException;
import Exceptions.SenhaIncorretaException;

public class PlataformaStreaming {
    private String nome;
    private HashMap<Integer, Midia> midias;
    private HashMap<String, Cliente> clientes;
    private Cliente clienteAtual;

    public HashMap<Integer, Midia> getMidias() {
        return midias;
    }

    public HashMap<String, Cliente> getClientes() {
        return clientes;
    }

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
     * Adiciona uma série ao banco de dados da plataforma.
     * 
     * @deprecated
     * @param novMidia A série a ser adicionada.
     */
    public void adicionarSerie(Midia novMidia) {
        adicionarMidia(novMidia);
    }

    /**
     * Adiciona uma mídia ao banco de dados da plataforma.
     *
     * @param novMidia A série a ser adicionada.
     */
    public void adicionarMidia(Midia novMidia) {
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

    /**
     * Salvar um novo cliente na plataforma
     * 
     * @param nome  nome do cliente
     * @param login login do cliente (nome de usuário)
     * @param senha senha do cliente
     * @throws IllegalArgumentException se os valores informados não são válidos
     *                                  para registrar um cliente (exemplo: login em
     *                                  branco)
     * @throws SenhaFracaException      se a senha não atende aos requisitos de uma
     *                                  senha forte
     * @throws IOException              se
     */
    public void cadastrarCliente(String nome, String login, String senha)
            throws IllegalArgumentException, SenhaFracaException, IOException {
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
        String generoComparativo = genero.toUpperCase();
        FiltroGenero filtro = new FiltroGenero();
        List<Midia> arrayList = new ArrayList<>(midias.values());
        return filtro.comparar(arrayList, generoComparativo);
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
     * @throws InvalidParameterException
     */
    public void registrarAudiencia(Midia novaMidia) throws InvalidParameterException {
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
        ArrayList<String> log = new ArrayList<>();
        int contador = 0;
        for (String SerieCSV : todasSeries) {
            String[] serie = SerieCSV.split(";");
            int id = Integer.parseInt(serie[0]);
            String nome = serie[1];
            String data = serie[2];
            String idioma = Util.gerarNovoIdioma();
            int ep = Util.gerarTotalEp();
            String genero = Util.gerarNovoGenero();
            try {
                Midia novaSerie = new Serie(id, nome, idioma, genero, ep, data);
                midias.put(id, novaSerie);
                contador++;
            } catch (Exception e) {
                log.add(e.getMessage());
            }
        }
        System.out.println("Foram carregados " + contador + " Séries com " + log.size() + " erros");
    }

    /**
     * Cadastra uma nova Serie no armazenamento de dados da aplicação.
     * 
     * @param id                  ID da nova série
     * @param nome                nome da nova série
     * @param idioma              idioma da nova série
     * @param genero              gênero da nova série
     * @param quantidadeEpisodios episódios da nova série
     * @param data                data de lançamento da nova série
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public void cadastrarSerie(int id, String nome, String idioma, String genero, int quantidadeEpisodios,
            String data) throws IOException, IllegalArgumentException {
        Midia midia = new Serie(id, nome, idioma, genero, quantidadeEpisodios, data);
        midia.salvar();
        midias.put(id, midia);
    }

    /**
     * * Cadastra um novo Filme no armazenamento de dados da aplicação.
     * 
     * @param id      ID do novo filme
     * @param nome    Nome do novo filme
     * @param idioma  Idioma do novo filme
     * @param genero  Gênero do novo filme
     * @param duracao Duração em minutos do novo filme
     * @param data    Data de lançamento do novo filme
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public void cadastrarFilme(int id, String nome, String idioma, String genero, int duracao, String data)
            throws IOException, IllegalArgumentException {
        Midia midia = new Filme(id, nome, idioma, genero, duracao, data);
        midia.salvar();
        midias.put(id, midia);
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
        ArrayList<String> log = new ArrayList<>();
        int contador = 0;

        for (String FilmeCSV : todosFilmes) {
            String[] filme = FilmeCSV.split(";");
            int id = Integer.parseInt(filme[0]);
            String nome = filme[1];
            String data = filme[2];
            int duracao = Integer.parseInt(filme[3]);
            String idioma = Util.gerarNovoIdioma();
            String genero = Util.gerarNovoGenero();
            try {
                Midia novoFilme = new Filme(id, nome, idioma, genero, duracao, data);
                midias.put(id, novoFilme);
                contador++;
            } catch (IllegalArgumentException e) {
                log.add(e.getMessage());
            }
        }
        System.out.println("Foram carregados " + contador + " Filmes com " + log.size() + " erros");
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
        ArrayList<String> log = new ArrayList<>();
        int contador = 0;
        for (String ClienteCSV : todosClientes) {
            String[] cliente = ClienteCSV.split(";");
            String nome = cliente[0];
            String login = cliente[1];
            String senha = cliente[2];
            try {
                Cliente novoCliente = new Cliente(nome, login, senha);
                clientes.put(login, novoCliente);
                contador++;
            } catch (IllegalArgumentException | SenhaFracaException e) {
                log.add(e.getMessage());
            }
        }
        System.out.println("Foram carregados " + contador + " Espectadores com " + log.size() + " erros");
    }

    /**
     * Carrega os dados de audiência a partir de um arquivo CSV.
     *
     * @throws IOException                   se ocorrer um erro de entrada/saída ao
     *                                       ler o arquivo.
     * @throws InvalidParameterSpecException
     */
    public void carregarAudiencia() throws IOException, InvalidParameterSpecException {
        String path = "assets/Audiencia.csv";
        String[] todosOsDados = Util.lerArquivo(path).split(System.lineSeparator());
        int contador = 0;
        ArrayList<String> log = new ArrayList<>();

        for (String audienciaCSV : todosOsDados) {
            String[] audiencia = audienciaCSV.split(";");
            String usuario = audiencia[0];
            String listaDestino = audiencia[1];
            int id = Integer.parseInt(audiencia[2]);

            if (!midias.containsKey(id)) {
                throw new IOException("Mídia não encontrada");
            }

            if (!clientes.containsKey(usuario)) {
                throw new IOException("Usuário não encontrado");
            }

            Midia midiaNoMapa = midias.get(id);
            Cliente clienteNoMapa = clientes.get(usuario);

            if (listaDestino.equals("F")) {
                try {
                    clienteNoMapa.adicionarNaLista(midiaNoMapa);
                } catch (InvalidParameterException e) {
                    String erro = "linha " + contador + " -" + e.getMessage();
                    log.add(erro);
                }
            } else {
                try {
                    clienteNoMapa.registrarAudiencia(midiaNoMapa);
                } catch (InvalidParameterException e) {
                    String erro = "linha " + contador + " -" + e.getMessage();
                    log.add(erro);
                }
            }
            contador++;
        }
        System.out.println("Foram carregados " + contador + " registros de audiência com " + log.size() + " erros");
    }

    /**
     * Carrega as avaliações a partir de um arquivo CSV.
     * O arquivo deve seguir o formato "userId;MidiaId;Avaliacao;data".
     * Cada linha do arquivo representa uma avaliação.
     *
     * @throws IOException                   se ocorrer um erro de leitura do
     *                                       arquivo
     * @throws InvalidParameterSpecException se houver um parâmetro inválido no
     *                                       arquivo de avaliações
     */
    public void carregarAvaliacoes() throws IOException, InvalidParameterSpecException {
        // userId;MidiaId;nota;data;avaliacao
        String path = "assets/Avaliacoes.csv";

        String[] todosOsDados = Util.lerArquivo(path).split(System.lineSeparator());
        int contador = 0;
        ArrayList<String> log = new ArrayList<>();
        for (String avaliacaoCSV : todosOsDados) {
            String[] avaliacao = avaliacaoCSV.split(";");
            String userID = avaliacao[0];
            int midiaId = Integer.parseInt(avaliacao[1]);
            int pontuacao = Integer.parseInt(avaliacao[2]);
            String dataString = avaliacao[3];
            String comentario = "";

            if (avaliacao.length == 5) {
                comentario = avaliacao[4];
            }

            SimpleDateFormat formato = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            Date data = null;
            try {
                formato.setTimeZone(TimeZone.getTimeZone("GMT-03:00"));
                data = formato.parse(dataString);
            } catch (ParseException e) {
                String erro = "Erro ao carregar a data" + e.getMessage();
                log.add(erro);
            }

            try {
                registrarAvaliacao(userID, midiaId, pontuacao, data, comentario);
                contador++;
            } catch (Exception e) {
                String erro = "Erro ao registrar avaliação" + e.getMessage();
                log.add(erro);
            }
        }
        System.out.println("Foram carregados " + contador + " Avaliações com " + log.size() + " erros");
    }

    /**
     * Busca uma mídia pelo nome.
     *
     * @param referencia O nome da série a ser buscada.
     * @return A série encontrada ou null caso não seja encontrada.
     */
    public Midia buscar(String referencia) {
        return midias.values().stream().filter(midia -> midia.getNome().equals(referencia))
                .findAny().orElse(null);
    }

    /**
     * Busca uma mídia pelo id.
     *
     * @param id O identificador da série a ser buscada.
     * @return A série encontrada ou null caso não seja encontrada.
     */
    public Midia buscar(int id) {
        return midias.get(id);
    }

    /**
     * Registra uma avaliação de um usuário para uma determinada mídia.
     *
     * @param userLogin O login do usuário que está fazendo a avaliação.
     * @param midiaId   O ID da mídia que está sendo avaliada.
     * @param avaliacao A avaliação atribuída à mídia.
     * @throws IOException              Se ocorrer um erro durante o processo de
     *                                  registro da avaliação.
     * @throws IllegalArgumentException Se algum dos parâmetros fornecidos for
     *                                  inválido.
     */
    public void registrarAvaliacao(String userLogin, int midiaId, int avaliacao, String comentario)
            throws IOException, IllegalArgumentException {
        Midia newMidia = this.buscar(midiaId);

        // Validação do userLogin
        if (userLogin == null || userLogin.isEmpty()) {
            throw new IllegalArgumentException("O login do usuário não pode ser nulo ou vazio.");
        }

        if (newMidia == null) {
            throw new IllegalArgumentException("O identificador da mídia está incorreto ou não existe.");
        }

        // Validação da avaliacao
        if (avaliacao < 1 || avaliacao > 5) {
            throw new IllegalArgumentException("A avaliação deve ser um valor entre 1 e 5.");
        }

        Avaliacao novaAvaliacao = new Avaliacao(userLogin, avaliacao, midiaId, comentario);
        newMidia.addAvaliacao(novaAvaliacao);
        novaAvaliacao.salvar();
    }

    /**
     * Registra uma avaliação de um usuário para uma determinada mídia.
     *
     * @param userLogin O login do usuário que está fazendo a avaliação.
     * @param midiaId   O ID da mídia que está sendo avaliada.
     * @param avaliacao A avaliação atribuída à mídia.
     * @param data      A data da avaliação.
     * @throws IOException              Se ocorrer um erro durante o processo de
     *                                  registro da avaliação.
     * @throws IllegalArgumentException Se algum dos parâmetros fornecidos for
     *                                  inválido.
     */
    public void registrarAvaliacao(String userLogin, int midiaId, int avaliacao, Date data, String comentario)
            throws IOException, IllegalArgumentException {
        Midia newMidia = this.buscar(midiaId);

        // Validação do userLogin
        if (userLogin == null || userLogin.isEmpty()) {
            throw new IllegalArgumentException("O login do usuário não pode ser nulo ou vazio.");
        }

        if (newMidia == null) {
            throw new IllegalArgumentException("O identificador da mídia está incorreto ou não existe.");
        }

        // Validação da avaliacao
        if (avaliacao < 1 || avaliacao > 5) {
            throw new IllegalArgumentException("A avaliação deve ser um valor entre 1 e 5.");
        }

        // Validação da data
        if (data == null) {
            throw new IllegalArgumentException("A data da avaliação não pode ser nula.");
        }

        Avaliacao novaAvaliacao = new Avaliacao(userLogin, avaliacao, midiaId, data, comentario);
        newMidia.addAvaliacao(novaAvaliacao);
    }

    /**
     *
     * Adiciona uma mídia à lista do cliente para ser vista posteriormente.
     *
     * @param novaMidia A nova mídia a ser adicionada.
     * @throws InvalidParameterException se a mídia já estivar na lista do usuário
     */
    public void adicionarNaListaParaVer(Midia novaMidia) throws InvalidParameterException {
        clienteAtual.adicionarNaLista(novaMidia);
    }

    /**
     * Dados de texto no formato CSV que representa as Mídias cadastradas
     * 
     * @return
     */
    public String listarMidia() {
        StringBuilder listaDeMidias = new StringBuilder();
        for (Midia midia : midias.values()) {
            listaDeMidias.append(midia.toString());
            listaDeMidias.append(Util.SEPARADOR_LINHA);
        }
        return listaDeMidias.toString();
    }

    public String getLoginClienteAtual() {
        return this.clienteAtual.getLogin();
    }

    public String getNome() {
        return nome;
    }

    public Cliente getClienteAtual() {
        return this.clienteAtual;
    }
}

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import javax.naming.NameNotFoundException;

import Exceptions.SenhaFracaException;
import Exceptions.SenhaIncorretaException;

public class PlataformaStreaming {
    private String nome;
    private HashMap<Integer, Serie> series;
    private HashMap<Integer, Filme> filmes;
    private HashMap<String, Cliente> clientes;
    private Cliente clienteAtual;

    public PlataformaStreaming(String nome) {
        this.nome = nome;
        series = new HashMap<>();
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
     * @param serie A série a ser adicionada.
     */
    public void adicionarSerie(Serie serie) {
        if (!series.containsValue(serie))
            this.series.put(serie.getId(), serie);
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
     * 
     * Filtra as séries por gênero.
     * 
     * @param genero O gênero a ser utilizado como critério de filtro.
     * @return Uma lista encadeada contendo as séries que correspondem ao gênero
     *         especificado.
     */
    public LinkedList<Serie> filtrarPorGenero(String genero) {
        LinkedList<Serie> seriesEncontradas = new LinkedList<>();
        for (HashMap.Entry<Integer, Serie> sr : this.series.entrySet()) {
            Serie serie = sr.getValue();
            if (serie.getGenero().equals(genero)) {
                seriesEncontradas.add(serie);
            }
        }
        return seriesEncontradas;
    }

    /**
     * 
     * Filtra as séries por idioma.
     * 
     * @param idioma O idioma pelo qual as séries devem ser filtradas.
     * @return Uma lista encadeada contendo as séries encontradas.
     */
    public LinkedList<Serie> filtrarPorIdioma(String idioma) {
        LinkedList<Serie> seriesEncontradas = new LinkedList<>();
        for (HashMap.Entry<Integer, Serie> sr : this.series.entrySet()) {
            Serie serie = sr.getValue();
            if (serie.getIdioma().equals(idioma)) {
                seriesEncontradas.add(serie);
            }
        }
        return seriesEncontradas;
    }

    /**
     * 
     * Filtra as séries por quantidade de episódios.
     * 
     * @param quantEpisodios A quantidade de episódios desejada para a filtragem.
     * @return Uma lista encadeada contendo as séries encontradas.
     */
    public LinkedList<Serie> filtrarPorQtdEpisodios(int quantEpisodios) {
        LinkedList<Serie> seriesEncontradas = new LinkedList<>();
        for (HashMap.Entry<Integer, Serie> sr : this.series.entrySet()) {
            Serie serie = sr.getValue();
            if (serie.getQuantidadeEpisodios() == quantEpisodios) {
                seriesEncontradas.add(serie);
            }
        }
        return seriesEncontradas;
    }

    /**
     * 
     * Registra a audiência de uma série para o cliente atual.
     * 
     * @param serie A série para a qual a audiência será registrada.
     */
    public void registrarAudiencia(Serie serie) {
        if (this.clienteAtual != null) {
            this.clienteAtual.registrarAudiencia(serie);
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
        String path = "codigo/assets/Series.csv";
        String[] todasSeries = Util.lerArquivo(path).split(System.lineSeparator());

        for (String SerieCSV : todasSeries) {
            String[] serie = SerieCSV.split(";");
            int id = Integer.parseInt(serie[0]);
            String nome = serie[1];
            String data = serie[2];
            String idioma = Util.gerarNovoIdioma();
            int ep = Util.gerarTotalEp();
            String genero = Util.gerarNovoGenero();
            Serie novaSerie = new Serie(id, nome, idioma, genero, ep, data);
            series.put(id, novaSerie);
            System.out.println(novaSerie.getNome());
        }
    }

    /**
     * 
     * Carrega os filmes a partir de um arquivo CSV.
     * 
     * @throws IOException se ocorrer um erro durante a leitura do arquivo
     */
    public void carregarFilmes() throws IOException {
        // id;nome;data;duracao
        String path = "codigo/assets/Filmes.csv";
        String[] todosFilmes = Util.lerArquivo(path).split(System.lineSeparator());

        for (String FilmeCSV : todosFilmes) {
            String[] filme = FilmeCSV.split(";");
            int id = Integer.parseInt(filme[0]);
            String nome = filme[1];
            String data = filme[2];
            int duracao = Integer.parseInt(filme[3]);
            String idioma = Util.gerarNovoIdioma();
            String genero = Util.gerarNovoGenero();
            Filme novoFilme = new Filme(id, nome, idioma, genero, duracao, data);
            filmes.put(id, novoFilme);
            System.out.println(novoFilme.getNome());
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
        String path = "codigo/assets/Espectadores.csv";
        String[] todosClientes = Util.lerArquivo(path).split(System.lineSeparator());

        for (String ClienteCSV : todosClientes) {
            String[] cliente = ClienteCSV.split(";");
            String nome = cliente[0];
            String login = cliente[1];
            String senha = cliente[2];
            Cliente novoCliente = new Cliente(nome, login, senha);
            clientes.put(login, novoCliente);
            System.out.println(novoCliente.getLogin());
            
        }
    }

    /**
     * Carrega os dados de audiência a partir de um arquivo CSV.
     *
     * @throws IOException se ocorrer um erro de entrada/saída ao ler o arquivo.
     */
    public void carregarAudiencia() throws IOException {
        // user;?;idMidia
        String path = "codigo/assets/Audiencia.csv";
        String[] todosOsDados = Util.lerArquivo(path).split(System.lineSeparator());

        for (String AudienciaCSV : todosOsDados) {
            String[] audiencia = AudienciaCSV.split(";");
            String usuario = audiencia[0];
            String algumaCoisa = audiencia[1];
            int id = Integer.parseInt(audiencia[2]);
            Midia midiaNoMapa;
            Cliente clienteNoMapa;
            if (series.containsKey(id)) {
                midiaNoMapa = series.get(id);
            } else if (filmes.containsKey(id)) {
                midiaNoMapa = filmes.get(id);
            } else {
                throw new IOException("Mídia não encontrada");
            }

            if (clientes.containsKey(usuario)) {
                clienteNoMapa = clientes.get(usuario);
                clienteNoMapa.registrarAudiencia(midiaNoMapa);
                System.out.print(midiaNoMapa.getNome() + "Audiencia: ");
                System.out.println(midiaNoMapa.getAudiencia());
            } else {
                throw new IOException("Usuário não encontrado");
            }
        }
    }

    /**
     * 
     * Busca uma série pelo nome.
     * 
     * @param nomeSerie O nome da série a ser buscada.
     * @return A série encontrada ou null caso não seja encontrada.
     */
    public Serie buscarSerie(String nomeSerie) {
        for (HashMap.Entry<Integer, Serie> sr : this.series.entrySet()) {
            Serie serie = sr.getValue();
            if (serie.getNome().equals(nomeSerie)) {
                return serie;
            }
        }
        return null;
    }
}
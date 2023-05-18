import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import javax.naming.NameNotFoundException;
import Exceptions.SenhaIncorretaException;

public class PlataformaStreaming {
    private String nome;
    private HashMap<Integer, Serie> series;
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

    public void carregarFilmes() {
        //id;nome;data;duracao
        String path = "codigo/assets/Filmes.csv";
        
    }

    public void carregarEspectadores() {

    }

    public void carregarAudiencia() {

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
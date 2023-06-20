import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Midia {

    static int proximo_id = 100;
    static final EnumGeneros[] GENEROS = EnumGeneros.values();

    private int id;
    private String nome;
    private String data;
    private String genero;
    private String idioma;
    private int audiencia;
    private List<Avaliacao> avaliacoes;

    /**
     * Cria uma nova instância da classe Midia com o nome, idioma e gênero
     * especificados.
     *
     * @param nome   o nome da mídia (obrigatório)
     * @param idioma o idioma da mídia (obrigatório)
     * @param genero o gênero, previamente cadastrado, da mídia (obrigatório)
     *
     * @throws IllegalArgumentException se o nome, idioma ou gênero forem nulos ou
     *                                  vazios
     */
    public Midia(String nome, String idioma, String genero) {
        validarParametros(nome, idioma, genero);
        this.avaliacoes = new ArrayList<>();
        this.nome = nome;
        this.idioma = idioma;
        this.genero = genero;
        this.id = proximo_id;
        proximo_id++;
    }

    /**
     * Cria uma nova instância da classe Midia com id, nome, data, idioma e gênero
     * especificados.
     *
     * @param id     o id da mídia (obrigatório)
     * @param nome   o nome da mídia (obrigatório)
     * @param data   a data em String da mídia (obrigatório)
     * @param idioma o idioma da mídia (obrigatório)
     * @param genero o gênero, previamente cadastrado, da mídia (obrigatório)
     *
     * @throws IllegalArgumentException se o nome, idioma ou gênero forem nulos ou
     *                                  vazios
     */
    public Midia(int id, String nome, String data, String idioma, String genero) {
        validarParametros(nome, idioma, genero);
        if (data == null || data.trim().isEmpty()) {
            throw new IllegalArgumentException("A data não pode ser vazia ou nula.");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("O id não pode ser negativo");
        }
        this.avaliacoes = new ArrayList<>();
        this.nome = nome;
        this.idioma = idioma;
        this.genero = genero;
        this.data = data;
        this.id = id;
    }

    /**
     * Valida os parâmetros de nome, idioma e gênero. Lança uma exceção se algum
     * parâmetro for nulo ou vazio, ou se o gênero não estiver cadastrado na
     * plataforma.
     *
     * @param nome   o nome da mídia a ser validado
     * @param idioma o idioma da mídia a ser validado
     * @param genero o gênero da mídia a ser validado
     *
     * @throws IllegalArgumentException se o nome, idioma ou gênero forem nulos ou
     *                                  vazios
     * @throws IllegalArgumentException se o gênero não estiver cadastrado na
     *                                  plataforma
     */
    private void validarParametros(String nome, String idioma, String genero) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio ou nulo.");
        }
        if (idioma == null || idioma.trim().isEmpty()) {
            throw new IllegalArgumentException("O idioma não pode ser vazio ou nulo.");
        }
        if (genero == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("O gênero não pode ser vazio ou nulo.");
        }
        boolean existeGenero = Arrays.stream(GENEROS)
                .map(EnumGeneros::getDescricao)
                .anyMatch(descricao -> descricao.equals(genero));

        if (!existeGenero) {
            throw new IllegalArgumentException("O gênero não está cadastrado na plataforma.");
        }
    }

    /**
     * Registra um ponto de audiência para a mídia. A cada vez que este método for
     * chamado, a contagem de audiência será incrementada em 1.
     *
     * Nota: Este método não recebe parâmetros e não retorna nenhum valor.
     *
     * @see #getAudiencia() Método que retorna a audiência atual da mídia
     */
    public void registrarAudiencia() {
        this.audiencia++;
    }

    /**
     * Retorna a audiência atual da mídia. A audiência representa a quantidade de
     * espectadores que assistiram à mídia.
     *
     * @return a audiência atual da mídia
     */
    public int getAudiencia() {
        return this.audiencia;
    }

    /**
     * Retorna o gênero associado a essa instância.
     *
     * @return uma String representando o gênero dessa instância.
     */
    public String getGenero() {
        return genero;
    }

    /**
     * Retorna o nome associado a essa instância.
     *
     * @return um String representando o nome dessa instância.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna o idioma associado a essa instância.
     *
     * @return um String representando o idioma dessa instância.
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * Retorna o id associado a essa instância.
     *
     * @return um inteiro representando o idioma dessa instância.
     */
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        StringBuilder aux = new StringBuilder();
        aux.append(this.id);
        aux.append(Util.SEPARADOR_CSV);
        aux.append(this.nome);
        aux.append(Util.SEPARADOR_CSV);
        aux.append(this.data);
        return aux.toString();
    }

    /**
     * Adiciona uma avaliação à mídia.
     *
     * @param avaliacao a avaliação a ser adicionada
     *
     * @throws IllegalArgumentException se o cliente já tiver avaliado esta mídia
     */
    public void addAvaliacao(Avaliacao avaliacao) {
        boolean existe = this.avaliacoes.stream()
                .anyMatch(a -> avaliacao.getLogin().equals(a.getLogin()));

        if (!existe) {
            this.avaliacoes.add(avaliacao);
        } else {
            throw new IllegalArgumentException("Este cliente já avaliou esta mídia.");
        }
    }

    /**
     * Retorna uma lista contendo todas as avaliações da mídia.
     *
     * @return uma lista com as avaliações da mídia
     */
    public List<Avaliacao> getTodasAvaliacoes() {
        return new ArrayList<>(avaliacoes);
    }

    protected abstract void salvar() throws IOException;
}

import java.util.Arrays;

public abstract class Midia {

    // #region Atributos
    static int proximo_id = 100;
    static final String[] GENEROS = {
            "Ação",
            "Comédia",
            "Drama",
            "Fantasia",
            "Horror",
            "Mistério",
            "Romance"
    };
    private int id;
    private String nome;
    private String data;
    private String genero;
    private String idioma;
    private int audiencia;
    
    // #endregion

    // #region Construtor
    /**
     * Cria uma nova instância da classe Midia com o nome, idioma e gênero
     * especificados e cria um id aleatório.
     * 
     * @param nome   o nome da mídia (obrigatório)
     * @param idioma o idioma da mídia (obrigatório)
     * @param genero o gênero, previamente cadastrado,da mídia (obrigatório)
     * 
     * @throws IllegalArgumentException se o nome, idioma ou gênero forem nulos ou
     *                                  vazios
     */
    public Midia(String nome, String idioma, String genero) throws IllegalArgumentException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da não pode ser vazio ou nulo.");
        }

        if (idioma == null || idioma.trim().isEmpty()) {
            throw new IllegalArgumentException("O idioma da não pode ser vazio ou nulo.");
        }

        if (genero == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("O gênero da não pode ser vazio ou nulo.");
        }

        boolean existeGenero = Arrays.asList(GENEROS).contains(genero);

        if (!existeGenero) {
            throw new IllegalArgumentException("O Genero não está cadastrado na plataforma");
        }

        this.nome = nome;
        this.idioma = idioma;
        this.genero = genero;
        this.id = proximo_id;
        proximo_id++;

        // OBSERVAÇÕES DE ESTUDO:
        // O método trim() é um método da classe String em Java que remove os espaços em
        // branco no início e no final de uma string. Ele retorna uma cópia da string
        // original, sem os espaços em branco no início e no final.
    }

    /**
     * Cria uma nova instância da classe Midia com id, nome, data, idioma e gênero
     * especificados.
     * 
     * @param id     o id da mídia (obrigatório)
     * @param nome   o nome da mídia (obrigatório)
     * @param data   a data em Sting da mídia (obrigatório)
     * @param idioma o idioma da midia (obrigatório)
     * @param genero o gênero, previamente cadastrado,da midia (obrigatório)
     * 
     * @throws IllegalArgumentException se o nome, idioma ou gênero forem nulos ou
     *                                  vazios
     */
    public Midia(int id, String nome, String data, String idioma, String genero) throws IllegalArgumentException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio ou nulo.");
        }

        if (idioma == null || idioma.trim().isEmpty()) {
            throw new IllegalArgumentException("O idioma não pode ser vazio ou nulo.");
        }

        if (genero == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("O gênero não pode ser vazio ou nulo.");
        }

        if (data == null || genero.trim().isEmpty()) {
            throw new IllegalArgumentException("O gênero não pode ser vazio ou nulo.");
        }

        if (id <= 0) {
            throw new IllegalArgumentException("O id não pode ser negativo");
        }

        boolean existeGenero = Arrays.asList(GENEROS).contains(genero);

        if (!existeGenero) {
            throw new IllegalArgumentException("O Genero não está cadastrado na plataforma");
        }

        this.nome = nome;
        this.idioma = idioma;
        this.genero = genero;
        this.data = data;
        this.id = id;
    }
    // #endregion Construtor

    /**
     * Registra um ponto de audiência para a série. A cada vez que este método for
     * chamado, a contagem de audiência será incrementada em 1.
     * 
     * Nota: Este método não recebe parâmetros e não retorna nenhum valor.
     * 
     * @see #getAudiencia() Método que retorna a audiência atual da série
     */
    public void registrarAudiencia() {
        this.audiencia++;
    }

    /**
     * Retorna a audiência atual da série. A audiência representa a quantidade de
     * espectadores que assistiram à série.
     * 
     * @return a audiência atual da série
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
        aux.append("-");
        aux.append(this.nome);
        aux.append(System.lineSeparator());

        return aux.toString();
    }

}

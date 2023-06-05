public class Filme extends Midia {

    // #region Atributos

    private double duracao;

    // #endregion

    // #region Construtor
    /**
     * 
     * Cria uma nova instância da classe Filme com os parâmetros especificados.
     * 
     * @param id      o id do filme
     * @param nome    o nome do filme
     * @param idioma  o idioma do filme
     * @param genero  o gênero do filme
     * @param duracao a duração do filme em minutos
     * @param data    a data do filme
     * @throws IllegalArgumentException se algum dos parâmetros obrigatórios for
     *                                  nulo ou vazio
     */
    public Filme(int id, String nome, String idioma, String genero, int duracao, String data)
            throws IllegalArgumentException {
        super(id, nome, data, idioma, genero);

        if (duracao <= 0) {
            throw new IllegalArgumentException("A duração não pode ser menor ou igual a zero.");
        }

        this.duracao = duracao * 60;
    }

    /**
     * 
     * Cria uma nova instância da classe Filme com os parâmetros especificados.
     * 
     * 
     * @param nome    o nome do filme
     * @param idioma  o idioma do filme
     * @param genero  o gênero do filme
     * @param duracao a duração do filme em minutos
     * 
     * 
     * @throws IllegalArgumentException se algum dos parâmetros obrigatórios for
     *                                  nulo ou vazio
     */
    public Filme(String nome, String idioma, String genero, Double duracao) throws IllegalArgumentException {
        super(nome, idioma, genero);

        if (duracao <= 0) {
            throw new IllegalArgumentException("A duração não pode ser menor ou igual a zero.");
        }

        this.duracao = duracao * 60;
    }

    // #endregion Construtor

    /**
     * Retorna a duracao em segundos.
     * 
     * @return um double com a duração em segundos.
     */
    public double getDuracao() {
        return this.duracao;
    }



}

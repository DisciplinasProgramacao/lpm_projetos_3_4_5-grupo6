import java.io.IOException;

public class Serie extends Midia {

    // #region Atributos

    private int quantidadeEpisodios;

    // #endregion

    // #region Construtor
    /**
     * Cria uma nova instância da classe Serie com os parâmetros especificados.
     * 
     * @param id                  o id da série
     * @param nome                o nome da série
     * @param idioma              o idioma da série
     * @param genero              o gênero da série
     * @param quantidadeEpisodios a quantidade de episódios da série
     * @param data                a data da série
     * @throws IllegalArgumentException se algum dos parâmetros obrigatórios for
     *                                  nulo ou vazio
     */
    public Serie(int id, String nome, String idioma, String genero, int quantidadeEpisodios, String data)
            throws IllegalArgumentException {
        super(id, nome, data, idioma, genero);

        if (quantidadeEpisodios <= 0) {
            throw new IllegalArgumentException("A quantidade de episódios não pode ser menor ou igual a zero.");
        }

        this.quantidadeEpisodios = quantidadeEpisodios;
    }

    /**
     * 
     * Cria uma nova instância da classe Serie com os parâmetros especificados e
     * gera um id automático.
     * 
     * @param nome                o nome da série
     * @param idioma              o idioma da série
     * @param genero              o gênero da série
     * @param quantidadeEpisodios a quantidade de episódios da série
     * @throws IllegalArgumentException se algum dos parâmetros obrigatórios for
     *                                  nulo ou vazio
     */
    public Serie(String nome, String idioma, String genero, int quantidadeEpisodios)
            throws IllegalArgumentException {
        super(nome, idioma, genero);

        if (quantidadeEpisodios <= 0) {
            throw new IllegalArgumentException("A quantidade de episódios não pode ser menor ou igual a zero.");
        }

        this.quantidadeEpisodios = quantidadeEpisodios;
    }

    // #endregion Construtor

    /**
     * 
     * Retorna a quantidade de episódios associada a essa instância.
     * 
     * @return um inteiro representando a quantidade de episódios dessa instância.
     */
    public int getQuantidadeEpisodios() {
        return quantidadeEpisodios;
    }

    @Override
    public void salvar() throws IOException {
        DAO dao = new DAO();
        dao.salvar(Util.CAMINHO_ARQUIVO_SERIES, this.toString());
    }

    @Override
    public String toString() {
        StringBuilder aux = new StringBuilder();
        aux.append("SÉRIE >>>");
        aux.append(super.toString());
        return aux.toString();
    }
}

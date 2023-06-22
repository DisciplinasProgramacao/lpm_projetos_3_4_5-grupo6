import java.io.IOException;

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

    /**
     * Salva o Filme no armazenamento de dados do projeto
     * 
     * @throws IOException
     */
    public void salvar() throws IOException {
        DAO dao = new DAO();
        dao.salvar(Util.CAMINHO_ARQUIVO_FILMES, this.toStringCSV());
    }

    @Override
    public String toStringCSV() {
        StringBuilder filmeParaCSV = new StringBuilder();
        filmeParaCSV.append(super.toStringCSV());
        filmeParaCSV.append(Util.SEPARADOR_CSV);
        filmeParaCSV.append((int) this.duracao / 60);
        return filmeParaCSV.toString();
    }

    @Override
    public String toString() {
        StringBuilder aux = new StringBuilder();

        aux.append(System.lineSeparator());
        aux.append("Filme");
        aux.append(System.lineSeparator());
        aux.append(super.toString());

        return aux.toString();
    }

}

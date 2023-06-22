import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Date;

/**
 * 
 * Representa uma avaliação feita por um usuário.
 */
public class Avaliacao {

    private String login;
    private Date dataDaAvaliacao;
    private int pontuacao;
    private int midiaIdAvaliada;
    private String comentario;

    /**
     * 
     * Constrói um novo objeto Avaliacao com o login e pontuação especificados.
     * 
     * @param login           o login do usuário que fez a avaliação
     * @param avaliacao       a pontuação da avaliação (entre 1 e 5)
     * @param midiaIdAvaliada o identificador da mídia avaliada
     * @throws InvalidParameterException se o login for nulo ou a pontuação estiver
     *                                   fora do intervalo válido
     */
    public Avaliacao(String login, int avaliacao, int midiaIdAvaliada)
            throws InvalidParameterException {
        validarLogin(login);
        validarPontuacao(avaliacao);
        validarIdMidia(midiaIdAvaliada);
        this.login = login;
        this.pontuacao = avaliacao;
        this.dataDaAvaliacao = new Date();
        this.midiaIdAvaliada = midiaIdAvaliada;
        this.comentario = comentario;
    }

    /**
     * 
     * Constrói um novo objeto Avaliacao com o login e pontuação especificados e com
     * um comentário.
     * 
     * @param login           o login do usuário que fez a avaliação
     * @param avaliacao       a pontuação da avaliação (entre 1 e 5)
     * @param midiaIdAvaliada o identificador da mídia avaliada
     * @param comentario      o comentário do cliente
     * @throws InvalidParameterException se o login for nulo ou a pontuação estiver
     *                                   fora do intervalo válido
     */
    public Avaliacao(String login, int avaliacao, int midiaIdAvaliada, String comentario)
            throws InvalidParameterException {
        validarLogin(login);
        validarPontuacao(avaliacao);
        validarIdMidia(midiaIdAvaliada);
        this.login = login;
        this.pontuacao = avaliacao;
        this.dataDaAvaliacao = new Date();
        this.midiaIdAvaliada = midiaIdAvaliada;
        this.comentario = comentario;
    }

    /**
     * 
     * Constrói um novo objeto Avaliacao com o login e pontuação especificados.
     * 
     * @param login           o login do usuário que fez a avaliação
     * @param avaliacao       a pontuação da avaliação (entre 1 e 5)
     * @param midiaIdAvaliada o identificador da mídia avaliada
     * @param data            data da avaliação
     * @throws InvalidParameterException se o login for nulo ou a pontuação estiver
     *                                   fora do intervalo válido
     */
    public Avaliacao(String login, int avaliacao, int midiaIdAvaliada, Date data) throws InvalidParameterException {
        validarLogin(login);
        validarPontuacao(avaliacao);
        validarIdMidia(midiaIdAvaliada);
        this.login = login;
        this.pontuacao = avaliacao;
        this.dataDaAvaliacao = data;
        this.midiaIdAvaliada = midiaIdAvaliada;
    }

    public void salvar() throws IOException {
        String path_avaliacoes = "assets/Avaliacoes.csv";
        StringBuilder auxCSV = new StringBuilder();
        auxCSV.append(login);
        auxCSV.append(";");
        auxCSV.append(midiaIdAvaliada);
        auxCSV.append(";");
        auxCSV.append(pontuacao);
        auxCSV.append(";");
        auxCSV.append(dataDaAvaliacao);
        if (comentario.length() > 1) {
            auxCSV.append(";");
            auxCSV.append(comentario);
        }
        DAO dao = new DAO();
        dao.salvar(path_avaliacoes, auxCSV.toString());
    }

    public void validarIdMidia(int midia) {
        if (midia == 0) {
            throw new InvalidParameterException("Valor da mídia inválido");
        }
    }

    /**
     * 
     * Valida o parâmetro de login.
     * 
     * @param login o login a ser validado
     * @throws InvalidParameterException se o login for nulo
     */
    private void validarLogin(String login) throws InvalidParameterException {
        if (login == null) {
            throw new InvalidParameterException("Cliente não pode ser null");
        }
    }

    /**
     * 
     * Valida o parâmetro de pontuação.
     * 
     * @param pontuacao a pontuação a ser validada
     * @throws InvalidParameterException se a pontuação estiver fora do intervalo
     *                                   válido
     */
    private void validarPontuacao(int pontuacao) throws InvalidParameterException {
        if (pontuacao < 1 || pontuacao > 5) {
            throw new InvalidParameterException("A deve ser um valor entre 1 e 5");
        }
    }

    /**
     * 
     * Obtém o login do usuário que fez a avaliação.
     * 
     * @return o login do usuário
     */
    public String getLogin() {
        return login;
    }

    /**
     * 
     * Obtém a data da avaliação.
     * 
     * @return a data da avaliação
     */
    public Date getDataDaAvaliacao() {
        return dataDaAvaliacao;
    }

    /**
     * 
     * Obtém a pontuação da avaliação.
     * 
     * @return a pontuação da avaliação
     */
    public int getPontuacao() {
        return pontuacao;
    }

    public int getMidiaIdAvaliada() {
        return midiaIdAvaliada;
    }
}
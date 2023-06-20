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

    /**
     * 
     * Constrói um novo objeto Avaliacao com o login e pontuação especificados.
     * 
     * @param login     o login do usuário que fez a avaliação
     * @param pontuacao a pontuação da avaliação (entre 1 e 5)
     * @throws InvalidParameterException se o login for nulo ou a pontuação estiver
     *                                   fora do intervalo válido
     */
    public Avaliacao(String login, int pontuacao) throws InvalidParameterException {
        validarLogin(login);
        validarPontuacao(pontuacao);
        this.login = login;
        this.pontuacao = pontuacao;
        this.dataDaAvaliacao = new Date();
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
}
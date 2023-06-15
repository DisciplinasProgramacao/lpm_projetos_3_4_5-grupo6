import java.security.InvalidParameterException;
import java.security.spec.InvalidParameterSpecException;
import java.util.Date;

public class Avaliacao {

    private String login;
    private Date dataDaAvaliacao;
    private int pontuacao;

    public String getLogin() {
        return login;
    }

    public Avaliacao(String login, int pontuacao) {
        if (login == null) {
            throw new InvalidParameterException("Cliente inválido");
        }
        if (pontuacao < 1 || pontuacao > 5) {
            throw new InvalidParameterException("Digite uma nota entre 1 e 5");
        }
        this.login = login;
        this.pontuacao = pontuacao;
        this.dataDaAvaliacao = new Date();
    }
}

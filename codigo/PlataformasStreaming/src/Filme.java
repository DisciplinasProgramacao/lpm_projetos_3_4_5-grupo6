import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Filme extends Midia {
	private double duracao;

	public Filme(int id, String nome, String dataLancamento, double duracao) {
		super(id, nome, dataLancamento);
		this.duracao = duracao;
	}
}

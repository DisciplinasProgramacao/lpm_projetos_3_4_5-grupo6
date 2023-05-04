import lombok.Data;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Data
public class Midia {

	private static int id;
	private int audiencia;
	private String nome;
	private String genero;
	private String idioma;
	private LocalDate dataLancamento;
	static final String[] GENEROS = {
			"Ação",
			"Comédia",
			"Drama",
			"Fantasia",
			"Horror",
			"Mistério",
			"Romance"
	};

	static final String[] IDIOMAS = {
			"Inglês",
			"Português",
			"Espanhol",
			"Italiano",
			"Francês",
			"Chinês"
	};

	public Midia(int id, String nome, String dataLancamento) {
		this.dataLancamento = validadados(dataLancamento);
		this.id = id;
		this.audiencia = 0;
		this.nome = nome;
		Random random = new Random();
		this.genero = GENEROS[random.nextInt()];
		this.idioma = IDIOMAS[random.nextInt()];
	}

	private LocalDate validadados(String dataLancamento) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date = LocalDate.parse(dataLancamento,formatter);

		if (id <= 0)
			throw new InvalidParameterException("Erro ao criar série: id " + id + " inválido!");

		if (nome.length() <= 0)
			throw new InvalidParameterException("Erro ao criar série: Nome " + nome + " inválido!");

//    if (contemGenero())
//      throw new InvalidParameterException("Erro ao criar série: Gênero " + genero + " inválido!");

//    if (idioma.length() <= 0)
//      throw new InvalidParameterException("Erro ao criar série: Idioma " + idioma + " inválido");

		if (date.compareTo(LocalDate.now()) == 0)
			throw new InvalidParameterException("Erro ao criar série: Data foi aplicada no futuro");

		return date;
	}

	public void registrarAudiencia() {
		this.audiencia++;
	}

	public static int getId() {
		return id;
	}

	public int getAudiencia() {
		return audiencia;
	}

	public String getNome() {
		return nome;
	}

	public String getGenero() {
		return genero;
	}

	public String getIdioma() {
		return idioma;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}
}

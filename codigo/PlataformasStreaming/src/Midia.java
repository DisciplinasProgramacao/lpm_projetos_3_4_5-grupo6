import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Midia {
	private static final String[] GENEROS = {
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
	private LocalDate dataLancamento;
	private String genero;
	private String idioma;
	private int audiencia;

	public void init(String id, String nome, String dataLancamento) {
		DateTimeFormatter formatadorDeDatas = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		if (id.length() <= 0)
			throw new InvalidParameterException("Erro ao criar mídia: ID " + id + " inválido!");

		if (nome.length() <= 0)
			throw new InvalidParameterException("Erro ao criar mídia: Nome " + nome + " inválido!");

		this.id = Integer.parseInt(id);
		this.nome = nome;

		this.dataLancamento = LocalDate.parse(dataLancamento, formatadorDeDatas);
		this.audiencia = 0;
	}

	public Midia(String id, String nome, String dataLancamento) {
		init(id, nome, dataLancamento);
		this.genero = GENEROS[new Random().nextInt(GENEROS.length)];
		this.idioma = "Inglês";
	}

	public void registrarAudiencia() {
		this.audiencia++;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	public String getGenero() {
		return genero;
	}

	public String getIdioma() {
		return idioma;
	}

	public int getAudiencia() {
		return audiencia;
	}
}

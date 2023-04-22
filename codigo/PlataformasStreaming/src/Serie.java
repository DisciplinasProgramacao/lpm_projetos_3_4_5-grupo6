import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Serie {
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
  private int quantidadeEpisodios;
  private int audiencia;

  public void init(String id, String nome, String dataLancamento) {
    DateTimeFormatter formatadorDeDatas = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    if (id.length() <= 0)
      throw new InvalidParameterException("Erro ao criar série: ID " + id + " inválido!");

    if (nome.length() <= 0)
      throw new InvalidParameterException("Erro ao criar série: Nome " + nome + " inválido!");

    this.id = Integer.parseInt(id);
    this.nome = nome;
    this.dataLancamento = LocalDate.parse(dataLancamento, formatadorDeDatas);
  }

  public Serie(String parametros) {
    String[] listaParametros = parametros.split(";");

    String id = listaParametros[0];
    String nome = listaParametros[1];
    String dataLancamento = listaParametros[2];

    init(id, nome, dataLancamento);

    this.audiencia = 0;
    this.genero = GENEROS[new Random().nextInt(GENEROS.length)];
    this.idioma = "Inglês";
    this.quantidadeEpisodios = 0;
    this.audiencia = 0;
  }

  public void registrarAudiencia() {
    this.audiencia++;
  }

  public String getName() {
    return this.nome;
  }

  public String getGenero() {
    return this.genero;
  }

  public String getIdioma() {
    return this.idioma;
  }

  public Integer getQuantidadeEpisodios() {
    return this.quantidadeEpisodios;
  }

  public int getAudiencia() {
    return this.audiencia;
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
}

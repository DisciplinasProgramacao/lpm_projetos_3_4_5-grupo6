public class Serie {
  private static final String[] GENEROS = { "Ação", "Aventura", "Comédia" };
  private String nome;
  private String genero;
  private String idioma;
  private int quantidadeEpisodios;
  private int audiencia;

  public Serie(String nome, String genero, String idioma, int quantidadeEpisodios) {
    if (nome.length() > 0) {
      this.nome = nome;
    }
    int i = 0;
    while (genero.toLowerCase() == null && i < GENEROS.length) {
      if (genero.toLowerCase() == GENEROS[i]) {
        this.genero = genero;
      }
      i++;
    }
    if (idioma.length() > 0) {
      this.idioma = idioma;
    }
    if (quantidadeEpisodios >= 0) {
      this.quantidadeEpisodios = quantidadeEpisodios;
    }
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
    return 0;
  }
}

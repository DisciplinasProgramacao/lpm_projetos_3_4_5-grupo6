import java.time.LocalDate;

public class Serie extends Midia {
  private int quantidadeEpisodios;

  public Serie(int id, String nome, String dataLancamento) {
    super(id, nome, dataLancamento);
  }

  public int getQuantidadeEpisodios() {
    return quantidadeEpisodios;
  }
}
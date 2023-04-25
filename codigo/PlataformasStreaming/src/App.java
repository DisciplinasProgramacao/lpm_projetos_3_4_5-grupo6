import java.io.FileNotFoundException;

public class App {
  public static void main(String[] args) throws FileNotFoundException {
    PlataformaStreaming plataforma = new PlataformaStreaming("pucflix");

    plataforma.carregarClientes();
    plataforma.carregarSeries();
    plataforma.carregarAudiencias();
  }
}

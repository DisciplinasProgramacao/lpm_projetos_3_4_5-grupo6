import java.io.FileNotFoundException;

public class App {
  public static void main(String[] args) throws FileNotFoundException {
    PlataformaStreaming plataforma = new PlataformaStreaming("pucflix");

    plataforma.adicionarSeries(LeituraArquivos.carregarSeries());
    plataforma.adicionarClientes(LeituraArquivos.carregarClientes());
    System.out.println();
  }
}

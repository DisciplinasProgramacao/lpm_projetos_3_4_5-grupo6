import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class App {

  static Scanner sc = new Scanner(System.in);
  static PlataformaStreaming plataforma = new PlataformaStreaming("pucflix");

  static void pause() {
    System.out.println("Digite Enter para continuar...");
    sc.nextLine();
  }

  public static void limparConsole() {
    try {
      if (System.getProperty("os.name").contains("Windows")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        System.out.print("\033[H\033[2J");
        System.out.flush();
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static int menuPrincipal() {
    int opcao = -1;

    System.out.println("MENU PRINCIPAL");
    System.out.println("1 - Carregando dados");
    System.out.println("0 - Cancelar");
    System.out.print("Digite sua opção: ");
    opcao = Integer.parseInt(sc.nextLine());
    return opcao;
  }

  public static void carregarDados() throws Exception{
    FileWriter arq = new FileWriter("codigo/assets/Audiencia.csv", true);
    arq.append("olá");
    arq.close();;
    System.out.println("PASSOUUUUU");
    pause();
    limparConsole();
    try {
      plataforma.carregarClientes();
      System.out.println("Dados dos clientes carregados com sucesso");
    } catch (Exception e) {
      System.out.println("ERRO ao carregar os dados dos Clientes: " + e.getMessage());
    }
    try {
      plataforma.carregarSeries();
      System.out.println("Dados das séries carregados com sucesso");
    } catch (Exception e) {
      System.out.println("ERRO ao carregar os dados das Series: " + e.getMessage());
    }

    try {
      plataforma.carregarAudiencias();
      System.out.println("Dados de audiência carregados com sucesso");
    } catch (Exception e) {
      System.out.println("ERRO ao carregar os dados de  Audiência: " + e.getMessage());
    }
  }

  public static void main(String[] args) throws Exception {
    int opcao = -1;
    do {
      opcao = menuPrincipal();
      switch (opcao) {
        case 1:
          carregarDados();
          break;

        default:
          break;
      }
      pause();
      limparConsole();
    } while (opcao != 0);
  }
}
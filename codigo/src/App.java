import java.util.ArrayList;
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
        System.out.println("1 - Listar filmes e series para o cliente");
        System.out.println("2 - Cadastrar Serie");
        System.out.println("0 - Cancelar");
        System.out.print("Digite sua opção: ");
        opcao = Integer.parseInt(sc.nextLine());
        return opcao;
    }

    public static void subMenuParaListarMidia() {
        System.out.println("");
        System.out.println("==========================");
        System.out.println("Menu de escolha para mostrar mídias:");
        System.out.println("1 - FILMES");
        System.out.println("2 - SÉRIES");
        System.out.println("0 - Cancelar");
        System.out.print("Digite sua opção: ");
        int opcaoSubMenu = Integer.parseInt(sc.nextLine());
        switch (opcaoSubMenu) {
            case 1:
                System.out.println("filmes sendo mostrados");
                break;
            case 2:
                System.out.println("séries sendo mostrados");
                break;
            default:
                System.out.println("Opção inválida");
                break;
        }
    }

    /**
     * Metodo de inicio para carregar todos os dados
     *
     * @param Sem Parametros
     * @return Sem Return
     * @throws Sem Throws
     */
    public static void init() {
        ArrayList<String> log = new ArrayList<>();
        // fazer um init pra carregar os dados
        try {
            plataforma.carregarEspectadores();
            plataforma.carregarSeries();
            plataforma.carregarAudiencia();
        } catch (Exception e) {
            log.add(e.getMessage());
        }
        if (log.size() > 0) {
            System.out.println("Dados carregados com: " + log.size() + " erros");

            for (String string : log) {
                System.out.println(string);
            }
        } else {
            System.out.println("Dados carregados");
            pause();
        }
    }

    public static void main(String[] args) throws Exception {

        init();

        int opcao = -1;
        do {
            opcao = menuPrincipal();
            switch (opcao) {
                case 1:
                    subMenuParaListarMidia();
                    break;
                case 2:
                    System.out.println("Calma jovem, as funcionalidades já já vão chegar");
                    break;
                default:
                    System.out.println("Selecione uma opção válida");
                    break;
            }
            pause();
            limparConsole();
        } while (opcao != 0);
    }
}

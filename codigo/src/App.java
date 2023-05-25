import java.util.ArrayList;
import java.util.Scanner;

import javax.naming.NameNotFoundException;

import Exceptions.SenhaIncorretaException;

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
        System.out.println("2 - Login na plataforma");
        System.out.println("3 - Adicionar mídia na lista para ver");
        System.out.println("4 - Assistir uma série");
        System.out.println("0 - Cancelar");
        System.out.print("Digite sua opção: ");
        opcao = Integer.parseInt(sc.nextLine());
        return opcao;
    }

    /**
     * Metodo para escolher o tipo de midia
     *
     * @param sem parametro
     * @return Opcao escolhida
     * @throws sem throws
     */
    public static int subMenuMidia() {
        System.out.println("");
        System.out.println("==========================");
        System.out.println("Menu de escolha para mostrar mídias:");
        System.out.println("1 - FILMES");
        System.out.println("2 - SÉRIES");
        System.out.println("0 - Cancelar");
        System.out.print("Digite sua opção: ");
        int opcao = Integer.parseInt(sc.nextLine());
        return opcao;
    }

    /**
     * Metodo para mostrar a lista de filmes ou series
     *
     * @throws sem throws
     * @param Sem Param
     * @return Sem return
     */
    public static void listarMidia() {
        plataforma.listarMidia();
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

    private static void efetuarLoginSistema() throws NameNotFoundException, SenhaIncorretaException {
        String nomeUsuario = "";
        String senha = "";
        System.out.println("");
        System.out.println("==========================");
        System.out.println("Efetuar Login:");
        System.out.println("");
        System.out.println("Digite o nome de Usuario");
        nomeUsuario = sc.nextLine();
        System.out.println("");
        System.out.println("Digite a senha:");
        senha = sc.nextLine();
        try {
            plataforma.login(nomeUsuario, senha);
        } catch (NameNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Efetue o login novamente por favor:");
            System.out.println("");
            System.out.println("Digite o nome de Usuario");
            nomeUsuario = sc.nextLine();
            System.out.println("");
            System.out.println("Digite a senha:");
            senha = sc.nextLine();
            plataforma.login(nomeUsuario, senha);
        } catch (SenhaIncorretaException e) {
            System.out.println(e.getMessage());
            System.out.println("Efetue o login novamente por favor:");
            System.out.println("");
            System.out.println("Digite o nome de Usuario");
            nomeUsuario = sc.nextLine();
            System.out.println("");
            System.out.println("Digite a senha:");
            senha = sc.nextLine();
            plataforma.login(nomeUsuario, senha);
        }
    }

    private static void fazerLogoutDoSistema() {
        System.out.println("");
        System.out.println("==========================");
        plataforma.logoff();
        System.out.println("Logout efetuado com sucesso!");
    }

    private static void adicionarMidiaNaListaParaVer() {
        Midia novaMidia = null;
        System.out.println("");
        System.out.println("==========================");
        System.out.println("Qual nome da midia deseja adicionar na lista: ");
        String nome = sc.nextLine();
        novaMidia = plataforma.buscar(nome);
        plataforma.adicionarNaListaParaVer(novaMidia);
    }

    private static void registrarAudienciaDaSerie() {
        Midia novaSerie = null;
        System.out.println("Qual nome da midia deseja assistir?");
        String nome = sc.nextLine();
        novaSerie = plataforma.buscar(nome);
        plataforma.registrarAudiencia(novaSerie);
        System.out.println("Serie assistida com sucesso! Obrigado por escolher nossa plataforma!");
    }

    public static void main(String[] args) throws Exception {

        init();

        int opcao = -1;
        do {
            opcao = menuPrincipal();
            switch (opcao) {
                case 1:
                    listarMidia();
                    break;
                case 2:
                    efetuarLoginSistema();
                    break;
                case 3:
                    adicionarMidiaNaListaParaVer();
                    break;
                case 4:
                    registrarAudienciaDaSerie();
                    break;
                case 5:
                    fazerLogoutDoSistema();
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

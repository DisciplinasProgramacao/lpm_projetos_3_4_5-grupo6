import java.util.ArrayList;
import java.util.List;
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
        // LISTA DO QUE O APP FAZ DA VIDA
        /*
         * 1. CARREGAR DADOS OK
         * 2. FAZER LOGIN OK
         * 3. LISTAR MIDIA OK
         * 4. FILTRAR GENERO, IDIOMA, QTD EPS
         * 5. REGISTRAR ADUICIENCIA OK
         * 6. FAZER UMA AVALIACAO (FAKE) OK - ESPERANDO IMPLEMENTAÇÃO
         * 7. FAZER UM COMENTARIO (FAKE) OK - ESPERANDO IMPLEMENTAÇÃO
         * 8. SALVAR DADOS - ESPERANDO IMPLEMENTAÇÃO
         * 9. CADASTRAR (FAZER T) - ESPERANDO IMPLEMENTAÇÃO
         */
        System.out.println("MENU PRINCIPAL");
        System.out.println("1 - Listar filmes e series para o cliente");
        System.out.println("2 - Login na plataforma");
        System.out.println("3 - Adicionar mídia na lista para ver");
        System.out.println("4 - Assistir a uma mídia");
        System.out.println("5 - Filtre a mídia por GÊNERO, IDIOMA OU QUANTIDADE DE EPISÓDIOS");
        System.out.println("6 - Avaliar uma mídia");
        System.out.println("7 - Comentar em uma mídia");
        System.out.println("8 - Salvar Dados");
        System.out.println("9 - Cadastrar <T>");
        System.out.println("10 - Fazer Logout do Sistema");

        System.out.println("0 - Cancelar");
        System.out.print("Digite sua opção: ");
        opcao = Integer.parseInt(sc.nextLine());
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

    private static void efetuarLoginSistema() {
        boolean login = false;
        do {
            String nomeUsuario = "";
            String senha = "";
            System.out.println("");
            System.out.println("==========================");
            System.out.println("Efetuar Login:");
            System.out.println("");
            System.out.println("Digite o nome de Usuário");
            nomeUsuario = sc.nextLine();
            System.out.println("");
            System.out.println("Digite a senha:");
            senha = sc.nextLine();
            try {
                plataforma.login(nomeUsuario, senha);
                login = true;
            } catch (NameNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (SenhaIncorretaException e) {
                System.out.println(e.getMessage());
            }
            if (!login) {
                pause();
                limparConsole();
            }
        } while (!login);
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

    private static void registrarAudienciaDaMidia() {
        // NAO DEIXAR O CLIENTE ATUAL ASSISTIR 2X
        Midia novaSerie = null;
        System.out.println("Qual nome da midia deseja assistir?");
        String nome = sc.nextLine();
        novaSerie = plataforma.buscar(nome);
        plataforma.registrarAudiencia(novaSerie);
        System.out.println("Serie assistida com sucesso! Obrigado por escolher nossa plataforma!");
    }

    public static void filtrarPorGenero() {
        List<Midia> arrayList = new ArrayList<>();
        System.out.println("Digite o gênero:");
        String genero = sc.nextLine();
        arrayList = plataforma.filtrarPorGenero(genero);
        System.out.println(arrayList);
    }

    public static void filtrarPorIdioma() {
        List<Midia> arrayList = new ArrayList<>();
        System.out.println("Digite o idioma:");
        String idioma = sc.nextLine();
        arrayList = plataforma.filtrarPorIdioma(idioma);
        System.out.println(arrayList);

    }

    public static void filtrarPorQuantidadeEpisodios() {
        List<Midia> arrayList = new ArrayList<>();
        System.out.println("Digite o idioma:");
        int qtd = sc.nextInt();
        arrayList = plataforma.filtrarPorQtdEpisodios(qtd);
        limparConsole();
        System.out.println(arrayList);
    }

    private static void filtrarMidia() {
        int opcao = subMenuParaFiltrar();
        switch (opcao) {
            case 1:
                filtrarPorGenero();
                break;
            case 2:
                filtrarPorIdioma();
                break;
            case 3:
                filtrarPorQuantidadeEpisodios();
                break;
            default:
                System.out.println("Escolha uma opção válida");
                break;
        }
    }

    private static int subMenuParaFiltrar() {
        int opcao = -1;
        System.out.println("");
        System.out.println("==========================");
        System.out.println("Escolha o método para filtrar");
        System.out.println("1 - Gênero");
        System.out.println("2 - Idioma");
        System.out.println("3 - Quantidade de episódios");
        return opcao;
    }

    private static void realizarUmaAvaliacao() {
        Midia novaMidia = null;
        System.out.println("");
        System.out.println("==========================");
        System.out.println("Selecione a midia para fazer a avaliação:");
        String nome = sc.nextLine();
        novaMidia = plataforma.buscar(nome);

        System.out.println("");
        System.out.println("Escolha entre 1 e 5:");
        int av = Integer.parseInt(sc.nextLine());
        // plataforma.fazerAvaliacao(novaMidia, av);
        System.out.println("Obrigado por contribuir!");
    }

    private static void realizarUmComentario() {
        Midia novaMidia = null;
        System.out.println("");
        System.out.println("==========================");
        System.out.println("Selecione a midia para fazer o comentário:");
        String nome = sc.nextLine();
        novaMidia = plataforma.buscar(nome);

        System.out.println("");
        System.out.println("Faça o comentário:");
        String cmt = sc.nextLine();
        // plataforma.fazerComentario(novaMidia, cmt);
        System.out.println("Obrigado por contribuir!");
    }

    private static void salvarDados() {
        System.out.println("Esperando implementação");
    }

    private static void cadastrarComTipoT() {
        System.out.println("Esperando implementação");
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
                    registrarAudienciaDaMidia();
                    break;
                case 5:
                    filtrarMidia();
                    break;
                case 6:
                    realizarUmaAvaliacao();
                    break;
                case 7:
                    realizarUmComentario();
                    break;
                case 8:
                    salvarDados();
                    break;
                case 9:
                    cadastrarComTipoT();
                    break;
                case 10:
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

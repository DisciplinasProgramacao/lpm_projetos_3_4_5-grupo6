import java.io.IOException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.naming.NameNotFoundException;

import Exceptions.SenhaFracaException;
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
         * PROJETO 3 E 4
         * 1. CARREGAR DADOS OK
         * 2. FAZER LOGIN OK
         * 3. LISTAR MIDIA OK
         * 4. FILTRAR GENERO, IDIOMA, QTD EPS - OK
         * 5. REGISTRAR ADUICIENCIA OK
         * 6. FAZER UMA AVALIACAO OK
         * 7. FAZER UM COMENTARIO - ESPERANDO IMPLEMENTAÇÃO
         * 8. SALVAR DADOS - OK
         * 9. CADASTRAR CLIENTE OK
         * 10. FAZER LOGOUT
         * PROJETO 5
         * 11. VER MIDIAS EM LANÇAMENTO COMO PROFISSIONAL
         * 12. MOSTRAR CLIENTE QUE MAIS ASSISTIU MÍDIAS E O COUNT
         * 13. MOSTRAR CLIENTE COM MAIS AVALIAÇÕES E O COUNT
         * 14. PORCENTAGEM CLIENTE COM >= 15 AVALIAÇÕES
         * 15. MOSTRAR 10 MÍDIAS DE MELHOR AVALIAÇÃO (* contendo 100 avaliações, em
         * ordem decrescente)
         * 16. MOSTRAR 10 MÍDIAS COM MAIS VISUALIZAÇÕES (em ordem decrescente)
         * 17. EM CADA GENERO - MOSTRAR 10 MÍDIAS DE MELHOR AVALIAÇÃO (* contendo 100
         * avaliações, em ordem decrescente)
         * 18. EM CADA GENERO - MOSTRAR 10 MÍDIAS COM MAIS VISUALIZAÇÕES (em ordem
         * decrescente)
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
        System.out.println("9 - Cadastrar Cliente");
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
        limparConsole();
        System.out.println(plataforma.listarMidia());
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
            plataforma.carregarFilmes();
            plataforma.carregarAudiencia();
            efetuarLoginSistema();
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
        try {
            plataforma.registrarAudiencia(novaSerie);

        } catch (InvalidParameterSpecException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Serie assistida com sucesso! Obrigado por escolher nossa plataforma!");
        subMenuAvaliacao(novaSerie);
    }

    public static void subMenuAvaliacao(Midia novaSerie) {
        String userId = plataforma.getCurrentUserId();
        int midiaId = novaSerie.getId();

        System.out.println("Deseja realizar uma avaliação");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");
        int res = Integer.parseInt(sc.nextLine());

        switch (res) {
            case 1:
                int avalacao = -1;
                do {
                    System.out.println("Qual a nota entre 1 a 5?");
                    avalacao = Integer.parseInt(sc.nextLine());
                } while (avalacao < 1 || avalacao > 5);
                try {
                    plataforma.registrarAvaliacao(userId, midiaId, avalacao);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                break;
            case 2:
                try {
                    plataforma.registrarAvaliacao(userId, midiaId, 0);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

                break;
            default:
                System.out.println("Escolha uma opção valida!");
                break;
        }

    };

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
        System.out.println("Digite o número de episódios:");
        int qtd = Integer.parseInt(sc.nextLine());
        arrayList = plataforma.filtrarPorQtdEpisodios(qtd);
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
        opcao = Integer.parseInt(sc.nextLine());
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
        System.out.println("Faça a avaliação:");
        int avaliacao = Integer.parseInt(sc.nextLine());
        try {
            plataforma.registrarAvaliacao(plataforma.getCurrentUserId(), novaMidia.getId(), avaliacao);
        } catch (IOException e) {
            realizarUmaAvaliacao();
            e.printStackTrace();
        }
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

    private static void cadastrarCliente() {
        System.out.println("");
        System.out.println("==========================");
        System.out.println("Bem vindo novo cliente, como se chama?");
        String nome = sc.nextLine();
        System.out.println("Qual será seu login?");
        String login = sc.nextLine();
        System.out.println("E sua senha?");
        String senha = sc.nextLine();

        try {
            plataforma.cadastrarCliente(nome, login, senha);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SenhaFracaException e) {
            // TODO Auto-generated catch block
            cadastrarCliente();
        }
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
                    cadastrarCliente();
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

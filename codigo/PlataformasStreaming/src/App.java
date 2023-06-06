import java.io.IOException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.naming.NameNotFoundException;

import Exceptions.SenhaFracaException;
import Exceptions.SenhaIncorretaException;

public class App {

    static Scanner scanner = new Scanner(System.in);
    static PlataformaStreaming plataforma = new PlataformaStreaming("pucflix");

    // #region Utilitários
    static void pausar() {
        System.out.print(System.lineSeparator() + "Digite qualquer tecla para continuar...");
        scanner.nextLine();
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
    // #endregion

    public static int menuPrincipal() {
        int opcao;
        System.out.println("==========================");
        System.out.println("MENU PRINCIPAL");
        System.out.println("1 - Listar mídias");
        System.out.println("2 - Fazer Login");
        System.out.println("3 - Fazer Logout");
        System.out.println("4 - Adicionar mídia na lista para ver");
        System.out.println("5 - Assistir a uma mídia");
        System.out.println("6 - Filtrar a mídia por gênero, idioma ou quantidade de episódios");
        System.out.println("7 - Avaliar uma mídia");
        System.out.println("8 - Comentar em uma mídia");
        System.out.println("9 - Salvar Dados");
        System.out.println("10 - Cadastrar Cliente");
        System.out.println("11 - Ver mídias em lançamento (perfil profissional)");
        System.out.println("12 - Cliente com mais mídias assistidas");
        System.out.println("13 - Cliente com mais avaliações");
        System.out.println("14 - Porcentagem de Clientes com >= 15 avaliações");
        System.out.println("15 - As 10 mídias mais vistas do Pucflix");
        System.out.println("16 - As 10 mídias com melhor avaliação do Pucflix");
        System.out.println("17 - As 10 mídias mais vistas do Pucflix em cada gênero");
        System.out.println("18 - As 10 mídias com melhor avaliação do Pucflix em cada gênero");
        System.out.println("0 - Cancelar" + System.lineSeparator());
        System.out.print("Digite sua opção: ");
        try {
            opcao = Integer.parseInt(scanner.nextLine());
        } catch (Exception e) {
            opcao = -1;
        }
        return opcao;
    }

    /**
     * Metodo para mostrar a lista de mídias
     */
    public static void listarMidias() {
        System.out.println(plataforma.listarMidia());
    }

    /**
     * Metodo de inicio para carregar todos os dados
     */
    public static void carregarDados() {
        ArrayList<String> log = new ArrayList<>();
        try {
            plataforma.carregarEspectadores();
            plataforma.carregarSeries();
            plataforma.carregarFilmes();
            plataforma.carregarAudiencia();
            System.out.println("Dados carregados com sucesso!");
        } catch (Exception e) {
            log.add(e.getMessage());
            System.out.println("Dados carregados com " + log.size() + " erro(s)");
            for (String string : log)
                System.out.println(string);
        }
    }

    private static void efetuarLogin() {
        String login;
        String senha;

        System.out.println("==========================");
        System.out.println("Efetuar Login");
        System.out.print("Digite o nome de usuário: ");
        login = scanner.nextLine();
        System.out.print("Digite a senha: ");
        senha = scanner.nextLine();
        try {
            Cliente cliente = plataforma.login(login, senha);
            System.out.println("Bem-vindo(a), " + cliente.getNome() + "!");
        } catch (NameNotFoundException e) {
            System.out.println("Erro ao fazer login: Usuário não cadastrado!");
        } catch (SenhaIncorretaException e) {
            System.out.println("Erro ao fazer login: Senha incorreta!");
        }
    }

    private static void efetuarLogout() {
        System.out.println("==========================");
        plataforma.logoff();
        System.out.println("Logout efetuado com sucesso!");
    }

    private static void adicionarMidiaNaListaParaVer() {
        System.out.println("==========================");
        System.out.println("Qual nome da midia deseja adicionar na lista: ");
        String nomeDaMidia = scanner.nextLine();
        Midia novaMidia = plataforma.buscar(nomeDaMidia);
        try {
            plataforma.adicionarNaListaParaVer(novaMidia);
        } catch (NullPointerException e) {
            System.out.println("Você deve estar logado para adicionar uma mídia na lista para ver!");
        }
    }

    private static void registrarAudienciaDaMidia() {
        // NAO DEIXAR O CLIENTE ATUAL ASSISTIR 2X
        Midia novaSerie = null;
        System.out.println("Qual nome da midia deseja assistir?");
        String nome = scanner.nextLine();
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
        String userId = plataforma.getLoginClienteAtual();
        int midiaId = novaSerie.getId();

        System.out.println("Deseja realizar uma avaliação");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");
        int res = Integer.parseInt(scanner.nextLine());

        switch (res) {
            case 1:
                int avalacao = -1;
                do {
                    System.out.println("Qual a nota entre 1 a 5?");
                    avalacao = Integer.parseInt(scanner.nextLine());
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
        String genero = scanner.nextLine();
        arrayList = plataforma.filtrarPorGenero(genero);
        System.out.println(arrayList);
    }

    public static void filtrarPorIdioma() {
        List<Midia> arrayList = new ArrayList<>();
        System.out.println("Digite o idioma:");
        String idioma = scanner.nextLine();
        arrayList = plataforma.filtrarPorIdioma(idioma);
        System.out.println(arrayList);

    }

    public static void filtrarPorQuantidadeEpisodios() {
        List<Midia> arrayList = new ArrayList<>();
        System.out.println("Digite o número de episódios:");
        int qtd = Integer.parseInt(scanner.nextLine());
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
        System.out.println("==========================");
        System.out.println("Escolha o método para filtrar");
        System.out.println("1 - Gênero");
        System.out.println("2 - Idioma");
        System.out.println("3 - Quantidade de episódios");
        opcao = Integer.parseInt(scanner.nextLine());
        return opcao;
    }

    private static void realizarUmaAvaliacao() {
        Midia novaMidia = null;
        System.out.println("==========================");
        System.out.println("Selecione a midia para fazer a avaliação:");
        String nome = scanner.nextLine();
        novaMidia = plataforma.buscar(nome);
        System.out.println("Faça a avaliação:");
        int avaliacao = Integer.parseInt(scanner.nextLine());
        try {
            plataforma.registrarAvaliacao(plataforma.getLoginClienteAtual(), novaMidia.getId(), avaliacao);
        } catch (IOException e) {
            realizarUmaAvaliacao();
            e.printStackTrace();
        }
    }

    private static void realizarUmComentario() {
        Midia novaMidia = null;
        System.out.println("==========================");
        System.out.println("Selecione a midia para fazer o comentário:");
        String nome = scanner.nextLine();
        novaMidia = plataforma.buscar(nome);
        System.out.println("Faça o comentário:");
        String cmt = scanner.nextLine();
        // plataforma.fazerComentario(novaMidia, cmt);
        System.out.println("Obrigado por contribuir!");
    }

    private static void salvarDados() {
        // TODO: Salvar Dados
        System.out.println("Em breve...");
    }

    private static void cadastrarCliente() {
        System.out.println("==========================");
        System.out.println("Bem vindo novo cliente, como se chama?");
        String nome = scanner.nextLine();
        System.out.println("Qual será seu login?");
        String login = scanner.nextLine();
        System.out.println("E sua senha?");
        String senha = scanner.nextLine();

        try {
            plataforma.cadastrarCliente(nome, login, senha);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar cliente: Dados inválidos!");
            e.printStackTrace();
        } catch (SenhaFracaException e) {
            System.out.println("Erro ao cadastrar cliente: Senha fraca!");
            cadastrarCliente();
        }
    }

    public static void main(String[] args) {
        carregarDados();
        int opcao;
        do {
            opcao = menuPrincipal();
            limparConsole();
            switch (opcao) {
                case 0:
                    System.out.println("Desligando...");
                    break;
                case 1:
                    listarMidias();
                    break;
                case 2:
                    efetuarLogin();
                    break;
                case 3:
                    efetuarLogout();
                    break;
                case 4:
                    adicionarMidiaNaListaParaVer();
                    break;
                case 5:
                    registrarAudienciaDaMidia();
                    break;
                case 6:
                    filtrarMidia();
                    break;
                case 7:
                    realizarUmaAvaliacao();
                    break;
                case 8:
                    realizarUmComentario();
                    break;
                case 9:
                    salvarDados();
                    break;
                case 10:
                    cadastrarCliente();
                    break;
                case 11:
                    // TODO: 11 - Ver mídias em lançamento (perfil profissional)
                    System.out.println("Em breve...");
                    break;
                case 12:
                    // TODO: 12 - Cliente com mais mídias assistidas
                    System.out.println("Em breve...");
                    break;
                case 13:
                    // TODO: 13 - Cliente com mais avaliações
                    System.out.println("Em breve...");
                    break;
                case 14:
                    // TODO: 14 - Porcentagem de Clientes com >= 15 avaliações
                    System.out.println("Em breve...");
                    break;
                case 15:
                    // TODO: 15 - As 10 mídias mais vistas do Pucflix
                    System.out.println("Em breve...");
                    break;
                case 16:
                    // TODO: 16 - As 10 mídias com melhor avaliação do Pucflix
                    System.out.println("Em breve...");
                    break;
                case 17:
                    // TODO: 17 - As 10 mídias mais vistas do Pucflix em cada gênero
                    System.out.println("Em breve...");
                    break;
                case 18:
                    // TODO: 18 - As 10 mídias com melhor avaliação do Pucflix em cada gênero
                    System.out.println("Em breve...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
            pausar();
            limparConsole();
        } while (opcao != 0);
    }

}

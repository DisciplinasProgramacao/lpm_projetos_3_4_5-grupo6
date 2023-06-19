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
        // relatorios
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

    /**
     * Metodo para efetuar login no sistema
     */
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

    /**
     * Metodo para efetuar logout no sistema
     */
    private static void efetuarLogout() {
        System.out.println("==========================");
        plataforma.logoff();
        System.out.println("Logout efetuado com sucesso!");
    }

    /**
     * Metodo para adicionar uma midia na lista para ver
     */
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

    /**
     * Metodo para assistir uma midia
     */
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

    /**
     * Metodo de subMenu de Avaliação
     */
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

    /*
     * Metodo para filtrar midia por genero
     */
    public static void filtrarPorGenero() {
        List<Midia> arrayList = new ArrayList<>();
        System.out.println("Digite o gênero:");
        String genero = scanner.nextLine();
        arrayList = plataforma.filtrarPorGenero(genero);
        if (arrayList.size() < 1) {
            System.out.println("Não há mídias com esse gênero.");
        } else {
            for (Midia midia : arrayList) {
                System.out.println(midia);
            }
        }

    }

    /*
     * Metodo para filtrar midia por idioma
     */
    public static void filtrarPorIdioma() {
        List<Midia> arrayList = new ArrayList<>();
        System.out.println("Digite o idioma:");
        String idioma = scanner.nextLine();
        arrayList = plataforma.filtrarPorIdioma(idioma);
        if (arrayList.size() < 1) {
            System.out.println("Não há mídias com esse gênero.");
        } else {
            for (Midia midia : arrayList) {
                System.out.println(midia);
            }
        }
    }

    /*
     * Metodo para filtrar midia por quantidade de episodios
     */
    public static void filtrarPorQuantidadeEpisodios() {
        List<Midia> arrayList = new ArrayList<>();
        System.out.println("Digite o número de episódios:");
        int qtd = Integer.parseInt(scanner.nextLine());
        arrayList = plataforma.filtrarPorQtdEpisodios(qtd);
        if (arrayList.size() < 1) {
            System.out.println("Não há mídias com esse gênero.");
        } else {
            for (Midia midia : arrayList) {
                System.out.println(midia);
            }
        }
    }

    /*
     * Metodo com switch para chama dos metodos de filtros
     */
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

    /*
     * Metodo com submenu para escolha do filtro
     */
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

    /*
     * Metodo para realizar uma avaliação de uma midia
     */
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

    /*
     * Metodo para realizar um comentario em uma midia
     */
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

    /*
     * Metodo para salvar dados
     */
    private static void salvarDados() {
        // TODO: Salvar Dados
        System.out.println("Em breve...");
    }

    /*
     * Metodo para cadastrar um Cliente na plataforma
     */
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
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * Metodo com sub menu principal
     */
    public static int subMenuPrincipal() {
        int opcao = -1;
        System.out.println("==========================");
        System.out.println("MENU PRINCIPAL");
        System.out.println("1 - Login, Logout e Registrar");
        System.out.println("2 - Processos com as Mídias");
        System.out.println("3 - Relatórios");

        System.out.println("Sua opção:");
        opcao = Integer.parseInt(scanner.nextLine());
        return opcao;
    }

    /*
     * Metodo com sub menu do sub menu, o cliente pode realizar tarefas que estão
     * relacionados a ele
     */
    public static int subMenuParaLoginLogoutRegistrar() {
        int opcao = -1;
        System.out.println("==========================");
        System.out.println("Escolha uma opção: ");
        System.out.println("1 - Fazer Login");
        System.out.println("2 - Fazer Logout");
        System.out.println("3 - Cadastrar Cliente");
        System.out.println("4 - Salvar Dados");

        System.out.println("Sua opção:");
        opcao = Integer.parseInt(scanner.nextLine());
        return opcao;
    }

    /*
     * Metodo com sub menu do sub menu, o cliente pode realizar tarefas com as
     * midias
     */
    public static int subMenuParaMidias() {
        int opcao = -1;
        System.out.println("==========================");
        System.out.println("Veja as opções: ");
        System.out.println("1 - Listar mídias");
        System.out.println("2 - Adicionar mídia na lista para ver");
        System.out.println("3 - Assistir a uma mídia");
        System.out.println("4 - Filtrar a mídia por gênero, idioma ou quantidade de episódios");
        System.out.println("5 - Avaliar uma mídia");
        System.out.println("6 - Comentar em uma mídia");
        System.out.println("7 - Cadastrar Serie");
        System.out.println("8 - Cadastrar filme");
        System.out.println("Sua opção:");
        opcao = Integer.parseInt(scanner.nextLine());
        return opcao;
    }

    /*
     * Metodo com sub menu do sub menu, o cliente pode verificar os relatórios
     */
    public static int subMenuRelatorios() {
        int opcao = -1;
        System.out.println("==========================");
        System.out.println("Veja as opções: ");
        System.out.println("1 - Cliente com mais mídias assistidas");
        System.out.println("2 - Cliente com mais avaliações");
        System.out.println("3 - Porcentagem de Clientes com >= 15 avaliações");
        System.out.println("4 - As 10 mídias mais vistas do Pucflix");
        System.out.println("5 - As 10 mídias com melhor avaliação do Pucflix");
        System.out.println("6 - As 10 mídias mais vistas do Pucflix em cada gênero");
        System.out.println("7 - As 10 mídias com melhor avaliação do Pucflix em cada gênero");

        System.out.println("Sua opção:");
        opcao = Integer.parseInt(scanner.nextLine());
        return opcao;
    }

    /*
     * Switch com os metodos de tarefas relacionadas ao cliente
     */
    public static void subSwitchLoginLogoutRegistrar() {
        int opcao = subMenuParaLoginLogoutRegistrar();
        switch (opcao) {
            case 1:
                efetuarLogin();
                break;
            case 2:
                efetuarLogout();
                break;
            case 3:
                cadastrarCliente();
                break;
            case 4:
                salvarDados();
                break;
            default:
                break;
        }
    }

    private static void cadastrarSerie() throws IOException {
        System.out.println();
        System.out.println("==========================");
        System.out.println("Cadastro de série:");

        System.out.println("Qual o id da série:");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("Qual o nome da série:");
        String nome = scanner.nextLine();

        System.out.println("Qual o gênero da série:");
        EnumGeneros[] generos = EnumGeneros.values();
        System.out.println();
        for (EnumGeneros enumGeneros : generos) {
            System.out.println(enumGeneros.getDescricao());
        }
        System.out.println();
        String genero = scanner.nextLine();

        System.out.println("Qual o idioma da série:");
        String idioma = scanner.nextLine();

        System.out.println("Qual a quantidade de episódios:");
        int qtdEp = Integer.parseInt(scanner.nextLine());

        System.out.println("Qual a data de lançamento da série:");
        String data = scanner.nextLine();

        try {
            plataforma.cadastrarSerie(id, nome, idioma, genero, qtdEp, data);
        } catch (IOException e) {
            System.out.println("Tente novamente, erro ao cadastrar a serie.");
            System.out.println("Problema encontrado: " + e.getMessage());

            cadastrarSerie();
        }
        System.out.println("Série cadastrada com sucesso!");
    }

    private static void cadastrarFilme() throws IOException {
        System.out.println();
        System.out.println("==========================");
        System.out.println("Cadastro de filme:");

        System.out.println("Qual o id do filme:");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.println("Qual o nome do filme:");
        String nome = scanner.nextLine();

        System.out.println("Qual o gênero do filme:");
        EnumGeneros[] generos = EnumGeneros.values();
        System.out.println();
        for (EnumGeneros enumGeneros : generos) {
            System.out.println(enumGeneros.getDescricao());
        }
        System.out.println();
        String genero = scanner.nextLine();

        System.out.println("Qual o idioma do filme:");
        String idioma = scanner.nextLine();

        System.out.println("Qual a duração:");
        int duracao = Integer.parseInt(scanner.nextLine());

        System.out.println("Qual a data de lançamento do filme:");
        String data = scanner.nextLine();

        try {
            plataforma.cadastrarFilme(id, nome, idioma, genero, duracao, data);
        } catch (IOException e) {
            System.out.println("Tente novamente, erro ao cadastrar o filme.");
            System.out.println("Problema encontrado: " + e.getMessage());

            cadastrarFilme();
        }
        System.out.println("Filme cadastrado com sucesso!");
    }

    /*
     * Switch com os metodos de tarefas relacionadas as midias
     */
    public static void subSwitchMidias() throws IOException {
        int opcao = subMenuParaMidias();
        switch (opcao) {
            case 1:
                listarMidias();
                break;
            case 2:
                adicionarMidiaNaListaParaVer();
                break;
            case 3:
                registrarAudienciaDaMidia();
                break;
            case 4:
                filtrarMidia();
                break;
            case 5:
                realizarUmaAvaliacao();
                break;
            case 6:
                realizarUmComentario();
                break;
            case 7:
                cadastrarSerie();
                break;
            case 8:
                cadastrarFilme();
                break;
            default:
                break;
        }
    }

    /*
     * Switch com os metodos de tarefas relacionadas aos relatórios
     */
    public static void subSwitchRelatorios() {
        int opcao = subMenuRelatorios();
        switch (opcao) {
            case 1:
                // TODO: Cliente com mais mídias assistidas
                System.out.println("Em breve...");
                break;
            case 2:
                // TODO: Cliente com mais avaliações
                System.out.println("Em breve...");
                break;
            case 3:
                // TODO: Porcentagem de Clientes com >= 15 avaliações
                System.out.println("Em breve...");
                break;
            case 4:
                // TODO: As 10 mídias mais vistas do Pucflix
                System.out.println("Em breve...");
                break;
            case 5:
                // TODO: As 10 mídias com melhor avaliação do Pucflix
                System.out.println("Em breve...");
                break;
            case 6:
                // TODO: As 10 mídias mais vistas do Pucflix em cada gênero
                System.out.println("Em breve...");
                break;
            case 7:
                // TODO: As 10 mídias com melhor avaliação do Pucflix em cada gênero
                System.out.println("Em breve...");
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) throws IOException {
        carregarDados();
        int opcao;
        do {
            opcao = subMenuPrincipal();
            limparConsole();
            switch (opcao) {
                case 1:
                    subSwitchLoginLogoutRegistrar();
                    break;
                case 2:
                    subSwitchMidias();
                    break;
                case 3:
                    subSwitchRelatorios();
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

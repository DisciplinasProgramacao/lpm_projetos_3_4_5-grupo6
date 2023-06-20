import java.io.IOException;
import java.security.InvalidParameterException;
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
    static Relatorio relatorio = new Relatorio();

    // #region Utilitários
    static void pausar() {
        System.out.print(System.lineSeparator() + "Digite qualquer tecla para continuar...");
        scanner.nextLine();
    }

    /**
     * Limpa o console exibindo o programa em execução.
     * Essa função limpa a tela do console, removendo todas as saídas anteriores.
     * O método usado para limpar o console varia dependendo do sistema operacional.
     * Atualmente, o suporte é fornecido para sistemas Windows e outros sistemas
     * operacionais.
     * Se ocorrer um erro durante a execução da limpeza do console, a mensagem de
     * erro será exibida.
     */
    public static void limparConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                // Limpa o console no Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Limpa o console em outros sistemas operacionais
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Exibe a mensagem de erro, caso ocorra algum problema
            System.out.println(e.getMessage());
        }
    }

    // #endregion

    /**
     * Exibe o menu principal e retorna a opção selecionada pelo usuário.
     *
     * @return A opção selecionada pelo usuário.
     */
    public static int menuPrincipal() {
        int opcao;

        System.out.println("==========================");
        System.out.println("MENU PRINCIPAL");
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
            plataforma.carregarAvaliacoes();
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
     *
     * @return true se o login for efetuado, false se o usuário escolher sair do
     *         login
     */
    private static boolean efetuarLogin() {
        while (true) {
            String login;
            String senha;

            System.out.println("==========================");
            System.out.println("Efetuar Login");
            System.out.print("Digite o nome de usuário (ou 'sair' para sair): ");
            login = scanner.nextLine();

            if (login.equalsIgnoreCase("sair")) {
                System.out.println("Saindo do login...");
                return false; // Retorna false para indicar que o usuário escolheu sair do login
            }

            System.out.print("Digite a senha: ");
            senha = scanner.nextLine();

            try {
                Cliente cliente = plataforma.login(login, senha);
                System.out.println("Bem-vindo(a), " + cliente.getNome() + "!");
                return true; // Retorna true para indicar que o login foi efetuado com sucesso
            } catch (NameNotFoundException e) {
                System.out.println("Erro ao fazer login: Usuário não cadastrado!");
            } catch (SenhaIncorretaException e) {
                System.out.println("Erro ao fazer login: Senha incorreta!");
            }
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
        if (novaMidia == null) {
            System.out.println("Midia não existe, refaça a busca.");
            return;
        }
        try {
            plataforma.adicionarNaListaParaVer(novaMidia);
        } catch (NullPointerException e) {
            System.out.println("Você deve estar logado para adicionar uma mídia na lista para ver!");
        }
        System.out.println("Mídia adicionada com sucesso!");
    }

    /**
     * Metodo para assistir uma midia
     *
     * @throws IOException
     * @throws IllegalArgumentException
     */
    private static void registrarAudienciaDaMidia() throws IllegalArgumentException, IOException {
        // NAO DEIXAR O CLIENTE ATUAL ASSISTIR 2X
        Midia novaSerie = null;
        System.out.println("Qual nome da midia deseja assistir?");
        String nome = scanner.nextLine();
        novaSerie = plataforma.buscar(nome);
        if (novaSerie == null) {
            System.out.println("Mídia não existe.");
            return;
        }
        try {
            plataforma.registrarAudiencia(novaSerie);
        } catch (InvalidParameterSpecException e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Serie assistida com sucesso! Obrigado por escolher nossa plataforma!");
        subMenuAvaliacao(novaSerie);
    }

    /**
     * Metodo de subMenu de Avaliação
     *
     * @throws IOException
     * @throws IllegalArgumentException
     */
    public static void subMenuAvaliacao(Midia novaSerie) throws IllegalArgumentException, IOException {
        String userId = plataforma.getLoginClienteAtual();
        int midiaId = novaSerie.getId();

        System.out.println("Deseja realizar uma avaliação");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");
        int res = Integer.parseInt(scanner.nextLine());

        switch (res) {
            case 1:
                int avaliacao = -1;
                do {
                    System.out.println("Qual a nota entre 1 a 5?");
                    avaliacao = Integer.parseInt(scanner.nextLine());
                } while (avaliacao < 1 || avaliacao > 5);
                try {
                    plataforma.registrarAvaliacao(userId, midiaId, avaliacao);
                } catch (InvalidParameterException e) {
                    System.out.println(e.getMessage());
                    return;
                }

                break;
            case 2:
                try {
                    plataforma.registrarAvaliacao(userId, midiaId, 0);
                } catch (InvalidParameterException e) {
                    System.out.println(e.getMessage());
                    return;
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
            return;
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
            System.out.println("Não há mídias com esse idioma.");
            return;
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
            System.out.println("Não há mídias com essa quantidade de episódios.");
            return;
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
        int opcao = -1;
        opcao = subMenuParaFiltrar();
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
        do {
            System.out.println("==========================");
            System.out.println("Escolha o método para filtrar");
            System.out.println("1 - Gênero");
            System.out.println("2 - Idioma");
            System.out.println("3 - Quantidade de episódios");
            opcao = Integer.parseInt(scanner.nextLine());
        } while (opcao < 1 || opcao > 3);
        return opcao;
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
            efetuarLogin();
        } catch (IllegalArgumentException e) {
            System.out.println("Erro ao cadastrar cliente: Dados inválidos!");
            e.printStackTrace();
            return;
        } catch (SenhaFracaException e) {
            System.out.println("Erro ao cadastrar cliente: Senha fraca!");
            return;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }
    }

    /*
     * Metodo com sub menu principal
     */
    public static int subMenuPrincipal() {
        int opcao = -1;
        do {
            System.out.println("==========================");
            System.out.println("MENU PRINCIPAL");
            System.out.println("1 - Login, Logout e Registrar");
            System.out.println("2 - Processos com as Mídias");
            System.out.println("3 - Relatórios");
            System.out.println("Sua opção:");
            opcao = Integer.parseInt(scanner.nextLine());
        } while (opcao < 1 || opcao > 3);
        return opcao;
    }

    /*
     * Metodo com sub menu do sub menu, o cliente pode realizar tarefas que estão
     * relacionados a ele
     */
    public static int subMenuParaLoginLogoutRegistrar() {
        int opcao = -1;
        do {
            System.out.println("==========================");
            System.out.println("Escolha uma opção: ");
            System.out.println("0 - Retornar ao menu principal");
            System.out.println("1 - Fazer Login");
            System.out.println("2 - Fazer Logout");
            System.out.println("3 - Cadastrar Cliente");

            System.out.println("Sua opção:");
            opcao = Integer.parseInt(scanner.nextLine());
        } while (opcao < 0 || opcao > 3);
        return opcao;
    }

    /*
     * Metodo com sub menu do sub menu, o cliente pode realizar tarefas com as
     * midias
     */
    public static int subMenuParaMidias() {
        int opcao = -1;
        do {
            System.out.println("==========================");
            System.out.println("Veja as opções: ");
            System.out.println("0 - Retornar ao menu principal");
            System.out.println("1 - Listar mídias");
            System.out.println("2 - Adicionar mídia na lista para ver");
            System.out.println("3 - Assistir a uma mídia");
            System.out.println("4 - Filtrar a mídia por gênero, idioma ou quantidade de episódios");
            System.out.println("5 - Cadastrar Serie");
            System.out.println("6 - Cadastrar filme");
            System.out.println("7 - Ver média de avaliações de uma mídia");
            System.out.println("8 - Ver minhas mídias da lista para ver");

            System.out.println("Sua opção:");
            opcao = Integer.parseInt(scanner.nextLine());
        } while (opcao < 0 || opcao > 7);
        return opcao;
    }

    /*
     * Metodo com sub menu do sub menu, o cliente pode verificar os relatórios
     */
    public static int subMenuRelatorios() {
        int opcao = -1;
        do {
            System.out.println("==========================");
            System.out.println("Veja as opções: ");
            System.out.println("0 - Retornar ao menu principal");
            System.out.println("1 - Cliente com mais mídias assistidas");
            System.out.println("2 - Cliente com mais avaliações");
            System.out.println("3 - Porcentagem de Clientes com >= 15 avaliações");
            System.out.println("4 - As 10 mídias mais vistas do Pucflix");
            System.out.println("5 - As 10 mídias com melhor avaliação do Pucflix");
            System.out.println("6 - As 10 mídias mais vistas do Pucflix em cada gênero");
            System.out.println("7 - As 10 mídias com melhor avaliação do Pucflix em cada gênero");

            System.out.println("Sua opção:");
            opcao = Integer.parseInt(scanner.nextLine());
        } while (opcao < 0 || opcao > 7);
        return opcao;
    }

    /*
     * Switch com os metodos de tarefas relacionadas ao cliente
     */
    public static void subSwitchLoginLogoutRegistrar() {
        int opcao = -1;
        opcao = subMenuParaLoginLogoutRegistrar();
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
            case 0:
                break;
            default:
                break;
        }
    }

    /**
     * Realiza o cadastro de uma série.
     *
     * Este método solicita ao usuário as informações necessárias para cadastrar uma
     * série, como o id, nome, gênero, idioma,
     * quantidade de episódios e data de lançamento. Em seguida, chama o método
     * "cadastrarSerie" da instância da classe
     * "plataforma" para efetuar o cadastro da série no sistema.
     *
     * @throws IOException se ocorrer um erro durante o cadastro da série.
     */
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

            return;
        }
        System.out.println("Série cadastrada com sucesso!");
    }

    /**
     * Realiza o cadastro de um filme.
     *
     * Este método solicita ao usuário as informações necessárias para cadastrar um
     * filme, como o id, nome, gênero, idioma,
     * duração e data de lançamento. Em seguida, chama o método "cadastrarFilme" da
     * instância da classe "plataforma" para
     * efetuar o cadastro do filme no sistema.
     *
     * @throws IOException se ocorrer um erro durante o cadastro do filme.
     */
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
            return;
        }
        System.out.println("Filme cadastrado com sucesso!");
    }

    /*
     * Switch com os metodos de tarefas relacionadas as midias
     */
    public static void subSwitchMidias() throws IOException {
        int opcao = -1;
        opcao = subMenuParaMidias();
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
                cadastrarSerie();
                break;
            case 6:
                cadastrarFilme();
                break;
            case 7:
                verMediaAvaliacoesMidia();
                break;
            case 8:
                verMinhaListaParaVer();
                break;
            case 0:
                break;
            default:
                break;
        }
    }

    public static void verMinhaListaParaVer() {
        System.out.println();
        System.out.println("==========================");
        System.out.println("Minha lista: ");
    }

    private static void verMediaAvaliacoesMidia() {
        System.out.println();
        System.out.println("==========================");
        System.out.println("Digite o nome da mídia:");
        String nomeDaMidia = scanner.nextLine();
        Midia novaMidia = plataforma.buscar(nomeDaMidia);
        if (novaMidia == null) {
            System.out.println("Midia não existe, refaça a busca.");
            return;
        }
        try {
            double avaliacaoDaMidia = novaMidia.mediaAvaliacoes();
            System.out.println("Midia: " + novaMidia.getNome() + ", sua média de avaliações: " + avaliacaoDaMidia);
        } catch (Exception e) {
            System.out.println("Avaliação não encontrada, refaça a busca.");
            return;
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
                relatorio.relatoriosPorParametro(1);
                break;
            case 2:
                // TODO: Cliente com mais avaliações
                relatorio.relatoriosPorParametro(2);
                break;
            case 3:
                // TODO: Porcentagem de Clientes com >= 15 avaliações
                relatorio.relatoriosPorParametro(3);
                break;
            case 4:
                // TODO: As 10 mídias mais vistas do Pucflix
                List<Integer> listaRelatorio = new ArrayList();
                Midia encontrada;
                listaRelatorio = relatorio.midiasMaisVistas();
                for (Integer cadaMidia : listaRelatorio) {
                    encontrada = plataforma.buscar(cadaMidia);
                    System.out.println(encontrada.toString());
                }
                break;
            case 5:
                // TODO: As 10 mídias com melhor avaliação do Pucflix
                relatorio.relatorioMediaAvaliacao();
                break;
            case 6:
                // TODO: As 10 mídias mais vistas do Pucflix em cada gênero
                // relatorio.relatoriosPorParametro(6);
                break;
            case 7:
                // TODO: As 10 mídias com melhor avaliação do Pucflix em cada gênero
                // relatorio.relatoriosPorParametro(7);
                break;
            case 0:
                break;
            default:
                break;
        }
    }

    /**
     * Exibe o submenu de login e cadastro.
     *
     * Este método exibe as opções disponíveis para o usuário no submenu de login e
     * cadastro, como fazer login ou se cadastrar
     * como cliente. Solicita a opção escolhida pelo usuário e a retorna.
     *
     * @return A opção escolhida pelo usuário.
     */
    public static int subMenuLoginCadastrar() {
        int opcao = -1;
        do {
            System.out.println("==========================");
            System.out.println("Escolha uma opção: ");
            System.out.println("1 - Fazer Login");
            System.out.println("2 - Cadastrar como Cliente");

            System.out.println("Sua opção:");
            opcao = Integer.parseInt(scanner.nextLine());
        } while (opcao < 1 || opcao > 2);
        return opcao;
    }

    /**
     * Inicializa o sistema.
     *
     * Este método é responsável por carregar os dados iniciais, exibir o submenu de
     * login e cadastro e realizar a ação
     * correspondente à opção escolhida pelo usuário. Primeiro, carrega os dados
     * iniciais chamando o método "carregarDados()".
     * Em seguida, exibe o submenu de login e cadastro chamando o método
     * "subMenuLoginCadastrar()" e obtém a opção escolhida
     * pelo usuário. Com base na opção escolhida, executa a ação correspondente:
     * efetuar login ou cadastrar um cliente.
     * Caso nenhuma opção válida seja escolhida, o método encerra sem executar
     * nenhuma ação adicional.
     */
    public static void init() {
        carregarDados();
        int opcao = -1;
        opcao = subMenuLoginCadastrar();
        switch (opcao) {
            case 1:
                efetuarLogin();
                break;
            case 2:
                cadastrarCliente();
            default:
                System.out.println("Digite um valor válido");
                break;
        }
    }

    /**
     * Ponto de entrada do programa.
     *
     * Este método é responsável por iniciar a execução do programa. Primeiro, chama
     * o método "init()" para inicializar o
     * sistema, carregar os dados iniciais e exibir o submenu de login e cadastro.
     * Em seguida, entra em um loop "do-while"
     * que permite ao usuário selecionar diferentes opções do menu principal. A cada
     * iteração do loop, exibe o submenu
     * correspondente à opção escolhida e executa a ação correspondente. O loop
     * continua até que o usuário selecione a opção
     * de sair (opção 0). Durante a execução do programa, utiliza os métodos
     * "limparConsole()" para limpar a tela, "pausar()"
     * para aguardar a interação do usuário e "subMenuPrincipal()" para exibir e
     * obter a opção do menu principal.
     *
     * @param args os argumentos de linha de comando (não utilizados neste
     *             programa).
     * @throws IOException se ocorrer um erro durante a execução do programa.
     */
    public static void main(String[] args) throws IOException {
        init();
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

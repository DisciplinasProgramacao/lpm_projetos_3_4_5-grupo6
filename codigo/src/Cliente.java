import java.util.ArrayList;
import java.util.List;

import Exceptions.SenhaFracaException;

public class Cliente {
    private String nome;
    private String login;
    private String senha;
    private List<Serie> listaParaVer;
    private List<Serie> listaJaVistas;

    /**
     * Construtor da classe Cliente.
     * 
     * @param nome  O nome do cliente. Deve ser uma string não vazia (obrigatório).
     * @param login O login do cliente. Deve ser uma string não vazia (obrigatório).
     * @param senha A senha do cliente. Deve ser uma string não vazia e ter pelo
     *              menos 8 caracteres (obrigatório).
     * @throws IllegalArgumentException Se o nome, login ou senha forem vazios ou
     *                                  nulos.
     * @throws SenhaFracaException      Se a senha tiver menos de 8 caracteres.
     */
    public Cliente(String nome, String login, String senha) throws IllegalArgumentException, SenhaFracaException {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente não pode ser vazio.");
        }
        if (login == null || login.isEmpty()) {
            throw new IllegalArgumentException("Login do cliente não pode ser vazio.");
        }
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("Senha do cliente não pode ser vazia.");
        }
        if (senha.length() < 8) {
            throw new SenhaFracaException("A senha deve ter pelo menos 8 caracteres.");
        }

        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.listaParaVer = new ArrayList<>();
        this.listaJaVistas = new ArrayList<>();
    }

    // #region Métodos
    /**
     * Método que adiciona uma série na lista de séries para ver do cliente.
     * 
     * @param serie série para ser adicinada. Deve ser um objeto do tipo Série
     *              (obrigatório)
     */
    public void adicionarNaLista(Serie serie) {

        if (!listaParaVer.contains(serie)) {
            listaParaVer.add(serie);
        }
    }

    /**
     * 
     * Método que remove uma Série da lista de Séries para ver.
     * 
     * @param nomeSerie O nome da Série que será removida da lista.
     */
    public void retirarDaLista(String nomeSerie) {
        listaParaVer.removeIf(serie -> serie.getNome().equals(nomeSerie));
    }

    /**
     * Filtra as séries por gênero.
     *
     * @param genero O gênero das séries a serem filtradas.
     * @return Uma lista contendo todas as séries que pertencem ao gênero
     *         especificado. Retorna as séris das listas já assistidas ou para
     *         assistir.
     * 
     */
    public List<Serie> filtrarPorGenero(String genero) {
        List<Serie> filtroPorGenero = new ArrayList<>();

        for (Serie serie : listaParaVer) {
            if (serie.getGenero().equals(genero)) {
                filtroPorGenero.add(serie);
            }
        }

        for (Serie serie : listaJaVistas) {
            if (serie.getGenero().equals(genero)) {
                filtroPorGenero.add(serie);
            }
        }
        return filtroPorGenero;
    }

    /*
     * MÉTODO COM STREAMING
     * public List<Serie> filtrarPorGenero(String genero) {
     * List<Serie> filtroPorGenero = new ArrayList<>();
     * filtroPorGenero.addAll(listaParaVer.stream().filter(serie ->
     * serie.getGenero().equals(genero)).collect(Collectors.toList()));
     * filtroPorGenero.addAll(listaJaVistas.stream().filter(serie ->
     * serie.getGenero().equals(genero)).collect(Collectors.toList()));
     * return filtroPorGenero;
     * }
     */

    /**
     * Filtra as séries por idioma.
     *
     * @param idioma O idioma das séries a serem filtradas.
     * @return Uma lista contendo todas as séries que pertencem ao idioma
     *         especificado. Retorna as séris das listas já assistidas ou para
     *         assistir.
     * 
     */
    public List<Serie> filtrarPorIdioma(String idioma) {
        List<Serie> filtroPorIdioma = new ArrayList<Serie>();

        for (Serie serie : listaParaVer) {
            if (serie.getIdioma().equals(idioma)) {
                filtroPorIdioma.add(serie);
            }
        }
        for (Serie serie : listaJaVistas) {
            if (serie.getIdioma().equals(idioma)) {
                filtroPorIdioma.add(serie);
            }
        }
        return filtroPorIdioma;
    }

    /**
     * Filtra as séries por quantidade de episódios.
     *
     * @param quantidade O valor (inteiro) das séries a serem filtradas.
     * @return Uma lista contendo todas as séries que contém a quantidade de
     *         episódios especificado. Retorna as séris das listas já assistidas ou
     *         para assistir.
     * 
     */
    public List<Serie> filtrarPorQtdEpisodios(int quantEpisodios) {
        List<Serie> filtroPorEp = new ArrayList<Serie>();

        for (Serie serie : listaParaVer) {
            if (serie.getQuantidadeEpisodios() == quantEpisodios) {
                filtroPorEp.add(serie);
            }
        }
        for (Serie serie : listaJaVistas) {
            if (serie.getQuantidadeEpisodios() == quantEpisodios) {
                filtroPorEp.add(serie);
            }
        }
        return filtroPorEp;
    }

    /**
     * 
     * Registra uma audiência para a série especificada.
     * 
     * @param serie a série para a qual será registrada a audiência
     */
    public void registrarAudiencia(Serie serie) {
        serie.registrarAudiencia();
    }

    /**
     * 
     * Verifica se a senha fornecida é igual à senha armazenada neste objeto de
     * cliente.
     * 
     * @param s a senha a ser verificada
     * @return true se a senha fornecida é igual à senha armazenada neste objeto de
     *         cliente, false caso contrário
     */
    public boolean senhaCorreta(String s) {
        return this.senha.equals(s);
    }
    // #endregion

    // #region Getters
    /**
     * 
     * Retorna uma cópia da lista de séries para assistir.
     * 
     * @return uma nova lista contendo as séries para assistir.
     */
    public List<Serie> getListaParaVer() {
        List<Serie> copia = new ArrayList<>(listaParaVer);
        return copia;
    }

    /**
     * 
     * Retorna uma cópia da lista de séries ja assistidas.
     * 
     * @return uma nova lista contendo as séries assistidas.
     */
    public List<Serie> getListaJaVistas() {
        List<Serie> copia = new ArrayList<>(listaJaVistas);
        return copia;
    }

    /**
     * 
     * Retorna o login do cliente.
     * 
     * @return string com login do usuário.
     */
    public String getLogin() {
        return this.login;
    }

    // #endregion
}
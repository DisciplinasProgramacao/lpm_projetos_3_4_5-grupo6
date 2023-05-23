
import java.util.ArrayList;
import java.util.List;

import Exceptions.SenhaFracaException;

public class Cliente {
    private String nome;
    private String login;
    private String senha;
    private List<Midia> listaParaVer;
    private List<Midia> listaJaVistas;

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
            throw new IllegalArgumentException("O nome do cliente não pode ser vazio.");
        }
        if (login == null || login.isEmpty()) {
            throw new IllegalArgumentException("O login do cliente não pode ser vazio.");
        }
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("A senha do cliente não pode ser vazia.");
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

    /**
     * Método que adiciona uma mídia na lista de mídias para ver do cliente.
     *
     * @param m A mídia a ser adicionada (obrigatório).
     */
    public void adicionarNaLista(Midia m) {
        if (!listaParaVer.contains(m)) {
            listaParaVer.add(m);
        }
    }

    /**
     * Método que remove uma mídia da lista de mídias para ver.
     *
     * @param nomeMidia O nome da mídia que será removida da lista.
     */
    public void retirarDaLista(String nomeMidia) {
        listaParaVer.removeIf(midia -> midia.getNome().equals(nomeMidia));
    }

    /**
     * Filtra as mídias por gênero.
     *
     * @param genero O gênero das mídias a serem filtradas.
     * @return Uma lista contendo todas as mídias que pertencem ao gênero
     *         especificado.
     */
    public List<Midia> filtrarPorGenero(String genero) {
        List<Midia> filtroPorGenero = new ArrayList<>();

        for (Midia m : listaParaVer) {
            if (m.getGenero().equals(genero)) {
                filtroPorGenero.add(m);
            }
        }

        for (Midia m : listaJaVistas) {
            if (m.getGenero().equals(genero)) {
                filtroPorGenero.add(m);
            }
        }

        return filtroPorGenero;
    }

    /**
     * Filtra as mídias por idioma.
     *
     * @param idioma O idioma das mídias a serem filtradas.
     * @return Uma lista contendo todas as mídias que pertencem ao idioma
     *         especificado.
     */
    public List<Midia> filtrarPorIdioma(String idioma) {
        List<Midia> filtroPorIdioma = new ArrayList<>();

        for (Midia m : listaParaVer) {
            if (m.getIdioma().equals(idioma)) {
                filtroPorIdioma.add(m);
            }
        }

        for (Midia m : listaJaVistas) {
            if (m.getIdioma().equals(idioma)) {
                filtroPorIdioma.add(m);
            }
        }

        return filtroPorIdioma;
    }

    /**
     * Filtra somente as séries por quantidade de episódios.
     *
     * @param quantEpisodios A quantidade de episódios das séries a serem filtradas.
     * @return Uma lista contendo todas as séries que contém a quantidade de
     *         episódios especificada.
     */
    public List<Midia> filtrarPorQtdEpisodios(int quantEpisodios) {
        List<Midia> filtroPorEp = new ArrayList<>();
        List<Serie> listaSeries = new ArrayList<>();

        for (Midia m : listaParaVer) {
            if (m instanceof Serie) {
                listaSeries.add((Serie) m);
            }
        }

        for (Midia m : listaJaVistas) {
            if (m instanceof Serie) {
                listaSeries.add((Serie) m);
            }
        }

        for (Serie serie : listaSeries) {
            if (serie.getQuantidadeEpisodios() == quantEpisodios) {
                filtroPorEp.add(serie);
            }
        }

        return filtroPorEp;
    }

    /**
     * Registra uma audiência para a mídia especificada e a registra na lista de
     * mídias assistidas.
     * Se a mídia estiver na lista de Mídias para Ver, ela é removida.
     * Se a Mídia não estiver na lista de assistida, ela é adicionada.
     *
     * @param m A mídia para a qual será registrada a audiência.
     */
    public void registrarAudiencia(Midia m) {

        if (listaParaVer.contains(m)) {
            listaParaVer.remove(m);
        }
        if (!listaJaVistas.contains(m)) {
            listaJaVistas.add(m);
        }
        m.registrarAudiencia();
    }

    /**
     * Verifica se a senha fornecida é igual à senha armazenada neste objeto de
     * cliente.
     *
     * @param s A senha a ser verificada.
     * @return true se a senha fornecida é igual à senha armazenada neste objeto de
     *         cliente, false caso contrário.
     */
    public boolean senhaCorreta(String s) {
        return this.senha.equals(s);
    }

    /**
     * Retorna uma cópia da lista de mídias para assistir.
     *
     * @return Uma nova lista contendo as mídias para assistir.
     */
    public List<Midia> getListaParaVer() {
        List<Midia> copia = new ArrayList<>(listaParaVer);
        return copia;
    }

    /**
     * Retorna uma cópia da lista de mídias já assistidas.
     *
     * @return Uma nova lista contendo as mídias assistidas.
     */
    public List<Midia> getListaJaVistas() {
        List<Midia> copia = new ArrayList<>(listaJaVistas);
        return copia;
    }

    /**
     * Retorna o login do cliente.
     *
     * @return String com o login do cliente.
     */
    public String getLogin() {
        return this.login;
    }
}
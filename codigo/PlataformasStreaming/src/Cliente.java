
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.List;

import Exceptions.SenhaFracaException;

public class Cliente {
    private static final String CAMINHO_ARQUIVO_ESPECTADORES = "assets/Espectadores.csv";
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
        validarNome(nome);
        validarLogin(login);
        validarSenha(senha);
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.listaParaVer = new ArrayList<>();
        this.listaJaVistas = new ArrayList<>();
    }

    // #region Validação de dados
    /**
     * Valida o nome passado como parâmetro.
     * 
     * @param nome
     * @throws IllegalArgumentException
     */
    private void validarNome(String nome) throws IllegalArgumentException {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente não pode ser vazio.");
        }
    }

    /**
     * Valida o login passado como parâmetro.
     * 
     * @param login
     * @throws IllegalArgumentException
     */
    private void validarLogin(String login) throws IllegalArgumentException {
        if (login == null || login.isEmpty()) {
            throw new IllegalArgumentException("O login do cliente não pode ser vazio.");
        }
    }

    /**
     * Valida a senha passada como parâmetro.
     * 
     * @param senha
     * @throws IllegalArgumentException
     * @throws SenhaFracaException
     */
    private void validarSenha(String senha) throws IllegalArgumentException, SenhaFracaException {
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("A senha do cliente não pode ser vazia.");
        }
        if (senha.length() < 8) {
            throw new SenhaFracaException("A senha deve ter pelo menos 8 caracteres.");
        }
    }

    // #endregion

    /**
     * Adiciona uma mídia na lista de mídias para ver do cliente.
     *
     * @param midia A mídia a ser adicionada (obrigatório).
     */
    public void adicionarNaLista(Midia midia) {
        if (!listaParaVer.contains(midia)) {
            listaParaVer.add(midia);
        }
    }

    /**
     * Remove uma mídia da lista de mídias para ver.
     *
     * @param nomeMidia O nome da mídia que será removida da lista.
     */
    public void retirarDaLista(String nomeMidia) {
        listaParaVer.removeIf(midia -> midia.getNome().equals(nomeMidia));
    }

    // #region Filtros
    /**
     * Filtra as mídias por gênero.
     *
     * @param genero O gênero das mídias a serem filtradas.
     * @return Uma lista contendo todas as mídias que pertencem ao gênero
     *         especificado.
     */
    public List<Midia> filtrarPorGenero(String genero) {
        FiltroGenero filtro = new FiltroGenero();
        return filtro.comparar(listaParaVer, genero);
    }

    /**
     * Filtra as mídias por idioma.
     *
     * @param idioma O idioma das mídias a serem filtradas.
     * @return Uma lista contendo todas as mídias que pertencem ao idioma
     *         especificado.
     */
    public List<Midia> filtrarPorIdioma(String idioma) {
        FiltroIdioma filtro = new FiltroIdioma();
        return filtro.comparar(listaParaVer, idioma);
    }

    /**
     * Filtra somente as séries por quantidade de episódios.
     *
     * @param quantEpisodios A quantidade de episódios das séries a serem filtradas.
     * @return Uma lista contendo todas as séries que contém a quantidade de
     *         episódios especificada.
     */
    public List<Midia> filtrarPorQtdEpisodios(int quantEpisodios) {
        FiltroTotalEp filtro = new FiltroTotalEp();
        return filtro.comparar(listaParaVer, Integer.toString(quantEpisodios));
    }
    // #endregion

    /**
     * Registra uma audiência para a mídia especificada e a registra na lista de
     * mídias assistidas.
     * Se a mídia estiver na lista de Mídias para Ver, ela é removida.
     * Se a Mídia não estiver na lista de assistida, ela é adicionada.
     *
     * @param midia A mídia para a qual será registrada a audiência.
     * @throws InvalidAlgorithmParameterException
     */
    public void registrarAudiencia(Midia midia) throws InvalidParameterSpecException {
        if (listaJaVistas.contains(midia))
            throw new InvalidParameterSpecException("Não é possível assistir uma mídia mais de uma vez");

        if (listaParaVer.contains(midia))
            listaParaVer.remove(midia);

        listaJaVistas.add(midia);
        midia.registrarAudiencia();
    }

    /**
     * Verifica se a senha fornecida é igual à senha armazenada neste objeto de
     * cliente.
     *
     * @param senha A senha a ser verificada.
     * @return true se a senha fornecida é igual à senha armazenada neste objeto de
     *         cliente, false caso contrário.
     */
    public boolean senhaCorreta(String senha) {
        return this.senha.equals(senha);
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
     * Salva o cliente no armazenamento de dados do projeto
     * 
     * @throws IOException
     */
    public void salvar() throws IOException {
        DAO dao = new DAO();
        dao.salvar(CAMINHO_ARQUIVO_ESPECTADORES, this.toString());
    }

    /**
     * Transforma os dados do cliente para o formato de texto.
     * 
     * @return os dados do cliente no formato: nome;login;senha
     */
    @Override
    public String toString() {
        StringBuilder clienteParaCSV = new StringBuilder();
        clienteParaCSV.append(this.nome).append(";").append(this.login).append(";").append(this.senha);
        return clienteParaCSV.toString();
    }

    // #region Getters
    /**
     * Retorna o login do cliente.
     *
     * @return String com o login do cliente.
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * 
     * @return o nome do cliente
     */
    public String getNome() {
        return nome;
    }
    // #endregion
}

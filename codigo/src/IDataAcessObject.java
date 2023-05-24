import java.util.List;

/**
 * Interface que define as operações básicas de acesso a dados para uma entidade
 * genérica.
 *
 * @param <T> o tipo de objeto manipulado pelo Data Access Object.
 */
public interface IDataAcessObject<T> {

    /**
     * Salva um objeto no sistema.
     *
     * @param objeto o objeto a ser salvo.
     */
    void salvar(T objeto);

    /**
     * Busca um objeto pelo seu ID.
     *
     * @param id o ID do objeto a ser buscado.
     * @return o objeto encontrado, ou null se nenhum objeto for encontrado com o ID
     *         fornecido.
     */
    Object buscarPorId(int id);

    /**
     * Busca todos os objetos do sistema.
     *
     * @return uma lista contendo todos os objetos encontrados.
     */
    List<T> buscarTodos();

    /**
     * Atualiza um objeto no sistema.
     *
     * @param objeto o objeto a ser atualizado.
     */
    void atualizar(T objeto);

    /**
     * Exclui um objeto do sistema.
     *
     * @param objeto o objeto a ser excluído.
     */
    void excluir(T objeto);
}
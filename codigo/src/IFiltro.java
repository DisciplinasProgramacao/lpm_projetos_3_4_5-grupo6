import java.util.List;

/**
 * Essa interface define um filtro para comparar uma lista de mídias com base em
 * um gênero específico.
 */
public interface IFiltro {

    /**
     * Compara a lista de mídias com base numa string fornecida.
     * 
     * @param t a lista de mídias a ser comparada
     * @param a pripriedade string a ser usado como critério de comparação
     * @return uma nova lista contendo as mídias que possuem o gênero correspondente
     */
    List<Midia> comparar(List<Midia> t, String a);

}
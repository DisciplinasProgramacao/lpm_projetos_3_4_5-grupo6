import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação da interface IFiltro que compara uma lista de mídias com base
 * no gênero fornecido.
 */
public class FiltroGenero implements IFiltro {

    /**
     * Compara a lista de mídias com base no gênero fornecido.
     * 
     * @param t a lista de mídias a ser comparada
     * @param a o gênero a ser usado como critério de comparação
     * @return uma nova lista contendo as mídias que possuem o gênero correspondente
     */
    @Override
    public List<Midia> comparar(List<Midia> t, String a) {
        return t.stream()
                .filter(midia -> midia.getGenero().equals(a))
                .collect(Collectors.toList());

    }

}

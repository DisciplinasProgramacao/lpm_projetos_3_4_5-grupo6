import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação da interface IFiltro que compara uma lista de mídias com base
 * no idioma fornecido.
 */
public class FiltroIdioma implements IFiltro {

    /**
     * Compara a lista de mídias com base no idioma fornecido.
     * 
     * @param t a lista de mídias a ser comparada
     * @param a o idioma a ser usado como critério de comparação
     * @return uma nova lista contendo as mídias que possuem o idioma correspondente
     */
    @Override
    public List<Midia> comparar(List<Midia> t, String a) {
        return t.stream()
                .filter(midia -> midia.getIdioma().equals(a))
                .collect(Collectors.toList());

    }

}

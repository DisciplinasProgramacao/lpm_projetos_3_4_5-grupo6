
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementação da interface IFiltro que compara uma lista de mídias com base
 * no Total de episódios fornecido.
 */
public class FiltroTotalEp implements IFiltro {

    /**
     * Compara a lista de mídias com base no Total de episódios fornecido.
     * 
     * @param t a lista de mídias a ser comparada
     * @param a o Total de episódios a ser usado como critério de comparação(STRING)
     * @return uma nova lista contendo as mídias que possuem o Total de episódios
     *         correspondente
     */
    @Override
    public List<Midia> comparar(List<Midia> t, String quantEpisodios) {
        int ep = Integer.parseInt(quantEpisodios);
        return t.stream()
                .map(m -> {
                    if (m instanceof Serie) {
                        return (Serie) m;
                    } else {
                        return null; // Se não for uma instância de Serie, retorna null
                    }
                })
                .filter(Objects::nonNull) // Filtra os elementos não nulos
                .filter(m -> m.getQuantidadeEpisodios() == ep)
                .collect(Collectors.toList());
    }

}

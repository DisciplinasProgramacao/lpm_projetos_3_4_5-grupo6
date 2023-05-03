import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Filtro<T extends Comparable<T>> { // adiciona restrição ao tipo genérico para que possa ser comparável
    private final List<T> dadosOriginais;
    private final Comparator<T> comparadorPadrao;

    public Filtro(List<T> listaOriginal, Comparator<T> comparador) {
        this.dadosOriginais = new ArrayList<>(listaOriginal); // cria uma cópia da lista original para evitar alterações inesperadas
        this.comparadorPadrao = comparador;
    }

    public Filtro(List<T> listaOriginal) {
        this(listaOriginal, null); // chama o construtor com parâmetros completos, passando null para o comparador
    }

    public List<T> filtrar(T elementoID){ // muda o tipo de retorno para List<T>, que é o tipo do objeto criado na função
        List<T> listaFiltrada = new ArrayList<>();

        for (T t : dadosOriginais) {
            if (comparadorPadrao != null) { // usa o comparador padrão, se existir
                if (t.equals(elementoID)) {
                    listaFiltrada.add(t);
                }
            }
        }

        return listaFiltrada;
    }
}

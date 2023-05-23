
import java.util.ArrayList;

import java.util.List;

public class FiltroMidia<T> {

        public List<Midia> filtroGenero(String genero1, List<Midia> lista) {
                List<Midia> midiasFiltradas = new ArrayList<Midia>();

                for (Midia midia : lista) {
                        if (genero1.equals(midia.getGenero())) {
                                midiasFiltradas.add(midia);
                        }
                }

                return midiasFiltradas;
        }

        public List<Midia> filtroGenero(String genero1, List<Midia> lista) {
                List<Midia> midiasFiltradas = new ArrayList<Midia>();

                for (Midia midia : lista) {
                        if (genero1.equals(midia.getGenero())) {
                                midiasFiltradas.add(midia);
                        }
                }

                return midiasFiltradas;
        }

}

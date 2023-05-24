
import java.util.ArrayList;

import java.util.List;

public class FiltroMidia {

        public List<Midia> filtrar(IFiltro filtro, List<Midia> midias, String chave){
                for (Midia midia : midias) {
                     if(filtro.comparar(midia, chave)==0))
                        listaFiltrada.add(midia)
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

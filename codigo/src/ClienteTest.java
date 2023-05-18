
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exceptions.SenhaFracaException;

public class ClienteTest {

    private Cliente cliente;
    Serie serie1;
    Serie serie2;
    Serie serie3;

    @BeforeEach
    public void setup() throws IllegalArgumentException, SenhaFracaException {

        cliente = new Cliente("John Doe", "johndoe", "password123");

        serie1 = new Serie("Série 1", "Inglês", "Drama", 5);
        serie2 = new Serie("Série 2", "Português", "Comédia", 10);
        serie3 = new Serie("Série 3", "Espanhol", "Ação", 15);
    }

    @Test
    public void adicionarNaListaTest() {

        cliente.adicionarNaLista(serie1);
        Assertions.assertTrue(cliente.getListaParaVer().contains(serie1));
        cliente.adicionarNaLista(serie2);
        Assertions.assertTrue(cliente.getListaParaVer().contains(serie2));
    }

    @Test
    public void adicionarNaListaAssistidasTest() {

        cliente.adicionarNaLista(serie1);
        Assertions.assertTrue(cliente.getListaParaVer().contains(serie1));
        cliente.adicionarNaLista(serie1);
        Assertions.assertFalse(cliente.getListaParaVer().contains(serie1));
        Assertions.assertTrue(cliente.getListaJaVistas().contains(serie1));
    }

    @Test
    public void retirarDaListaTest() {

        cliente.adicionarNaLista(serie1);
        cliente.adicionarNaLista(serie2);

        cliente.retirarDaLista("Série 1");
        Assertions.assertFalse(cliente.getListaParaVer().contains(serie1));
        Assertions.assertTrue(cliente.getListaParaVer().contains(serie2));
    }

    @Test
    public void filtrarPorGeneroTest() {

        cliente.adicionarNaLista(serie1);
        cliente.adicionarNaLista(serie2);
        cliente.adicionarNaLista(serie3);

        Assertions.assertEquals(1, cliente.filtrarPorGenero("Drama").size());
        Assertions.assertEquals(1, cliente.filtrarPorGenero("Ação").size());
    }

    @Test
    public void filtrarPorIdiomaTest() {

        cliente.adicionarNaLista(serie1);
        cliente.adicionarNaLista(serie2);
        cliente.adicionarNaLista(serie3);

        Assertions.assertEquals(1, cliente.filtrarPorIdioma("Inglês").size());
        Assertions.assertEquals(1, cliente.filtrarPorIdioma("Espanhol").size());
    }

    @Test
    public void testFiltrarPorGenero() {
        cliente.adicionarNaLista(serie1);
        cliente.adicionarNaLista(serie2);
        cliente.adicionarNaLista(serie3);
        List<Serie> resultado = cliente.filtrarPorGenero("Drama");
        assertEquals(1, resultado.size());
        assertEquals(serie1, resultado.get(0));
    }

    @Test
    public void testFiltrarPorIdioma() {
        cliente.adicionarNaLista(serie1);
        cliente.adicionarNaLista(serie2);
        cliente.adicionarNaLista(serie3);
        List<Serie> resultado = cliente.filtrarPorIdioma("Português");
        assertEquals(1, resultado.size());
        assertEquals(serie2, resultado.get(0));
    }

    @Test
    public void testFiltrarPorQtdEpisodios() {
        cliente.adicionarNaLista(serie1);
        cliente.adicionarNaLista(serie2);
        cliente.adicionarNaLista(serie3);
        List<Serie> resultado = cliente.filtrarPorQtdEpisodios(10);
        assertEquals(1, resultado.size());
        assertEquals(serie2, resultado.get(0));
    }

}
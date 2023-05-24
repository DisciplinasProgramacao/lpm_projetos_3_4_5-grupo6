
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.NameNotFoundException;
import Exceptions.SenhaFracaException;
import Exceptions.SenhaIncorretaException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import java.util.LinkedList;
import java.util.List;

public class PlataformaStreamingTest {
    private PlataformaStreaming plataforma;
    private Serie serie1;
    private Serie serie2;
    private Cliente cliente1;
    private Cliente cliente2;

    @BeforeEach
    public void setup() throws IllegalArgumentException, SenhaFracaException {
        plataforma = new PlataformaStreaming("Minha Plataforma");

        serie1 = new Serie("Série 1", "Inglês", "Drama", 5);
        serie2 = new Serie("Série 2", "Português", "Comédia", 10);

        cliente1 = new Cliente("John Doe", "johndoe", "password123");
        cliente2 = new Cliente("Jane Doe", "janedoe", "password123");

        plataforma.adicionarSerie(serie1);
        plataforma.adicionarSerie(serie2);
        plataforma.adicionarCliente(cliente1);
        plataforma.adicionarCliente(cliente2);
    }

    @Test
    public void testLoginAutenticacaoSucesso() throws NameNotFoundException, SenhaIncorretaException {
        Cliente cliente = plataforma.login("janedoe", "password123");
        assertEquals(cliente1.getClass(), cliente.getClass());

    }

    @Test
    public void testLoginUsuarioNaoEncontrado() {
        assertThrows(NameNotFoundException.class, () -> {
            plataforma.login("usuario_inexistente", "senha");
        });
    }

    @Test
    public void testLoginSenhaIncorreta() {
        assertThrows(SenhaIncorretaException.class, () -> {
            plataforma.login("janedoe", "senha_incorreta");
        });
    }

    @Test
    public void testAdicionarSerie() {
        Midia serie3 = new Serie("Breaking Bad", "Inglês", "Drama", 5);
        plataforma.adicionarSerie(serie3);
        Midia serieEncontrada = plataforma.buscar("Breaking Bad");
        assertEquals(serie3.getNome(), serieEncontrada.getNome());
    }

    @Test
    public void testAdicionarCliente() {
        Cliente cliente3 = null;
        try {
            cliente3 = new Cliente("KitKat", "kitkat", "password123");
            plataforma.adicionarCliente(cliente3);
        } catch (IllegalArgumentException | SenhaFracaException e) {
            e.printStackTrace();
        }

        Cliente clienteEncontrado = null;
        try {
            clienteEncontrado = plataforma.login("kitkat", "password123");
        } catch (NameNotFoundException | SenhaIncorretaException e) {
            e.printStackTrace();
        }
        assertEquals(cliente3.getLogin(), clienteEncontrado.getLogin());
    }

    @Test
    public void testFiltrarPorGenero() {
        List<Midia> seriesFiltradas = plataforma.filtrarPorGenero("Drama");
        assertEquals(1, seriesFiltradas.size());
        assertTrue(seriesFiltradas.contains(serie1));
    }

    @Test
    public void testFiltrarPorIdioma() {
        List<Midia> seriesFiltradas = plataforma.filtrarPorIdioma("Inglês");
        assertEquals(1, seriesFiltradas.size());

    }

    @Test
    public void testFiltrarPorQtdEpisodios() {
        List<Midia> seriesFiltradas = plataforma.filtrarPorQtdEpisodios(10);
        assertEquals(1, seriesFiltradas.size());
        assertTrue(seriesFiltradas.contains(serie2));
    }

    @Test
    public void testRegistrarAudiencia() {
        try {
            plataforma.login("kitkat", "password123");
            plataforma.registrarAudiencia(serie1);
            plataforma.logoff();
            plataforma.login("janedoe", "password123");
            plataforma.registrarAudiencia(serie2);
            Assertions.assertEquals(1, serie1.getAudiencia());
            Assertions.assertEquals(1, serie1.getAudiencia());
        } catch (NameNotFoundException | SenhaIncorretaException e) {
            e.printStackTrace();
        }

    }

}

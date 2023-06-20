
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Assert;
import org.junit.Test;

import Exceptions.SenhaFracaException;

public class MidiaTest {

    @Test
    public void testCriarMidia() {
        String nome = "Filme A";
        String idioma = "Português";
        String genero = "Ação";
        Double duracao = 60d;
        Midia midia = new Filme(nome, idioma, genero, duracao);
        Assert.assertEquals("Filme A", midia.getNome());
        Assert.assertEquals(idioma, midia.getIdioma());
        Assert.assertEquals(genero, midia.getGenero());
    }

    @Test
    public void testCriarMidiaNomeNulo() {
        String nome = null;
        String idioma = "Português";
        String genero = "Ação";
        Double duracao = 60d;

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            Midia midia = new Filme(nome, idioma, genero, duracao);
            ;
        });

    }

    @Test
    public void deveAdicionarUmaAvaliacao() throws IllegalArgumentException, SenhaFracaException {
        String nome = "Filme A";
        String idioma = "Português";
        String genero = "Ação";
        Double duracao = 60d;
        Midia midia = new Filme(nome, idioma, genero, duracao);

        Assert.assertEquals(0, midia.getTodasAvaliacoes().size());

        Cliente cliente1 = new Cliente("John Doe", "johndoe", "password123");
        Avaliacao avaliacao = new Avaliacao(cliente1.getLogin(), 4);

        midia.addAvaliacao(avaliacao);

        Assert.assertEquals(1, midia.getTodasAvaliacoes().size());

    }

}
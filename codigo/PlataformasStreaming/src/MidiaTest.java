
import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import Exceptions.SenhaFracaException;

public class MidiaTest {

    @Test
    public void testCriarMidiaFilme() {
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
    public void testSalvarMidiaFilme() throws IOException {
        String nome = "Filme A";
        String idioma = "Português";
        String genero = "Ação";
        Double duracao = 60d;
        Midia midia = new Filme(nome, idioma, genero, duracao);
        midia.salvar();
    }

    @Test
    public void testCriarMidiaSerie() throws IOException {
        String nome = "Serie A";
        String idioma = "Português";
        String genero = "Ação";
        Double ep = 60d;
        Midia midia = new Filme(nome, idioma, genero, ep);
        Assert.assertEquals("Serie A", midia.getNome());
        Assert.assertEquals(idioma, midia.getIdioma());
        Assert.assertEquals(genero, midia.getGenero());
        midia.salvar();
    }

    @Test
    public void testCriarMidiaNomeNulo() {
        String nome = null;
        String idioma = "Português";
        String genero = "Ação";
        Double duracao = 60d;

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            new Filme(nome, idioma, genero, duracao);
            ;
        });

    }

    @Test
    public void deveAdicionarUmaAvaliacao() throws IllegalArgumentException, SenhaFracaException {
        String nome = "Filme A";
        String idioma = "Português";
        String genero = "Ação";
        String data = "21/04/2022";
        int duracao = 60;
        Midia midia = new Filme(001, nome, idioma, genero, duracao, data);

        Assert.assertEquals(0, midia.getTodasAvaliacoes().size());

        Cliente cliente1 = new Cliente("John Doe", "johndoe", "password123");
        Avaliacao avaliacao = new Avaliacao(cliente1.getLogin(), 4, 001);

        midia.addAvaliacao(avaliacao);

        Assert.assertEquals(1, midia.getTodasAvaliacoes().size());

    }

}
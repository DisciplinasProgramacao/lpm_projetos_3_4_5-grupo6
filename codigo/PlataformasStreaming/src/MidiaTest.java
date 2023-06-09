
import org.junit.Assert;
import org.junit.Test;


public class MidiaTest {



    @Test
    public void testCriarMidia() {
        String   nome = "Filme A";
        String  idioma = "Português";
        String  genero = "Ação";
        Double  duracao = 60d;
        Midia midia = new Filme(nome, idioma, genero, duracao);
        Assert.assertEquals("Filme A", midia.getNome());
        Assert.assertEquals(idioma, midia.getIdioma());
        Assert.assertEquals(genero, midia.getGenero());
    }

    @Test
    public void testCriarMidiaNomeNulo() {
        String   nome = null;
        String  idioma = "Português";
        String  genero = "Ação";
        Double  duracao = 60d;
       

        Assert.assertThrows(IllegalArgumentException.class, () -> {
            Midia midia = new Filme(nome, idioma, genero, duracao);;
        });

    }
    @Test
    public void testContabilizarMediaDaMidia() {
        String   nome = "Filme A";
        String  idioma = "Português";
        String  genero = "Ação";
        Double  duracao = 60d;
        Midia midia = new Filme(nome, idioma, genero, duracao);
        midia.registrarPontosDeAvaliacoes(2);
        midia.registrarPontosDeAvaliacoes(5);
        float avaliacaoEsperada = 3.5f;
        double margemDeErro = 0.001; 
        Assert.assertEquals(avaliacaoEsperada, midia.obterMediaDasAvaliacoes(), margemDeErro);
    }

}
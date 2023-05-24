
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.jupiter.api.Test;

public class FilmeTest {
    @Test
    public void testConstrutor() {
        // Teste com nome nulo
        assertThrows(IllegalArgumentException.class, () -> {
            Filme filme = new Filme(null, "Inglês", "Ação", 120.0);
        });

        // Teste com idioma vazio
        assertThrows(IllegalArgumentException.class, () -> {
            Filme filme = new Filme("Filme 1", "", "Ação", 120.0);
        });

        // Teste com gênero nulo
        assertThrows(IllegalArgumentException.class, () -> {
            Filme filme = new Filme("Filme 1", "Inglês", null, 120.0);
        });

        // Teste com duração menor ou igual a zero
        assertThrows(IllegalArgumentException.class, () -> {
            Filme filme = new Filme("Filme 1", "Inglês", "Ação", -10.0);
        });

        // Teste com gênero não cadastrado
        assertThrows(IllegalArgumentException.class, () -> {
            Filme filme = new Filme("Filme 1", "Inglês", "Aventura", 120.0);
        });

        // Teste válido
        Filme filme = new Filme("Filme 1", "Inglês", "Ação", 120.0);
        assertEquals("Filme 1", filme.getNome());
        assertEquals("Inglês", filme.getIdioma());
        assertEquals("Ação", filme.getGenero());

    }

    @Test
    public void testRegistrarAudiencia() {
        Filme filme = new Filme("Filme 1", "Inglês", "Ação", 120.0);
        assertEquals(0, filme.getAudiencia());

        filme.registrarAudiencia();
        assertEquals(1, filme.getAudiencia());

        filme.registrarAudiencia();
        assertEquals(2, filme.getAudiencia());
    }

}

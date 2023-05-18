import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*TESTE GERADO PELO CHATGPT DEVIDO PRAZO APERTADO E VALIDADO POR LUIZ FELIPE VIEIRA */
public class SerieTest {

    private Serie serie;

    @BeforeEach
    public void setUp() {
        serie = new Serie("Breaking Bad", "Inglês", "Drama", 5);
    }

    @Test
    public void testConstrutor() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Serie(null, "Inglês", "Drama", 0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Serie("", "Inglês", "Drama", 1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Serie("Breaking Bad", null, "Drama", 9));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Serie("Breaking Bad", "", "Drama", 5));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Serie("Breaking Bad", "Inglês", null, 8));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Serie("Breaking Bad", "Inglês", "", 50));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Serie("Breaking Bad", "Inglês", "", 0));
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Serie("Breaking Bad", "Inglês", "", -5));
    }

    @Test
    public void testRegistrarAudiencia() {
        serie.registrarAudiencia();
        serie.registrarAudiencia();
        serie.registrarAudiencia();
        Assertions.assertEquals(3, serie.getAudiencia());
    }

    @Test
    public void testGetAudiencia() {
        Assertions.assertEquals(0, serie.getAudiencia());
        serie.registrarAudiencia();
        Assertions.assertEquals(1, serie.getAudiencia());
    }

}

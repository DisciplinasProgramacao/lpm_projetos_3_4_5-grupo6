import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArvoreAnosMesesTest {
    private ArvoreAnosMeses arvore;

    @BeforeEach
    public void setUp() {
        arvore = new ArvoreAnosMeses();
    }

    @Test
    public void testInserirValor() {
        arvore.inserirValor(2022, 3, 1);
        arvore.inserirValor(2022, 3, 2);
        arvore.inserirValor(2023, 1, 3);

        Integer[] valores2022_3 = arvore.obterValor(2022, 3);
        Integer[] valores2023_1 = arvore.obterValor(2023, 1);
        Integer[] valores2023_2 = arvore.obterValor(2023, 2);

        Assertions.assertEquals(2, valores2022_3.length);
        Assertions.assertEquals(1, valores2023_1.length);
        Assertions.assertNull(valores2023_2);
    }

    @Test
    public void testObterValor_Invalido() {
        Integer[] valores = arvore.obterValor(2022, 5);
        Assertions.assertNull(valores);
    }
}
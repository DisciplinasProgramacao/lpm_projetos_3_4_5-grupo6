import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.Transient;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class SerieTest {
    Serie novaSerie;

    @Test
    @BeforeEach
    public void init(){
         novaSerie = new Serie('Série Teste');
    }

    // @Test
    // public void naoPodeCriarSerieSemNome(){
    // assertThrows(InvalidParameterException.class, ()-> new SerieTest('') )
    // assertEquals(0, getAudiencia());
    // assertTrue(novaSerie.getQuantidadeEpisodios()>0);
    // }

    @Test
    public void registrarNovoPontoDeAudiencia() {
        int estadoAudienciaAntesDaModificacao = novaSerie.getAudiencia();
        novaSerie.registrarAudiencia();
        assertEquals(estadoAudienciaAntesDaModificacao + 1, novaSerie.getAudiencia());
    }

}

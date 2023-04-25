import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;


public class SerieTest {


  @Test
  public void registrarNovoPontoDeAudiencia() {
    Serie novaSerie = new Serie("1", "How I Met Your Mother", "01/01/2023");
    int estadoAudienciaAntesDaModificacao = novaSerie.getAudiencia();
    novaSerie.registrarAudiencia();
    assertEquals(estadoAudienciaAntesDaModificacao + 1, novaSerie.getAudiencia());
  }
}

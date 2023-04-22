import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class SerieTest {
  Serie novaSerie;

  @Test
  @BeforeEach
  public void init() {
    // TODO
  }

  @Test
  public void registrarNovoPontoDeAudiencia() {
    int estadoAudienciaAntesDaModificacao = novaSerie.getAudiencia();
    novaSerie.registrarAudiencia();
    assertEquals(estadoAudienciaAntesDaModificacao + 1, novaSerie.getAudiencia());
  }
}

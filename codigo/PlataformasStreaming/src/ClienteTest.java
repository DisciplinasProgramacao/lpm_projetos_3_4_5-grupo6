import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class ClienteTest {
  Cliente novoCliente;

  @Test
  @BeforeEach
  public void init() {
    novoCliente = new Cliente("Cliente Teste");
  }

  @Test
  public void adicionarSerieNaListaParaVer() {
    int tamanhoDaListaParaVer = getListaParaVer().size();
    Serie s = new Serie("Bla Bla Bla");
    novoCliente.adicionarNaLista(s.getId());
    assertEquals(tamanhoDaListaParaVer + 1, novoCliente.getListaParaVer);
  }

}

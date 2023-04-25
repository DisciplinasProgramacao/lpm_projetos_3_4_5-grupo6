import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.security.InvalidParameterException;

import org.junit.Test;

public class ClienteTest {
  @Test
  public void clienteDeveTerNomeLoginESenha() {
    assertThrowsExactly(InvalidParameterException.class, () -> {
      new Cliente("nome;login");
    });
  }

  @Test
  public void clienteDeveTerInformacoesDeTamanhoMaiorQue0() {
    assertThrowsExactly(InvalidParameterException.class, () -> {
      new Cliente("nome;;senha");
    });
  }

  @Test
  public void naoDeveSerPossivelAddSerieSeElaJaEstaNaLista() {
    Cliente cliente = new Cliente("Fulano de Tal", "fulano", "senha123");
    Serie serie = new Serie("1", "How I Met Your Mother", "01/01/2023");
    cliente.adicionarNaLista(serie);
    cliente.adicionarNaLista(serie);
    cliente.adicionarNaLista(serie);
    assertEquals(1, cliente.getListaParaVer().size());
  }

  @Test
  public void adicionarSerieNaListaParaVer() {
    Cliente cliente = new Cliente("Fulano de Tal", "fulano", "senha123");
    Serie serie = new Serie("1", "How I Met Your Mother", "01/01/2023");
    cliente.adicionarNaLista(serie);
    assertEquals(1, cliente.getListaParaVer().size());
  }
}

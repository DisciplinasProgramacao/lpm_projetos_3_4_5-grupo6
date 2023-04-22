import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.security.InvalidParameterException;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

public class ClienteTest {
  Cliente novoCliente;

  @Test
  @BeforeEach
  public void init() {
    // TODO
  }

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
  public void adicionarSerieNaListaParaVer() {
    // TODO
  }
}

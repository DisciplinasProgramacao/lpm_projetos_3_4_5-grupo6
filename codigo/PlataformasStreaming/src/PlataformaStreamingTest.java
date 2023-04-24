import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import javax.naming.NameNotFoundException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PlataformaStreamingTest {
	private static PlataformaStreaming plataforma;

	@BeforeAll
	public static void initAll() {
		plataforma = new PlataformaStreaming("Plataforma de Teste");
		plataforma.carregarClientes();
		plataforma.carregarSeries();
		plataforma.carregarAudiencias();
	}

	@Test
	public void clienteDeveConseguirLogarComSucesso() {
		try {
			plataforma.login("Yuk218531", "YTon19241");
			assertEquals(plataforma.getClienteAtual().getLogin(), "Yuk218531");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void soDeveSerPossivelLogarSeEstiverCadastrado() {
		assertThrowsExactly(NameNotFoundException.class, () -> {
			plataforma.login("usuarionaocadastrado", "senha");
		});
	}

	@Test
	public void deveSerPossivelAdicionarSeriesAPlataforma() {
		Serie novaSerie = new Serie("1;Better Call Saul;01/01/2023");
		plataforma.adicionarSerie(novaSerie);
		assertEquals(novaSerie, plataforma.buscarSerie("Better Call Saul"));
	}

	@Test
	public void naoDeveSerPossivelAdicionarUmaSerieDuplicada() {
		PlataformaStreaming novaPlataforma = new PlataformaStreaming("Plataforma");
		Serie serie = new Serie("123;Suits;02/01/2023");
		novaPlataforma.adicionarSerie(serie);
		novaPlataforma.adicionarSerie(serie);
		assertEquals(1, novaPlataforma.quantidadeSeries());
	}

	@Test
	public void deveSerPossivelAdicionarUmNovoCliente() {
		Cliente novoCliente = new Cliente("Ciclano", "ciclano", "senha456");
		plataforma.adicionarCliente(novoCliente);
		try {
			plataforma.login("ciclano", "senha456");
			assertEquals(plataforma.getClienteAtual().getLogin(), novoCliente.getLogin());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deveSerPossivelFazerLogOff() {
		try {
			plataforma.login("Kle96961", "KPer09061");
			plataforma.logoff();
			assertNull(plataforma.getClienteAtual());
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deveSerPossivelEncontrarUmaSeriePeloNome() {
		Serie serieEncontrada = plataforma.buscarSerie("Fixing Trees");
		assertEquals("Fixing Trees", serieEncontrada.getNome());

	}
}

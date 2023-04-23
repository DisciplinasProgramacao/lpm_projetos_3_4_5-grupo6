import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class LeituraArquivosTest {
	@Test
	public void deveSerPossivelCarregarOArquivoDeClientes() {
		ArrayList<Cliente> clientes = LeituraArquivos.carregarClientes();
		assertEquals("Ada A N Austen", clientes.get(1).getNome());
	}

	@Test
	public void deveSerPossivelCarregarOArquivoDeSeries() {
		ArrayList<Serie> series = LeituraArquivos.carregarSeries();
		assertEquals("Red County", series.get(9).getNome());
	}

	public void deveSerPossivelCarregarOArquivoDeAudiencia() {
		// TODO
	}

	public void deveSerPossivelCarregarOArquivoDeFilmes() {
		// TODO
	}
}

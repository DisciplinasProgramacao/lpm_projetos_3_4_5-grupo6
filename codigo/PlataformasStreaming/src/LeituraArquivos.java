import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

final class LeituraArquivos {
	static ArrayList<Cliente> carregarClientes() {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		Scanner scanner;
		try {
			scanner = new Scanner(caminho("Espectadores.csv").toFile());
			while (scanner.hasNext())
				clientes.add(new Cliente(scanner.nextLine()));
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		return clientes;
	}

	static ArrayList<Serie> carregarSeries() {
		ArrayList<Serie> series = new ArrayList<Serie>();
		Scanner scanner;
		try {
			scanner = new Scanner(caminho("Series.csv").toFile());
			while (scanner.hasNext())
				series.add(new Serie(scanner.nextLine()));
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println(e);
		}
		return series;
	}

	private static Path caminho(String arquivo) {
		ClassLoader classLoader = LeituraArquivos.class.getClassLoader();
		Path raizClasse = Paths.get(classLoader.getResource("").getPath());
		Path raizProjeto = raizClasse.getParent();
		Path caminhoArquivo = Paths.get(raizProjeto.toString(), "assets", arquivo);
		return caminhoArquivo;
	}
}

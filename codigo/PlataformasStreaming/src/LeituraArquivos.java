import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

final class LeituraArquivos {
	static ArrayList<Cliente> carregarClientes() throws FileNotFoundException {
		ArrayList<Cliente> clientes = new ArrayList<Cliente>();
		Scanner scanner = new Scanner(new File("assets/Espectadores.csv"));
		while (scanner.hasNext())
			clientes.add(new Cliente(scanner.nextLine()));
		scanner.close();
		return clientes;
	}

	static ArrayList<Serie> carregarSeries() throws FileNotFoundException {
		ArrayList<Serie> series = new ArrayList<Serie>();
		Scanner scanner = new Scanner(new File("assets/Series.csv"));
		while (scanner.hasNext())
			series.add(new Serie(scanner.nextLine()));
		scanner.close();
		return series;
	}
}

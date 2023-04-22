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
		return clientes;
	}
}

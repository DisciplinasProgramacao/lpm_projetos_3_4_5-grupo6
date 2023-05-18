import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Util {

    static Random random = new Random();

    /**
     * Lê o conteúdo de um arquivo.
     *
     * @param filePath O caminho do arquivo a ser lido.
     * @return O conteúdo do arquivo como uma string.
     * @throws IOException Se ocorrer um erro de leitura ou fechamento do arquivo.
     */
    public static String lerArquivo(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        BufferedReader reader = null;

        try {
            File file = new File(filePath);
            reader = new BufferedReader(new FileReader(file));

            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append(System.lineSeparator());// separador de linha do sistema operacional que está executando.
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    // Tratar qualquer exceção ao fechar o leitor
                }
            }
        }

        return content.toString();
    }

    public static String gerarNovoGenero() {
        String[] genero = {
                "Ação",
                "Comédia",
                "Drama",
                "Fantasia",
                "Horror",
                "Mistério",
                "Romance"
        };

        int numeroAleatorio = random.nextInt(7);
        return genero[numeroAleatorio];

    }

    public static String gerarNovoIdioma() {
        String[] idioma = {
                "Português",
                "Italiano",
                "Espanhol",
                "Coreano",
                "Inglês",
                "Russo",
                "Belga"
        };

        int numeroAleatorio = random.nextInt(7);
        return idioma[numeroAleatorio];

    }

    public static int gerarTotalEp() {
        int numeroAleatorio = random.nextInt(67);
        return numeroAleatorio;

    }
}

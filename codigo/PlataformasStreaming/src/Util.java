import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class Util {
    static Random random = new Random();
    public static final String CAMINHO_ARQUIVO_SERIES = "assets/Series.csv";
    public static final String SEPARADOR_CSV = ";";
    public static final String SEPARADOR_LINHA = System.lineSeparator();

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

    /**
     * Salva uma string no arquivo CSV especificado.
     * 
     * @param caminhoDoArquivo O caminho do arquivo CSV.
     * @param dadoCSV          A string a ser salva no arquivo.
     * @throws IOException Se ocorrer um erro de E/S durante a operação.
     */
    public static void salvarNoArquivo(String caminhoDoArquivo, String dadosParaAcrescentar) throws IOException {
        try {
            if (Files.notExists(Path.of(caminhoDoArquivo))) {
                Files.createFile(Path.of(caminhoDoArquivo));
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoDoArquivo, true));
            writer.write(dadosParaAcrescentar);
            writer.newLine();
            writer.close();
            System.out.println("Dados salvos com sucesso no final do arquivo.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados no arquivo: " + e.getMessage());
        }
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
        int numeroAleatorio = random.nextInt(1, 7);
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
        int numeroAleatorio = random.nextInt(1, 7);
        return idioma[numeroAleatorio];
    }

    public static int gerarTotalEp() {
        int numeroAleatorio = random.nextInt(1, 72);
        return numeroAleatorio;
    }
}

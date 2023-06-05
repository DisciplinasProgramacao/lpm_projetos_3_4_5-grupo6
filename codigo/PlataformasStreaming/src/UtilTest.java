import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class UtilTest {

    @Test
    public void testSalvarNoArquivo() throws IOException {
        // Define o caminho do arquivo de teste
        String filePath = "/assets/teste.csv";
        // Define o dado a ser salvo no arquivo
        String dadoCSV = "Exemplo;de;dado;CSV";
        // Chama o método que será testado
        Util.salvarNoArquivo(filePath, dadoCSV);

        // Verifica se o arquivo foi criado
        Assertions.assertTrue(Files.exists(Path.of(filePath)));

        // Lê o conteúdo do arquivo e verifica se corresponde ao dado salvo
        String fileContent = Files.readString(Path.of(filePath));
        Assertions.assertEquals(dadoCSV + System.lineSeparator(), fileContent);
    }
}

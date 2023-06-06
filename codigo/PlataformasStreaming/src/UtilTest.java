import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UtilTest {
    public static String caminhoDoArquivo;
    public static String dadosCSV;
    public static File arquivo;

    @BeforeAll
    public static void initAll() {
        caminhoDoArquivo = "assets/teste.csv";
        dadosCSV = "Exemplo;de;dado;CSV";
    }

    @Test
    public void deveSerPossivelLerUmArquivo() {
        arquivo = new File(caminhoDoArquivo);
        assertTrue(arquivo.exists());
    }

    @Test
    public void deveSerPossivelAcrescentarDadosAoArquivo() throws IOException {
        String conteudoDoArquivo = Files.readString(Path.of(caminhoDoArquivo));
        Util.salvarNoArquivo(caminhoDoArquivo, dadosCSV);
        String conteudoEsperado = conteudoDoArquivo + dadosCSV + System.lineSeparator();
        Assertions.assertEquals(Files.readString(Path.of(caminhoDoArquivo)), conteudoEsperado);
    }
}

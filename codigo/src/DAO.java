import java.io.IOException;

/**
 * A classe DAO é responsável por salvar dados em um arquivo.
 */
public class DAO {

    /**
     * Salva os dados em um arquivo no caminho especificado.
     *
     * @param path O caminho do arquivo onde os dados serão salvos.
     * @param CSV  Os dados a serem salvos no formato CSV.
     * @throws IOException Se ocorrer um erro durante a operação de salvamento.
     */
    public void salvar(String path, String CSV) throws IOException {
        Util.salvarNoArquivo(path, CSV);
    }
}

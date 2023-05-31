import java.io.IOException;

public class DAO {
    public void salvar(String path, String CSV) throws IOException {
        Util.salvarNoArquivo(path, CSV);
    }
}

import java.nio.file.Path;
import java.nio.file.Paths;

final class Utilitarios {
	public static Path caminho(String arquivo) {
		ClassLoader classLoader = Utilitarios.class.getClassLoader();
		Path raizClasse = Paths.get(classLoader.getResource("").getPath());
		Path raizProjeto = raizClasse.getParent();
		Path caminhoArquivo = Paths.get(raizProjeto.toString(), "assets", arquivo);
		return caminhoArquivo;
	}
}

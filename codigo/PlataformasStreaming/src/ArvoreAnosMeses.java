import java.security.InvalidParameterException;
import java.util.TreeMap;

/**
 * Classe para registrar as mídias assistidas por um cliente por período.
 */
public class ArvoreAnosMeses {
    private TreeMap<Integer, TreeMap<Integer, Integer[]>> arvore;

    /**
     * Construtor que inicializa a árvore vazia.
     */
    public ArvoreAnosMeses() {
        arvore = new TreeMap<>();
    }

    /**
     * Insere um id de uma mídia em um determinado ano e mês na árvore.
     *
     * @param anoRef  O ano a ser inserido.
     * @param mesRef  O mês a ser inserido.
     * @param idMidia O id da série a ser inserido no período.
     * @throws InvalidParameterException se algum paramentro é inválido
     */
    public void inserirValor(int anoRef, int mesRef, int idMidia) throws InvalidParameterException {
        if (mesRef < 1 || mesRef > 12) {
            throw new InvalidParameterException("Valor do mês inválido");
        }

        // se a árvore não contem o ano, então é criado.
        if (!arvore.containsKey(anoRef)) {
            TreeMap<Integer, Integer[]> mesDoAno = new TreeMap<>();
            Integer[] arrayDeSeriesAssistidas = new Integer[1];
            arrayDeSeriesAssistidas[0] = idMidia;
            mesDoAno.put(mesRef, arrayDeSeriesAssistidas);
            arvore.put(anoRef, mesDoAno);
        } else {
            TreeMap<Integer, Integer[]> mesDoAno = arvore.get(anoRef);
            Integer[] arrayDeSeriesAssistidas = mesDoAno.get(mesRef);
            // Verifica se o array é nulo ou vazio
            if (arrayDeSeriesAssistidas == null) {
                // Cria um novo array com um elemento
                arrayDeSeriesAssistidas = new Integer[1];
                arrayDeSeriesAssistidas[0] = idMidia;
            } else {
                // Cria um novo array com um tamanho maior
                Integer[] novoArray = new Integer[arrayDeSeriesAssistidas.length + 1];

                // Copia os elementos existentes para o novo array
                System.arraycopy(arrayDeSeriesAssistidas, 0, novoArray, 0, arrayDeSeriesAssistidas.length);

                // Adiciona o novo item na última posição do novo array
                novoArray[novoArray.length - 1] = idMidia;

                // Atualiza a referência para o novo array
                mesDoAno.put(mesRef, novoArray);
                arvore.put(anoRef, mesDoAno);
            }
        }
    }

    /**
     * Obtém o valor associado a um determinado ano e mês na árvore.
     * Essa função serve para mostrar a quantidade de mídias que um usuário assistiu em 
     * um tempo especificado.
     *
     * @param ano O ano a ser buscado.
     * @param mes O mês a ser buscado.
     * @return O valor inteiro associado ao mês especificado. Se o ano ou mês não
     *         existirem na árvore, retorna 0.
     */
    public Integer[] obterValor(int ano, int mes) throws NullPointerException {
        if (arvore.containsKey(ano)) {
            TreeMap<Integer, Integer[]> meses = arvore.get(ano);
            if (meses.containsKey(mes)) {
                return meses.get(mes);
            }
        }
        return null;
    }

    /**
     * Imprime a árvore mostrando a quantidade de inteiros por mês.
     * Essa função serve para mostrar quantas mídias o unuário assistiu ao longo dos anos
     */
    public void imprimirArvore() {
        for (Integer ano : arvore.keySet()) {
            System.out.println("Ano " + ano + ":");
            TreeMap<Integer, Integer[]> meses = arvore.get(ano);
            for (Integer mes : meses.keySet()) {
                Integer[] quantidade = meses.get(mes);
                System.out.println("  Mês " + mes + ": " + quantidade.length);
            }
        }
    }
}

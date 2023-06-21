import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Relatorio {

    public static void contabilizarClienteMaisMidias() {
        Map<String, Integer> clientes = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("assets/Audiencia.csv"))) {
            String linha;
            br.readLine(); // Ignora a primeira linha (cabeçalho)

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                String cliente = dados[0];
                String tipoMidia = dados[1];

                if (tipoMidia.equals("A")) {
                    clientes.put(cliente, clientes.getOrDefault(cliente, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String clienteMaisMidias = "";
        int maxMidias = 0;

        for (Map.Entry<String, Integer> entry : clientes.entrySet()) {
            String cliente = entry.getKey();
            int midiasAssistidas = entry.getValue();

            if (midiasAssistidas > maxMidias) {
                clienteMaisMidias = cliente;
                maxMidias = midiasAssistidas;
            }
        }

        System.out.println("Relatório: Cliente que assistiu mais mídias");
        System.out.println("Cliente: " + clienteMaisMidias);
        System.out.println("Quantidade de mídias assistidas: " + maxMidias);
    }

    public static void relatorioClienteMaisAvaliacoes() {
        Map<String, Integer> clientes = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("assets/Avaliacoes.csv"))) {
            String linha;
            br.readLine(); // Ignora a primeira linha (cabeçalho)

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                String cliente = dados[0];

                clientes.put(cliente, clientes.getOrDefault(cliente, 0) + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String clienteMaisAvaliacoes = "";
        int maxAvaliacoes = 0;

        for (Map.Entry<String, Integer> entry : clientes.entrySet()) {
            String cliente = entry.getKey();
            int avaliacoes = entry.getValue();

            if (avaliacoes > maxAvaliacoes) {
                clienteMaisAvaliacoes = cliente;
                maxAvaliacoes = avaliacoes;
            }
        }

        System.out.println("Relatório: Cliente com mais avaliações");
        System.out.println("Cliente: " + clienteMaisAvaliacoes);
        System.out.println("Número de avaliações: " + maxAvaliacoes);
    }

    public static void relatorioPorcentagemClientesMais15Avaliacoes() {
        Map<String, Integer> clientes = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("assets/Avaliacoes.csv"))) {
            String linha;
            br.readLine(); // Ignora a primeira linha (cabeçalho)

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                String cliente = dados[0];

                clientes.put(cliente, clientes.getOrDefault(cliente, 0) + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int totalClientes = clientes.size();
        int clientesMais15Avaliacoes = 0;

        for (int avaliacoes : clientes.values()) {
            if (avaliacoes >= 15) {
                clientesMais15Avaliacoes++;
            }
        }

        double porcentagem = (double) clientesMais15Avaliacoes / totalClientes * 100;

        System.out.println("Relatório: Porcentagem de clientes com pelo menos 15 avaliações");
        System.out.println("Porcentagem: " + porcentagem);
    }

    /**
     * Calcula o relatório das 10 mídias com melhor avaliação, considerando apenas aquelas com pelo menos 100 avaliações.
     *
     * @return
     */
    public static void calculaRelatorio4() {
        String nomeArquivo = "assets/Avaliacoes.csv";
        int minAvaliacoes = 100;
        int numTopMidias = 10;

        Map<String, Integer> midiasAvaliacoes = new HashMap<>();
        Map<String, Integer> midiasQuantidades = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            br.readLine(); // Ignorar a primeira linha (cabeçalho)

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                String midiaId = dados[1];
                int avaliacao = Integer.parseInt(dados[2]);

                midiasAvaliacoes.put(midiaId, midiasAvaliacoes.getOrDefault(midiaId, 0) + avaliacao);
                midiasQuantidades.put(midiaId, midiasQuantidades.getOrDefault(midiaId, 0) + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        PriorityQueue<String> topMidias = new PriorityQueue<>(
                (midia1, midia2) -> (int) (getMediaAvaliacoes(midia2, midiasAvaliacoes, midiasQuantidades)
                        - getMediaAvaliacoes(midia1, midiasAvaliacoes, midiasQuantidades)));

        for (String midiaId : midiasAvaliacoes.keySet()) {
            int totalAvaliacoes = midiasQuantidades.get(midiaId);

            if (totalAvaliacoes >= minAvaliacoes) {
                topMidias.offer(midiaId);

                if (topMidias.size() > numTopMidias) {
                    topMidias.poll();
                }
            }
        }

        System.out.println("As 10 mídias de melhor avaliação (com pelo menos 100 avaliações):");
        while (!topMidias.isEmpty()) {
            String midiaId = topMidias.poll();
            double mediaAvaliacoes = getMediaAvaliacoes(midiaId, midiasAvaliacoes, midiasQuantidades);
            System.out.println("Mídia: " + midiaId + ", Média de Avaliações: " + mediaAvaliacoes);
        }
    }

    /**
     * Obtém a média de avaliações para uma determinada mídia.
     *
     * @param midiaId            ID da mídia
     * @param midiasAvaliacoes   Mapa que armazena as avaliações das mídias
     * @param midiasQuantidades  Mapa que armazena a quantidade de avaliações das mídias
     * @return a média de avaliações da mídia especificada
     */
    private static double getMediaAvaliacoes(String midiaId, Map<String, Integer> midiasAvaliacoes,
            Map<String, Integer> midiasQuantidades) {
        int somaAvaliacoes = midiasAvaliacoes.getOrDefault(midiaId, 0);
        int totalAvaliacoes = midiasQuantidades.getOrDefault(midiaId, 0);
        return (double) somaAvaliacoes / totalAvaliacoes;
    }

    /**
     * Calcula o relatório das 10 mídias com mais visualizações, considerando apenas aquelas cuja letra é 'A'.
     *
     * @return
     */
    public static void relatorio5() {
        String nomeArquivo = "assets/Audiencia.csv";
        int numTopMidias = 10;

        Map<String, Integer> midiasVisualizacoes = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(";");
                String midiaId = dados[2];
                String letra = dados[1];

                if (letra.equals("A")) {
                    midiasVisualizacoes.put(midiaId, midiasVisualizacoes.getOrDefault(midiaId, 0) + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        PriorityQueue<Map.Entry<String, Integer>> topMidias = new PriorityQueue<>(
                (m1, m2) -> m2.getValue().compareTo(m1.getValue()));

        for (Map.Entry<String, Integer> entry : midiasVisualizacoes.entrySet()) {
            topMidias.offer(entry);

            if (topMidias.size() > numTopMidias) {
                topMidias.poll();
            }
        }

        System.out.println("As 10 mídias com mais visualizações:");
        while (!topMidias.isEmpty()) {
            Map.Entry<String, Integer> entry = topMidias.poll();
            String midiaId = entry.getKey();
            int visualizacoes = entry.getValue();
            System.out.println("Mídia: " + midiaId + ", Visualizações: " + visualizacoes);
        }
    }

}

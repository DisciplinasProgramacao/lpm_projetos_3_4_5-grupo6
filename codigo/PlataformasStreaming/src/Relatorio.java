import java.util.*;

public class Relatorio {

    public static void obterClienteComMaisMidias(HashMap<String, Cliente> clientes) {
        Cliente clienteComMaisMidias = null;
        int maiorQuantidade = 0;

        for (Cliente cliente : clientes.values()) {
            int quantidadeMidiasAssistidas = cliente.getListaJaVistas().size();

            if (quantidadeMidiasAssistidas > maiorQuantidade) {
                maiorQuantidade = quantidadeMidiasAssistidas;
                clienteComMaisMidias = cliente;
            }
        }

        int quantidadeMidiasAssistidas = clienteComMaisMidias.getListaJaVistas().size();
        System.out.println("O cliente que assistiu mais mídias é: " + clienteComMaisMidias.getNome());
        System.out.println("Quantidade de mídias assistidas: " + quantidadeMidiasAssistidas);
    }

    public static void gerarRelatorioAvaliacoes(Map<Integer, Midia> hashMidias) {
        Map<String, Integer> avaliacoesPorCliente = new HashMap<>();

        // Percorre todas as mídias e suas avaliações
        for (Map.Entry<Integer, Midia> entry : hashMidias.entrySet()) {
            Midia midia = entry.getValue();
            List<Avaliacao> avaliacoes = midia.getTodasAvaliacoes();

            // Atualiza a contagem de avaliações para cada cliente
            for (Avaliacao avaliacao : avaliacoes) {
                String cliente = avaliacao.getLogin();
                avaliacoesPorCliente.put(cliente, avaliacoesPorCliente.getOrDefault(cliente, 0) + 1);
            }
        }

        String clienteComMaisAvaliacoes = null;
        int quantidadeAvaliacoes = 0;

        // Encontra o cliente com mais avaliações
        for (Map.Entry<String, Integer> entry : avaliacoesPorCliente.entrySet()) {
            String cliente = entry.getKey();
            int avaliacoes = entry.getValue();

            if (avaliacoes > quantidadeAvaliacoes) {
                clienteComMaisAvaliacoes = cliente;
                quantidadeAvaliacoes = avaliacoes;
            }
        }

        System.out.println("Cliente com mais avaliações: " + clienteComMaisAvaliacoes);
        System.out.println("Quantidade de avaliações: " + quantidadeAvaliacoes);
    }

    public static void gerarRelatorioMelhoresAvaliacoes(HashMap<Integer, Midia> midias) {
        Map<Integer, Integer> midiaAvaliacoes = new HashMap<>();
        Map<Integer, Double> midiaPontuacoes = new HashMap<>();

        for (Map.Entry<Integer, Midia> entry : midias.entrySet()) {
            Midia midia = entry.getValue();
            List<Avaliacao> avaliacoes = midia.getTodasAvaliacoes();

            for (Avaliacao avaliacao : avaliacoes) {
                int midiaId = midia.getId();
                int totalAvaliacoes = midiaAvaliacoes.getOrDefault(midiaId, 0) + 1;
                double somaPontuacoes = midiaPontuacoes.getOrDefault(midiaId, 0.0) + avaliacao.getPontuacao();

                midiaAvaliacoes.put(midiaId, totalAvaliacoes);
                midiaPontuacoes.put(midiaId, somaPontuacoes);
            }
        }

        List<Integer> midiasComMinimoAvaliacoes = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : midiaAvaliacoes.entrySet()) {
            int midiaId = entry.getKey();
            int totalAvaliacoes = entry.getValue();
            if (totalAvaliacoes >= 1) {
                midiasComMinimoAvaliacoes.add(midiaId);
            }
        }

        Collections.sort(midiasComMinimoAvaliacoes, new Comparator<Integer>() {
            @Override
            public int compare(Integer midia1, Integer midia2) {
                double pontuacao1 = midiaPontuacoes.get(midia1) / midiaAvaliacoes.get(midia1);
                double pontuacao2 = midiaPontuacoes.get(midia2) / midiaAvaliacoes.get(midia2);
                return Double.compare(pontuacao2, pontuacao1);
            }
        });

        System.out.println("As 10 mídias de melhor avaliação com pelo menos 100 avaliações:");

        int count = 0;
        for (Integer midia : midiasComMinimoAvaliacoes) {
            if (count == 10) {
                break;
            }
            Midia midiaObj = midias.get(midia);
            double pontuacaoMedia = midiaPontuacoes.get(midia) / midiaAvaliacoes.get(midia);
            System.out.println("Mídia #" + midia + " - Nome: " + midiaObj.getNome() + " - Pontuação média: " + pontuacaoMedia);
            count++;
        }
    }

    public static void imprimirTop10MidiasMaisAudiencia(HashMap<Integer, Midia> midias) {
        // Filtrar as mídias com audiência maior que 0
        List<Midia> midiasComAudiencia = new ArrayList<>();
        for (Map.Entry<Integer, Midia> entry : midias.entrySet()) {
            Midia midia = entry.getValue();
            if (midia.getAudiencia() > 0) {
                midiasComAudiencia.add(midia);
            }
        }

        // Ordenar as mídias com base na audiência (em ordem decrescente)
        Collections.sort(midiasComAudiencia, new Comparator<Midia>() {
            @Override
            public int compare(Midia m1, Midia m2) {
                return Integer.compare(m2.getAudiencia(), m1.getAudiencia());
            }
        });

        // Obter as 10 primeiras mídias
        int tamanhoRelatorio = Math.min(midiasComAudiencia.size(), 10);
        List<Midia> top10Midias = midiasComAudiencia.subList(0, tamanhoRelatorio);

        // Imprimir as top 10 mídias
        System.out.println("Top 10 Mídias com Mais Audiência:");
        for (int i = 0; i < top10Midias.size(); i++) {
            Midia midia = top10Midias.get(i);
            System.out.println((i + 1) + ". " + midia.getNome() + " - Audiência: " + midia.getAudiencia());
        }
    }
}
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Relatorio {

    public void relatoriosPorParametro(int relatorio) {

        String caminho;
        if (relatorio == 1) {
            caminho = "assets/Audiencia.csv";
        } else {
            caminho = "assets/Avaliacoes.csv";
        }

        Map<String, Integer> coluna1 = new HashMap<>();
        Map<String, Integer> coluna3 = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue; // Ignora a primeira linha
                }

                String[] valores = linha.split(";");
                if (valores.length >= 3) {
                    String nome = valores[0];
                    String idMidia = valores[1];
                    int avaliacao = Integer.parseInt(valores[2]);

                    int contagemNomesAtual = coluna1.getOrDefault(nome, 0);
                    coluna1.put(nome, contagemNomesAtual + 1);

                    int contagemAvaliacoesAtual = coluna3.getOrDefault(nome, 0);
                    coluna3.put(nome, contagemAvaliacoesAtual + avaliacao);
                }
            }

            String nomeMaisAssistiu = "";
            int maxAssistido = 0;

            for (Map.Entry<String, Integer> entry : coluna1.entrySet()) {
                String nome = entry.getKey();
                int frequencia = entry.getValue();

                if (frequencia > maxAssistido) {
                    nomeMaisAssistiu = nome;
                    maxAssistido = frequencia;
                }
            }

            String nomeMaisAvaliou = "";
            int maxAvaliou = 0;

            for (Map.Entry<String, Integer> entry : coluna3.entrySet()) {
                String nome = entry.getKey();
                int totalAvaliacoes = entry.getValue();

                if (totalAvaliacoes > maxAvaliou) {
                    nomeMaisAvaliou = nome;
                    maxAvaliou = totalAvaliacoes;
                }
            }

            double porcentagem = (double) coluna1.entrySet().stream()
                    .filter(entry -> entry.getValue() > 15)
                    .count() / (coluna1.size() - 1) * 100; // Subtrai 1 para excluir a primeira linha

            if (relatorio == 1) {
                System.out.println("O cliente que mais assistiu foi: " + nomeMaisAssistiu + " (" + maxAssistido
                        + " Mídias assistidas).");
            } else if (relatorio == 2) {
                System.out.println(
                        "O cliente que mais avaliou foi: " + nomeMaisAvaliou + " (" + maxAssistido + " avaliações).");
            } else if (relatorio == 3) {
                System.out.println("Porcentagem de clientes que avaliaram mais de 15x " + porcentagem + "%");
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    public void relatorioMediaAvaliacao() {
        String caminho = "assets/Avaliacao.csv";

        Map<String, List<Integer>> avaliacoesPorMidia = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(caminho))) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue; // Ignora a primeira linha
                }

                String[] valores = linha.split(";");
                if (valores.length >= 3) {
                    String nome = valores[0];
                    String idMidia = valores[1];
                    int avaliacao = Integer.parseInt(valores[2]);

                    List<Integer> listaAvaliacoes = avaliacoesPorMidia.getOrDefault(idMidia, new ArrayList<>());
                    listaAvaliacoes.add(avaliacao);
                    avaliacoesPorMidia.put(idMidia, listaAvaliacoes);
                }
            }

            List<Map.Entry<String, Double>> mediasAvaliacoes = new ArrayList<>();

            for (Map.Entry<String, List<Integer>> entry : avaliacoesPorMidia.entrySet()) {
                String idMidia = entry.getKey();
                List<Integer> listaAvaliacoes = entry.getValue();

                double media = calcularMediaAvaliacoes(listaAvaliacoes);
                mediasAvaliacoes.add(new AbstractMap.SimpleEntry<>(idMidia, media));
            }

            // Ordena as médias de avaliação em ordem decrescente
            Collections.sort(mediasAvaliacoes, (a, b) -> Double.compare(b.getValue(), a.getValue()));

            // Exibe os valores até o décimo item da lista
            int limite = Math.min(10, mediasAvaliacoes.size());
            for (int i = 0; i < limite; i++) {
                Map.Entry<String, Double> entry = mediasAvaliacoes.get(i);
                String idMidia = entry.getKey();
                double media = entry.getValue();

                System.out.println("ID da Mídia: " + idMidia + ", Média de Avaliação: " + media);
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    private static double calcularMediaAvaliacoes(List<Integer> avaliacoes) {
        if (avaliacoes.isEmpty()) {
            return 0.0;
        }

        int soma = 0;
        for (int avaliacao : avaliacoes) {
            soma += avaliacao;
        }

        return (double) soma / avaliacoes.size();
    }
}

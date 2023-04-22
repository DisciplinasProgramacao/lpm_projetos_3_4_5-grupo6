import java.util.HashMap;
import java.util.LinkedList;

import javax.naming.NameNotFoundException;

public class PlataformaStreaming {
    private String nome;
    private HashMap<Integer, Serie> series;
    private HashMap<String, Cliente> clientes;
    private Cliente clienteAtual;

    public Cliente login(String nomeUsuario, String senha) throws NameNotFoundException {
        for (HashMap.Entry<String, Cliente> cl : this.clientes.entrySet()) {
            Cliente cliente = cl.getValue();

            if(cliente.getNomeDeUsuario().equals(nomeUsuario) && cliente.getSenha().equals(senha)) {
                this.clienteAtual = cliente;
                return cliente;
            }
        }

        throw new NameNotFoundException("Usuário não encontrado");
    }

    public void adicionarSerie(Serie serie) {
        this.series.put(serie.getId(), serie);
    }

    public void adicionarCliente(Cliente cliente) {
        this.clientes.put(cliente.getNomeDeUsuario(), cliente);
    }

    public LinkedList<Serie> filtrarPorGenero(String genero) {
        LinkedList<Serie> seriesEncontradas = new LinkedList<>();
        for (HashMap.Entry<Integer, Serie> sr : this.series.entrySet()) {
            Serie serie = sr.getValue();
            if(serie.getGenero().equals(genero)) {
                seriesEncontradas.add(serie);
            }
        }
        return seriesEncontradas;
    }

    public LinkedList<Serie> filtrarPorIdioma(String idioma) {
        LinkedList<Serie> seriesEncontradas = new LinkedList<>();
        for (HashMap.Entry<Integer, Serie> sr : this.series.entrySet()) {
            Serie serie = sr.getValue();
            if(serie.getIdioma().equals(idioma)) {
                seriesEncontradas.add(serie);
            }
        }
        return seriesEncontradas;
    }

    public LinkedList<Serie> filtrarPorQtdEpisodios(int quantEpisodios) {
        LinkedList<Serie> seriesEncontradas = new LinkedList<>();
        for (HashMap.Entry<Integer, Serie> sr : this.series.entrySet()) {
            Serie serie = sr.getValue();
            if(serie.getQuantidadeEpisodios().equals(quantEpisodios)) {
                seriesEncontradas.add(serie);
            }
        }
        return seriesEncontradas;
    }

    public void registrarAudiencia(Serie serie) {
        serie.registrarAudiencia();
    }

    public void logoff() {
        this.clienteAtual = null;
    }

    public Serie buscarSerie(String nomeSerie) {
        for (HashMap.Entry<Integer, Serie> sr : this.series.entrySet()) {
            Serie serie = sr.getValue();
            if(serie.getName().equals(nomeSerie)) {
                return serie;
            }
        }
        return null;
    }

    public Cliente getClienteAtual() {
        return clienteAtual;
    }

    public String getNome() {
        return nome;
    }
}

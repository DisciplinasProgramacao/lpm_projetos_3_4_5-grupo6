import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

import javax.naming.NameNotFoundException;

public class PlataformaStreaming {
  private String nome;
  private HashMap<Integer, Serie> series;
  private HashMap<String, Cliente> clientes;
  private Cliente clienteAtual;

  public PlataformaStreaming(String nome) {
    this.nome = nome;
    series = new HashMap<>();
    clientes = new HashMap<>();
  }

  // #region Métodos
  public Cliente login(String nomeUsuario, String senha) throws NameNotFoundException {
    for (HashMap.Entry<String, Cliente> cl : this.clientes.entrySet()) {
      Cliente cliente = cl.getValue();
      if (cliente.getLogin().equals(nomeUsuario) && cliente.senhaCorreta(senha)) {
        this.clienteAtual = cliente;
        return cliente;
      }
    }
    throw new NameNotFoundException("Usuário não encontrado");
  }

  public void adicionarSerie(Serie serie) {
    if (!series.containsValue(serie))
      this.series.put(serie.getId(), serie);
  }

  public void carregarSeries() {
    try {
      Scanner scanner = new Scanner(Utilitarios.caminho("Series.csv").toFile());
      while (scanner.hasNext())
        adicionarSerie(new Serie(scanner.nextLine()));
      scanner.close();
    } catch (FileNotFoundException e) {
      System.out.println(e);
    }
  }

  public void adicionarCliente(Cliente cliente) {
    this.clientes.put(cliente.getLogin(), cliente);
  }

  public void carregarClientes() {
    try {
      Scanner scanner = new Scanner(Utilitarios.caminho("Espectadores.csv").toFile());
      while (scanner.hasNext())
        adicionarCliente(new Cliente(scanner.nextLine()));
      scanner.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void carregarAudiencias()  {
    try {
      Scanner scanner = new Scanner(Utilitarios.caminho("Audiencia.csv").toFile());
      while (scanner.hasNext()) {
        String[] parametrosAudiencia = scanner.nextLine().split(";");
        String nomeUsuario = parametrosAudiencia[0];
        String listaUsuario = parametrosAudiencia[1];
        int idSerie = Integer.parseInt(parametrosAudiencia[2]);

        if (listaUsuario.equals("F"))
          clientes.get(nomeUsuario).adicionarNaLista(series.get(idSerie));
        else if (listaUsuario.equals("A"))
          clientes.get(nomeUsuario).registrarAudiencia(series.get(idSerie));
      }
      scanner.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public LinkedList<Serie> filtrarPorGenero(String genero) {
    LinkedList<Serie> seriesEncontradas = new LinkedList<>();
    for (HashMap.Entry<Integer, Serie> sr : this.series.entrySet()) {
      Serie serie = sr.getValue();
      if (serie.getGenero().equals(genero)) {
        seriesEncontradas.add(serie);
      }
    }
    return seriesEncontradas;
  }

  public LinkedList<Serie> filtrarPorIdioma(String idioma) {
    LinkedList<Serie> seriesEncontradas = new LinkedList<>();
    for (HashMap.Entry<Integer, Serie> sr : this.series.entrySet()) {
      Serie serie = sr.getValue();
      if (serie.getIdioma().equals(idioma)) {
        seriesEncontradas.add(serie);
      }
    }
    return seriesEncontradas;
  }

  public LinkedList<Serie> filtrarPorQtdEpisodios(int quantEpisodios) {
    LinkedList<Serie> seriesEncontradas = new LinkedList<>();
    for (HashMap.Entry<Integer, Serie> sr : this.series.entrySet()) {
      Serie serie = sr.getValue();
      if (serie.getQuantidadeEpisodios() == quantEpisodios) {
        seriesEncontradas.add(serie);
      }
    }
    return seriesEncontradas;
  }

  public void registrarAudiencia(Serie serie) {
    this.clienteAtual.registrarAudiencia(serie);
  }

  public void logoff() {
    this.clienteAtual = null;
  }

  public Serie buscarSerie(String nomeSerie) {
    for (HashMap.Entry<Integer, Serie> sr : this.series.entrySet()) {
      Serie serie = sr.getValue();
      if (serie.getNome().equals(nomeSerie)) {
        return serie;
      }
    }
    return null;
  }

  public int quantidadeSeries() {
    return series.size();
  }
  // #endregion

  // #region Getters
  public Cliente getClienteAtual() {
    return clienteAtual;
  }

  public String getNome() {
    return nome;
  }
  // #endregion
}

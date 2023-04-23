import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Cliente {
  private String nome;
  private String login;
  private String senha;
  private List<Serie> listaParaVer;
  private List<Serie> listaJaVistas;

  private void init(String nome, String login, String senha) {
    if (nome.length() < 1 || nome == null) {
      throw new InvalidParameterException("Erro ao criar cliente: O nome deve ter pelo menos um caractere!");
    }
    if (login.length() < 1 || login == null) {
      throw new InvalidParameterException("Erro ao criar cliente: O login deve ter pelo menos um caractere!");
    }
    if (senha.length() < 1 || senha == null) {
      throw new InvalidParameterException("Erro ao criar cliente: A senha deve ter pelo menos um caractere!");
    }
    this.nome = nome;
    this.login = login;
    this.senha = senha;
    listaParaVer = new ArrayList<Serie>();
    listaJaVistas = new ArrayList<Serie>();
  }

  public Cliente(String parametros) {
    String[] listaParametros = parametros.split(";");
    if (listaParametros.length != 3)
      throw new InvalidParameterException("Erro ao criar cliente: Não foram informados todos os campos!");
    String nome = listaParametros[0];
    String login = listaParametros[1];
    String senha = listaParametros[2];
    init(nome, login, senha);
  }

  // #region Métodos
  public void adicionarNaLista(Serie serie) {
    if (!this.listaParaVer.contains(serie)) {
      listaParaVer.add(serie);
    }
  }

  public void retirarDaLista(String nomeSerie) {
    for (Serie serie : listaParaVer) {
      if (serie.getName().equals(nomeSerie)) {
        listaParaVer.remove(serie);
        break;
      }
    }
  }

  public List<Serie> filtrarPorGenero(String genero) {
    List<Serie> filtroPorGenero = new ArrayList<Serie>();
    for (Serie serie : listaParaVer) {
      if (serie.getGenero().equals(genero)) {
        filtroPorGenero.add(serie);
      }
    }
    for (Serie serie : listaJaVistas) {
      if (serie.getGenero().equals(genero)) {
        filtroPorGenero.add(serie);
      }
    }
    return filtroPorGenero;
  }

  public List<Serie> filtrarPorIdioma(String idioma) {
    List<Serie> filtroPorIdioma = new ArrayList<Serie>();
    for (Serie serie : listaParaVer) {
      if (serie.getIdioma().equals(idioma)) {
        filtroPorIdioma.add(serie);
      }
    }
    for (Serie serie : listaJaVistas) {
      if (serie.getIdioma().equals(idioma)) {
        filtroPorIdioma.add(serie);
      }
    }
    return filtroPorIdioma;
  }

  public List<Serie> FiltrarPorQtdEpisodios(int quantEpisodios) {
    List<Serie> filtroPorEp = new ArrayList<Serie>();
    for (Serie serie : listaParaVer) {
      if (serie.getQuantidadeEpisodios().equals(quantEpisodios)) {
        filtroPorEp.add(serie);
      }
    }
    for (Serie serie : listaJaVistas) {
      if (serie.getQuantidadeEpisodios().equals(quantEpisodios)) {
        filtroPorEp.add(serie);
      }
    }
    return filtroPorEp;
  }

  public boolean senhaCorreta(String senha) {
    return this.senha.equals(senha);
  }

  public void registrarAudiencia(Serie serie) {
    this.listaJaVistas.add(serie);
    serie.registrarAudiencia();
  }
  // #endregion

  // #region Getters
  public String getLogin() {
    return login;
  }

  public String getNome() {
    return nome;
  }

  public List<Serie> getListaParaVer() {
    return listaParaVer;
  }

  public List<Serie> getListaJaVistas() {
    return listaJaVistas;
  }
  // #endregion
}

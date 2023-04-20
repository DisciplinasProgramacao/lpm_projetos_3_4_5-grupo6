import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

public class Cliente {

    private String nomeDeUsuario;
    private String senha;

    private List<Serie> listaParaVer;
    private List<Serie> listaJaVistas;

    public Cliente(String nomeDeUsuario, String senha) {
        this.nomeDeUsuario = nomeDeUsuario;
        this.senha = senha;
        listaParaVer = new ArrayList<Serie>();
        listaJaVistas = new ArrayList<Serie>();
    }

    public void adicionarNaLista(Serie serie){
        listaParaVer.add(serie);
    }

    public void retirarDaLista(String nomeSerie){
        for(Serie serie : listaParaVer){
            if (serie.getName().equals(nomeSerie)){
                listaParaVer.remove(serie);
                break;
            }
        }
    }

    public List<Serie> filtrarPorGenero(String genero){
        List<Serie> filtroPorGenero = new ArrayList<Serie>();
        for (Serie serie : listaParaVer){
            if (serie.getGenero().equals(genero)){
                filtroPorGenero.add(serie);
            }
        }

        for (Serie serie : listaJaVistas){
            if (serie.getGenero().equals(genero)){
//Fazer assim que tiver o id da serie                if (serie.hashCode())
                filtroPorGenero.add(serie);
            }
        }

        return filtroPorGenero;
    }

    public List<Serie> filtrarPorIdioma(String idioma){
        List<Serie> filtroPorIdioma = new ArrayList<Serie>();
        for (Serie serie : listaParaVer){
            if (serie.getIdioma().equals(idioma)){
                filtroPorIdioma.add(serie);
            }
        }

        for (Serie serie : listaJaVistas){
            if (serie.getIdioma().equals(idioma)){
//Fazer assim que tiver o id da serie                if (serie.hashCode())
                filtroPorIdioma.add(serie);
            }
        }

        return filtroPorIdioma;
    }

    public List<Serie> FiltrarPorQtdEpisodios(int quantEpisodios){
        List<Serie> filtroPorEp = new ArrayList<Serie>();
        for (Serie serie : listaParaVer){
            if (serie.getQuantidadeEpisodios().equals(quantEpisodios)){
                filtroPorEp.add(serie);
            }
        }

        for (Serie serie : listaJaVistas){
            if (serie.getQuantidadeEpisodios().equals(quantEpisodios)){
//Fazer assim que tiver o id da serie                if (serie.hashCode())
                filtroPorEp.add(serie);
            }
        }

        return filtroPorEp;
    }

    public void registrarAudiencia(Serie serie){
        serie.registrarAudiencia();
    }

    public String getNomeDeUsuario() {
        return nomeDeUsuario;
    }

    public String getSenha() {
        return senha;
    }
}
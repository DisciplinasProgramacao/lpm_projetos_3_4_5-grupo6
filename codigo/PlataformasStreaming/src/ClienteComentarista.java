import java.time.LocalDate;

public class ClienteComentarista  implements IClienteComentarista{

    @Override
    public boolean verificarTipoCliente(ArvoreAnosMeses arvore) {
        LocalDate now = LocalDate.now();
        int anoDoMesAtual = now.getYear();
        int mesAtual = now.getMonthValue();
        int mesPassado;
        int anoDoMesPassado;

        if (mesAtual == 1) {
            mesPassado = 12;
            anoDoMesPassado = anoDoMesAtual - 1;
        } else {
            mesPassado = mesAtual - 1;
            anoDoMesPassado = anoDoMesAtual;
        }

        int totalMidiaAssistidaMesAtual;
        int totalMidiaAssistidaMesPassado;

        try {
            totalMidiaAssistidaMesAtual = arvore.obterValor(anoDoMesAtual, mesAtual).length;

        } catch (NullPointerException e) {
            totalMidiaAssistidaMesAtual = 0;
        }

        try {
            totalMidiaAssistidaMesPassado = arvore.obterValor(anoDoMesPassado,
                    mesPassado).length;
        } catch (NullPointerException e) {
            totalMidiaAssistidaMesPassado = 0;
        }

        if (totalMidiaAssistidaMesAtual >= 5 || totalMidiaAssistidaMesPassado >= 5) {
            return true;
        } else {
            return false;
        }
    }
    
}

package Exceptions;
/**
 * Exceção que é lançada quando uma senha é considerada fraca.
 */
public class SenhaFracaException extends Exception {

    /**
     * Construtor que recebe uma mensagem de erro e a repassa para a superclasse Exception.
     *
     * @param message A mensagem de erro a ser repassada.
     */
    public SenhaFracaException(String message) {
        super(message);
    }

}
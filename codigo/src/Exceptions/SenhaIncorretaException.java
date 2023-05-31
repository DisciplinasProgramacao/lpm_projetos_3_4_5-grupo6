package Exceptions;

/**
 * 
 * Exceção que é lançada quando uma senha fornecida é considerada incorreta.
 */
public class SenhaIncorretaException extends Exception {

    /**
     * Recebe uma mensagem de erro e a repassa para a superclasse Exception quando a senha é incorreta.
     *
     * @param message A mensagem de erro a ser repassada.
     */
    public SenhaIncorretaException(String message) {
        super(message);
    }
}
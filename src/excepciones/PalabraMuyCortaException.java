package excepciones;

/**
 * Excepción personalizada que se lanza cuando la palabra que está en
 * la posición seleccionada por el usuario tiene menos de 4 letras.
 *
 * Igual que PalabraNoEncontradaException, es una regla propia del
 * programa (no un error del sistema), por eso se lanza con throw.
 *
 * Desarrollado por: Valentina Solano.
 */
public class PalabraMuyCortaException extends Exception {

    /**
     * Constructor que recibe el mensaje de error y lo pasa
     * a la clase padre Exception mediante super(mensaje).
     *
     * @param mensaje descripción del error
     */
    public PalabraMuyCortaException(String mensaje) {
        super(mensaje);
    }
}

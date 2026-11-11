package excepciones;

/**
 * Excepción personalizada ENCADENADA que representa el intento de
 * acceder a una posición que no existe dentro del vector del poema.
 *
 * Envuelve la ArrayIndexOutOfBoundsException original del sistema,
 * que queda guardada como causa y se puede consultar con getCause().
 *
 * Desarrollado por: Valentina Solano.
 */
public class PosicionPoemaInvalidaException extends Exception {

    /**
     * Constructor que solo recibe el mensaje de error.
     *
     * @param mensaje descripción del error
     */
    public PosicionPoemaInvalidaException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor que recibe el mensaje y la excepción original.
     * Con super(mensaje, causa) se conserva la referencia a la
     * ArrayIndexOutOfBoundsException que produjo el error.
     *
     * @param mensaje descripción del error
     * @param causa   excepción original del sistema (ArrayIndexOutOfBoundsException)
     */
    public PosicionPoemaInvalidaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

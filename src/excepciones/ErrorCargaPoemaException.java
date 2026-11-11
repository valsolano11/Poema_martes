package excepciones;

/**
 * Excepción personalizada ENCADENADA que representa un error al cargar
 * el poema desde el archivo de texto.
 *
 * Se usa para "envolver" la excepción original del sistema
 * (FileNotFoundException o IOException) sin perderla: la excepción
 * original queda guardada como causa y se puede consultar con getCause().
 *
 * Desarrollado por: Valentina Solano.
 */
public class ErrorCargaPoemaException extends Exception {

    /**
     * Constructor que solo recibe el mensaje de error.
     *
     * @param mensaje descripción del error
     */
    public ErrorCargaPoemaException(String mensaje) {
        super(mensaje);
    }

    /**
     * Constructor que recibe el mensaje y la excepción original.
     * Nota para el profesor: con super(mensaje, causa) se conserva la
     * excepción del sistema dentro de esta excepción propia; esto es lo
     * que permite el encadenamiento de excepciones (Exception Chaining).
     *
     * @param mensaje descripción del error
     * @param causa   excepción original del sistema (IOException / FileNotFoundException)
     */
    public ErrorCargaPoemaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}

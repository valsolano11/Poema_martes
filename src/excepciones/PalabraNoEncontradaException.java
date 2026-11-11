package excepciones;

/**
 * Excepción personalizada que se lanza cuando el usuario busca
 * una palabra que no existe dentro del vector del poema.
 *
 * Es una excepción "lanzada directamente por el programador":
 * Java no sabe que una palabra no encontrada es un error, por eso
 * soy yo quien decide lanzarla con throw desde la clase Poema.
 *
 * Desarrollado por: Valentina Solano.
 */
public class PalabraNoEncontradaException extends Exception {

    /**
     * Constructor que recibe el mensaje de error.
     * Con super(mensaje) se envía el mensaje a la clase padre (Exception),
     * así después se puede consultar con getMessage().
     *
     * @param mensaje descripción del error
     */
    public PalabraNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}

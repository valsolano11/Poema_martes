package modelo;

import excepciones.ErrorCargaPoemaException;
import excepciones.PalabraMuyCortaException;
import excepciones.PalabraNoEncontradaException;
import excepciones.PosicionPoemaInvalidaException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Clase Poema.
 * Se encarga de leer el archivo de texto, limpiar su contenido, contar
 * las palabras y guardarlas en un vector (arreglo unidimensional de String).
 * También permite consultar, buscar y validar las palabras del vector.
 *
 * Desarrollado por: Valentina Solano.
 */
public class Poema {

    // Longitud mínima que debe tener una palabra según la actividad
    private static final int LONGITUD_MINIMA = 4;

    // Vector principal donde se guardan las palabras del poema en orden
    private String[] palabras;

    /**
     * Constructor: el vector empieza vacío hasta que se cargue un archivo.
     */
    public Poema() {
        palabras = new String[0];
    }

    /**
     * Lee todo el contenido de un archivo de texto y lo devuelve en un String.
     *
     * Este método NO captura los errores: los declara con throws para que
     * el método que lo llame decida qué hacer con ellos. Así lo puedo usar
     * en dos escenarios distintos:
     *  - En la vista se captura directamente la FileNotFoundException (excepción nativa).
     *  - En cargar() se captura y se encadena en una ErrorCargaPoemaException.
     *
     * @param ruta nombre o ruta del archivo
     * @return el texto completo del archivo
     * @throws FileNotFoundException si el archivo no existe
     * @throws IOException si ocurre otro error de lectura
     */
    public String leerArchivo(String ruta) throws FileNotFoundException, IOException {
        String contenido = "";

        // Igual que en los ejemplos de clase se usa try-with-resources:
        // el BufferedReader se cierra solo al terminar, haya error o no.
        // Se indica UTF-8 para que las tildes (días, móviles...) se lean bien en Windows.
        try (BufferedReader br = new BufferedReader(new FileReader(ruta, StandardCharsets.UTF_8))) {
            String linea;
            // readLine() devuelve null cuando ya no hay más líneas en el archivo
            while ((linea = br.readLine()) != null) {
                contenido = contenido + linea + " "; // el espacio separa la última palabra de cada verso
            }
        }
        return contenido;
    }

    /**
     * Carga el poema: lee el archivo y llena el vector de palabras.
     *
     * Profesor, en este método se evidencia el proceso de lectura del archivo
     * y el encadenamiento de excepciones: se captura la excepción del sistema
     * y se lanza una excepción propia que la conserva como causa.
     *
     * @param ruta nombre o ruta del archivo del poema
     * @throws ErrorCargaPoemaException si no se pudo leer el archivo
     */
    public void cargar(String ruta) throws ErrorCargaPoemaException {
        String texto;
        try {
            texto = leerArchivo(ruta);
        } catch (FileNotFoundException e) {
            // En este punto se conserva la excepción original (e) para
            // demostrar el encadenamiento de excepciones.
            throw new ErrorCargaPoemaException("No se pudo cargar el poema porque el archivo '"
                    + ruta + "' no existe.", e);
        } catch (IOException e) {
            throw new ErrorCargaPoemaException("Ocurrió un error al leer el archivo '"
                    + ruta + "'.", e);
        }
        llenarVector(texto);
    }

    /**
     * Limpia el texto, cuenta las palabras y las guarda en el vector.
     *
     * @param texto contenido completo del archivo
     */
    private void llenarVector(String texto) {
        // 1. Limpiar los signos de puntuación (comas y puntos)
        String textoLimpio = texto.replace(",", "").replace(".", "");

        // 2. Separar el texto por espacios, igual que en el ejemplo del CSV
        //    donde se separaba por comas. Este arreglo es solo auxiliar,
        //    se usa para poder contar las palabras antes de crear el vector.
        String divisor = " ";
        String[] separadas = textoLimpio.trim().split(divisor);

        // 3. Contar cuántas palabras hay (se ignoran cadenas vacías, que
        //    aparecerían si hubiera dos espacios seguidos o el archivo estuviera vacío)
        int totalPalabras = 0;
        for (int i = 0; i < separadas.length; i++) {
            if (!separadas[i].isEmpty()) {
                totalPalabras++;
            }
        }

        // 4. Declarar el vector con el tamaño EXACTO de palabras contadas
        palabras = new String[totalPalabras];

        // 5. Guardar cada palabra en el vector, respetando el orden original
        int posicion = 0;
        for (int i = 0; i < separadas.length; i++) {
            if (!separadas[i].isEmpty()) {
                palabras[posicion] = separadas[i];
                posicion++;
            }
        }
    }

    /**
     * Devuelve la palabra que está en una posición del vector.
     * Si la posición no existe, se captura la ArrayIndexOutOfBoundsException
     * y se lanza una PosicionPoemaInvalidaException que la conserva como causa.
     *
     * @param posicion índice de la palabra (empieza en 0)
     * @return la palabra encontrada
     * @throws PosicionPoemaInvalidaException si la posición está fuera del vector
     */
    public String obtenerPalabra(int posicion) throws PosicionPoemaInvalidaException {
        try {
            return palabras[posicion];
        } catch (ArrayIndexOutOfBoundsException e) {
            // Se conserva la excepción original para demostrar el encadenamiento
            throw new PosicionPoemaInvalidaException("La posición " + posicion
                    + " no es válida. Las posiciones van de 0 a " + (palabras.length - 1) + ".", e);
        }
    }

    /**
     * Busca una palabra dentro del vector sin importar mayúsculas o minúsculas.
     *
     * Nota para el profesor: aquí se demuestra el manejo de excepciones
     * personalizadas mediante throw.
     *
     * @param palabraBuscada palabra que escribe el usuario
     * @return la posición donde se encontró la palabra
     * @throws PalabraNoEncontradaException si la palabra no está en el poema
     */
    public int buscarPalabra(String palabraBuscada) throws PalabraNoEncontradaException {
        for (int i = 0; i < palabras.length; i++) {
            if (palabras[i].equalsIgnoreCase(palabraBuscada.trim())) {
                return i;
            }
        }
        // Si el ciclo termina sin encontrarla, se lanza la excepción propia
        throw new PalabraNoEncontradaException("La palabra '" + palabraBuscada
                + "' no se encuentra en el poema.");
    }

    /**
     * Valida que la palabra de una posición tenga al menos 4 letras.
     * Reutiliza obtenerPalabra() para no repetir el control de la posición.
     *
     * @param posicion índice de la palabra a validar
     * @return la palabra, si cumple con la longitud mínima
     * @throws PalabraMuyCortaException si la palabra tiene menos de 4 letras
     * @throws PosicionPoemaInvalidaException si la posición no existe
     */
    public String validarLongitud(int posicion)
            throws PalabraMuyCortaException, PosicionPoemaInvalidaException {
        String palabra = obtenerPalabra(posicion);
        if (palabra.length() < LONGITUD_MINIMA) {
            throw new PalabraMuyCortaException("La palabra '" + palabra + "' tiene "
                    + palabra.length() + " letra(s) y no cumple con la longitud mínima de "
                    + LONGITUD_MINIMA + " letras.");
        }
        return palabra;
    }

    /**
     * @return el vector de palabras del poema
     */
    public String[] getPalabras() {
        return palabras;
    }

    /**
     * @return la cantidad de palabras guardadas en el vector
     */
    public int getCantidadPalabras() {
        return palabras.length;
    }
}

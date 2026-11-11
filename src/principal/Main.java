package principal;

import excepciones.ErrorCargaPoemaException;
import modelo.Poema;
import vista.MenuConsola;

/**
 * Clase principal: inicia la aplicación.
 * Carga el poema desde poema.txt y, si todo sale bien, abre el menú.
 *
 * Desarrollado por: Valentina Solano.
 * Actividad Evaluativa - Manejo de Archivos y Excepciones en Java.
 */
public class Main {

    // El archivo debe estar en la raíz del proyecto
    private static final String ARCHIVO_POEMA = "poema.txt";

    public static void main(String[] args) {
        Poema poema = new Poema();

        try {
            poema.cargar(ARCHIVO_POEMA);
            System.out.println("Poema cargado correctamente desde '" + ARCHIVO_POEMA + "'.");
            System.out.println("Total de palabras guardadas en el vector: " + poema.getCantidadPalabras());
        } catch (ErrorCargaPoemaException e) {
            // Sin el poema el menú no tiene datos, por eso el programa termina aquí
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Causa original: " + e.getCause());
            System.out.println("Verifique que poema.txt esté en la raíz del proyecto.");
            return;
        }

        MenuConsola menu = new MenuConsola(poema);
        menu.iniciar();
    }
}

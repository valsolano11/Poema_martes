package vista;

import excepciones.ErrorCargaPoemaException;
import excepciones.PalabraMuyCortaException;
import excepciones.PalabraNoEncontradaException;
import excepciones.PosicionPoemaInvalidaException;
import modelo.Poema;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase MenuConsola.
 * Muestra el menú interactivo, recibe los datos del usuario y presenta
 * los mensajes. Cada opción permite probar uno de los escenarios de
 * excepciones de la actividad usando bloques try-catch-finally.
 *
 * Desarrollado por: Valentina Solano.
 */
public class MenuConsola {

    private static final int OPCION_SALIR = 8;

    private Scanner teclado;
    private Poema poema;

    /**
     * Constructor: recibe el poema ya cargado desde el archivo.
     *
     * @param poema objeto con el vector de palabras
     */
    public MenuConsola(Poema poema) {
        this.poema = poema;
        this.teclado = new Scanner(System.in);
    }

    /**
     * Ciclo principal del menú. Se repite hasta que el usuario elija salir,
     * así se pueden probar varios escenarios sin reiniciar el programa.
     */
    public void iniciar() {
        int opcion;
        do {
            mostrarOpciones();
            try {
                opcion = teclado.nextInt();
            } catch (InputMismatchException e) {
                opcion = -1; // valor inválido para que caiga en el default
            } finally {
                teclado.nextLine(); // limpia lo que quedó escrito en la consola
            }

            switch (opcion) {
                case 1:
                    mostrarPalabras();
                    break;
                case 2:
                    consultarPorPosicion();
                    break;
                case 3:
                    buscarPalabra();
                    break;
                case 4:
                    validarLongitud();
                    break;
                case 5:
                    abrirArchivoInexistente();
                    break;
                case 6:
                    probarErrorCargaPoema();
                    break;
                case 7:
                    probarPosicionInvalida();
                    break;
                case OPCION_SALIR:
                    System.out.println("Programa finalizado. ¡Hasta luego!");
                    break;
                default:
                    System.out.println("Opción no válida. Escriba un número del 1 al 8.");
            }
        } while (opcion != OPCION_SALIR);

        teclado.close();
    }

    private void mostrarOpciones() {
        System.out.println();
        System.out.println("========= MENÚ - POEMA (Valentina Solano) =========");
        System.out.println("1. Mostrar todas las palabras del vector");
        System.out.println("2. Consultar una palabra por su posición");
        System.out.println("3. Buscar una palabra en el poema");
        System.out.println("4. Validar la longitud de una palabra");
        System.out.println("5. Intentar abrir un archivo inexistente");
        System.out.println("6. Probar error de lectura encapsulado (ErrorCargaPoemaException)");
        System.out.println("7. Consultar posición inválida (PosicionPoemaInvalidaException)");
        System.out.println("8. Salir");
        System.out.print("Seleccione una opción: ");
    }

    /**
     * Opción 1: recorre el vector y muestra cada palabra con su posición.
     */
    private void mostrarPalabras() {
        String[] palabras = poema.getPalabras();
        System.out.println("El poema tiene " + palabras.length + " palabras:");
        for (int i = 0; i < palabras.length; i++) {
            System.out.println("  [" + i + "] " + palabras[i]);
        }
    }

    /**
     * Opción 2 - EXCEPCIONES NATIVAS (escenario A.2).
     * Se accede directamente al vector con la posición que escribe el usuario,
     * sin ninguna validación previa, para que Java lance sus propias excepciones:
     *  - número fuera de rango  -> ArrayIndexOutOfBoundsException
     *  - texto en vez de número -> InputMismatchException
     */
    private void consultarPorPosicion() {
        String[] palabras = poema.getPalabras();
        try {
            System.out.print("Ingrese una posición (0 a " + (palabras.length - 1) + "): ");
            int posicion = teclado.nextInt();
            System.out.println("Palabra en la posición " + posicion + ": " + palabras[posicion]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("[ArrayIndexOutOfBoundsException] Esa posición no existe en el vector.");
            System.out.println("Mensaje de Java: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("[InputMismatchException] Debe escribir un número entero, no texto.");
        } finally {
            teclado.nextLine(); // se limpia la entrada en cualquier caso
        }
    }

    /**
     * Opción 3 - EXCEPCIÓN PERSONALIZADA (escenario B.1).
     * Si la palabra no existe, Poema lanza PalabraNoEncontradaException.
     */
    private void buscarPalabra() {
        System.out.print("Escriba la palabra que desea buscar: ");
        String palabra = teclado.nextLine();
        try {
            int posicion = poema.buscarPalabra(palabra);
            System.out.println("La palabra '" + palabra + "' se encuentra en la posición " + posicion + ".");
        } catch (PalabraNoEncontradaException e) {
            System.out.println("[PalabraNoEncontradaException] " + e.getMessage());
        } finally {
            System.out.println("Búsqueda finalizada.");
        }
    }

    /**
     * Opción 4 - EXCEPCIÓN PERSONALIZADA (escenario B.2).
     * Si la palabra tiene menos de 4 letras, Poema lanza PalabraMuyCortaException.
     */
    private void validarLongitud() {
        try {
            System.out.print("Ingrese la posición de la palabra a validar: ");
            int posicion = teclado.nextInt();
            String palabra = poema.validarLongitud(posicion);
            System.out.println("La palabra '" + palabra + "' cumple con la longitud mínima.");
        } catch (PalabraMuyCortaException e) {
            System.out.println("[PalabraMuyCortaException] " + e.getMessage());
        } catch (PosicionPoemaInvalidaException e) {
            System.out.println("[PosicionPoemaInvalidaException] " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("[InputMismatchException] Debe escribir un número entero.");
        } finally {
            teclado.nextLine();
        }
    }

    /**
     * Opción 5 - EXCEPCIÓN NATIVA (escenario A.1).
     * Se llama a leerArchivo() y aquí mismo se captura la
     * FileNotFoundException que lanza Java, sin envolverla en otra excepción.
     */
    private void abrirArchivoInexistente() {
        System.out.print("Escriba el nombre del archivo a abrir (ejemplo: noexiste.txt): ");
        String nombreArchivo = teclado.nextLine();
        try {
            String contenido = poema.leerArchivo(nombreArchivo);
            System.out.println("El archivo sí existe. Contenido:");
            System.out.println(contenido);
        } catch (FileNotFoundException e) {
            System.out.println("[FileNotFoundException] El archivo '" + nombreArchivo + "' no existe.");
            System.out.println("Mensaje de Java: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("[IOException] Error al leer el archivo: " + e.getMessage());
        } finally {
            System.out.println("Intento de apertura finalizado. El programa continúa.");
        }
    }

    /**
     * Opción 6 - EXCEPCIÓN ENCADENADA (escenario C.1).
     * Se intenta cargar un poema desde otro archivo. Si falla, Poema lanza
     * ErrorCargaPoemaException y con getCause() se muestra la excepción original.
     * Se usa un objeto Poema nuevo para no perder el poema que ya está cargado.
     */
    private void probarErrorCargaPoema() {
        System.out.print("Escriba el nombre del archivo del poema (ejemplo: poema_falso.txt): ");
        String nombreArchivo = teclado.nextLine();
        Poema poemaPrueba = new Poema();
        try {
            poemaPrueba.cargar(nombreArchivo);
            System.out.println("El archivo se cargó con " + poemaPrueba.getCantidadPalabras() + " palabras.");
        } catch (ErrorCargaPoemaException e) {
            System.out.println("[ErrorCargaPoemaException] " + e.getMessage());
            mostrarCausa(e);
        } finally {
            System.out.println("Prueba de carga finalizada.");
        }
    }

    /**
     * Opción 7 - EXCEPCIÓN ENCADENADA (escenario C.2).
     * A diferencia de la opción 2, aquí la ArrayIndexOutOfBoundsException se
     * captura dentro de Poema y se vuelve a lanzar como PosicionPoemaInvalidaException.
     */
    private void probarPosicionInvalida() {
        try {
            System.out.print("Ingrese una posición (pruebe con una inválida, ej: 99): ");
            int posicion = teclado.nextInt();
            String palabra = poema.obtenerPalabra(posicion);
            System.out.println("Palabra en la posición " + posicion + ": " + palabra);
        } catch (PosicionPoemaInvalidaException e) {
            System.out.println("[PosicionPoemaInvalidaException] " + e.getMessage());
            mostrarCausa(e);
        } catch (InputMismatchException e) {
            System.out.println("[InputMismatchException] Debe escribir un número entero.");
        } finally {
            teclado.nextLine();
        }
    }

    /**
     * Muestra la excepción original guardada dentro de una excepción encadenada.
     * Se usa en las opciones 6 y 7 para no repetir el mismo código.
     *
     * @param e excepción propia que contiene la causa original
     */
    private void mostrarCausa(Exception e) {
        Throwable causa = e.getCause();
        if (causa != null) {
            System.out.println("   Causa original (getCause()): " + causa.getClass().getName());
            System.out.println("   Mensaje de la causa: " + causa.getMessage());
        }
    }
}

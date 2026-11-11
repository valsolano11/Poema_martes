# Actividad Evaluativa: Manejo de Archivos y Excepciones en Java

**Autora:** Valentina Solano
**Asignatura:** Programación Orientada a Objetos / Java

Aplicación de consola que lee la primera estrofa de *Canción de la vida profunda* (Porfirio Barba Jacob)
desde `poema.txt`, limpia comas y puntos, cuenta las palabras (**37**) y las guarda en un vector
`String[]` de tamaño exacto. Un menú interactivo permite probar seis escenarios de excepciones.

## Estructura del proyecto

```
PoemaExcepciones/
├── poema.txt                         Primera estrofa del poema
└── src/
    ├── excepciones/
    │   ├── PalabraNoEncontradaException.java     (personalizada)
    │   ├── PalabraMuyCortaException.java         (personalizada)
    │   ├── ErrorCargaPoemaException.java         (encadenada)
    │   └── PosicionPoemaInvalidaException.java   (encadenada)
    ├── modelo/
    │   └── Poema.java                Lectura del archivo, vector y validaciones
    ├── vista/
    │   └── MenuConsola.java          Menú interactivo y mensajes
    └── principal/
        └── Main.java                 Inicia la aplicación
```

## Cómo ejecutar

Requiere Java 11 o superior. Desde la carpeta `PoemaExcepciones` (donde está `poema.txt`):

```bash
javac -encoding UTF-8 -d bin src/excepciones/*.java src/modelo/*.java src/vista/*.java src/principal/*.java
java -cp bin principal.Main
```

En IntelliJ / NetBeans / Eclipse: marcar `src` como carpeta de código fuente, ejecutar `principal.Main`
y verificar que el *working directory* sea la carpeta del proyecto (donde está `poema.txt`).

## Pruebas de los seis escenarios

| # | Tipo | Opción | Qué escribir | Resultado esperado |
|---|---|---|---|---|
| A.1 | Nativa | 5 | `noexiste.txt` | `[FileNotFoundException] El archivo 'noexiste.txt' no existe.` |
| A.2 | Nativa | 2 | `50` | `[ArrayIndexOutOfBoundsException] ... Index 50 out of bounds for length 37` |
| A.2 | Nativa | 2 | `hola` | `[InputMismatchException] Debe escribir un número entero, no texto.` |
| B.1 | Personalizada | 3 | `perro` | `[PalabraNoEncontradaException] La palabra 'perro' no se encuentra en el poema.` |
| B.2 | Personalizada | 4 | `0` (palabra "Hay") | `[PalabraMuyCortaException] La palabra 'Hay' tiene 3 letra(s)...` |
| C.1 | Encadenada | 6 | `poema_falso.txt` | `[ErrorCargaPoemaException] ...` + `Causa original (getCause()): java.io.FileNotFoundException` |
| C.2 | Encadenada | 7 | `99` | `[PosicionPoemaInvalidaException] ...` + `Causa original (getCause()): java.lang.ArrayIndexOutOfBoundsException` |

Casos correctos para comparar: opción 2 con `4` → `somos`; opción 3 con `vida` → posición 28;
opción 4 con `4` → `somos` cumple la longitud mínima.

**Comprobar `getCause()`:** en las opciones 6 y 7 el programa imprime la clase y el mensaje de la causa
original. Si `getCause()` devolviera `null`, esas dos líneas no aparecerían.

**Comprobar que el menú sigue funcionando:** después de cada error el menú vuelve a aparecer. También
se puede escribir texto (ej. `abc`) o un número fuera de 1–8 en el menú principal y el programa
responde "Opción no válida" sin cerrarse. Solo termina con la opción `8`.

## Secuencia de commits propuesta

| # | Mensaje | Contenido |
|---|---|---|
| 1 | `feat: crear estructura del proyecto y archivo poema.txt` | `poema.txt`, `.gitignore`, `README.md` y carpetas de paquetes |
| 2 | `feat: implementar excepciones personalizadas y encadenadas` | las 4 clases de `excepciones/` |
| 3 | `feat: implementar lectura y procesamiento del vector de palabras` | `modelo/Poema.java` (lectura, limpieza, conteo, vector, throw/throws) |
| 4 | `feat: desarrollar menú interactivo y manejo de excepciones` | `vista/MenuConsola.java` y `principal/Main.java` |

Las excepciones van antes que el modelo porque `Poema.java` las usa; así cada commit compila.

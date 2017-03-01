/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package commands.util

import org.crsh.text.ui.UIBuilder

/**
 * Se encarga de mostrar los resultado en pantalla, formato Tabla o Lista
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
final class MostrarResultadosUtil {
    private static final MostrarResultadosUtil INSTANCE = new MostrarResultadosUtil();

    /**
     *
     * @param headers Lista con los encabezados.
     * @param elementos Lista de elementos del catálogo.
     * @param propiedades Lista con las propiedades a mostrar.
     * @param mostrarEnLista Indica el formato de salida.
     *
     * @return espliegue visual en la consola con los elementos del catálogo.
     */
    public static def mostrarResultados(List<String> headers, def elementos,
                                 List<String> propiedades, Boolean mostrarEnLista) {
        if (mostrarEnLista) {
            INSTANCE.mostrarListaResultados(headers, elementos, propiedades)
        } else {
            INSTANCE.mostrarTablaResultados(headers, elementos, propiedades)
        }
    }

    /**
     * Utilizado para representar los elementos del catálogo en un formato de tabla.
     *
     * @param headers Lista con los encabezados de la tabla.
     * @param elementos Lista de elementos del catálogo.
     * @param propiedades Lista con las propiedades a mostrar.
     *
     * @return Despliegue visual en la consola con los elementos del catálogo.
     */
    private def mostrarTablaResultados(List<String> headers, def elementos, List<String> propiedades) {
        new UIBuilder().table(separator: null, overflow: 'hidden', rightCellPadding: 1) {
            header(decoration: bold, foreground: black, background: white) {
                headers.each {
                    label(it)
                }
            }

            elementos.each { elemento ->
                row {
                    propiedades.each {
                        label(elemento."$it" == null ? "" : elemento."$it", foreground: white)
                    }
                }
            }
        }
    }

    /**
     * Utilizado para representar los elementos del catálogo en un formato de lista.
     *
     * @param headers Lista con los encabezados de la lista.
     * @param elementos Lista de elementos del catálogo.
     * @param propiedades Lista con las propiedades a mostrar.
     *
     * @return Despliegue visual en la consola con los elementos del catálogo.
     */
    private def mostrarListaResultados(List<String> headers, def elementos, List<String> propiedades) {
        new UIBuilder().table(separator: null, overflow: 'hidden', rightCellPadding: 1) {
            elementos.each { elemento ->
                headers.eachWithIndex { String h, int i ->
                    if (elemento."${propiedades[i]}" != null) {
                        row {
                            label("$h: ", foreground: white)
                            label(elemento."${propiedades[i]}", foreground: white)
                        }
                    }
                }
                row {
                    label("")
                }
            }
        }
    }
}

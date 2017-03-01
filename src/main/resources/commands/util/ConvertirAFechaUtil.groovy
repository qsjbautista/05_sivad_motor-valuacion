/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package commands.util

import mx.com.nmp.ms.sivad.valuacion.dominio.validador.ValidadorFecha
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.ISODateTimeFormat

/**
 * Convierte una fecha contenida en una cadena a {@link LocalDate}
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
final class ConvertirAFechaUtil {
    private static final DateTimeFormatter FORMATO_FECHA = ISODateTimeFormat.dateHourMinuteSecond();

    /**
     * Convierte una fecha contenida en una cadena a {@link LocalDate}
     *
     * @param valor Cadena que contiene la fecha.
     *
     * @return {@link LocalDate}
     *
     * @throws IllegalArgumentException Si el formato no es valido.<
     */
    static LocalDate convertirAFecha(String valor) {
        LocalDate fecha

        try {
            fecha = LocalDate.parse(valor)
        } catch (IllegalArgumentException | UnsupportedOperationException e) {
            throw new IllegalArgumentException(
                "El formato de la fecha [$valor] no es valido.\n${e.getLocalizedMessage()}")
        }

        ValidadorFecha.validarFechaFutura(fecha, "La fecha de vigencia solicitada no puede ser una fecha futura.")
    }

    static String convertirAString(DateTime fecha) {
        fecha.toString(FORMATO_FECHA)
    }
}

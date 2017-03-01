/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.validador;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.util.Assert;

/**
 * Clase de utilería para validar fechas.
 *
 * @author ngonzalez
 */
public final class ValidadorFecha {

    private static final String FECHA_FUTURA = "La fecha es posterior a la fecha actual.";
    private static final String FECHA_NULA = "La fecha no debe ser nula.";



    // METODOS

    /**
     * Constructor.
     */
    private ValidadorFecha() {
        // Utility classes should always be final and have a private constructor.
    }

    /**
     * Valida si la fecha es posterior a la fecha actual.
     *
     * @param fecha La fecha que se requiere validar.
     * @return La fecha si no es posterior a la fecha actual.
     * @throws IllegalArgumentException Cuando la fecha es posterior.
     */
    public static LocalDate validarFechaFutura(LocalDate fecha) {
        return validarFechaFutura(fecha, FECHA_FUTURA);
    }

    /**
     * Valida si la fecha es posterior a la fecha actual.
     *
     * @param fecha La fecha que se requiere validar.
     * @return La fecha si no es posterior a la fecha actual.
     * @throws IllegalArgumentException Cuando la fecha es posterior.
     */
    public static DateTime validarFechaFutura(DateTime fecha) {
        return validarFechaFutura(fecha, FECHA_FUTURA);
    }

    /**
     * Valida si la fecha es posterior a la fecha actual.
     *
     * @param fecha La fecha que se requiere validar.
     * @param msg Mensaje de error.
     * @return La fecha si no es posterior a la fecha actual.
     * @throws IllegalArgumentException Cuando la fecha es posterior.
     */
    public static LocalDate validarFechaFutura(LocalDate fecha, String msg) {
        Assert.notNull(fecha, FECHA_NULA);

        if (fecha.compareTo(LocalDate.now()) > 0) {
            throw new IllegalArgumentException(msg);
        }

        return fecha;
    }

    /**
     * Valida si la fecha es posterior a la fecha actual.
     *
     * @param fecha La fecha que se requiere validar.
     * @param msg Mensaje de error.
     * @return La fecha si no es posterior a la fecha actual.
     * @throws IllegalArgumentException Cuando la fecha es posterior.
     */
    public static DateTime validarFechaFutura(DateTime fecha, String msg) {
        Assert.notNull(fecha, FECHA_NULA);

        if (fecha.compareTo(DateTime.now()) > 0) {
            throw new IllegalArgumentException(msg);
        }

        return fecha;
    }

}

/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.jpa.dominio.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Clase de utilería utilizada para manipular fechas.
 *
 * @author ngonzalez
 */
public class DateUtil {



    // METODOS

    /**
     * Constructor.
     */
    private DateUtil() {
        // Utility classes should always be final and have a private constructor.
    }

    /**
     * Permite obtener la fecha que se recibe como parámetro establecida al inicio del día (00:00:00.000).
     *
     * @param fecha La fecha a procesar.
     * @return La fecha establecida en el inicio del día.
     */
    public static Date getStartOfDay(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * Permite obtener la fecha que se recibe como parámetro establecida al final del día (23:59:59.999).
     *
     * @param fecha La fecha a procesar.
     * @return La fecha establecida en el final del día.
     */
    public static Date getEndOfDay(Date fecha) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }

    /**
     * Permite validar si la fecha que se recibe como parámetro es posterior a la fecha actual o no.
     *
     * @param fecha La fecha a procesar.
     * @return TRUE si es posterior y FALSE en caso contrario.
     */
    public static boolean isGreaterThanNow(Date fecha) {
        if (fecha.compareTo(new Date()) > 0) {
            return true;
        }

        return false;
    }

}

/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.validador;

import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * Clase de utilería para validarPositivo numeros.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public final class ValidadorNumero {

    /**
     * Constructor, privado ya que no debe ser instanciada.
     */
    private ValidadorNumero() {
        super();
    }

    /**
     * Metodo que valida si {@code valor} es mayor que cero.
     *
     * @param valor El valor numérico a validarPositivo.
     * @return {@code valor} si es mayor que cero.
     * @throws IllegalArgumentException Cuando {@code valor} es menor o igual que cero.
     */
    public static BigDecimal validarPositivo(BigDecimal valor) {
        return validarPositivo(valor, "El valor debe ser mayor que 0.");
    }

    /**
     * Metodo que valida si {@code valor} es mayor que cero.
     *
     * @param valor El valor numérico a validarPositivo.
     * @param msg El mensaje de error cuando el valor es menor a cero.
     * @return {@code valor} si es mayor que cero.
     * @throws IllegalArgumentException Cuando {@code valor} es menor o igual que cero.
     */
    public static BigDecimal validarPositivo(BigDecimal valor, String msg) {
        Assert.notNull(valor, "El valor no debe ser nulo");

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(msg);
        }

        return valor;
    }

    /**
     * Metodo que valida si {@code valor} es mayor que cero.
     *
     * @param valor El valor numérico a validarPositivo.
     * @return {@code valor} si es mayor que cero.
     * @throws IllegalArgumentException Cuando {@code valor} es menor o igual que cero.
     */
    public static int validarPositivo(int valor) {
        return validarPositivo(valor, "El valor debe ser mayor que 0.");
    }

    /**
     * Metodo que valida si {@code valor} es mayor que cero.
     *
     * @param valor El valor numérico a validarPositivo.
     * @param msg El mensaje de error cuando el valor es menor a cero.
     * @return {@code valor} si es mayor que cero.
     * @throws IllegalArgumentException Cuando {@code valor} es menor o igual que cero.
     */
    public static int validarPositivo(int valor, String msg) {
        if (valor <= 0) {
            throw new IllegalArgumentException(msg);
        }

        return valor;
    }

}

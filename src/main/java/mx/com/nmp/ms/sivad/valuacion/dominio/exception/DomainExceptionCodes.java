/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.exception;

/**
 * Enum que define los códigos de excepción que serán manejadas en el dominio.
 *
 * @author ngonzalez
 */
public enum DomainExceptionCodes {

    BUILDER_NULO("BUILDER_NULO",
        "El objeto constructor no debe ser nulo."),
    ALHAJA_CALIDAD_NULA("ALHAJA_CALIDAD_NULA",
        "La calidad de la alhaja no debe ser nula."),
    ALHAJA_COLOR_NULO("ALHAJA_COLOR_NULO",
        "El color de la alhaja no debe ser nulo."),
    ALHAJA_METAL_NULO("ALHAJA_METAL_NULO",
        "El metal de la alhaja no debe ser nulo."),
    ALHAJA_PESO_NULO("ALHAJA_PESO_NULO",
        "El peso de la alhaja no debe ser nulo."),
    ALHAJA_VALOR_EXPERTO_NO_SOPORTADO("ALHAJA_VALOR_EXPERTO_NO_SOPORTADO",
        "Valor experto tipo unitario no soportado para alhajas."),
    COMPLEMENTARIO_VALOR_EXPERTO_NULO("COMPLEMENTARIO_VALOR_EXPERTO_NULO",
        "El valor experto de la pieza complementaria no debe ser nulo."),
    DIAMANTE_CLARIDAD_NULA("DIAMANTE_CLARIDAD_NULA",
        "La claridad del diamante no debe ser nula."),
    DIAMANTE_COLOR_NULO("DIAMANTE_COLOR_NULO",
        "El color del diamante no debe ser nulo."),
    DIAMANTE_CORTE_NULO("DIAMANTE_CORTE_NULO",
        "El corte del diamante no debe ser nulo."),
    DIAMANTE_QUILATES_NULO("DIAMANTE_QUILATES_NULO",
        "El valor en quilates del diamante no debe ser nulo."),
    LISTA_PIEZAS_NULA("LISTA_PIEZAS_NULA",
        "La lista de piezas no debe ser nula."),
    LISTA_PIEZAS_VACIA("LISTA_PIEZAS_VACIA",
        "La lista de piezas no debe estar vacia.");

    /**
     * Código de la excepción.
     */
    private String codeException;

    /**
     * Mensaje de la excepción.
     */
    private String messageException;



    // METODOS

    /**
     * Constructor.
     *
     * @param codeException El código de la excepción.
     * @param messageException El mensaje de la excepción.
     */
    DomainExceptionCodes(String codeException, String messageException) {
        this.codeException = codeException;
        this.messageException = messageException;
    }



    // GETTERS

    public String getCodeException() {
        return codeException;
    }

    public String getMessageException() {
        return messageException;
    }

}

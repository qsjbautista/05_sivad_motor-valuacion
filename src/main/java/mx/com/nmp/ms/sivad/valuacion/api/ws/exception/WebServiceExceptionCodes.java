/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.api.ws.exception;

/**
 * Enum que define los códigos de error que serán manejados en los servicios.
 *
 * @author ngonzalez
 */
public enum WebServiceExceptionCodes {

    NMPMV001("NMP-MV-001", "Funcionalidad no disponible."),
    NMPMV002("NMP-MV-002", "No está permitido valuar más de una alhaja con las mismas características."),
    NMPMV003("NMP-MV-003", "Error en datos de entrada. La información de la prenda es incorrecta."),
    NMPMV004("NMP-MV-004", "Error en datos de entrada. La información de la alhaja es incorrecta."),
    NMPMV005("NMP-MV-005", "Error en datos de entrada. La información del diamante es incorrecta."),
    NMPMV006("NMP-MV-006", "Error en datos de entrada. La información del complemento es incorrecta."),
    NMPMV007("NMP-MV-007", "Error en datos de entrada. La información de la pieza está incompleta. " +
        "Debe contener la información de una alhaja o un diamante o un complemento."),
    NMPMV008("NMP-MV-008", "Error en datos de entrada. La información de la pieza está incorrecta. " +
        "Debe contener la información de solamente una pieza valuable (alhaja, diamante o complemento)."),
    NMPMV009("NMP-MV-009", "Ocurrió un error al realizar la valuación de la prenda."),
    NMPMV010("NMP-MV-010", "Ocurrió un error inesperado al momento de realizar la valuación de la prenda."),;

    /**
     * Código de error de la excepción.
     */
    private String codeException;

    /**
     * Mensaje de error de la excepción.
     */
    private String messageException;



    // METODOS

    /**
     * Constructor.
     *
     * @param codeException El código de error de la excepción.
     * @param messageException El mensaje de error de la excepción.
     */
    WebServiceExceptionCodes(String codeException, String messageException) {
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

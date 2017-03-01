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
    ALHAJA_CALIDAD_VACIA("ALHAJA_CALIDAD_VACIA",
        "La calidad de la alhaja no debe estar vacia."),
    ALHAJA_COLOR_NULO("ALHAJA_COLOR_NULO",
        "El color de la alhaja no debe ser nulo."),
    ALHAJA_COLOR_VACIO("ALHAJA_COLOR_VACIO",
        "El color de la alhaja no debe estar vacio."),
    ALHAJA_DESPLAZAMIENTO_MENOR_IGUAL_CERO("ALHAJA_DESPLAZAMIENTO_MENOR_IGUAL_CERO",
        "El factor de desplazamiento de la alhaja no debe ser menor, ni igual a cero."),
    ALHAJA_INCREMENTO_MENOR_IGUAL_CERO("ALHAJA_INCREMENTO_MENOR_IGUAL_CERO",
        "El factor de incremento de la alhaja no debe ser menor, ni igual a cero."),
    ALHAJA_METAL_NULO("ALHAJA_METAL_NULO",
        "El metal de la alhaja no debe ser nulo."),
    ALHAJA_METAL_VACIO("ALHAJA_METAL_VACIO",
        "El metal de la alhaja no debe estar vacio."),
    ALHAJA_PESO_MENOR_IGUAL_CERO("ALHAJA_PESO_MENOR_IGUAL_CERO",
        "El peso de la alhaja no debe ser menor, ni igual a cero."),
    ALHAJA_PESO_NULO("ALHAJA_PESO_NULO",
        "El peso de la alhaja no debe ser nulo."),
    ALHAJA_VALOR_EXPERTO_MENOR_IGUAL_CERO("ALHAJA_VALOR_EXPERTO_MENOR_IGUAL_CERO",
        "El valor experto de la alhaja no debe ser menor, ni igual a cero."),
    ALHAJA_VALOR_EXPERTO_NO_SOPORTADO("ALHAJA_VALOR_EXPERTO_NO_SOPORTADO",
        "Valor experto tipo unitario no soportado para alhajas."),
    COMPLEMENTARIO_NUM_PIEZAS_MENOR_IGUAL_CERO("COMPLEMENTARIO_NUM_PIEZAS_MENOR_IGUAL_CERO",
        "El numero de piezas de la pieza complementaria no debe ser menor, ni igual a cero."),
    COMPLEMENTARIO_VALOR_EXPERTO_MENOR_IGUAL_CERO("COMPLEMENTARIO_VALOR_EXPERTO_MENOR_IGUAL_CERO",
        "El valor experto de la pieza complementaria no debe ser menor, ni igual a cero."),
    COMPLEMENTARIO_VALOR_EXPERTO_NULO("COMPLEMENTARIO_VALOR_EXPERTO_NULO",
        "El valor experto de la pieza complementaria no debe ser nulo."),
    DIAMANTE_CLARIDAD_NULA("DIAMANTE_CLARIDAD_NULA",
        "La claridad del diamante no debe ser nula."),
    DIAMANTE_CLARIDAD_VACIA("DIAMANTE_CLARIDAD_VACIA",
        "La claridad del diamante no debe estar vacia."),
    DIAMANTE_COLOR_NULO("DIAMANTE_COLOR_NULO",
        "El color del diamante no debe ser nulo."),
    DIAMANTE_COLOR_VACIO("DIAMANTE_COLOR_VACIO",
        "El color del diamante no debe estar vacio."),
    DIAMANTE_CORTE_NULO("DIAMANTE_CORTE_NULO",
        "El corte del diamante no debe ser nulo."),
    DIAMANTE_CORTE_VACIO("DIAMANTE_CORTE_VACIO",
        "El corte del diamante no debe estar vacio."),
    DIAMANTE_QUILATES_MENOR_IGUAL_CERO("DIAMANTE_QUILATES_MENOR_IGUAL_CERO",
        "El valor en quilates del diamante no debe ser menor, ni igual a cero."),
    DIAMANTE_QUILATES_NULO("DIAMANTE_QUILATES_NULO",
        "El valor en quilates del diamante no debe ser nulo."),
    DIAMANTE_NUM_PIEZAS_MENOR_IGUAL_CERO("DIAMANTE_NUM_PIEZAS_MENOR_IGUAL_CERO",
        "El numero de piezas del diamante no debe ser menor, ni igual a cero."),
    DIAMANTE_VALOR_EXPERTO_MENOR_IGUAL_CERO("DIAMANTE_VALOR_EXPERTO_MENOR_IGUAL_CERO",
        "El valor experto del diamante no debe ser menor, ni igual a cero."),
    LISTA_PIEZAS_NULA("LISTA_PIEZAS_NULA",
        "La lista de piezas no debe ser nula."),
    LISTA_PIEZAS_VACIA("LISTA_PIEZAS_VACIA",
        "La lista de piezas no debe estar vacia."),
    MODIFICADOR_CONDICION_PRENDA_FECHA_LISTADO_NULA("MODIFICADOR_CONDICION_PRENDA_FECHA_LISTADO_NULA",
        "La fecha del listado no debe ser nula."),
    MODIFICADOR_CONDICION_PRENDA_LISTA_MODIFICADORES_NULA("MODIFICADOR_CONDICION_PRENDA_LISTA_MODIFICADORES_NULA",
        "La lista de modificadores - condición prenda no debe ser nula."),
    MODIFICADOR_CONDICION_PRENDA_LISTA_MODIFICADORES_VACIA("MODIFICADOR_CONDICION_PRENDA_LISTA_MODIFICADORES_VACIA",
        "La lista de modificadores - condición prenda no debe estar vacia."),
    MODIFICADOR_CONDICION_PRENDA_ULTIMA_ACTUALIZACION_NULA("MODIFICADOR_CONDICION_PRENDA_ULTIMA_ACTUALIZACION_NULA",
        "La fecha de ultima actualizacion no debe ser nula."),
    CODICION_FISICA_PRENDA("CODICION_FISICA_PRENDA", "La condicion fisica de la prenda no debe ser nula o vacia.");

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

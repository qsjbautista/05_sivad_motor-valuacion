/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package commands

import mx.com.nmp.ms.sivad.valuacion.dominio.exception.PoliticaCastigoNoEncontradaException
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.FactorPoliticasCastigoFactory
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.PoliticasCastigoFactory
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.PoliticasCastigo
import mx.com.nmp.ms.sivad.valuacion.dominio.repository.PoliticasCastigoRepository
import mx.com.nmp.ms.sivad.valuacion.dominio.validador.ValidadorNumero
import mx.com.nmp.ms.sivad.valuacion.infrastructure.estrategia.RedondeoEstrategiaUtil
import org.crsh.cli.Argument
import org.crsh.cli.Command
import org.crsh.cli.Man
import org.crsh.cli.Option
import org.crsh.cli.Required
import org.crsh.cli.Usage
import org.crsh.command.InvocationContext
import org.crsh.text.ui.Overflow
import org.crsh.text.ui.UIBuilder
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormatter
import org.joda.time.format.ISODateTimeFormat
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.DataIntegrityViolationException

/**
 * Utilizado por la consola CRaSH para administrar el listado de políticas de castigo.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
@SuppressWarnings("GroovyUnusedDeclaration")
@Usage("Administraci\u00f3n de las Pol\u00edticas de Castigo")
@Man("""Comando que provee las siguientes operaciones sobre las Pol\u00edticas de Castigo:
Sustituir las pol\u00edticas vigentes actuales por nuevas Pol\u00edticas de Castigo.
Consultar las Pol\u00edticas de Castigo con base en una fecha de vigencia.""")
class PoliticasCastigoCommand {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoliticasCastigoCommand)

    private static final DateTimeFormatter FORMATO_FECHA = ISODateTimeFormat.dateHourMinuteSecond()

    private static final BigDecimal FACTOR = BigDecimal.valueOf(100)

    @Command
    @SuppressWarnings("GroovyAssignabilityCheck")
    @Usage("Permite actualizar la lista de pol\u00edticas de castigo.")
    @Man("""Permite sustituir las pol\u00edticas vigentes actuales por nuevas Pol\u00edticas de Castigo,
en formato centenas '.' decimales""")
    void actualizar(InvocationContext context,
                   @Usage("porcentaje de castigo para Diamante.")
                   @Man("Porcentaje de castigo para Diamante (ej. 60, 54.57)")
                   @Required @Option(names = ["d", "porcentajeDiamante"]) String porcentajeDiamante,
                   @Usage("porcentaje de castigo para Alhaja.")
                   @Man("Porcentaje de castigo para Alhaja (ej. 110.1, 30)")
                   @Required @Option(names = ["a", "porcentajeAlhaja"])String porcentajeAlhaja,
                   @Usage("porcentaje de castigo para Complemento.")
                   @Man("Porcentaje de castigo para Complemento (ej. 45, 92.03)")
                   @Required @Option(names = ["c", "porcentajeComplemento"])String porcentajeComplemento) {
        FactorPoliticasCastigoFactory fabricaVo =
            context.attributes["spring.beanfactory"].getBean(FactorPoliticasCastigoFactory)
        PoliticasCastigoFactory fabrica = context.attributes["spring.beanfactory"].getBean(PoliticasCastigoFactory)

        /**
         * Convierte el porcentaje de castigo ingresado a factor.
         */
        try {
            BigDecimal fd = convertirAFactor(porcentajeDiamante)
            BigDecimal fa = convertirAFactor(porcentajeAlhaja)
            BigDecimal fc = convertirAFactor(porcentajeComplemento)

            Map<Class<? extends Pieza>, BigDecimal> vo = fabricaVo.crearCon(fd, fa, fc)
            PoliticasCastigo entidad = fabrica.crearPersistibleCon(vo, DateTime.now())

            entidad.actualizar()
            out.println("El Listado de Pol\u00edticas de Castigo fue actualizado correctamente.", green)
        } catch (DataIntegrityViolationException e) {
            LOGGER.info("Error al procesar la solicitud", e)
            out.println("El valor capturado es demasiado grande, m\u00e1ximo 3 d\u00edgitos enteros.", red)
        } catch (IllegalArgumentException e) {
            LOGGER.info("Error al procesar la solicitud", e)
            out.println(e.getMessage(), red)
        } catch (Exception e) {
            LOGGER.info("Ocurri\u00f3 un error al guardar las políticas de castigo", e)
            out.println("Ocurri\u00f3 un error inesperado al actualizar el Listado de Pol\u00edticas de Castigo", red)
        }
    }

    @Command
    @SuppressWarnings("GroovyAssignabilityCheck")
    @Usage("Permite consultar la lista de pol\u00edticas de castigo.")
    @Man("Permite consultar las Pol\u00edticas de Castigo con base en una fecha de vigencia.")
    consultar(InvocationContext context,
              @Usage("fecha de vigencia.")
                  @Man("Fecha de vigencia con la cual se consultaran las pol\u00edticas de castigo.")
                  @Argument String fecha) {
        DateTime fechaVigencia = null

        if (fecha) {
            try {
                fechaVigencia = convertirAFecha(fecha)
            } catch (IllegalArgumentException e) {
                out.println(e.getMessage(), red)
                return
            }
        }

        PoliticasCastigoRepository repositorio = context.attributes["spring.beanfactory"]
            .getBean(PoliticasCastigoRepository)

        try {
            recuperarElementos(repositorio, fechaVigencia)
        } catch (PoliticaCastigoNoEncontradaException e) {
            LOGGER.info("No se encontro resultado", e)
            procesarMensajeError(fechaVigencia)
        } catch (Exception e) {
            LOGGER.info("Error al recuperar las pol\u00edticas de castigo", e)
            out.println("Ocurri\u00f3 un error inesperado al consultar el Listado de Políticas de Castigo", red)
        }
    }

    /**
     * Convierte un {@link String} a {@link BigDecimal}, en formato de factor (dividio por cien).
     *
     * @param valor Valor a convertir.
     *
     * @return Factor convertido.
     */
    private static BigDecimal convertirAFactor(String valor) {
        try {
            BigDecimal numero = valor.toBigDecimal()
            /**
             * Convierte el porcentaje de castigo ingresado a factor.
             */
            ValidadorNumero.validarPositivo(numero) / FACTOR
        } catch (NumberFormatException e) {
            final String MSJ_ERROR = "El formato del n\u00famero [$valor] no es valido"
            LOGGER.info(MSJ_ERROR, e)
            throw new NumberFormatException(MSJ_ERROR)
        } catch (IllegalArgumentException e) {
            final String MSJ_ERROR = "El valor del n\u00famero [$valor] debe ser positivo mayor a cero"
            LOGGER.info(MSJ_ERROR, e)
            throw new IllegalArgumentException(MSJ_ERROR)
        }
    }

    /**
     * Utilizado para representar los elementos del catálogo en un formato de tabla.
     *
     * @param map Lista de elementos del catálogo.
     *
     * @return Despliegue visual en la consola con los elementos del catálogo.
     */
    @SuppressWarnings("GroovyAssignabilityCheck")
    private void mostrarTablaResultados(Map<Class<? extends Pieza>, BigDecimal> map, DateTime fecha) {
        out.println("Fecha Vigencia: ${convertirAString(fecha)}", green)

        def resultados =  new UIBuilder().table(separator: dashed,
                overflow: Overflow.HIDDEN, rightCellPadding: 1) {

            header(decoration: bold, foreground: black, background: white) {
                label("Pieza")
                label("Politica Castigo")
            }

            map.entrySet().each { e ->
                row(foreground: white) {
                    label(e.key.simpleName)
                    /**
                     * Convierte el factor de castigo recuperado a porcentaje.
                     */
                    label(RedondeoEstrategiaUtil.get().redondear(e.value * FACTOR))
                }
            }
        }

        out.println(resultados)
    }

    /**
     * Crea el mensaje cuando se presenta un error en la consulta de las políticas
     *
     * @param fecha Fecha de consulta.
     *
     * @return Mesaje de error.
     */
    @SuppressWarnings("GroovyAssignabilityCheck")
    private void procesarMensajeError(DateTime fecha) {
        String msj

        if (fecha) {
            msj = "para la fecha solicitada, [${convertirAString(fecha)}]"
        } else {
            msj = "vigente."
        }

        out.println("No existe un Listado de Pol\u00edticas de Castigo $msj", red)
    }

    /**
     * Convierte una fecha contenida en una cadena a {@link DateTime}
     *
     * @param valor Cadena que contiene la fecha.
     *
     * @return {@link DateTime}
     *
     * @throws IllegalArgumentException Si el formato no es válido o es una fecha futura.
     */
    private static DateTime convertirAFecha(String valor) {
        DateTime fecha

        try {
            fecha = DateTime.parse(valor)
        } catch (IllegalArgumentException | UnsupportedOperationException e) {
            throw new IllegalArgumentException(
                "El formato de la fecha [$valor] no es valido.\n${e.getLocalizedMessage()}")
        }

        if (fecha.isAfterNow()) {
            throw new IllegalArgumentException("La fecha de vigencia [${convertirAString(fecha)}] " +
                "no puede ser una fecha futura.")
        }

        fecha
    }

    private static String convertirAString(DateTime fecha) {
        fecha.toString(FORMATO_FECHA)
    }

    /**
     * Recupera las políticas de castigo vigentes o por fecha especificada.
     *
     * @param repositorio Repositorio para recuperar los datos.
     * @param fecha Fecha de consulta.
     */
    private void recuperarElementos(PoliticasCastigoRepository repositorio, DateTime fecha) {
        if (fecha) {
            List<PoliticasCastigo> resultado = repositorio.consultar(fecha)

            resultado.each {
                mostrarTablaResultados(it.factores, it.fechaListado)
            }
        } else {
            PoliticasCastigo resultado = repositorio.consultar()
            mostrarTablaResultados(resultado.factores, resultado.fechaListado)
        }
    }
}

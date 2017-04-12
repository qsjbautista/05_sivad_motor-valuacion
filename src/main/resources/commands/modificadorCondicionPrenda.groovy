/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package commands

import commands.util.ConvertirAFechaUtil
import commands.util.MostrarResultadosUtil
import commands.util.ReadObjecstFromString
import mx.com.nmp.ms.sivad.valuacion.dominio.exception.ListadoNoEncontradoException
import mx.com.nmp.ms.sivad.valuacion.dominio.factory.ListadoModificadorCondicionPrendaFactory
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ListadoModificadorCondicionPrenda
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ModificadorCondicionPrenda
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ModificadorCondicionPrendaFactory
import mx.com.nmp.ms.sivad.valuacion.dominio.repository.ModificadorCondicionPrendaRepository
import org.crsh.cli.*
import org.crsh.command.InvocationContext
import org.joda.time.LocalDate

/**
 * Utilizada por la consola CRaSH para la administración de los modificadores - condición prenda.
 *
 * @author ngonzalez
 */
@SuppressWarnings("GroovyUnusedDeclaration")
@Usage("Administracion del Listado de Modificadores - Condicion Prenda")
class modificadorCondicionPrenda {

    private static final String CONDICION_PRENDA = "condicion prenda"
    private static final String FACTOR = "factor"
    private static final List<String> HEADERS = ["Condicion Prenda", "Factor"]
    private static final List<String> NOMBRE_PROPIEDADES_MODIFICADOR_CONDICION_PRENDA = [CONDICION_PRENDA, FACTOR]
    private static final List<String> PROPIEDADES_MODIFICADOR_CONDICION_PRENDA = ["condicionPrenda", "factor"]



    // METODOS

    /**
     * Permite actualizar el listado de modificadores - condición prenda.
     *
     * @param context El contexto de la invocación.
     * @param contenido El nuevo contenido del listado.
     * @return El listado actualizado.
     */
    @Usage("Permite actualizar el Listado de Modificadores - Condicion Prenda")
    @Command
    def actualizar(InvocationContext context,
                   @Usage("Nuevo contenido del Listado de Modificadores - Condicion Prenda")
                   @Required @Argument String contenido) {

        ListadoModificadorCondicionPrenda listadoModificadorCondicionPrenda

        try {
            ReadObjecstFromString rof =
                new ReadObjecstFromString(contenido, 2, NOMBRE_PROPIEDADES_MODIFICADOR_CONDICION_PRENDA);
            List<Map<String, String>> objects = rof.readObjects()
            listadoModificadorCondicionPrenda = crearListado(objects, context)
        } catch (IllegalArgumentException e) {
            out.println("${e.getMessage()}")
            return
        }

        try {
            getModificadorCondicionPrendaRepository(context).actualizarListado(listadoModificadorCondicionPrenda)
            out.println("El Listado de Modificadores - Condicion Prenda fue actualizado correctamente.")
        } catch (Exception e) {
            e.printStackTrace()
            out.println("Ocurrio un error inesperado al actualizar el Listado de Modificadores - Condicion Prenda.")
        }
    }

    /**
     * Permite recuperar el listado de modificadores - condición prenda vigente o de alguna fecha de vigencia específica.
     *
     * @param context El contexto de la invocación.
     * @param fecha La fecha por la cual se quiere consultar el listado.
     * @param mostrarEnLista Permite indicar el formato de salida.
     * @return El listado de modificadores - condición prenda obtenido.
     */
    @Usage("Permite recuperar el Listado de Modificadores - Condicion Prenda vigente o de alguna fecha de vigencia especifica")
    @Command
    def consultar(InvocationContext context,
                  @Usage("Fecha de vigencia a consultar con formato yyyy-mm-dd")
                  @Option(names = ["f", "fecha"]) String fecha,
                  @Usage("Indica si el resultado se muestra en formato de lista")
                  @Option(names = ["l", "mostrarEnLista"]) Boolean mostrarEnLista) {

        LocalDate fechaFormat = null

        if (fecha) {
            try {
                fechaFormat = ConvertirAFechaUtil.convertirAFecha(fecha)
            } catch (IllegalArgumentException e) {
                out.println("${e.getMessage()}")
                return
            }
        }

        try {
            List<ListadoModificadorCondicionPrenda> elementos = recuperarElementos(context, fechaFormat);
            mostrarResultados(elementos, mostrarEnLista)
        } catch (ListadoNoEncontradoException e) {
            e.printStackTrace()
            procesarMensajeError(fechaFormat)
        }
    }

    /**
     * Permite recuperar los elementos del catálogos vigentes o por alguna fecha específica.
     *
     * @param context El contexto de la invocación.
     * @param fecha La fecha de consulta.
     * @return La lista de elementos.
     */
    private static List<ListadoModificadorCondicionPrenda> recuperarElementos(
        InvocationContext context,
        LocalDate fecha) {

        if (fecha) {
            getModificadorCondicionPrendaRepository(context).consultarListadoPorUltimaActualizacion(fecha).collect()
        } else {
            [getModificadorCondicionPrendaRepository(context).consultarListadoVigente()]
        }
    }

    /**
     * Metodo auxiliar utilizado para mostrar los resultados de la consulta de acuerdo al formato especificado.
     *
     * @param elementos La lista de elementos a mostrar.
     * @param mostrarEnLista Permite indicar el formato de salida.
     */
    @SuppressWarnings("GroovyAssignabilityCheck")
    private void mostrarResultados(
        List<ListadoModificadorCondicionPrenda> elementos,
        Boolean mostrarEnLista) {

        Collections.sort(elementos, new Comparator<ListadoModificadorCondicionPrenda>() {
            @Override
            int compare(ListadoModificadorCondicionPrenda o1, ListadoModificadorCondicionPrenda o2) {
                return o2.ultimaActualizacion <=> o1.ultimaActualizacion
            }
        })

        elementos.each {
            out.println("Fecha Vigencia: ${ConvertirAFechaUtil.convertirAString(it.ultimaActualizacion)}", green)
            out.println(MostrarResultadosUtil
                .mostrarResultados(HEADERS, it.modificadoresCondicionPrenda, PROPIEDADES_MODIFICADOR_CONDICION_PRENDA, mostrarEnLista))
        }
    }

    /**
     * Metodo auxiliar utilizado para crear el mensaje que se presenta cuando ocurre un error en
     * la consulta del catálogo.
     *
     * @param fecha La fecha de consulta.
     * @return El mensaje de error.
     */
    private static def procesarMensajeError(LocalDate fecha) {
        String msj

        if (fecha) {
            msj = "para la fecha solicitada."
        } else {
            msj = "vigente."
        }

        "No existe un Listado de Modificadores - Condicion Prenda $msj"
    }

    /**
     * Metodo auxiliar utilizado para crear el listado de modificadores - condición prenda.
     *
     * @param objects El contenido del listado a procesar.
     * @param context El contexto de la invocación.
     * @return El listado creado.
     */
    private static ListadoModificadorCondicionPrenda crearListado(
        List<Map<String, String>> objects,
        InvocationContext context) {

        Set<ModificadorCondicionPrenda> modificadores = new HashSet<>()

        objects.eachWithIndex { Map<String, String> entry, int i ->
            BigDecimal factor

            try {
                factor = entry[FACTOR].toBigDecimal()
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(
                    "El formato del $FACTOR [${entry[FACTOR]}] no es valido.\nEn $entry", e);
            }

            ModificadorCondicionPrenda modificador =
                ModificadorCondicionPrendaFactory.crear(entry[CONDICION_PRENDA], factor)
            modificadores.add(modificador)
        }

        getListadoModificadorCondicionPrendaFactory(context).crear(LocalDate.now(), modificadores)
    }

    /**
     * Permite obtener la referencia del repositorio {@link ModificadorCondicionPrendaRepository}.
     *
     * @param context El contexto de la invocación.
     * @return Referencia al repositorio de {@link ModificadorCondicionPrendaRepository}.
     */
    private static ModificadorCondicionPrendaRepository getModificadorCondicionPrendaRepository(
        InvocationContext context) {

        context.attributes['spring.beanfactory'].getBean(ModificadorCondicionPrendaRepository)
    }

    /**
     * Permite obtener la referencia del repositorio {@link ListadoModificadorCondicionPrendaFactory}.
     *
     * @param context El contexto de la invocación.
     * @return Referencia al repositorio de {@link ListadoModificadorCondicionPrendaFactory}.
     */
    private static ListadoModificadorCondicionPrendaFactory getListadoModificadorCondicionPrendaFactory(
        InvocationContext context) {

        context.attributes['spring.beanfactory'].getBean(ListadoModificadorCondicionPrendaFactory)
    }

}

/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.repository;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ListadoModificadorCondicionPrenda;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.ModificadorCondicionPrenda;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.vo.CondicionPrendaVO;
import org.joda.time.LocalDate;

import java.util.Set;

/**
 * Utilizado para canalizar el acceso a datos de modificadores - condición prenda.
 *
 * @author ngonzalez
 */
public interface ModificadorCondicionPrendaRepository {

    /**
     * Permite obtener el modificador - condición prenda vigente, que corresponda a las características indicadas.
     *
     * @param condicionPrendaVO Value Object con la condición física de la prenda.
     * @return El modificador - condición prenda obtenido.
     */
    ModificadorCondicionPrenda consultarModificadorCondicionPrendaVigente(
        CondicionPrendaVO condicionPrendaVO);

    /**
     * Permite obtener el listado vigente de modificadores - condición prenda.
     *
     * @return El listado vigente de modificadores - condición prenda.
     */
    ListadoModificadorCondicionPrenda consultarListadoVigente();

    /**
     * Permite obtener el conjunto de listados de modificadores - condición prenda que correspondan la fecha de
     * última actualización indicada.
     *
     * @param ultimaActualizacion La fecha de última actualización.
     * @return El conjunto de listados obtenido.
     */
    Set<ListadoModificadorCondicionPrenda> consultarListadoPorUltimaActualizacion(
        LocalDate ultimaActualizacion);

    /**
     * Permite actualizar el listado vigente con la información del listado que se recibe como parámetro.
     *
     * @param listado El nuevo listado de modificadores - condición prenda.
     * @return El listado de modificadores - condición prenda actualizado.
     */
    ListadoModificadorCondicionPrenda actualizarListado(
        ListadoModificadorCondicionPrenda listado);

}

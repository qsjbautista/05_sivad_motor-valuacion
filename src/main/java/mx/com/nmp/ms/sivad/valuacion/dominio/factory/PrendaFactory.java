/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package mx.com.nmp.ms.sivad.valuacion.dominio.factory;

import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Pieza;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.Prenda;
import mx.com.nmp.ms.sivad.valuacion.dominio.modelo.dto.PrendaDTO;

import java.util.List;

/**
 * Interface que define el contrato para la fabricación de entidades tipo {@link Prenda}.
 *
 * @author ngonzalez
 */
public interface PrendaFactory {

    /**
     * Permite crear una entidad de tipo {@link Prenda} con base en los argumentos recibidos.
     *
     * @param prendaDTO DTO con la información de la pieza de tipo prenda.
     * @return La entidad creada.
     */
    public Prenda create(PrendaDTO prendaDTO);

    /**
     * Permite crear una entidad de tipo {@link Prenda} con base en los argumentos recibidos.
     *
     * @param piezas Lista de piezas que conforman la prenda.
     * @param  condicionFisica Identificador de la condición fisica de la prenda.
     * @return La entidad creada.
     */
    public Prenda create(List<Pieza> piezas, String condicionFisica);

    /**
     * Permite crear una entidad de tipo {@link Prenda} con base en el builder recibido.
     *
     * @param builder El Builder con los atributos de la prenda.
     * @return La entidad creada.
     */
    public Prenda create(Prenda.Builder builder);

}

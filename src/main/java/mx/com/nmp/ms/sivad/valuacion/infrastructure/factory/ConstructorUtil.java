/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.infrastructure.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

/**
 * Clase de utilería para recuperar el constructores y crear instancias.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public final class ConstructorUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConstructorUtil.class);

    /**
     * Constructor, privado ya que no debe ser instanciada.
     */
    private ConstructorUtil() {
        super();
    }

    /**
     * Recupera el constructor de una clase, según el tipo de parámetros que recibe.
     *
     * @param tipo Clase de la cual se quiere recuperar el constructos.
     * @param tipoParametros Lista de clase de los parámetros del constructor.
     * @param <T> Tipo generico, espesifica el Tipo de constructor.
     *
     * @return Constructor<T>
     */
    public static <T> Constructor<T> getConstructor(final Class<T> tipo, final Class<?>... tipoParametros) {
        Constructor<T> constructor = null;
        try {
            constructor = tipo.getDeclaredConstructor(tipoParametros);
        } catch (NoSuchMethodException e) {
            LOGGER.error("Ocurrio un error inesperado al recuperar el contructor de la entidad.", e);
        }

        return constructor;
    }

    /**
     * Crea una instancia a partir del constructor y la lista de argumentos.
     *
     * @param constructor Constructor del objeto a crear.
     * @param argumentos Lista de argumentos del constructor.
     * @param <T> Tipo generico, especifica el Tipo de objeto a crear.
     *
     * @return Instancia del objeto creada.
     */
    public static <T> T getInstancia(final Constructor<T> constructor, final Object... argumentos) {
        T instancia = null;
        boolean isAccessible = constructor.isAccessible();

        try {
            setAccessible(constructor, true);

            instancia = constructor.newInstance(argumentos);
        } catch (Exception e) {
            LOGGER.error("Ocurrio un error inesperado al crear la instancia de la entidad.", e);
        } finally {
            setAccessible(constructor, isAccessible);
        }

        return instancia;
    }

    /**
     * Establece la bandera {@code accessible} al constructor.
     *
     * @param constructor Constructor
     * @param accessible Valor de la bandera {@code accessible}
     */
    private static void setAccessible(Constructor constructor, boolean accessible) {
        if (accessible != constructor.isAccessible()) {
            constructor.setAccessible(accessible);
        }
    }
}

/**
 * Proyecto:        NMP - Microservicio de Motor de Valuación
 * Quarksoft S.A.P.I. de C.V. – Todos los derechos reservados. Para uso exclusivo de Nacional Monte de Piedad.
 */
package commands.util

/**
 * Se encarga de leer una cadena de texto y recuperar las propiedades del los objetos que contiene.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
final class ReadObjecstFromString {
    private static final String OBJECT_SEPARATOR = "\n\n"
    private static final String PROPERTY_SEPARATOR = ":"
    private static final int PROPERTY_SIZE = 2

    private String contenido;
    private int requiredProperties;
    private List<String> findProperties;

    /**
     * Constructor.
     *
     * @param contenido Cadena de texto con la información.
     * @param requiredProperties Numero de propiedades requeridas para el objeto.
     * @param findProperties Propiedades a buscar en {@code contenido}
     *
     * @throws IllegalArgumentException Si la cadena {@code contenido} no tiene valor.
     * @throws IllegalArgumentException Si la lista {@code findProperties} no tiene valores.
     */
    ReadObjecstFromString(String contenido, int requiredProperties, List<String> findProperties) {
        if (!contenido.trim()) {
            throw new IllegalArgumentException("Final inesperado, la cadena no contiene elementos.")
        }

        if (findProperties.isEmpty()) {
            throw new IllegalArgumentException("Se debe indicar al menos una propiedad a buscar.")
        }

        this.contenido = contenido.normalize().trim()
        this.requiredProperties = requiredProperties

        this.findProperties = findProperties
    }

    /**
     * Recupera los objetos contenidos en la cadena de entrada {@link #contenido}
     *
     * @return Listado de los objetos encontrados, en formato de mapa
     *
     * @throws IllegalArgumentException Si el objeto encontrado no tiene al menos las propiedades
     * especidicadas por {@link #requiredProperties}
     * @throws IllegalArgumentException Si las propiedades del encontrado con cumplen con el formato 'propiedad:valor'
     */
    List<Map<String, String>> readObjects() {
        String[] strObjects = contenido.split(OBJECT_SEPARATOR)
        List<Map<String, String>> result = []
        int elemento = 0

        strObjects.each { String entry ->
            String props = entry.trim()

            if (props) {
                List<String> properties = readProperties(props, ++elemento)
                result << readObject(properties, elemento)
            }
        }

        result
    }

    /**
     * Lee las propiedades de un objeto.
     *
     * @param contenido Cadena con las propiedades del objeto.
     * @param elemento Numero de elemento que se esta procesando.
     *
     * @return Propiedades de un objeto.
     *
     * @throws IllegalArgumentException Si el objeto encontrado no tiene al menos las propiedades
     * especidicadas por {@link #requiredProperties}
     */
    private List<String> readProperties(String contenido, int elemento) {
        List<String> properties = contenido.readLines();

        if (properties.size() < requiredProperties) {
            throw new IllegalArgumentException(
                "Final inesperado, almenos $requiredProperties propiedades son requeridas, elemento: $elemento")
        }

        properties
    }

    /**
     * Cree aun objeto en formato mapa a partir de la lista de propiedades.
     *
     * @param properties Lista de propiedades del objeto.
     * @param elemento Numero de elemento que se esta procesando.
     *
     * @return Objeto en formato mapa
     *
     * @throws IllegalArgumentException Si las propiedades del encontrado con cumplen con el formato 'propiedad:valor'
     */
    private Map<String, String> readObject(List<String> properties, int elemento) {
        Map<String, String> mapObject = [:]
        properties.eachWithIndex { String entry, int i ->
            String[] property = entry.trim().split(PROPERTY_SEPARATOR)

            if (property.size() < PROPERTY_SIZE) {
                throw new IllegalArgumentException("""Final inesperado, propiedad no valida [$entry]
debe contener 'propiedad:valor', elemento: $elemento, linea: ${i + 1}""")
            }

            def (String k, String v) = property.collect() { String prop ->
                prop.trim()
            }

            String key = findProperty(k)
            mapObject."$key" = v
        }

        mapObject
    }

    /**
     * Verifica si la porpiedad encontrada es valida.
     *
     * @param property Propieadad encontrada.
     *
     * @return Propieadad esperada.
     *
     * @throws IllegalArgumentException Si la propiedad encontrada
     * no es valida (no es encuentra en las propiedades buscadas).
     */
    private String findProperty(String property) {
        String prop = null

        for (String e : findProperties) {
            if (e.equalsIgnoreCase(property)) {
                prop = e
                break
            }
        }

        if (prop) {
            prop
        } else {
            throw new IllegalArgumentException("Final inesperado, no se esperaba la propieadad [$property]")
        }
    }
}

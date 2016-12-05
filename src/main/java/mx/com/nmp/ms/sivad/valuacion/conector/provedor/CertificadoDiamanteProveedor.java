/*
 *
 * Microservicios - Motor de Valuación
 *
 * <p><b>Quarksoft Copyrigth © 2016</b></p>
 *
 */
package mx.com.nmp.ms.sivad.valuacion.conector.provedor;

/**
 * Contrato que deben seguir los proveedores de datos de Certificado de diamante.
 *
 * @author <a href="https://wiki.quarksoft.net/display/~cachavez">Carlos Chávez Melena</a>
 */
public interface CertificadoDiamanteProveedor {
    /**
     * Recupera el Certificado del diamante.
     *
     * @return Certificado del diamante.
     */
    String getCertificadoDiamante();
}

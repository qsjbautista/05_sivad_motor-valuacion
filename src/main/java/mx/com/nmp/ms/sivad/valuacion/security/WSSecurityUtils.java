package mx.com.nmp.ms.sivad.valuacion.security;

import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * WS Utils
 * @author Quarksoft
 *
 */
public class WSSecurityUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(WSSecurityUtils.class);

	/*static{
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
    }*/

    /**
     * Setea la direccion del endpoint
     * @param port
     * @param endpoint
     * @return
     */
    public static <T> T createService(T port, URL wsdlRemote, String apikeyHeader, String apiKeyValue, String ns) {
    	
    	BindingProvider bindingProvider = (BindingProvider) port;
    	
    	// Replace URL
    	String localUrl = (String) bindingProvider.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
    	String remoteUrl = wsdlRemote.toString().replaceAll("\\?wsdl", "");
    	LOGGER.info("Replacing endpoint: " + localUrl + "->" + remoteUrl);
    	bindingProvider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, remoteUrl);

    	return addHttpAPIKeyHeader(port, apikeyHeader, apiKeyValue, ns);
    }
    
    
	/**
	 * Agrega un http header con el API de autorizacion
	 * @param port
	 * @param apikeyHeader
	 * @param apiKeyValue
	 */
	@SuppressWarnings("rawtypes")
	private static <T> T addHttpAPIKeyHeader(T port, String apikeyHeader, String apiKeyValue, String ns) {
		LOGGER.info("Adding header and api key: " + apikeyHeader);

		BindingProvider bindingProvider = (BindingProvider)port;
		Binding binding = bindingProvider.getBinding();
	    List<Handler> handlerChain = binding.getHandlerChain();
	    handlerChain.add(new SOAPHeaderHandler(apikeyHeader, apiKeyValue, ns));
	    binding.setHandlerChain(handlerChain);
	    LOGGER.info("Added addHttpAPIKeyHeader");

	    return port;
	}


	/**
	 * SOAP Header Handler
	 * Se encarga de enviar http header con el API de autorizacion
	 * @author Quarksoft
	 */
	public static class SOAPHeaderHandler implements SOAPHandler<SOAPMessageContext> {

	    private String apikeyHeader;
	    private String apiKeyValue;
	    private String ns;

	    public SOAPHeaderHandler(final String apikeyHeader, final String apiKeyValue, String ns) {
	    	super();
		    this.apikeyHeader = apikeyHeader;
		    this.apiKeyValue = apiKeyValue;
		    this.ns = ns;
	    }

	    public Set<QName> getHeaders() {
	        return new HashSet<QName>();
	    }

		@SuppressWarnings("unchecked")
		public boolean handleMessage(SOAPMessageContext smc) {
	        Boolean outboundProperty = (Boolean)smc.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);
	        if (outboundProperty.booleanValue()) {
	        	try {
	        		SOAPMessage message = smc.getMessage();
	        		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
					SOAPHeader header = message.getSOAPHeader();
					if (header == null) {
						header = envelope.addHeader();
					}
					SOAPElement apiKeyElement = header.addChildElement(envelope.createName(apikeyHeader, "", ns));
					apiKeyElement.setValue(apiKeyValue);

	        		Map<String, List<String>> headers = (Map<String, List<String>>) smc.get(MessageContext.HTTP_REQUEST_HEADERS);
	                if (null == headers) {
	                    headers = new HashMap<String, List<String>>();
	                }
	                headers.put(apikeyHeader, Collections.singletonList(apiKeyValue));
	                LOGGER.info("Added " + apiKeyValue + " to headers");

	                // Remove action
	                headers.put("SOAPAction", Collections.singletonList(""));

	                smc.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

	        	} catch (Exception e) {
					e.printStackTrace();
				}
	        }
	        
	        return true;
	    }

	    public boolean handleFault(SOAPMessageContext smc) {
	        return true;
	    }

	    // nothing to clean up
	    public void close(MessageContext messageContext) {
	    }

	}

	public static String replaceHostInUrl(String originalURL, String newAuthority) {
		String host = null;
		try {
			URI uri = new URI(originalURL);
			uri = new URI(
				uri.getScheme().toLowerCase(Locale.US),
				newAuthority,
				uri.getPath(),
				uri.getQuery(),
				uri.getFragment()
			);
			host = uri.toString();
		}
		catch (Exception ex) {
			LOGGER.warn("Error al reemplazar WSDL address. {}", ex);
		}
		return host;
	}

}

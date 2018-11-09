package mx.com.nmp.ms.sivad.valuacion.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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


/**
 * WS Utils
 * @author Quarksoft
 *
 */
public class WSSecurityUtils {
	
	/*static{
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
    }*/

	/**
	 * Agrega un http header con el API de autorizacion
	 * @param port
	 * @param apikeyHeader
	 * @param apiKeyValue
	 */
	@SuppressWarnings("rawtypes")
	public static <T> void addHttpAPIKeyHeader(T port, String apikeyHeader, String apiKeyValue, String ns) {
		BindingProvider bindingProvider = (BindingProvider)port;
		Binding binding = bindingProvider.getBinding();
	    List<Handler> handlerChain = binding.getHandlerChain();
	    handlerChain.add(new SOAPHeaderHandler(apikeyHeader, apiKeyValue, ns));
	    binding.setHandlerChain(handlerChain);
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

	        		Map<String, List<String>> headers = (Map<String, List<String>>) smc.get(MessageContext.HTTP_RESPONSE_HEADERS);
	                if (null == headers) {
	                    headers = new HashMap<String, List<String>>();
	                    smc.put(MessageContext.HTTP_RESPONSE_HEADERS, headers);
	                }
	                headers.put(apikeyHeader, Collections.singletonList(apiKeyValue));

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

}

package pl.sfs.service;

import java.io.IOException;
import java.util.Map;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

/***
 * Klasa odpowiedzialna za wywolywanie uslugi internetowej przy uzyciu SOAP
 * 
 * @author Marcin Peck <marcinpeck@gmail.com>
 *
 */
public class SFSSoapClient {
	private static SFSSoapClient sfsClient = null;
	private SoapSerializationEnvelope envelope = null;
	private HttpTransportSE httpTransport = null;
	private static final String NAMESPACE = "http://tempuri.org/";
	private static final String WSDL_URL = "http://sfservice.azurewebsites.net/Service1.svc?singleWsdl";
	private final String ACTION_URL ="http://tempuri.org/IService1/";
	
	
	/***
	 * Zapewnienie SOAP klienta jako singleton
	 * 
	 * @return SFSSoapClient
	 */
	public static SFSSoapClient getInstace(){
		if (sfsClient == null){
			sfsClient = new SFSSoapClient();
			return sfsClient;
		}else{
			return sfsClient;
		}
	}
	
	public SFSSoapClient()
	{
		this.envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		this.envelope.dotNet = true;
		this.httpTransport = new HttpTransportSE(WSDL_URL);
	}
	
	/***
	 * Wywolanie zadania SOAP
	 * 
	 * @param methodName nazwa metody zdalnej
	 * @param parameters opcjonalne parametry
	 * @return SoapObject odpowiedz SOAP
	 * @throws SFSException wyjatek z informacja o nieudanej operacji, usluga nic nie zwroci
	 */
	public Object soapCall(String methodName, Map<Integer, Object[]> parameters) throws SFSException
	{
		SoapObject client = new SoapObject(this.NAMESPACE, methodName);
		this.envelope.bodyOut = client;
				
		if (parameters!=null && !parameters.isEmpty()){
			for (Map.Entry<Integer, Object[]> entry : parameters.entrySet()){
				client.addProperty((String)entry.getValue()[0], entry.getValue()[1]);
			}
		}
		
		try {
			this.httpTransport.call(ACTION_URL + methodName, this.envelope);
			return envelope.getResponse();
		} catch (HttpResponseException e) {
			e.printStackTrace();
			throw new SFSException("Nie udalo sie pobrac danych");
		} catch (IOException e) {
			e.printStackTrace();
			throw new SFSException("Nie udalo sie pobrac danych");
		} catch (XmlPullParserException e) {
			e.printStackTrace();
			throw new SFSException("Nie udalo sie pobrac danych");
		}
		
	}
	
	
}

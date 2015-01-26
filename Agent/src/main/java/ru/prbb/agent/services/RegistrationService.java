package ru.prbb.agent.services;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URI;
import java.util.Enumeration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * 
 * @author RBr
 *
 */
@Service
@EnableScheduling
public class RegistrationService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private boolean isRegistered;
	private URI server;

	@PostConstruct
	public void init() {
 		//String serverJobber = "http://172.23.149.175:8080/Jobber/Agents"; // мой
		//String serverJobber = "http://192.168.100.101:8080/Jobber/Agents"; // Облако
		String serverJobber = "http://172.16.15.36:10180/Jobber/Agents"; // Облако редирект

		try {
			Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
			while (nis.hasMoreElements()) {
				NetworkInterface ni = nis.nextElement();

				if (!ni.isUp() || ni.isLoopback() || ni.isPointToPoint() || ni.isVirtual()) {
					continue;
				}

				Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress inetAddress = inetAddresses.nextElement();
					
					if (inetAddress instanceof Inet6Address)
						continue;
					if (inetAddress.isAnyLocalAddress())
						continue;
					
					if ((inetAddress.getAddress()[0] & 0xff) == 172) {
						String localhost = inetAddress.getHostAddress();
						server = new URIBuilder(serverJobber).setParameter("host", localhost).build();
						return;
					}
				}
			}
		} catch (Exception e) {
			log.error("RegistrationService.init", e);
		}
	}

	/**
	 * Регистрация на сервере каждые 5 мин
	 */
	@Scheduled(fixedDelay = 5 * 60 * 1000, initialDelay = 1000)
	public void registration() {
		if (null == server) {
			log.warn("RegistrationService.registration: Public Network Interface not found.");
			return;
		}

		log.info("Registration URI is " + server);
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			HttpPut httpPut = new HttpPut(server);

			log.debug("Executing request " + httpPut.getRequestLine());

			String responseBody = httpclient.execute(httpPut, responseHandler);
			log.debug(responseBody);
			isRegistered = true;
		} catch (Exception e) {
			log.error("registration", e);
		}
	}

	@PreDestroy
	public void destroy() {
		if (null == server) {
			log.warn("RegistrationService.destroy: Public Network Interface not found.");
			return;
		}

		if (isRegistered) {
			log.info("Unregistration URI is " + server);
			
			try (CloseableHttpClient httpclient = HttpClients.createSystem()) {
				HttpDelete httpDelete = new HttpDelete(server);
				
				log.debug("Executing request " + httpDelete.getRequestLine());
				
				String responseBody = httpclient.execute(httpDelete, responseHandler);
				log.debug(responseBody);
			} catch (Exception e) {
				log.error("unregistration", e);
			}
		}
	}

	private ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

		public String handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity) : null;
			} else {
				String reason = response.getStatusLine().getReasonPhrase();
				throw new ClientProtocolException("Unexpected response status: " + status + ' ' + reason);
			}
		}

	};
}

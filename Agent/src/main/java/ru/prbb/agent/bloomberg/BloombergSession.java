package ru.prbb.agent.bloomberg;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import com.bloomberglp.blpapi.CorrelationID;
import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Event;
import com.bloomberglp.blpapi.Event.EventType.Constants;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Service;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;

/**
 * 
 * @author RBr
 * 
 */
public class BloombergSession {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final Session session;

	private Marker marker = MarkerFactory.getMarker("BLOOMBERG_SESSION");

	/**
	 * 
	 * @param name
	 *            описание сессии
	 */
	public BloombergSession(String name) {
		final SessionOptions sesOpt = new SessionOptions();
		sesOpt.setServerHost("localhost");
		sesOpt.setServerPort(8194);
		session = new Session(sesOpt);
		log.info(marker, name);
	}

	/**
	 * 
	 * @param name
	 *            описание сессии
	 * @param host
	 *            хост
	 * @param port
	 *            порт
	 */
	public BloombergSession(String name, String host, int port) {
		final SessionOptions sesOpt = new SessionOptions();
		sesOpt.setServerHost(host);
		sesOpt.setServerPort(port);
		session = new Session(sesOpt);
		log.info(marker, name);
	}

	public boolean start() {
		log.info(marker, "Start session");
		try {
			return session.start();
		} catch (Exception e) {
			log.error(marker, "Start session", e);
			throw new RuntimeException(e);
		}
	}

	public void stop() {
		log.info(marker, "Stop session");
		try {
			session.stop();
		} catch (InterruptedException e) {
			log.error(marker, "Stop session", e);
			throw new RuntimeException(e);
		}
	}

	public boolean openService(String uri) {
		log.info(marker, "Open servise " + uri);
		try {
			if (session.openService(uri)) {
				return true;
			}
			throw new RuntimeException("Servise " + uri + " unavailable");
		} catch (Exception e) {
			log.error(marker, "Open service", e);
			throw new RuntimeException(e);
		}
	}

	public Service getService(String uri) {
		log.info(marker, "Get servise " + uri);
		try {
			if (session.openService(uri)) {
				return session.getService(uri);
			}
			throw new RuntimeException("Servise " + uri + " unavailable");
		} catch (Exception e) {
			log.error(marker, "Get service", e);
			throw new RuntimeException(e);
		}
	}

	public Request createRequest(String uri, String operationName) {
		return getService(uri).createRequest(operationName);
	}

	public void sendRequest(Request request, MessageHandler messageHandler) {
		sendRequest(request, messageHandler, new CorrelationID(1));
	}

	public void sendRequest(Request request, MessageHandler messageHandler, CorrelationID correlationId) {
		log.info(marker, "Send request:");
		log.info(marker, request.toString());

		try {
			session.sendRequest(request, correlationId);
		} catch (IOException e) {
			log.error(marker, "Send request", e);
			throw new RuntimeException(e);
		}

		try {
			boolean continueToLoop = true;
			while (continueToLoop) {
				final Event event = session.nextEvent();

				switch (event.eventType().intValue()) {
				case Constants.RESPONSE: // final response
					continueToLoop = false;
				case Constants.PARTIAL_RESPONSE:
					for (Message message : event) {
						log.info(marker, event.eventType().toString());
						log.info(marker, message.toString());

						if (message.hasElement("responseError")) {
							final Element re = message.getElement("responseError");
							throw new RuntimeException(re.getElementAsString("message"));
						}

						messageHandler.processMessage(message);
					}
					break;
				default:
					log.info(marker, event.eventType().toString());
					for (Message message : event) {
						log.info(marker, message.toString());
					}
					break;
				}
			}
		} catch (InterruptedException e) {
			log.error(marker, "Next event", e);
			throw new RuntimeException(e);
		}
	}

	public static String getElementAsString(Element element, String name) {
		try {
			return element.getElementAsString(name);
		} catch (Exception e) {
			return "";
		}
	}

	public static double getElementAsFloat64(Element element, String name) {
		try {
			return element.getElementAsFloat64(name);
		} catch (Exception e) {
			return 0;
		}
	}

	public static String getValueAsString(Element element) {
		try {
			return element.getValueAsString();
		} catch (Exception e) {
			return "";
		}
	}
}

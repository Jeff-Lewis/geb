package ru.prbb.agent.requests;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
abstract class BloombergRequest {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	private Session session;

	protected abstract void execute(String name);

	protected abstract void process(Message message);

	protected boolean start(String name) {
		try {
			final SessionOptions sessionOptions = new SessionOptions();
			sessionOptions.setServerHost("localhost");
			sessionOptions.setServerPort(8194);
			session = new Session(sessionOptions);
			log.info("Start session " + name);
			return session.start();
		} catch (Exception e) {
			log.error("Start session " + name, e);
		}
		return false;
	}

	protected void stop() {
		try {
			session.stop();
			log.info("Stop session");
		} catch (InterruptedException e) {
			log.error("Stop session", e);
		}
	}

	protected boolean openService(String uri) {
		try {
			if (session.openService(uri)) {
				log.info("Open servise " + uri);
				return true;
			}
			log.error("Servise '" + uri + "' unavailable");
		} catch (Exception e) {
			log.error("Open service", e);
		}
		return false;
	}

	protected Service getService(String uri) {
		try {
			log.info("Get servise " + uri);
			if (session.openService(uri)) {
				return session.getService(uri);
			}
			throw new RuntimeException("Servise '" + uri + "' unavailable");
		} catch (Exception e) {
			log.error("Get service", e);
			throw new RuntimeException(e);
		}
	}

	protected Request createRequest(String uri, String operationName) {
		return getService(uri).createRequest(operationName);
	}

	protected void sendRequest(Request request, CorrelationID correlationId) {
		log.info("Send request:");
		log.info(request.toString());

		try {
			session.sendRequest(request, correlationId);
		} catch (IOException e) {
			log.error("Send request", e);
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
						log.info(event.eventType().toString());
						log.info(message.toString());

						if (message.hasElement("responseError")) {
							final Element re = message.getElement("responseError");
							throw new RuntimeException(re.getElementAsString("message"));
						}

						process(message);
					}
					break;
				default:
					log.info(event.eventType().toString());
					for (Message message : event) {
						log.info(message.toString());
					}
					break;
				}
			}
		} catch (InterruptedException e) {
			log.error("Next event", e);
			throw new RuntimeException(e);
		}
	}

	protected static String getElementAsString(Element element, String name) {
		try {
			return element.getElementAsString(name);
		} catch (Exception e) {
			return "";
		}
	}

	protected static double getElementAsFloat64(Element element, String name) {
		try {
			return element.getElementAsFloat64(name);
		} catch (Exception e) {
			return 0;
		}
	}

	protected static String getValueAsString(Element element) {
		try {
			return element.getValueAsString();
		} catch (Exception e) {
			return "";
		}
	}
}

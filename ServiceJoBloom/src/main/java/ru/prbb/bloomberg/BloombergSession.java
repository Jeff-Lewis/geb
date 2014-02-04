package ru.prbb.bloomberg;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
	final static Log log = LogFactory.getLog(BloombergSession.class);

	private final Session session;

	private PrintStream prn = createPrintStream();

	@Override
	protected void finalize() throws Throwable {
		if ((prn != null) && (prn != System.out)) {
			prn.close();
		}
		super.finalize();
	}

	private static PrintStream createPrintStream() {
		try {
			final File d = new File("D:/WEB/bloomberg_log");
			if (!d.isDirectory()) {
				d.mkdirs();
			}
			final String fn = new SimpleDateFormat("yyyyMMdd-HHmmss.SSS").format(new Date()) + ".txt";
			final File f = new File(d, fn);
			log.debug("Create bloomberg log file " + f);
			return new PrintStream(f);
		} catch (FileNotFoundException e) {
			log.error(e);
		}
		return System.out;
	}

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
		if (prn != null) {
			prn.println(name);
		}
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
		if (prn != null) {
			prn.println(name);
		}
	}

	public boolean start() {
		if (prn != null) {
			prn.println("Start session");
		}
		try {
			return session.start();
		} catch (Exception e) {
			if (prn != null) {
				prn.println(e);
			}
			log.error(e);
			throw new RuntimeException(e);
		}
	}

	public void stop() {
		if (prn != null) {
			prn.println("Stop session.");
			if (prn != System.out) {
				prn.close();
			}
			prn = null;
		}
		try {
			session.stop();
		} catch (InterruptedException e) {
			if (prn != null) {
				prn.println(e);
			}
			log.error(e);
			throw new RuntimeException(e);
		}
	}

	public boolean openService(String uri) {
		if (prn != null) {
			prn.println("Open servise " + uri);
		}
		try {
			if (session.openService(uri)) {
				return true;
			}
			throw new RuntimeException("Servise " + uri + " unavailable");
		} catch (Exception e) {
			if (prn != null) {
				prn.println(e);
			}
			log.error(e);
			throw new RuntimeException(e);
		}
	}

	public Service getService(String uri) {
		if (prn != null) {
			prn.println("Get servise " + uri);
		}
		try {
			if (session.openService(uri)) {
				return session.getService(uri);
			}
			throw new RuntimeException("Servise " + uri + " unavailable");
		} catch (Exception e) {
			if (prn != null) {
				prn.println(e);
			}
			log.error(e);
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
		if (prn != null) {
			prn.println("Send request:");
			prn.println(request);
		}

		try {
			session.sendRequest(request, correlationId);
		} catch (IOException e) {
			if (prn != null) {
				prn.println(e);
			}
			log.error(e);
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
						if (prn != null) {
							prn.println(event.eventType());
							prn.println(message);
						}

						if (message.hasElement("responseError")) {
							final Element re = message.getElement("responseError");
							throw new RuntimeException(re.getElementAsString("message"));
						}

						messageHandler.processMessage(message);
					}
					break;
				default:
					if (prn != null) {
						prn.println(event.eventType());
						for (Message message : event) {
							prn.println(message);
						}
					}
					break;
				}
			}
		} catch (InterruptedException e) {
			if (prn != null) {
				prn.println(e);
			}
			log.error(e);
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

package ru.prbb.agent.services;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * Параметры агента
 * 
 * @author RBr
 */
@Service
public class SetupAgent implements CommandLineRunner {

	private final Log log = LogFactory.getLog(getClass());

	private String hostAgent = "localhost";
	private int portAgent = 48080;
	private String hostServer = null;
	private int portServer = 48080;
	private boolean isMaster = false;
	private boolean isPassive = false;

	public String getHostAgent() {
		return hostAgent;
	}

	public int getPortAgent() {
		return portAgent;
	}

	public String getHostServer() {
		return hostServer;
	}

	public int getPortServer() {
		return portServer;
	}

	public boolean isMaster() {
		return isMaster;
	}

	public boolean isSlave() {
		return !isMaster;
	}

	public boolean isPassive() {
		return isPassive;
	}

	/**
	 * Обработка командной строки
	 */
	@Override
	public void run(String... args) throws Exception {
		hostAgent = InetAddress.getLocalHost().getHostAddress();

		Iterator<String> it = Arrays.asList(args).iterator();
		while (it.hasNext()) {
			String arg = it.next();
			log.trace("Get argument " + arg);
			switch (arg.toLowerCase()) {
			case "-server":
				if (it.hasNext()) {
					arg = it.next();
					log.trace("Get argument " + arg);
					InetAddress address = InetAddress.getByName(arg);
					hostServer = address.getHostAddress();
				} else {
					log.warn("Specify server host ip.");
				}
				break;

			case "-master":
				isMaster = true;
				break;

			case "-passive":
				isPassive = true;

			default:
				break;
			}
		}

		if (isMaster) {
			hostServer = hostAgent;
		}

		log.info("master:" + isMaster);
		log.info("server:" + hostServer);
		log.info("agent:" + hostAgent);
		log.info("passive:" + isPassive);
	}
}

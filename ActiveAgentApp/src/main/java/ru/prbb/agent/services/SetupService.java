package ru.prbb.agent.services;

import java.io.IOException;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

/**
 * Параметры агента
 * 
 * @author RBr
 */
@Service
public class SetupService implements CommandLineRunner {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private String hostAgent = null;
	private Integer portAgent = 48080;

	private String hostServer = "172.16.153.164";
	private int portServer = 8080;

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

	/**
	 * Обработка командной строки
	 */
	@Override
	public void run(String... args) throws Exception {
		Iterator<String> it = Arrays.asList(args).iterator();
		while (it.hasNext()) {
			String arg = it.next();
			log.info("Get argument " + arg);

			switch (arg.toLowerCase()) {
			case "-server":
				if (it.hasNext()) {
					arg = it.next();
					log.info("Server host " + arg);
					InetAddress address = InetAddress.getByName(arg);
					hostServer = address.getHostAddress();
				} else {
					log.warn("Specify server host ip.");
				}
				break;

			case "-port":
				if (it.hasNext()) {
					arg = it.next();
					log.info("Server port " + arg);
					portServer = Integer.parseInt(arg);
				} else {
					log.warn("Specify server host port.");
				}
				break;

			default:
				break;
			}
		}

		hostAgent = getLocalHostAddress();

		log.info("server:" + hostServer + ":" + portServer);
		log.info("agent:" + hostAgent + ":" + portAgent);
	}

	private String getLocalHostAddress() throws IOException {
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
					return inetAddress.getHostAddress();
				}
			}
		}
		return InetAddress.getLocalHost().getHostAddress();
	}
}

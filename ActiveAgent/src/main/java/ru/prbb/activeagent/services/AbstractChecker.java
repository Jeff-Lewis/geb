/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.prbb.activeagent.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author ruslan
 */
abstract class AbstractChecker implements Runnable {

    protected final Logger logger = Logger.getLogger(getClass().getName());

    protected final URI uri;
    protected final ObjectMapper mapper;

    private final ScheduledExecutorService exec;
    private ScheduledFuture<?> ft;
	private boolean isRun;

    protected AbstractChecker(String host) throws URISyntaxException {
        uri = new URI(host);
        mapper = new ObjectMapper();
        //mapper.setDateFormat(new SimpleDateFormat("yyyyMMdd"));
        exec = Executors.newSingleThreadScheduledExecutor();
    }

	public URI getUri() {
		return uri;
	}

	@Override
    public String toString() {
        String status = (ft == null) ? "stop" : isRun ? "work" : "wait";
		return "[ " + status + " ]   " + uri;
    }

    public void start() {
        if (ft == null) {
            ft = exec.scheduleWithFixedDelay(this, 0, getDelaySec(), TimeUnit.SECONDS);
        }
    }

	public void stop() {
        if (ft != null) {
        	ft.cancel(false);
        	ft = null;
        }
	}

    @Override
    public void run() {
    	isRun = true;
        try (CloseableHttpClient httpClient = createHttpClient()) {
            check(httpClient);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    	isRun = false;
    }

    protected abstract int getDelaySec();
    protected abstract void check(CloseableHttpClient httpClient) throws Exception;

    private CloseableHttpClient createHttpClient() {
        return HttpClients.createDefault();
    }
}

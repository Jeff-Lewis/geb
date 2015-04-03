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
    protected final ScheduledExecutorService exec;
    protected final ObjectMapper mapper;

    protected ScheduledFuture ft;
    protected String status = "stop";

    protected AbstractChecker(String host) throws URISyntaxException {
        uri = new URI(host);
        exec = Executors.newSingleThreadScheduledExecutor();
        mapper = new ObjectMapper();
        //mapper.setDateFormat(new SimpleDateFormat("yyyyMMdd"));
    }

    @Override
    public String toString() {
        return uri + "   [ " + status + "]";
    }

    public void start() {
        if (ft == null) {
            ft = exec.scheduleWithFixedDelay(this, 0, 5, TimeUnit.SECONDS);
            status = "wait";
        }
    }

    public void stop() {
        exec.shutdown();
        ft = null;
        status = "stop";
    }

    @Override
    public void run() {
        status = "work";
        try (CloseableHttpClient httpClient = createHttpClient()) {
            run(httpClient);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        }
        status = "wait";
    }

    protected abstract void run(CloseableHttpClient httpClient) throws Exception;

    private CloseableHttpClient createHttpClient() {
        return HttpClients.createDefault();
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.prbb.activeagent.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author ruslan
 */
public class JobberChecker extends AbstractChecker {

    public JobberChecker(String host, String path) throws URISyntaxException {
        super("http://" + host + path + "/AgentTask");
    }

    @Override
    protected void run(CloseableHttpClient httpClient) throws IOException {
        String body = "";

        HttpGet request = new HttpGet(uri);
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            StatusLine statusLine = response.getStatusLine();

            int statusCode = statusLine.getStatusCode();
            if (statusCode < 200 || statusCode >= 300) {
                String reason = statusLine.getReasonPhrase();
                logger.log(Level.SEVERE, "SubscriptionChecker response status: {0} {1}",
                        new Object[]{statusCode, reason});
                return;
            }

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                body = EntityUtils.toString(entity);
            }
        }

        if (null == body || body.isEmpty()) {
            status = "Ожидание";
            return;
        }

        logger.info(body);
        @SuppressWarnings("unchecked")
        Map<String, Object> task = mapper.readValue(body, HashMap.class);

        if (task == null) {
            status = "Ожидание";
            return;
        }

        String type = (String) task.get("type");
        String idTask = task.get("idTask").toString();
        logger.log(Level.INFO, "Process task {0} {1}", new Object[]{type, idTask});
    }

}

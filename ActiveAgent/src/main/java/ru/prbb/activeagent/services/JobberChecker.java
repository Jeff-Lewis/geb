/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.prbb.activeagent.services;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.type.TypeReference;
import ru.prbb.activeagent.data.JobberTask;

/**
 * @author ruslan
 */
public class JobberChecker extends AbstractChecker {

    public JobberChecker(String host, String path) throws URISyntaxException {
        super("http://" + host + path + "/AgentTask");
    }

    @Override
    protected int getDelaySec() {
        return 3;
    }

    private ResponseHandler<JobberTask> taskHandler = new ResponseHandler<JobberTask>() {

        @Override
        public JobberTask handleResponse(HttpResponse response) {
            try {
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode < 200 || statusCode >= 300) {
                    String reason = statusLine.getReasonPhrase();
                    throw new Exception("Response status: " + statusCode + " " + reason);
                }
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String body = EntityUtils.toString(entity);

                    return mapper.readValue(body,
                            new TypeReference<JobberTask>() {
                            });
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "JobberChecker", ex);
            }
            return null;
        }
    };

    @Override
    protected void check(CloseableHttpClient httpClient) throws IOException {
        HttpGet request = new HttpGet(uri);
        JobberTask task = httpClient.execute(request, taskHandler);

        if (task == null) {
            return;
        }

        logger.log(Level.INFO, "Process task {0} {1}", new Object[]{task.getType(), task.getId()});
    }

}

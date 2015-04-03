/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.prbb.activeagent.services;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.type.TypeReference;
import ru.prbb.activeagent.data.SecurityItem;
import ru.prbb.activeagent.data.SubscriptionItem;
import static ru.prbb.activeagent.data.SubscriptionItem.RUNNING;
import static ru.prbb.activeagent.data.SubscriptionItem.STOPPED;

/**
 *
 * @author ruslan
 */
public class SubscriptionChecker extends AbstractChecker {

    private final List<SubscriptionRunner> subs = new ArrayList<>();

    public SubscriptionChecker(String host) throws URISyntaxException {
        super("http://" + host + "/Jobber/Subscription");
    }

    @Override
    protected void run(CloseableHttpClient httpClient) throws Exception {
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

//        @SuppressWarnings("unchecked")
//        List<Map<String, Object>> list = mapper.readValue(body, ArrayList.class);
        List<SubscriptionItem> list = mapper.readValue(body,
                new TypeReference<ArrayList<SubscriptionItem>>() {
                });

        if (list == null) {
            status = "Ожидание";
            return;
        }

        for (SubscriptionItem item : list) {
//            Long id = Long.valueOf(String.valueOf(map.get("id")));
//            SubscriptionItem item = new SubscriptionItem(uri, id);
//            item.setName(String.valueOf(map.get("name")));
//            item.setComment(String.valueOf(map.get("comment")));
//            item.setStatus(String.valueOf(map.get("status")));

            int idx = -1;

            for (int i = 0; i < subs.size(); ++i) {
                SubscriptionRunner runner = subs.get(i);
                if (runner.getId().equals(item.getId())) {
                    idx = i;
                    break;
                }
            }

            if (idx >= 0) {
                SubscriptionRunner runner = subs.get(idx);
                runner.setName(item.getName());
                runner.setComment(item.getComment());

                if (runner.isStopped()) {
                    if (isRunning(item)) {
                        start(httpClient, runner);
                    }
                } else {
                    if (isRunning(item)) {
                        start(httpClient, runner);
                    } else {
                        runner.stop();
                    }
                }
            } else {
                if (isRunning(item)) {
                    SubscriptionRunner runner = new SubscriptionRunner(uri, item);
                    subs.add(runner);
                    start(httpClient, runner);
                }
            }
        }
    }

    private boolean isRunning(SubscriptionItem item) {
        if (RUNNING.equals(status)) {
            return true;
        }
        if (STOPPED.equals(status)) {
            return false;
        }
        throw new RuntimeException("Unknown subscription status: " + status);
    }

    private void start(CloseableHttpClient httpClient, SubscriptionRunner runner) throws Exception {
        String body = "";

        HttpGet request = new HttpGet(runner.getURI());
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

//        @SuppressWarnings("unchecked")
//        List<Map<String, Object>> list = mapper.readValue(body, ArrayList.class);
//
//        Set<SecurityItem> secs = new HashSet<>(list.size());
//        for (Map<String, Object> map : list) {
//            SecurityItem sec = new SecurityItem();
//            sec.setId(Long.valueOf(String.valueOf(map.get("id"))));
//            sec.setCode(String.valueOf(map.get("code")));
//            sec.setName(String.valueOf(map.get("name")));
//            secs.add(sec);
//         }
        Set<SecurityItem> secs = mapper.readValue(body,
                new TypeReference<HashSet<SecurityItem>>() {
                });

        runner.start(secs);
    }

}

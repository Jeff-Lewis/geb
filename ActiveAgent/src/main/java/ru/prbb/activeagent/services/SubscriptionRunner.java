/**
 *
 */
package ru.prbb.activeagent.services;

import com.bloomberglp.blpapi.CorrelationID;
import com.bloomberglp.blpapi.Event;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;
import com.bloomberglp.blpapi.Subscription;
import com.bloomberglp.blpapi.SubscriptionList;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import ru.prbb.activeagent.data.SecurityItem;
import ru.prbb.activeagent.data.SubscriptionItem;

/**
 * @author RBr
 */
public class SubscriptionRunner implements Runnable {

    protected final Logger logger = Logger.getLogger(getClass().getName());

    private final URI uri;
    private final SubscriptionItem item;
    private final ExecutorService es;

    private final Set<SecurityItem> securities = new HashSet<>();

    private Session session;
    private SubscriptionList subscriptions;

    public SubscriptionRunner(URI uri, SubscriptionItem item) throws URISyntaxException {
        String path = uri.getPath() + "/" + item.getId();
        this.uri = new URIBuilder(uri).setPath(path).build();
        this.item = item;
        this.es = Executors.newSingleThreadExecutor();
    }

    public URI getURI() {
        return uri;
    }

    public Long getId() {
        return item.getId();
    }

    void setName(String name) {
        item.setName(name);
    }

    void setComment(String comment) {
        item.setComment(comment);
    }

    public void start(Set<SecurityItem> secs) throws Exception {
        if (secs.isEmpty()) {
            return;
        }
        if (isRunning() && secs.equals(securities)) {
            return;
        }

        securities.clear();
        securities.addAll(secs);

        if (null == session) {
            openSession();
        } else {
            session.resubscribe(newSubscrList(securities));
        }
    }

    private void openSession() throws Exception {
        SessionOptions sessionOptions = new SessionOptions();
        sessionOptions.setServerHost("localhost");
        sessionOptions.setServerPort(8194);
        sessionOptions.setAutoRestartOnDisconnection(true);

        session = new Session(sessionOptions);
        try {
            if (!session.start()) {
                throw new Exception("Failed to start session for " + item.getName());
            }

            if (!session.openService("//blp/mktdata")) {
                throw new Exception("Failed to open //blp/mktdata for " + item.getName());
            }

            session.subscribe(newSubscrList(securities));

            es.execute(this);

            logger.log(Level.INFO, "Subscribe started " + item.getName() + " " + uri);
        } catch (Exception e) {
        	logger.log(Level.SEVERE, e.getMessage());
            session.stop();
            session = null;
        }
    }

    public void stop() {
    	logger.info(uri + " Stop " + item.getName());
    	isRun = false;
        try {
        	if (subscriptions != null) {
				session.unsubscribe(subscriptions);
			}
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Subscribe stopped " + item.getName(), ex);
        }
        try {
            if (session != null) {
				session.stop();
			}
        } catch (InterruptedException ex) {
            logger.log(Level.SEVERE, "Subscribe stopped " + item.getName(), ex);
        }
        session = null;
    }

    public boolean isRunning() {
        return session != null;
    }

    public boolean isStopped() {
        return !isRunning();
    }

    @Override
    public String toString() {
        return "Subscription [id=" + item.getId() + ", name=" + item.getName()
                + ", comment=" + item.getComment() + ", status=" + item.getStatus() + "]";
    }

    private SubscriptionList newSubscrList(Set<SecurityItem> securities) {
		subscriptions = new SubscriptionList();
        for (SecurityItem securityItem : securities) {
            String code = securityItem.getCode();

            Subscription subscription = new Subscription(code,
                    "LAST_PRICE,RT_PX_CHG_PCT_1D", "interval=25.0", new CorrelationID(securityItem.getCode()));

            subscriptions.add(subscription);
        }
        return subscriptions;
    }

    private boolean isRun;

    @Override
    public void run() {
        try (CloseableHttpClient httpClient = createHttpClient()) {
        	isRun = true;
            while (isRun) {
                Event event = session.nextEvent();
                Event.EventType eventType = event.eventType();
                switch (eventType.intValue()) {
                    case Event.EventType.Constants.SUBSCRIPTION_DATA:
                    case Event.EventType.Constants.SUBSCRIPTION_STATUS:
                        processSubscription(httpClient, event);
                        break;
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        } finally {
            stop();
        }
    }

    private CloseableHttpClient createHttpClient() {
        return HttpClients.createDefault();
    }

    private String getElementAsString(Message msg, String name) {
        try {
            return msg.getElementAsString(name);
        } catch (Exception e) {
            return null;
        }
    }

    private void processSubscription(CloseableHttpClient httpClient, Event event) throws Exception {
        StringBuilder data = new StringBuilder();

        for (Message msg : event) {
            if ("SubscriptionFailure".equals(msg.messageType().toString())) {
                String description = msg.getElement("reason").getElementAsString("description");
                logger.log(Level.SEVERE, "SubscriptionFailure:" + description);
                continue;
            }

            if (msg.hasElement("LAST_PRICE") && msg.hasElement("RT_PX_CHG_PCT_1D")) {
                String security_code = msg.correlationID().object().toString();
                String last_price = getElementAsString(msg, "LAST_PRICE");
                String last_chng = getElementAsString(msg, "RT_PX_CHG_PCT_1D");

                String update = security_code + '\t' + last_price + '\t' + last_chng;
                data.append(update).append('\n');
            }
        }

        if (data.length() > 1) {
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("data", data.toString()));

            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            //httpPost.setEntity(new StringEntity(data.toString(), "UTF-8"));

            String response = httpClient.execute(httpPost, updateHandler);
            if ("OK".equals(response)) {
                String time = sdf.format(new Date());
                logger.log(Level.INFO, time + uri + " - " + response + "\n" + data);
            } else {
                if (!response.isEmpty()) {
                    logger.severe(response);
                }
            }
        }
    }

    private ResponseHandler<String> updateHandler = new ResponseHandler<String>() {

        @Override
        public String handleResponse(HttpResponse response) {
            try {
                StatusLine statusLine = response.getStatusLine();
                int statusCode = statusLine.getStatusCode();
                if (statusCode < 200 || statusCode >= 300) {
                    String reason = statusLine.getReasonPhrase();
                    throw new Exception("Response status: " + statusCode + " " + reason);
                }
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity);
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, "Update subscription " + item.getName(), ex);
            }
            return "";
        }
    };

    private SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm:ss ");

}

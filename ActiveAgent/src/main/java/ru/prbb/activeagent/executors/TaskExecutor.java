package ru.prbb.activeagent.executors;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import ru.prbb.activeagent.data.TaskItem;

import com.bloomberglp.blpapi.CorrelationID;
import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Event;
import com.bloomberglp.blpapi.Event.EventType.Constants;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;
import com.bloomberglp.blpapi.Session;
import com.bloomberglp.blpapi.SessionOptions;
import java.text.SimpleDateFormat;
import java.util.Date;
import ru.prbb.activeagent.MainJFrame;

public abstract class TaskExecutor {

    protected final Logger logger = Logger.getLogger(getClass().getName());

    private final String type;
    protected final ObjectMapper mapper;

    private URI uriTask;
    private CloseableHttpClient httpClient;

    protected TaskExecutor(String type) {
        this.type = type;
        mapper = new ObjectMapper();
    }

    protected Session startSession() throws IOException, InterruptedException {
        final SessionOptions sesOpt = new SessionOptions();
        sesOpt.setServerHost("localhost");
        sesOpt.setServerPort(8194);

        Session session = new Session(sesOpt);

        if (session.start()) {
            return session;
        }

        throw new IOException("Unable to start bloomberg session for " + sesOpt.getServerHost());
    }

    public String getType() {
        return type;
    }

    public void setUri(URI uriTask) {
        this.uriTask = uriTask;
    }

    public void setHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public String sendError(String message) {
        return send("ERROR:" + message);
    }

    public String send(String data) {
        try {
            HttpPost requestDone = new HttpPost(uriTask);
            requestDone.setEntity(new StringEntity(data));
            String taskDone = httpClient.execute(requestDone, taskDoneHandler);
            return taskDone;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Send data " + data, e);
        }
        return "";
    }

    private ResponseHandler<String> taskDoneHandler = new ResponseHandler<String>() {

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
                logger.log(Level.SEVERE, "JobberChecker", ex);
            }
            return "";
        }
    };

    public abstract void execute(TaskItem task, String data) throws Exception;

    protected void sendRequest(Session session, Request request) throws Exception {
        sendRequest(session, request, new CorrelationID());
    }

    protected void sendRequest(Session session, Request request, CorrelationID correlationId) throws Exception {
        final boolean isShowTask = Boolean.getBoolean(MainJFrame.IS_SHOW_TASK);
        if (isShowTask) {
            String time = sdf.format(new Date());
            logger.log(Level.INFO, "{0} bloomberg request\n{1}", new Object[]{time, request});
        }

        session.sendRequest(request, correlationId);

        boolean continueToLoop = true;
        while (continueToLoop) {
            final Event event = session.nextEvent();

            if (isShowTask) {
                String time = sdf.format(new Date());
                logger.log(Level.INFO, "{0} bloomberg event\n{1}", new Object[]{time, event});
            }

            switch (event.eventType().intValue()) {
                case Constants.RESPONSE: // final response
                    continueToLoop = false;
                case Constants.PARTIAL_RESPONSE:
                    for (Message message : event) {

                        if (message.hasElement("responseError")) {
                            final Element re = message.getElement("responseError");
                            throw new RuntimeException(re.getElementAsString("message"));
                        }

                        processMessage(message);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    protected abstract void processMessage(Message message);

    protected String getElementAsString(Element fs, String name) {
        try {
            return fs.getElementAsString(name);
        } catch (Exception ignore) {
        }
        return "";
    }

    private final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm:ss");
}

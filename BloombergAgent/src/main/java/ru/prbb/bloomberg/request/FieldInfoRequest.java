/**
 * 
 */
package ru.prbb.bloomberg.request;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ru.prbb.bloomberg.core.BloombergRequest;
import ru.prbb.bloomberg.core.BloombergSession;
import ru.prbb.bloomberg.core.MessageHandler;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;

/**
 * Ввод нового параметра
 * 
 * @author RBr
 * 
 */
public class FieldInfoRequest implements BloombergRequest, MessageHandler {
	private static final Log log = LogFactory.getLog(FieldInfoRequest.class);

	private final String code;

	public FieldInfoRequest(String code) {
		this.code = code;
	}

	private final Map<String, Object> answer = new HashMap<>();

	public Map<String, Object> getAnswer() {
		return answer;
	}

	@Override
	public void execute(String name) {
		final BloombergSession bs = new BloombergSession(name);
		bs.start();
		try {
			final Request request = bs.createRequest("//blp/apiflds", "FieldInfoRequest");
			request.append("id", code);
			request.set("returnFieldDocumentation", false);

			bs.sendRequest(request, this, null);
		} finally {
			bs.stop();
		}
	}

	@Override
	public void processMessage(Message msg) {
		final Element fields = msg.getElement("fieldData");
		final int numElements = fields.numValues();
		for (int i = 0; i < numElements; i++) {
			final Element field = fields.getValueAsElement(i);
			final String id = field.getElementAsString("id");
			if (field.hasElement("fieldInfo")) {
				final Element fieldInfo = field.getElement("fieldInfo");
				final String mnemonic = fieldInfo.getElementAsString("mnemonic");
				final String description = fieldInfo.getElementAsString("description");

				if (log.isDebugEnabled()) {
					log.debug("fldId=" + id);
					log.debug("fldMnemonic=" + mnemonic);
					log.debug("fldDesc=" + description);
				}

				answer.put("BLM_ID", id);
				answer.put("NAME", description);
				answer.put("CODE", mnemonic);
			} else {
				final Element fieldError = field.getElement("fieldError");
				final String message = fieldError.getElementAsString("message");
				log.debug("ERROR: " + id + " - " + message);
			}

		}
	}
}

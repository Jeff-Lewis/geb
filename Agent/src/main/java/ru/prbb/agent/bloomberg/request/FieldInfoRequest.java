/**
 * 
 */
package ru.prbb.agent.bloomberg.request;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.prbb.agent.bloomberg.BloombergRequest;
import ru.prbb.agent.bloomberg.BloombergSession;
import ru.prbb.agent.bloomberg.MessageHandler;

import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;

/**
 * Ввод нового параметра
 * 
 * @author RBr
 */
public class FieldInfoRequest implements BloombergRequest, MessageHandler {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final String code;

	public FieldInfoRequest(String code) {
		this.code = code;
	}

	private final Map<String, String> answer = new HashMap<>();

	public Map<String, String> getAnswer() {
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
					log.debug("fldId=" + id + ", fldMnemonic=" + mnemonic + ", fldDesc=" + description);
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

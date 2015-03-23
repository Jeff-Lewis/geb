/**
 * 
 */
package ru.prbb.agent.requests;

import java.util.HashMap;
import java.util.Map;

import com.bloomberglp.blpapi.CorrelationID;
import com.bloomberglp.blpapi.Element;
import com.bloomberglp.blpapi.Message;
import com.bloomberglp.blpapi.Request;

/**
 * Ввод нового параметра
 * 
 * @author RBr
 */
public class FieldInfoRequest extends BloombergRequest {

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
		start(name);
		try {
			final Request request = createRequest("//blp/apiflds", "FieldInfoRequest");
			request.append("id", code);
			request.set("returnFieldDocumentation", false);

			sendRequest(request, new CorrelationID(answer));
		} finally {
			stop();
		}
	}

	@Override
	public void process(Message msg) {
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

package ru.prbb.agent.bloomberg;

import com.bloomberglp.blpapi.Message;

/**
 * 
 * @author RBr
 * 
 */
public interface MessageHandler {

	void processMessage(Message message);

}

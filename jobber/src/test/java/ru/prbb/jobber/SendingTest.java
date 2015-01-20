package ru.prbb.jobber;

public class SendingTest {

	public static void main(String[] args) {
		String text = "123456789012345678901";
		System.out.println(text);
		int lenSendMsg = 8;
		do {
			int endIndex = Math.min(lenSendMsg, text.length());
			String textSend = text.substring(0, endIndex);
			text = text.substring(endIndex);

			System.out.println(textSend);
		} while (!text.isEmpty());
	}
}

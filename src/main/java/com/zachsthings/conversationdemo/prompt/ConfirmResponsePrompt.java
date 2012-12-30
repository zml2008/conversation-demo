package com.zachsthings.conversationdemo.prompt;

import java.util.regex.Pattern;

import org.spout.api.chat.ChatArguments;
import org.spout.api.chat.conversation.ResponseHandler;
import org.spout.api.chat.style.ChatStyle;

/**
 * Confirms that a provided response is correct. Accepts the text input in the constructor
 * instead of getting a value from the context, since the context value could be a non-string.
 *
 * Uses regexes to match correct values
 */
public class ConfirmResponsePrompt extends ResponseHandler {
	public static final Pattern POSITIVE_RESPONSE = Pattern.compile("(?i)(?:y(es|eah)?|for sure|totally)");
	public static final Pattern NEGATIVE_RESPONSE = Pattern.compile("(?i)((hell )?n(o|ope|ah)?)|nevah|go die|fuck off");
	private final ResponseHandler previous, next;
	private final ChatArguments response;

	public ConfirmResponsePrompt(ResponseHandler previous, ResponseHandler next, ChatArguments response) {
		this.previous = previous;
		this.next = next;
		this.response = response;
	}

	@Override
	public void onAttached() {
		getConversation().broadcastToReceivers(new ChatArguments("You input: ", response, ChatStyle.RESET, ". Is this correct (Y/n)?"));
	}

	public void onInput(ChatArguments message) {
		boolean correct;
		String answer = message.getPlainString();
		if (answer.isEmpty()) {
			correct = true;
		} else if (POSITIVE_RESPONSE.matcher(answer).matches()) {
			correct = true;
		} else if (NEGATIVE_RESPONSE.matcher(answer).matches()) {
			correct = false;
		} else {
			getConversation().broadcastToReceivers(new ChatArguments("Unknown response: ", answer, ". Please reply with y or n"));
			return;
		}

		if (correct) {
			getConversation().setResponseHandler(next);
		} else {
			getConversation().setResponseHandler(previous);
		}
	}
}

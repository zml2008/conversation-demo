package com.zachsthings.conversationdemo.prompt;

import org.spout.api.chat.ChatArguments;
import org.spout.api.chat.conversation.ResponseHandler;
import org.spout.api.chat.style.ChatStyle;
import org.spout.api.map.DefaultedKey;
import org.spout.api.map.DefaultedKeyImpl;

/**
 * @author zml2008
 */
public class NamePrompt extends ResponseHandler {
	public static final DefaultedKey<String> NAME = new DefaultedKeyImpl<String>("name", "Untitled");

	public void onAttached() {
		super.onAttached();
		getConversation().broadcastToReceivers(new ChatArguments(ChatStyle.BRIGHT_GREEN, "Enter your name: "));
	}

	public void onInput(ChatArguments message) {
		String name = message.getPlainString().trim();
		getConversation().getContext().put(NAME, name);
		getConversation().setResponseHandler(new ConfirmResponsePrompt(this, new FavoriteColorPrompt(), message));
	}
}

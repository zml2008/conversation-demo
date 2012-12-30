package com.zachsthings.conversationdemo.prompt;

import com.zachsthings.conversationdemo.ConvoDemoPlugin;
import org.spout.api.chat.ChatArguments;
import org.spout.api.chat.conversation.ResponseHandler;
import org.spout.api.chat.style.ChatStyle;

/**
 * Broadcasts the responses collected in the previous prompts
 */
public class BroadcastResponsePrompt extends ResponseHandler {

	@Override
	public void onAttached() {
		final String name = getConversation().getContext().get(NamePrompt.NAME);
		final ChatStyle favoriteColor = getConversation().getContext().get(FavoriteColorPrompt.FAV_COLOR);

		ChatArguments message = new ChatArguments(ChatStyle.CYAN, "Welcome ", name, "! Their favorite color is ", favoriteColor, favoriteColor.getName(), ChatStyle.CYAN, ".");
		ConvoDemoPlugin.GREETING_BROADCAST_CHANNEL.broadcastToReceivers(message);

		getConversation().finish();
	}

	public void onInput(ChatArguments message) {
		// Don't do anything, the conversation's done!
	}
}

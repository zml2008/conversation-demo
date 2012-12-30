package com.zachsthings.conversationdemo.prompt;

import org.spout.api.chat.ChatArguments;
import org.spout.api.chat.conversation.ResponseHandler;
import org.spout.api.chat.style.ChatStyle;
import org.spout.api.chat.style.ColorChatStyle;
import org.spout.api.map.DefaultedKey;
import org.spout.api.map.DefaultedKeyImpl;

/**
 * @author zml2008
 */
public class FavoriteColorPrompt extends ResponseHandler {
	public static final DefaultedKey<ChatStyle> FAV_COLOR = new DefaultedKeyImpl<ChatStyle>("favorite-color", ChatStyle.RESET);

	@Override
	public void onAttached() {
		ChatArguments colors = new ChatArguments(ChatStyle.BRIGHT_GREEN, "What is your favorite color? Options are: ");

		boolean first = true;
		for (ChatStyle style : ChatStyle.getValues()) {
			if (style instanceof ColorChatStyle) {
				if (!first) {
					colors.append(ChatStyle.RESET, ", ");
				}
				colors.append(style, style.getLookupName());
				first = false;
			}
		}

		getConversation().broadcastToReceivers(colors);

	}

	public void onInput(ChatArguments message) {
		ChatStyle matchedStyle;
		if (message.getArguments().size() == 1 && message.getArguments().get(0) instanceof ColorChatStyle) {
			matchedStyle = (ChatStyle) message.getArguments().get(0);
		} else {
			String style = message.getPlainString();
			ChatStyle testStyle = ChatStyle.byName(style);
			if (testStyle == null) {
				getConversation().broadcastToReceivers(new ChatArguments(ChatStyle.RED, "Unknown style: ", style));
				return;
			}
			matchedStyle = testStyle;
		}

		if (!(matchedStyle instanceof ColorChatStyle)) {
			getConversation().broadcastToReceivers(new ChatArguments(ChatStyle.RED, "Style ", matchedStyle.getName(), " is not a color!"));
			return;
		}

		getConversation().getContext().put(FAV_COLOR, matchedStyle);
		getConversation().setResponseHandler(new ConfirmResponsePrompt(this, new BroadcastResponsePrompt(), new ChatArguments(matchedStyle.getName())));
	}
}

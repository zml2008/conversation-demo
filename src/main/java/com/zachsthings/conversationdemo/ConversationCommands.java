package com.zachsthings.conversationdemo;

import com.zachsthings.conversationdemo.prompt.NamePrompt;
import org.spout.api.chat.ChatArguments;
import org.spout.api.chat.ChatTemplate;
import org.spout.api.chat.conversation.Conversation;
import org.spout.api.chat.style.ChatStyle;
import org.spout.api.command.CommandContext;
import org.spout.api.command.CommandSource;
import org.spout.api.command.annotated.Command;
import org.spout.api.event.player.PlayerChatEvent;
import org.spout.api.exception.CommandException;

/**
 * @author zml2008
 */
public class ConversationCommands {
	private final ConvoDemoPlugin plugin;

	public ConversationCommands(ConvoDemoPlugin plugin) {
		this.plugin = plugin;
	}

	@Command(aliases = {"greet", "greetconvo"}, desc = "Begin a conversation to explain your greeting")
	public void greet(CommandContext args, CommandSource sender) throws CommandException {
		Conversation greetConversation = new Conversation("Greeter", sender)
				.setResponseHandler(new NamePrompt());
		greetConversation.setFormat(new ChatTemplate(new ChatArguments(ChatStyle.GRAY, "[Greeting] ", ChatStyle.RESET, PlayerChatEvent.MESSAGE)));
		sender.setActiveChannel(greetConversation);
	}
}

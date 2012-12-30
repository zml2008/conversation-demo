package com.zachsthings.conversationdemo;

import org.spout.api.chat.channel.ChatChannel;
import org.spout.api.chat.channel.PermissionChatChannel;
import org.spout.api.command.annotated.AnnotatedCommandRegistrationFactory;
import org.spout.api.command.annotated.SimpleInjector;
import org.spout.api.plugin.CommonPlugin;

/**
 * Hello world!
 */
public class ConvoDemoPlugin extends CommonPlugin {
	public static final ChatChannel GREETING_BROADCAST_CHANNEL = new PermissionChatChannel("Greetings", "convodemo.broadcast.greeting");

	public void onEnable() {
		AnnotatedCommandRegistrationFactory factory = new AnnotatedCommandRegistrationFactory(new SimpleInjector(this));
		getEngine().getRootCommand().addSubCommands(this, ConversationCommands.class, factory);

		getEngine().getDefaultPermissions().addDefaultPermission("convodemo.broadcast.greeting");
	}

	public void onDisable() {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}

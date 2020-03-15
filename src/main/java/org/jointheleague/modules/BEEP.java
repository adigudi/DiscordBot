package org.jointheleague.modules;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.javacord.api.event.message.MessageCreateEvent;

public class BEEP extends CustomMessageCreateListener {

	private static final String BEEP = "!beep";
	private static final String ZEBRA = "!dance";
	private static final String SPRINT = "!sprinter";
	private static final String COAST = "!coaster";

	public BEEP(String channelName) {
		super(channelName);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		if (event.getMessageContent().contains(BEEP)) {
			event.getChannel().sendMessage("BEEP");
		}
		else if(event.getMessageContent().contains(ZEBRA)) {
			event.getChannel().sendMessage("https://www.youtube.com/watch?v=-Z668Qc0P4Q");
		}
		else if(event.getMessageContent().contains(SPRINT)) {
			event.getChannel().sendMessage("https://gonctd.com/wp-content/uploads/2020/01/SPRINTER.pdf");
		}
		else if(event.getMessageContent().contains(COAST)) {
			event.getChannel().sendMessage("https://gonctd.com/wp-content/uploads/2020/01/COASTER-Schedule-Flyer-October-2019.pdf");
		}
		else if(event.getMessageContent().contains("!info")) {
			event.getChannel().sendMessage("Hi, I'm the Java SBot Version S001. My documentation can be found at http://bot.samuelsharp.com");
		}
	}
	
}
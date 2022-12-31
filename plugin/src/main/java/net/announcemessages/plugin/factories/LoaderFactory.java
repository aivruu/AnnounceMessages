package net.announcemessages.plugin.factories;

import net.announcemessages.plugin.AnnounceMessages;
import net.announcemessages.plugin.loaders.CommandLoader;
import net.announcemessages.plugin.loaders.EventLoader;

public interface LoaderFactory {
	static CommandLoader.Builder newCommandLoader(AnnounceMessages plugin) {
		return new CommandLoader.Builder(plugin);
	}
	
	static EventLoader.Builder newEventLoader(AnnounceMessages plugin) {
		return new EventLoader.Builder(plugin);
	}
}

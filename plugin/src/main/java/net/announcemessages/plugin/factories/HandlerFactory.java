package net.announcemessages.plugin.factories;

import net.announcemessages.plugin.notify.UpdateHandler;

public interface HandlerFactory {
	static UpdateHandler newUpdateHandler(int resourceNumber) {
		return new UpdateHandler(resourceNumber);
	}
}

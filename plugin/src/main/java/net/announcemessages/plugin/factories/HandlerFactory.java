package net.announcemessages.plugin.factories;

import net.announcemessages.plugin.notify.UpdateHandler;
import net.xconfig.bukkit.config.BukkitConfigurationHandler;

public interface HandlerFactory {
	static UpdateHandler newUpdateHandler(BukkitConfigurationHandler configurationHandler, int resourceNumber) {
		return new UpdateHandler(configurationHandler, resourceNumber);
	}
}

package net.announcemessages.plugin.factories;

import net.announcemessages.api.managers.ActionManager;
import net.announcemessages.plugin.AnnounceMessages;
import net.announcemessages.plugin.managers.SimpleActionManager;
import net.announcemessages.plugin.managers.SimpleAnnounceManager;
import net.luckperms.api.model.user.UserManager;
import net.xconfig.bukkit.config.BukkitConfigurationHandler;

public interface ManagerFactory {
	static SimpleActionManager newActionManager(AnnounceMessages plugin) {
		return new SimpleActionManager(plugin);
	}
	
	static SimpleAnnounceManager newAnnounceManager(BukkitConfigurationHandler configurationHandler, UserManager userManager, ActionManager actionManager) {
		return new SimpleAnnounceManager(configurationHandler, userManager, actionManager);
	}
}

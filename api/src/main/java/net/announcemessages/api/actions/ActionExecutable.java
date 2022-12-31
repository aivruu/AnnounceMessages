package net.announcemessages.api.actions;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public interface ActionExecutable {
	ActionContext getContext();
	
	void execute(JavaPlugin plugin, Player player, String container);
}

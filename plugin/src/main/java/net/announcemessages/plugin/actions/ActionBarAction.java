package net.announcemessages.plugin.actions;

import net.announcemessages.api.actions.ActionContext;
import net.announcemessages.api.actions.ActionExecutable;
import net.announcemessages.plugin.util.LogUtils;
import net.announcemessages.plugin.util.Utils;
import net.xconfig.bukkit.config.BukkitConfigurationHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class ActionBarAction
implements ActionExecutable {
	private final BukkitConfigurationHandler configurationHandler;
	
	public ActionBarAction(BukkitConfigurationHandler configurationHandler) {
		this.configurationHandler = Objects.requireNonNull(configurationHandler, "The BukkitConfigurationHandler object is null.");
	}
	
	@Override
	public ActionContext getContext() {
		return ActionContext.ACTION_BAR;
	}
	
	@Override
	public void execute(JavaPlugin plugin, Player player, String container) {
		if (!container.contains(";")) return;
		
		String[] parts = container.split(";", 2);
		
		int duration;
		try { duration = Integer.parseInt(parts[0]); }
		catch (NumberFormatException exception) {
			LogUtils.error("Cannot parse the actionbar duration parameter because the value isn't valid.");
			if (configurationHandler.condition("", "config.yml", "config.debug-mode")) exception.printStackTrace();
			return;
		}
		
		Utils.sendActionBar(plugin, player, parts[1], duration);
	}
}

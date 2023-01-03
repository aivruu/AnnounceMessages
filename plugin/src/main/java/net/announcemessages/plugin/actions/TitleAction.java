package net.announcemessages.plugin.actions;

import net.announcemessages.api.actions.ActionContext;
import net.announcemessages.api.actions.ActionExecutable;
import net.announcemessages.plugin.util.LogUtils;
import net.announcemessages.plugin.util.Utils;
import net.xconfig.bukkit.config.BukkitConfigurationHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static java.lang.Integer.parseInt;

public class TitleAction
implements ActionExecutable {
	private final BukkitConfigurationHandler configurationHandler;
	
	public TitleAction(BukkitConfigurationHandler configurationHandler) {
		this.configurationHandler = Objects.requireNonNull(configurationHandler, "The BukkitConfigurationHandler object is null.");
	}
	
	@Override
	public ActionContext getContext() {
		return ActionContext.TITLE;
	}
	
	@Override
	public void execute(JavaPlugin plugin, Player player, String container) {
		if (!container.contains(";")) return;
		
		String[] parts = container.split(";", 5);
		
		int fadeIn;
		int stay;
		int fadeOut;
		try {
			fadeIn = parseInt(parts[2]);
			stay = parseInt(parts[3]);
			fadeOut = parseInt(parts[4]);
		} catch (NumberFormatException exception) {
			LogUtils.error("Cannot parse the title time parameters because the values are not valid.");
			if (configurationHandler.condition("", "config.yml", "config.debug-mode")) exception.printStackTrace();
			return;
		}
		
		Utils.sendTitle(player, parts[0], parts[1], fadeIn, stay, fadeOut);
	}
}

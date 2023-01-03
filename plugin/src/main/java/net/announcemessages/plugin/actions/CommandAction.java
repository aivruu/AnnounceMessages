package net.announcemessages.plugin.actions;

import net.announcemessages.api.actions.ActionContext;
import net.announcemessages.api.actions.ActionExecutable;
import net.announcemessages.plugin.util.LogUtils;
import net.xconfig.bukkit.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandAction
implements ActionExecutable {
	public CommandAction() {}
	
	@Override
	public ActionContext getContext() {
		return ActionContext.COMMAND;
	}
	
	@Override
	public void execute(JavaPlugin plugin, Player player, String container) {
		if (!container.contains(";")) return;
		
		String[] parts = container.split(";", 2);
		String command = TextUtils.colorize(parts[1]);
		
		switch (parts[0].toUpperCase()) {
			default:
				LogUtils.error("That command dispatch type isn't valid.");
				break;
			case "CONSOLE":
				Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
				break;
			case "PLAYER": player.chat(command);
		}
	}
}

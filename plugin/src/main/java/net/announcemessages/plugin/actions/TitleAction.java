package net.announcemessages.plugin.actions;

import net.announcemessages.api.actions.ActionContext;
import net.announcemessages.api.actions.ActionExecutable;
import net.announcemessages.plugin.util.LogUtils;
import net.announcemessages.plugin.util.Utils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TitleAction
implements ActionExecutable {
	public TitleAction() {}
	
	@Override
	public ActionContext getContext() {
		return ActionContext.TITLE;
	}
	
	@Override
	public void execute(JavaPlugin plugin, Player player, String container) {
		String[] parts = container.split(";", 5);
		
		int fadeIn;
		int stay;
		int fadeOut;
		try {
			fadeIn = Integer.parseInt(parts[2]);
			stay = Integer.parseInt(parts[3]);
			fadeOut = Integer.parseInt(parts[4]);
		} catch (NumberFormatException exception) {
			LogUtils.error("Cannot parse the title time parameters because the values are not valid.");
			return;
		}
		
		Utils.sendTitle(player, parts[0], parts[2], fadeIn, stay, fadeOut);
	}
}

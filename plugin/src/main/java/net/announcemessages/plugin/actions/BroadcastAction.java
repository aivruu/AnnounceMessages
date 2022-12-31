package net.announcemessages.plugin.actions;

import net.announcemessages.api.actions.ActionContext;
import net.announcemessages.api.actions.ActionExecutable;
import net.announcemessages.plugin.util.PlaceholderUtils;
import net.xconfig.bukkit.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class BroadcastAction
implements ActionExecutable {
	public BroadcastAction() {}
	
	@Override
	public ActionContext getContext() {
		return ActionContext.BROADCAST;
	}
	
	@Override
	public void execute(JavaPlugin plugin, Player player, String container) {
		Bukkit.getOnlinePlayers().forEach(target -> target.sendMessage(TextUtils.colorize(PlaceholderUtils.parse(target, container))));
	}
}

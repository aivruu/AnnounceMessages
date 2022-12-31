package net.announcemessages.plugin.actions;

import net.announcemessages.api.actions.ActionContext;
import net.announcemessages.api.actions.ActionExecutable;
import net.announcemessages.plugin.util.PlaceholderUtils;
import net.xconfig.bukkit.utils.TextUtils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MessageAction
implements ActionExecutable {
	public MessageAction() {}
	
	@Override
	public ActionContext getContext() {
		return ActionContext.MESSAGE;
	}
	
	@Override
	public void execute(JavaPlugin plugin, Player player, String container) {
		player.sendMessage(TextUtils.colorize(PlaceholderUtils.parse(player, container)));
	}
}

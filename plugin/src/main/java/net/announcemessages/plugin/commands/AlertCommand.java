package net.announcemessages.plugin.commands;

import net.announcemessages.api.events.ServerAnnouncementEvent;
import net.announcemessages.plugin.AnnounceMessages;
import net.announcemessages.plugin.enums.Permissions;
import net.announcemessages.plugin.util.LogUtils;
import net.announcemessages.plugin.util.Utils;
import net.announcemessages.plugin.xseries.XSound;
import net.xconfig.bukkit.config.BukkitConfigurationHandler;
import net.xconfig.bukkit.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class AlertCommand implements CommandExecutor {
	private final BukkitConfigurationHandler configurationHandler;
	
	public AlertCommand(BukkitConfigurationHandler configurationHandler) {
		this.configurationHandler = Objects.requireNonNull(configurationHandler, "The BukkitConfigurationHandler object is null.");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String prefix = configurationHandler.text("", "config.yml", "config.prefix", true);
		
		if (!(sender instanceof Player)) {
			LogUtils.error("This command can't be executed by the console!");
			return false;
		}
		
		Player player = (Player) sender;
		if (!player.hasPermission(Permissions.COMMAND_ALERT.getPermission())) {
			Utils.playSound(
				 player,
				 configurationHandler.text("", "config.yml", "config.sounds.permission", false),
				 configurationHandler.number("", "config.yml", "config.sounds.volume"),
				 configurationHandler.number("", "config.yml", "config.sounds.volume")
			);
			player.sendMessage(configurationHandler.text("", "messages.yml", "messages.no-permission", true).replace("<prefix>", prefix));
			return false;
		}
		
		if (args.length == 0) {
			player.sendMessage(configurationHandler.text("", "messages.yml", "messages.alert-usage", true).replace("<prefix>", prefix));
			return false;
		}

		String message = configurationHandler.text("", "messages.yml", "messages.alert-format", true) + TextUtils.colorize(String.join(" ", args));
		
		ServerAnnouncementEvent event = new ServerAnnouncementEvent(message);
		Bukkit.getPluginManager().callEvent(event);
		if (event.isCancelled()) return false;
		
		Bukkit.broadcastMessage(message);
		return false;
	}
}

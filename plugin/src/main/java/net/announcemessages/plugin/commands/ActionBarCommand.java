package net.announcemessages.plugin.commands;

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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Objects;

public class ActionBarCommand implements CommandExecutor {
	private final AnnounceMessages plugin;
	private final BukkitConfigurationHandler configurationHandler;
	
	public ActionBarCommand(AnnounceMessages plugin, BukkitConfigurationHandler configurationHandler) {
		this.plugin = Objects.requireNonNull(plugin, "The AnnounceMessages instance is null.");
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
		if (!player.hasPermission(Permissions.COMMAND_ACTIONBAR.getPermission())) {
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
			player.sendMessage(configurationHandler.text("", "messages.yml", "messages.actionbar-usage", true).replace("<prefix>", prefix));
			return false;
		}
		
		if (args[0].equalsIgnoreCase("all")) {
			if (args.length == 1) {
				player.sendMessage(configurationHandler.text("", "messages.yml", "messages.actionbar-usage", true).replace("<prefix>", prefix));
				return false;
			}
			
			Bukkit.getOnlinePlayers().forEach(target -> {
				Utils.sendActionBar(plugin, target, TextUtils.colorize(String.join(" ", args)), 100);
			});
			return false;
		}
		
		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			player.sendMessage(configurationHandler.text("", "messages.yml", "messages.offline-target", true).replace("<prefix>", prefix));
			return false;
		}
		
		if (args.length == 1) {
			player.sendMessage(configurationHandler.text("", "messages.yml", "messages.actionbar-usage", true).replace("<prefix>", prefix));
			return false;
		}
		
		Utils.sendActionBar(plugin, target, TextUtils.colorize(String.join(" ", args)), 100);
		return false;
	}
}

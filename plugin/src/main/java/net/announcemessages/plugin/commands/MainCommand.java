package net.announcemessages.plugin.commands;

import net.announcemessages.plugin.AnnounceMessages;
import net.announcemessages.plugin.enums.Permissions;
import net.announcemessages.plugin.util.LogUtils;
import net.announcemessages.plugin.util.Utils;
import net.xconfig.bukkit.config.BukkitConfigurationHandler;
import net.xconfig.bukkit.config.BukkitConfigurationModel;
import net.xconfig.bukkit.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class MainCommand
implements CommandExecutor {
	private final BukkitConfigurationModel configurationManager;
	private final BukkitConfigurationHandler configurationHandler;
	
	public MainCommand(BukkitConfigurationModel configurationManager, BukkitConfigurationHandler configurationHandler) {
		this.configurationManager = Objects.requireNonNull(configurationManager, "The BukkitConfigurationModel object is null.");
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
		if (args.length == 0) {
			player.sendMessage(prefix + TextUtils.colorize(" &a&lUPDATE: &eImprovements & Optimization!"));
			player.sendMessage(prefix + TextUtils.colorize(" &fRunning on &a" + Bukkit.getServer().getBukkitVersion()));
			player.sendMessage(prefix + TextUtils.colorize(" &fDeveloped by &aInitSync &7- &e" + AnnounceMessages.getPlugin().release));
			return false;
		}
		
		switch(args[0]) {
			default:
				player.sendMessage(configurationHandler.text("", "messages.yml", "messages.no-command", true).replace("<prefix>", prefix));
				break;
			case "help":
				if (!player.hasPermission(Permissions.COMMAND_LIST.getPermission())) {
					Utils.playSound(
						 player,
						 configurationHandler.text("", "config.yml", "config.sounds.permission", false),
						 configurationHandler.number("", "config.yml", "config.sounds.volume"),
						 configurationHandler.number("", "config.yml", "config.sounds.volume")
					);
					player.sendMessage(configurationHandler.text("", "messages.yml", "messages.no-permission", true).replace("<prefix>", prefix));
					return false;
				}
				
				configurationHandler.textList("", "messages.yml", "messages.help", true).forEach(player::sendMessage);
				break;
			case "reload":
				if (!player.hasPermission(Permissions.COMMAND_RELOAD.getPermission())) {
					Utils.playSound(
						 player,
						 configurationHandler.text("", "config.yml", "config.sounds.permission", false),
						 configurationHandler.number("", "config.yml", "config.sounds.volume"),
						 configurationHandler.number("", "config.yml", "config.sounds.volume")
					);
					player.sendMessage(configurationHandler.text("", "messages.yml", "messages.no-permission", true).replace("<prefix>", prefix));
					return false;
				}
				
				if (args.length == 1) {
					configurationManager.reload("", "config.yml");
					configurationManager.reload("", "messages.yml");
					
					Utils.playSound(
						 player,
						 configurationHandler.text("", "config.yml", "config.sounds.reload", false),
						 configurationHandler.number("", "config.yml", "config.sounds.volume"),
						 configurationHandler.number("", "config.yml", "config.sounds.volume")
					);
					player.sendMessage(configurationHandler.text("", "messages.yml", "messages.reload-all", true).replace("<prefix>", prefix));
					return false;
				}
				
				switch (args[1]) {
					default:
						player.sendMessage(configurationHandler.text("", "messages.yml", "messages.no-file", true).replace("<prefix>", prefix));
						break;
					case "config":
						configurationManager.reload("", "config.yml");
						
						Utils.playSound(
							 player,
							 configurationHandler.text("", "config.yml", "config.sounds.reload", false),
							 configurationHandler.number("", "config.yml", "config.sounds.volume"),
							 configurationHandler.number("", "config.yml", "config.sounds.volume")
						);
						player.sendMessage(configurationHandler.text("", "messages.yml", "messages.reload-config", true).replace("<prefix>", prefix));
						break;
					case "messages":
						configurationManager.reload("", "messages.yml");
						
						Utils.playSound(
							 player,
							 configurationHandler.text("", "config.yml", "config.sounds.reload", false),
							 configurationHandler.number("", "config.yml", "config.sounds.volume"),
							 configurationHandler.number("", "config.yml", "config.sounds.volume")
						);
						player.sendMessage(configurationHandler.text("", "messages.yml", "messages.reload-messages", true).replace("<prefix>", prefix));
						break;
				}
				break;
		}
		return false;
	}
}

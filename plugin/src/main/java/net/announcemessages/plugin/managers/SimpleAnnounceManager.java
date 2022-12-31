package net.announcemessages.plugin.managers;

import net.announcemessages.api.managers.ActionManager;
import net.announcemessages.api.managers.AnnounceManager;
import net.announcemessages.plugin.util.PlaceholderUtils;
import net.luckperms.api.model.user.UserManager;
import net.xconfig.bukkit.config.BukkitConfigurationHandler;
import net.xconfig.bukkit.utils.TextUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

public class SimpleAnnounceManager
implements AnnounceManager {
	private final BukkitConfigurationHandler configurationHandler;
	private final UserManager userManager;
	private final ActionManager actionManager;
	
	public SimpleAnnounceManager(BukkitConfigurationHandler configurationHandler, UserManager userManager, ActionManager actionManager) {
		this.configurationHandler = Objects.requireNonNull(configurationHandler, "The BukkitConfigurationHandler object is null.");
		this.userManager = Objects.requireNonNull(userManager, "The UserManager object is null.");
		this.actionManager = Objects.requireNonNull(actionManager, "The ActionManager object is null.");
	}
	
	@Override
	public String getPlayerGroup(UUID uuid) {
		return userManager.getUser(uuid).getPrimaryGroup();
	}
	
	@Override
	public String getJoinMessage(Player player) {
		ConfigurationSection section = configurationHandler.configSection("", "config.yml", "config.announcements.groups." + getPlayerGroup(player.getUniqueId()));
		if (section == null) return "";
		
		return PlaceholderUtils.parse(player, TextUtils.colorize(section.getString("join")));
	}
	
	@Override
	public String getQuitMessage(Player player) {
		ConfigurationSection section = configurationHandler.configSection("", "config.yml", "config.announcements.groups." + getPlayerGroup(player.getUniqueId()));
		if (section == null) return "";
		
		return PlaceholderUtils.parse(player, TextUtils.colorize(section.getString("quit")));
	}
	
	@Override
	public void performAction(Player player) {
		ConfigurationSection section = configurationHandler.configSection("", "config.yml", "config.announcements.groups." + getPlayerGroup(player.getUniqueId()));
		if (section == null) return;
		
		actionManager.execute(player, section.getStringList("actions"));
	}
}

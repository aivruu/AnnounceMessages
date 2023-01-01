package net.announcemessages.plugin.listeners;

import net.announcemessages.api.managers.AnnounceManager;
import net.xconfig.bukkit.config.BukkitConfigurationHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Objects;

public class PlayerListener implements Listener {
	private final BukkitConfigurationHandler configurationHandler;
	private final AnnounceManager announceManager;
	
	public PlayerListener(BukkitConfigurationHandler configurationHandler, AnnounceManager announceManager) {
		this.configurationHandler = Objects.requireNonNull(configurationHandler, "The BukkitConfigurationHandler object is null.");
		this.announceManager = Objects.requireNonNull(announceManager, "The AnnounceManager object is null.");
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		event.setJoinMessage(announceManager.getJoinMessage(player));
		
		if (configurationHandler.condition("", "config.yml", "config.announcements.allow-motd")) {
			configurationHandler.textList("", "config.yml", "config.announcements.motd", true).forEach(player::sendMessage);
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		event.setQuitMessage(announceManager.getQuitMessage(event.getPlayer()));
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent event) {
		event.setLeaveMessage(announceManager.getQuitMessage(event.getPlayer()));
	}
}

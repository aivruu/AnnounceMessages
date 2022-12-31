package net.announcemessages.api.managers;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface AnnounceManager {
	String getPlayerGroup(UUID uuid);
	
	String getJoinMessage(Player player);
	
	String getQuitMessage(Player player);
	
	void performAction(Player player);
}

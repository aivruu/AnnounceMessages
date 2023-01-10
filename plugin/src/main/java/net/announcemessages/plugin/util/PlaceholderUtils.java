package net.announcemessages.plugin.util;

import com.google.common.base.Preconditions;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlaceholderUtils {
	private static final boolean PLACEHOLDERS = Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null &&
		 Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
	
	private PlaceholderUtils() {}
	
	public static String parse(Player player, String text) {
		Preconditions.checkNotNull(player, "The player is null.");
		
		if (!PLACEHOLDERS) return text.replace("<br>", "\n");
		
		return PlaceholderAPI.setPlaceholders(player, text.replace("<br>", "\n"));
	}
}

package net.announcemessages.plugin.util;

import net.announcemessages.plugin.AnnounceMessages;
import net.announcemessages.plugin.xseries.XSound;
import net.xconfig.bukkit.utils.TextUtils;
import net.xtitle.api.TitleManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

public class Utils {
	private static final TitleManager MANAGER = AnnounceMessages.getPlugin().getTitleManager();
	
	private Utils() {}
	
	public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		checkNotNull(player, "The player is null.");
		checkArgument(!title.isEmpty(), "The title message is empty.");
		checkArgument(!subtitle.isEmpty(), "The subtitle message is empty.");
		
		MANAGER.sendTitle(player, fadeIn, stay, fadeOut, TextUtils.colorize(title), TextUtils.colorize(subtitle));
	}
	
	public static void clearTitle(Player player) {
		checkNotNull(player, "The player is null.");
		
		MANAGER.clearTitle(player);
	}
	
	public static void sendActionBar(JavaPlugin plugin, Player player, String message, long duration) {
		checkNotNull(player, "The player is null.");
		checkArgument(!message.isEmpty(), "The message is empty.");
		
		MANAGER.sendActionBar(plugin, player, message, duration);
	}
	
	public static void playSound(Player player, String sound, int volume, int pitch) {
		checkNotNull(player, "The player is null.");
		checkArgument(!sound.isEmpty(), "The sound type is empty.");
		
		player.playSound(player.getLocation(), XSound.matchXSound(sound.toUpperCase()).get().parseSound(), volume, pitch);
	}
}

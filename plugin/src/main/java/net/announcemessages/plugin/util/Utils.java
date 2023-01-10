package net.announcemessages.plugin.util;

import com.google.common.base.Preconditions;
import net.announcemessages.plugin.AnnounceMessages;
import net.announcemessages.plugin.xseries.XSound;
import net.xconfig.bukkit.utils.TextUtils;
import net.xtitle.api.TitleManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Utils {
	private static final TitleManager MANAGER = AnnounceMessages.getPlugin().getTitleManager();
	
	private Utils() {}
	
	public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
		MANAGER.sendTitle(
			 player,
			 fadeIn,
			 stay,
			 fadeOut,
			 TextUtils.colorize(PlaceholderUtils.parse(player, title)),
			 TextUtils.colorize(PlaceholderUtils.parse(player, subtitle))
		);
	}
	
	public static void clearTitle(Player player) {
		MANAGER.clearTitle(player);
	}
	
	public static void sendTabList(Player player, String footer, String header) {
		MANAGER.sendTabList(
			 player,
			 TextUtils.colorize(PlaceholderUtils.parse(player, footer)),
			 TextUtils.colorize(PlaceholderUtils.parse(player, header))
		);
	}
	
	public static void sendActionBar(JavaPlugin plugin, Player player, String message, long duration) {
		MANAGER.sendActionBar(
			 plugin,
			 player,
			 TextUtils.colorize(PlaceholderUtils.parse(player, message)),
			 duration
		);
	}
	
	public static void playSound(Player player, String sound, int volume, int pitch) {
		Preconditions.checkNotNull(player, "The player is null.");
		Preconditions.checkArgument(!sound.isEmpty(), "The sound type is empty.");
		
		player.playSound(player.getLocation(), XSound.matchXSound(sound.toUpperCase()).get().parseSound(), volume, pitch);
	}
}

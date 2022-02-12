package am.alqj.util;

import am.alqj.AnnounceMessagesPlugin;
import me.clip.placeholderapi.PlaceholderAPI;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtil {

  private static final AnnounceMessagesPlugin plugin = AnnounceMessagesPlugin.getPlugin(AnnounceMessagesPlugin.class);

  private static final Pattern HEX_PATTERN = Pattern.compile("&(#[A-Fa-f0-9]{6})");
  private static final char COLOR_CHAR = ChatColor.COLOR_CHAR;

  public static boolean hex = false;

  public static String color(String m) {
    if(hex) {
      Matcher matcher = HEX_PATTERN.matcher(m);
      StringBuffer b = new StringBuffer(m.length() + 4 * 8);
      while (matcher.find()) {
        String g = matcher.group(1);
        matcher.appendReplacement(b, COLOR_CHAR + "x"
                + COLOR_CHAR + g.charAt(0) + COLOR_CHAR + g.charAt(1)
                + COLOR_CHAR + g.charAt(2) + COLOR_CHAR + g.charAt(3)
                + COLOR_CHAR + g.charAt(4) + COLOR_CHAR + g.charAt(5)
        );
      }

      return matcher.appendTail(b).toString();
    }

    return ChatColor.translateAlternateColorCodes('&', m);
  }

  public static String placeholders(String text, Player player) {

    if(player == null) return null;

    if(text.contains("%user_name%")) text = text.replace("%user_name%", player.getName());

    if(text.contains("%user_display%")) text = text.replace("%user_display%", player.getDisplayName());

    if(text.contains("%user_prefix%")) text = text.replace("%user_prefix%", plugin.chat.getPlayerPrefix(player));

    if(text.contains("%user_suffix%")) text = text.replace("%user_suffix%", plugin.chat.getPlayerSuffix(player));

    if(text.contains("%user_level%")) text = text.replace("%user_level%", player.getLevel() + "");

    if(text.contains("%user_exp%")) text = text.replace("%user_exp%", player.getTotalExperience() + "");

    return text;
  }

  public static String papi(Player player, String text) {
    if(plugin.papi) return PlaceholderAPI.setPlaceholders(player, text);

    return null;
  }

}

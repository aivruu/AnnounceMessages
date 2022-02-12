package am.alqj;

import am.alqj.reflection.ActionBar;
import am.alqj.reflection.Titles;
import org.bukkit.entity.Player;

public class AnnounceMessagesAPI {

  private static final AnnounceMessagesPlugin plugin = AnnounceMessagesPlugin.getPlugin(AnnounceMessagesPlugin.class);

  /**
   * Send a actionbar to player, the content of messages can contains Hex Colors, placeholders and more.
   * @param player HumanEntity
   * @param message String
   */
  public static void sendActionBar(Player player, String message) { ActionBar.sendActionBar(player, message); }

  /**
   * Send a actionbar to player of determinated duration.
   * @param player HumanEntity
   * @param message String
   * @param duration Long
   */
  public static void sendActionBar(Player player, String message, long duration) { ActionBar.sendActionBar(player, message, duration); }

  /**
   * Send a actionbar to all players connected of the server.
   * @param message String
   */
  public static void sendAllActionBar(String message) { ActionBar.sendAllActionBar(message); }

  /**
   * This send a title and a subtitle to player, this supports Hex Colors & Placeholders.
   * @param player HumanEntity
   * @param fadeIn int
   * @param stay int
   * @param fadeOut int
   * @param title String
   * @param subtitle String
   */
  public static void sendTitle(Player player, int fadeIn, int stay, int fadeOut, String title, String subtitle) {
    if(plugin.getVersionNumber() > 10) {
      player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
      return;
    }
    Titles.sendTitle(player, fadeIn, stay, fadeOut, title, subtitle);
  }

  /**
   * This send only a title to the player.
   * @param player HumanEntity
   * @param fadeIn int
   * @param stay int
   * @param fadeOut int
   * @param title String
   */
  public static void sendTitle(Player player, int fadeIn, int stay, int fadeOut, String title) {
    if(plugin.getVersionNumber() > 10) {
      player.sendTitle(title, null, fadeIn, stay, fadeOut);
      return;
    }
    Titles.sendTitle(player, fadeIn, stay, fadeOut, title);
  }
}

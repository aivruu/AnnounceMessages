package am.alqj.listeners;

import am.alqj.AnnounceMessagesPlugin;
import am.alqj.util.TextUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MotdListener implements Listener {

  private final AnnounceMessagesPlugin plugin;

  public MotdListener(AnnounceMessagesPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    FileConfiguration config = plugin.getSettings().getConfig();
    Player player = event.getPlayer();

    String text = config.getString("messages.motd_message");
    String[] var = text.split("\n");

    for(int i = 0 ; i < var.length ; i++) {

      String motd = var[i];

      motd = TextUtil.color(motd);
      motd = TextUtil.placeholders(motd, player);
      motd = TextUtil.papi(player, motd);

      player.sendMessage(motd);
    }
  }
}

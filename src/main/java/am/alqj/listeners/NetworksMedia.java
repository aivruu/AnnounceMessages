package am.alqj.listeners;

import am.alqj.AnnounceMessagesPlugin;
import am.alqj.util.TextUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;

public class NetworksMedia implements Listener {

  private final AnnounceMessagesPlugin plugin;

  public NetworksMedia(AnnounceMessagesPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    FileConfiguration config = plugin.getSettings().getConfig();
    Player player = event.getPlayer();

    if(config.getBoolean("messages.networks.enable")) {

      String name = config.getString("messages.networks.first.name");
      String url = config.getString("messages.networks.first.url");
      String description = config.getString("messages.networks.first.description");

      Arrays.asList(description.split("\\n"));

      String name1 = config.getString("messages.networks.second.name");
      String url1 = config.getString("messages.networks.second.url");
      String description1 = config.getString("messages.networks.second.description");

      Arrays.asList(description1.split("\\n"));

      String name2 = config.getString("messages.networks.third.name");
      String url2 = config.getString("messages.networks.third.url");
      String description2 = config.getString("messages.networks.third.description");

      Arrays.asList(description2.split("\\n"));

      String name3 = config.getString("messages.networks.four.name");
      String url3 = config.getString("messages.networks.four.url");
      String description3 = config.getString("messages.networks.four.description");

      Arrays.asList(description3.split("\\n"));

      TextComponent BUTTON = new TextComponent(TextUtil.color(name));
      BUTTON.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url));
      BUTTON.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextUtil.color(description)).create()));

      TextComponent BUTTON1 = new TextComponent(TextUtil.color(name1));
      BUTTON1.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url1));
      BUTTON1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextUtil.color(description1)).create()));

      TextComponent BUTTON2 = new TextComponent(TextUtil.color(name2));
      BUTTON2.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url2));
      BUTTON2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextUtil.color(description2)).create()));

      TextComponent BUTTON3 = new TextComponent(TextUtil.color(name3));
      BUTTON3.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, url3));
      BUTTON3.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(TextUtil.color(description3)).create()));

      BUTTON.addExtra(BUTTON1);
      BUTTON.addExtra(BUTTON2);
      BUTTON.addExtra(BUTTON3);

      player.spigot().sendMessage(BUTTON);

      player.sendMessage("");
    }
  }
}

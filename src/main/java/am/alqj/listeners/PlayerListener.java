package am.alqj.listeners;

import am.alqj.AnnounceMessagesPlugin;
import am.alqj.enums.Permissions;
import am.alqj.notify.Updater;
import am.alqj.util.TextUtil;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

  private final AnnounceMessagesPlugin plugin;

  public PlayerListener(AnnounceMessagesPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    FileConfiguration groups = plugin.getSettings().getGroups();
    Player player = event.getPlayer();

    String group = plugin.permission.getPrimaryGroup(player);

    String message;

    if(groups.getBoolean("groups.enable_join")) {
      if(groups.getConfigurationSection("groups." + group) != null) {
        message = groups.getString("groups." + group + ".join_message");

        message = TextUtil.color(message);
        message = TextUtil.placeholders(message, player);
        message = PlaceholderAPI.setPlaceholders(player, message);

        for(String actions : groups.getStringList("groups." + group + ".join_actions")) {

          actions = TextUtil.color(actions);
          actions = TextUtil.placeholders(actions, player);
          actions = TextUtil.papi(player, actions);

          plugin.getActions().execute(actions, player);
        }

        event.setJoinMessage(message);
        return;
      }

      event.setJoinMessage(null);

      plugin.getLogger().warning("The group '" + group + "' doesn't exists in the groups.yml!");

      return;
    }

    event.setJoinMessage(null);
  }

  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    FileConfiguration groups = plugin.getSettings().getGroups();
    Player player = event.getPlayer();

    String group = plugin.permission.getPrimaryGroup(player);

    String message;

    if(groups.getBoolean("groups.enable_quit")) {
      if(groups.getConfigurationSection("groups." + group) != null) {
        message = groups.getString("groups." + group + ".quit_message");

        message = TextUtil.color(message);
        message = TextUtil.placeholders(message, player);
        message = PlaceholderAPI.setPlaceholders(player, message);

        for(String actions : groups.getStringList("groups." + group + ".quit_actions")) {

          actions = TextUtil.color(actions);
          actions = TextUtil.placeholders(actions, player);
          actions = TextUtil.papi(player, actions);

          plugin.getActions().execute(actions, player);
        }

        event.setQuitMessage(message);
        return;
      }

      event.setQuitMessage(null);

      plugin.getLogger().warning("The group '" + group + "' doesn't exists in the groups.yml!");

      return;
    }

    event.setQuitMessage(null);
  }

  @EventHandler
  public void onKick(PlayerKickEvent event) {
    FileConfiguration groups = plugin.getSettings().getGroups();
    Player player = event.getPlayer();

    String group = plugin.permission.getPrimaryGroup(player);

    String message;

    if(groups.getBoolean("groups.enable_quit")) {
      if(groups.getConfigurationSection("groups." + group) != null) {
        message = groups.getString("groups." + group + ".quit_message");

        message = TextUtil.color(message);
        message = TextUtil.placeholders(message, player);
        message = PlaceholderAPI.setPlaceholders(player, message);

        for(String actions : groups.getStringList("groups." + group + ".quit_actions")) {

          actions = TextUtil.color(actions);
          actions = TextUtil.placeholders(actions, player);
          actions = TextUtil.papi(player, actions);

          plugin.getActions().execute(actions, player);
        }

        event.setLeaveMessage(message);
        return;
      }

      event.setLeaveMessage(null);

      plugin.getLogger().warning("The group '" + group + "' doesn't exists in the groups.yml!");

      return;
    }

    event.setLeaveMessage(null);
  }

  @EventHandler
  public void onUpdate(PlayerJoinEvent event) {
    FileConfiguration config = plugin.getSettings().getConfig();
    Player player = event.getPlayer();

    if(config.getBoolean("notify") && player.hasPermission(Permissions.UPDATE_LOG.getPermission())) {
      Updater
              .of(plugin)
              .number(98941)
              .handle((versionType, version) -> {

                switch(versionType) {

                  case FOUNDED:
                    player.sendMessage(TextUtil.color(config.getString("messages.update_announce"))
                            .replace("%version%", version));
                    return;

                  case LATEST:
                    return;

                  case UNAVAILABLE:
                    player.sendMessage(TextUtil.color(config.getString("messages.error_update")));
                    return;
                }
              }).check();
    }
  }
}

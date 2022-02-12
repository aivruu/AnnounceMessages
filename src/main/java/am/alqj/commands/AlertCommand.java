package am.alqj.commands;

import am.alqj.AnnounceMessagesPlugin;
import am.alqj.enums.Permissions;
import am.alqj.util.TextUtil;
import am.alqj.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class AlertCommand implements CommandExecutor {

  private final AnnounceMessagesPlugin plugin;
  private final ConsoleCommandSender console;

  public AlertCommand(AnnounceMessagesPlugin plugin) {
    this.plugin = plugin;
    this.console = Bukkit.getConsoleSender();
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    FileConfiguration config = plugin.getSettings().getConfig();

    String prefix = config.getString("prefix");

    if(sender instanceof Player) {
      Player player = (Player) sender;

      if(player.hasPermission(Permissions.COMMAND_ALERT.getPermission())) {
        if (args.length == 0) {

          player.sendMessage(TextUtil.color(config.getString("messages.alert_usage"))
                  .replace(config.getString("identifier"), TextUtil.color(prefix)));

          return true;
        }

        String format = config.getString("messages.alert_format");

        for (String s : args) {
          format = format + s + " ";
        }

        for (Player all : Bukkit.getOnlinePlayers()) {
          all.sendMessage(TextUtil.color(format));
        }

        return false;
      }

      player.playSound(player.getLocation(), XSound.valueOf(config.getString("sounds.permission")).parseSound(), config.getInt("sounds.volume"),
              config.getInt("sounds.pitch"));
      player.sendMessage(TextUtil.color(config.getString("messages.permission"))
              .replace(config.getString("identifier"), TextUtil.color(prefix)));

      return false;
    }

    console.sendMessage(TextUtil.color(config.getString("messages.console"))
            .replace(config.getString("identifier"), TextUtil.color(prefix)));

    return false;
  }
}

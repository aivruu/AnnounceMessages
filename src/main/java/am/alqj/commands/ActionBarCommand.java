package am.alqj.commands;

import am.alqj.AnnounceMessagesAPI;
import am.alqj.AnnounceMessagesPlugin;
import am.alqj.enums.Permissions;
import am.alqj.reflection.ActionBar;
import am.alqj.util.TextUtil;
import am.alqj.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ActionBarCommand implements CommandExecutor {

  private final AnnounceMessagesPlugin plugin;
  private final ConsoleCommandSender console;

  public ActionBarCommand(AnnounceMessagesPlugin plugin) {
    this.plugin = plugin;
    this.console = Bukkit.getConsoleSender();
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    FileConfiguration config = plugin.getSettings().getConfig();

    String prefix = config.getString("prefix");

    if(sender instanceof Player) {
      Player player = (Player) sender;

      if(player.hasPermission(Permissions.COMMAND_ACTIONBAR.getPermission())) {

        if(args.length == 0) {

          player.sendMessage(TextUtil.color(config.getString("messages.actionbar_usage"))
                  .replace(config.getString("identifier"), TextUtil.color(prefix)));

          return true;
        }

        String message = "";

        for(String s : args) {
          message = message + s + " ";
        }

        message = TextUtil.color(message);

        int caracter_limit_actionbar = config.getInt("actionbar_caracter_limit");

        if(message.length() >= caracter_limit_actionbar) {
          player.sendMessage(TextUtil.color(config.getString("messages.actionbar_max_caracter_limit"))
                  .replace(config.getString("identifier"), TextUtil.color(prefix))
                  .replace("%caracters%", caracter_limit_actionbar + ""));

          return true;
        }

        for(Player all : Bukkit.getOnlinePlayers()) {
          AnnounceMessagesAPI.sendActionBar(all, message);
        }

        player.sendMessage(TextUtil.color(config.getString("messages.actionbar_sended"))
                .replace(config.getString("identifier"), TextUtil.color(prefix)));
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

package am.alqj.commands;

import am.alqj.AnnounceMessagesAPI;
import am.alqj.AnnounceMessagesPlugin;
import am.alqj.enums.Permissions;
import am.alqj.reflection.Titles;
import am.alqj.util.TextUtil;
import am.alqj.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class TitleCommand implements CommandExecutor {

  private final AnnounceMessagesPlugin plugin;
  private final ConsoleCommandSender console;

  public TitleCommand(AnnounceMessagesPlugin plugin) {
    this.plugin = plugin;
    this.console = Bukkit.getConsoleSender();
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    FileConfiguration config = plugin.getSettings().getConfig();

    String prefix = config.getString("prefix");

    if(sender instanceof Player) {
      Player player = (Player) sender;

      if(player.hasPermission(Permissions.COMMAND_TITLE.getPermission())) {

        if(args.length == 0) {

          player.sendMessage(TextUtil.color(config.getString("messages.title_usage"))
                  .replace(config.getString("identifier"), TextUtil.color(prefix)));

          return true;
        }

        String title = "";

        for(String s : args) {
          title = title + s + " ";
        }

        title = TextUtil.color(title);

        int caracter_limit_title = config.getInt("title_caracter_limit");

        if(title.length() >= caracter_limit_title) {
          player.sendMessage(TextUtil.color(config.getString("messages.title_max_caracter_limit"))
                  .replace(config.getString("identifier"), TextUtil.color(prefix))
                  .replace("%caracters%", caracter_limit_title + ""));

          return true;
        }

        for(Player all : Bukkit.getOnlinePlayers()) {
          AnnounceMessagesAPI.sendTitle(all, 20, 40, 20, title);
        }

        player.sendMessage(TextUtil.color(config.getString("messages.title_sended"))
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

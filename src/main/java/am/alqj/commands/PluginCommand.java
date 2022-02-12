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

public class PluginCommand implements CommandExecutor {

  private final AnnounceMessagesPlugin plugin;
  private final ConsoleCommandSender console;

  public PluginCommand(AnnounceMessagesPlugin plugin) {
    this.plugin = plugin;
    this.console = Bukkit.getConsoleSender();
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    FileConfiguration config = plugin.getSettings().getConfig();

    String prefix = config.getString("prefix");

    if(sender instanceof Player) {
      Player player = (Player) sender;

      if(args.length == 0) {
        player.sendMessage(TextUtil.color(prefix + "&7 Created by &bAlqj &8- &e" + plugin.getVersionId()));
        return true;
      }

      switch(args[0]) {

        case "help":
          if(player.hasPermission(Permissions.COMMAND_LIST.getPermission())) {
            help(player);

            return true;
          }

          player.playSound(player.getLocation(), XSound.valueOf(config.getString("sounds.permission")).parseSound(), config.getInt("sounds.volume"),
                  config.getInt("sounds.pitch"));
          player.sendMessage(TextUtil.color(config.getString("messages.permission"))
                  .replace(config.getString("identifier"), TextUtil.color(prefix)));

          return false;

        case "reload":
          if(player.hasPermission(Permissions.COMMAND_RELOAD.getPermission())) {

            if(args.length == 1) {
              player.sendMessage(TextUtil.color(config.getString("messages.reload"))
                      .replace(config.getString("identifier"), TextUtil.color(prefix)));

              return true;
            }

            switch(args[1]) {

              case "config.yml":
                plugin.getSettings().reload("config.yml");

                player.playSound(player.getLocation(), XSound.valueOf(config.getString("sounds.reload")).parseSound(), config.getInt("sounds.volume"),
                        config.getInt("sounds.pitch"));
                player.sendMessage(TextUtil.color(config.getString("messages.config"))
                        .replace(config.getString("identifier"), TextUtil.color(prefix)));

                return true;

              case "groups.yml":
                plugin.getSettings().reload("groups.yml");

                player.playSound(player.getLocation(), XSound.valueOf(config.getString("sounds.reload")).parseSound(), config.getInt("sounds.volume"),
                        config.getInt("sounds.pitch"));
                player.sendMessage(TextUtil.color(config.getString("messages.groups"))
                        .replace(config.getString("identifier"), TextUtil.color(prefix)));

                return true;
            }

            player.sendMessage(TextUtil.color(config.getString("messages.file"))
                    .replace(config.getString("identifier"), TextUtil.color(prefix)));

            return false;
          }

          player.playSound(player.getLocation(), XSound.valueOf(config.getString("sounds.permission")).parseSound(), config.getInt("sounds.volume"),
                  config.getInt("sounds.pitch"));
          player.sendMessage(TextUtil.color(config.getString("messages.permission"))
                  .replace(config.getString("identifier"), TextUtil.color(prefix)));

          return false;
      }

      player.sendMessage(TextUtil.color(config.getString("messages.command"))
              .replace(config.getString("identifier"), TextUtil.color(prefix)));

      return false;
    }

    if(args.length == 0) {
      console.sendMessage(TextUtil.color(prefix + "&7 Created by &bAlqj &8- &e" + plugin.getVersionId()));
      return true;
    }

    switch(args[0]) {

      case "help":
        if(console.hasPermission(Permissions.COMMAND_LIST.getPermission())) {
          help(console);

          return true;
        }

        console.sendMessage(TextUtil.color(config.getString("messages.permission"))
                .replace(config.getString("identifier"), TextUtil.color(prefix)));

        return false;

      case "reload":
        if(console.hasPermission(Permissions.COMMAND_RELOAD.getPermission())) {

          if(args.length == 1) {
            console.sendMessage(TextUtil.color(config.getString("messages.reload"))
                    .replace(config.getString("identifier"), TextUtil.color(prefix)));

            return true;
          }

          switch(args[1]) {

            case "config.yml":
              plugin.getSettings().reload("config.yml");

              console.sendMessage(TextUtil.color(config.getString("messages.config"))
                      .replace(config.getString("identifier"), TextUtil.color(prefix)));

              return true;

            case "groups.yml":
              plugin.getSettings().reload("groups.yml");

              console.sendMessage(TextUtil.color(config.getString("messages.groups"))
                      .replace(config.getString("identifier"), TextUtil.color(prefix)));

              return true;
          }

          console.sendMessage(TextUtil.color(config.getString("messages.file"))
                  .replace(config.getString("identifier"), TextUtil.color(prefix)));

          return false;
        }

        console.sendMessage(TextUtil.color(config.getString("messages.permission"))
                .replace(config.getString("identifier"), TextUtil.color(prefix)));

        return false;
    }

    console.sendMessage(TextUtil.color(config.getString("messages.command"))
            .replace(config.getString("identifier"), TextUtil.color(prefix)));

    return false;
  }

  private void help(CommandSender sender) {
    FileConfiguration config = plugin.getSettings().getConfig();

    String text = config.getString("messages.commands_help");
    String[] var = text.split("\n");
    for(int i = 0 ; i < var.length ; i++) {
      String message = var[i];

      message = TextUtil.color(message);

      sender.sendMessage(message);
    }
  }
}

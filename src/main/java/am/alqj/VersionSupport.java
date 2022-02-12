package am.alqj;

import am.alqj.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.util.logging.Level;

public class VersionSupport {

  private final AnnounceMessagesPlugin plugin;
  private final ConsoleCommandSender console;

  public VersionSupport(AnnounceMessagesPlugin plugin) {
    this.plugin = plugin;
    this.console = Bukkit.getConsoleSender();

    init();
  }

  private void init() {
    try {
      String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

      switch(version) {
        case "v1_8_R1":
        case "v1_8_R2":
        case "v1_8_R3":
          console.sendMessage(TextUtil.color("&f Release: &b1.8"));
          return;

        case "v1_9_R1":
        case "v1_9_R2":
          console.sendMessage(TextUtil.color("&f Release: &b1.9"));
          return;

        case "v1_10_R1":
          console.sendMessage(TextUtil.color("&f Release: &b1.10"));
          return;

        case "v1_11_R1":
          console.sendMessage(TextUtil.color("&f Release: &b1.11"));
          return;

        case "v1_12_R1":
          console.sendMessage(TextUtil.color("&f Release: &b1.12"));
          return;

        case "v1_13_R1":
        case "v1_13_R2":
          console.sendMessage(TextUtil.color("&f Release: &b1.13"));
          return;

        case "v1_14_R2":
          console.sendMessage(TextUtil.color("&f Release: &b1.14"));
          return;

        case "v1_15_R2":
          console.sendMessage(TextUtil.color("&f Release: &b1.15"));
          return;

        case "v1_16_R1":
        case "v1_16_R2":
        case "v1_16_R3":
          TextUtil.hex = true;

          console.sendMessage(TextUtil.color("&f Release: &b1.16"));
          return;
        case "v1_17_R1":
          TextUtil.hex = true;

          console.sendMessage(TextUtil.color("&f Release: &b1.17"));
          return;
        case "v1_18_R1":
          TextUtil.hex = true;

          console.sendMessage(TextUtil.color("&f Release: &b1.18"));
          return;
      }

      console.sendMessage(TextUtil.color("&f Release: &care you using a unsupported version."));
      console.sendMessage("");

      Bukkit.getScheduler().cancelTasks(plugin);
      Bukkit.getPluginManager().disablePlugin(plugin);

    } catch(ArrayIndexOutOfBoundsException ex) {
      console.sendMessage(TextUtil.color("&f Release: &can occurred a error to check your server version."));
      console.sendMessage("");

      Bukkit.getScheduler().cancelTasks(plugin);
      Bukkit.getPluginManager().disablePlugin(plugin);
    }
  }
}

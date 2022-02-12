package am.alqj.util;

import am.alqj.AnnounceMessagesAPI;
import am.alqj.AnnounceMessagesPlugin;
import am.alqj.xseries.XPotion;
import am.alqj.xseries.XSound;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;

public class Actions {

  private final AnnounceMessagesPlugin plugin;

  public Actions(AnnounceMessagesPlugin plugin) {
    this.plugin = plugin;
  }

  public void effect(String line, Player player) {
    line = line.replace("<$Effect> ", "");

    String[] var = line.split(";");

    player.addPotionEffect(XPotion.matchXPotion(var[0]).get().buildPotionEffect(Integer.parseInt(var[1]), Integer.parseInt(var[2]) - 1));
  }

  public void titles(String line, Player player) {
    line = line.replace("<$Titles> ", "");

    String[] var = line.split(";");
    String title = var[0];
    String subtitle = var[1];

    int in = Integer.parseInt(var[2]);
    int show = Integer.parseInt(var[3]);
    int out = Integer.parseInt(var[4]);

    AnnounceMessagesAPI.sendTitle(player, in, show, out, title, subtitle);
  }

  public void title(String line, Player player) {
    line = line.replace("<$Title> ", "");

    String[] var = line.split(";");

    String title = var[0];

    int in = Integer.parseInt(var[1]);
    int show = Integer.parseInt(var[2]);
    int out = Integer.parseInt(var[3]);

    AnnounceMessagesAPI.sendTitle(player, in, show, out, title);
  }

  public void actionBar(String line, Player player) {
    line = line.replace("<$ActionBar> ", "");

    AnnounceMessagesAPI.sendActionBar(player, line);
  }

  public void sound(String line, Player player) {
    line = line.replace("<$Sound> ", "");

    String[] var = line.split(";");

    XSound sound = XSound.valueOf(var[0]);

    int volume = Integer.parseInt(var[1]);
    int pitch = Integer.parseInt(var[2]);

    player.playSound(player.getLocation(), sound.parseSound(), volume, pitch);
  }

  public void firework(String line, Player player) {
    line = line.replace("<$Firework> ", "");

    String[] var = line.split(";");

    Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
    FireworkMeta meta = firework.getFireworkMeta();

    meta.addEffect(FireworkEffect.builder()
            .flicker(Boolean.parseBoolean(var[0]))
            .trail(Boolean.parseBoolean(var[1]))

            .with(FireworkEffect.Type.valueOf(var[2]))

            .withColor(Color.BLUE)
            .withColor(Color.GREEN)
            .withColor(Color.AQUA).build());

    meta.setPower(Integer.parseInt(var[3]));

    firework.setFireworkMeta(meta);
  }

  public void message(String line, Player player) {
    line = line.replace("<$Message> ", "");

    player.sendMessage(line);
  }

  public void console(String line) {
    line = line.replace("<$Console> ", "");

    Bukkit.getConsoleSender().sendMessage(line);
  }

  public void command(String line, Player player) {
    line = line.replace("<$Command> ", "");

    player.performCommand(line);
  }

  public void commandConsole(String line) {
    line = line.replace("<$ConsoleCMD> ", "");

    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), line);
  }

  /**
   * Register and build all the actions.
   * @param line
   * @param player
   */
  public void execute(String line, Player player) {
    if(line.startsWith("<$Effect> ")) {
      effect(line, player);
      return;
    }

    if(line.startsWith("<$Remove>")) {
      for(PotionEffect effectsType : player.getActivePotionEffects()) {
        player.removePotionEffect(effectsType.getType());
      }
      return;
    }

    if(line.startsWith("<$Titles> ")) {
      titles(line, player);
      return;
    }

    if(line.startsWith("<$Title> ")) {
      title(line, player);
      return;
    }

    if(line.startsWith("<$ActionBar> ")) {
      actionBar(line, player);
      return;
    }

    if(line.startsWith("<$Sound> ")) {
      sound(line, player);
      return;
    }

    if(line.startsWith("<$Firework> ")) {
      firework(line, player);
      return;
    }

    if(line.startsWith("<$Message> ")) {
      message(line, player);
      return;
    }

    if(line.startsWith("<$Console> ")) {
      console(line);
      return;
    }

    if(line.startsWith("<$Command> ")) {
      command(line, player);
      return;
    }

    if(line.startsWith("<$ConsoleCMD> ")) {
      commandConsole(line);
      return;
    }

    plugin.getLogger().warning("The action '" + line + "' doesn't exists or is invalid.");
  }
}

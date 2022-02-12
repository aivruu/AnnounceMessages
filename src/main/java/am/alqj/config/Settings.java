package am.alqj.config;

import am.alqj.AnnounceMessagesPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Settings {

  private final AnnounceMessagesPlugin plugin;

  private File cfile;
  private File gfile;
  private FileConfiguration config;
  private FileConfiguration groups;

  public Settings(AnnounceMessagesPlugin plugin) {
    this.plugin = plugin;

    createAndLoad();
  }

  private void createAndLoad() {
    cfile = new File("plugins/AnnounceMessages", "config.yml");
    if(!cfile.exists()) plugin.saveResource("config.yml", false);

    gfile = new File("plugins/AnnounceMessages", "groups.yml");
    if(!gfile.exists()) plugin.saveResource("groups.yml", false);

    config = YamlConfiguration.loadConfiguration(cfile);
    groups = YamlConfiguration.loadConfiguration(gfile);
  }

  public void save(String file) {
    switch(file) {
      case "config.yml":
        try {
          config.save(cfile);
        } catch(IOException ex) {
          ex.printStackTrace();
        }
        return;

      case "groups.yml":
        try {
          groups.save(gfile);
        } catch(IOException ex) {
          ex.printStackTrace();
        }
        return;
    }

    plugin.getLogger().warning("The file '" + file + "' doesn't exists.");
  }

  public void reload(String file) {
    switch(file) {
      case "config.yml":
        config = YamlConfiguration.loadConfiguration(cfile);
        return;

      case "groups.yml":
        groups = YamlConfiguration.loadConfiguration(gfile);
        return;
    }

    plugin.getLogger().warning("The file '" + file + "' doesn't exists.");
  }

  public FileConfiguration getConfig() { return config; }
  public FileConfiguration getGroups() { return groups; }
}

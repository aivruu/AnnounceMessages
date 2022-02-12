package am.alqj;

import am.alqj.commands.*;
import am.alqj.config.Settings;
import am.alqj.listeners.MotdListener;
import am.alqj.listeners.NetworksMedia;
import am.alqj.listeners.PlayerListener;
import am.alqj.notify.Updater;
import am.alqj.util.Actions;
import am.alqj.util.TextUtil;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnnounceMessagesPlugin extends JavaPlugin {

  PluginDescriptionFile PDF = getDescription();
  private final ConsoleCommandSender console = Bukkit.getConsoleSender();

  private Settings settings;
  private Actions actions;
  private int VERSION;

  public boolean papi = false;

  public Chat chat = null;
  public Permission permission = null;

  public String getDeveloper() { return "Alqj"; }
  public String getVersionId() { return PDF.getVersion(); }

  @Override
  public void onEnable() {
    long START = System.currentTimeMillis();

    String SOFTWARE_SERVER = Bukkit.getVersion().split("-")[1];
    if(SOFTWARE_SERVER.startsWith("Spigot")) {

      console.sendMessage(TextUtil.color("&8[&aAMSG&8] &fSoftware detected: &6Spigot"));
      console.sendMessage(TextUtil.color("&8[&aAMSG&8] &fIt's recommended use &bPaperSpigot&f, this is just a warning."));
    }

    console.sendMessage("");
    console.sendMessage(TextUtil.color("&a AnnounceMessages"));
    console.sendMessage("");
    console.sendMessage(TextUtil.color("&f Author: &b" + getDeveloper()));
    console.sendMessage(TextUtil.color("&f Version: &b" + getVersionId()));
    console.sendMessage("");

    settings = new Settings(this);
    actions = new Actions(this);

    setupCommands();
    setupListeners();

    if(Bukkit.getPluginManager().getPlugin("Vault") == null) {

      console.sendMessage(TextUtil.color("&f Vault: &cunavailable"));
      console.sendMessage("");

      Bukkit.getPluginManager().disablePlugin(this);
      return;
    }

    chatProvider();
    permissionProvider();
    console.sendMessage(TextUtil.color("&f Vault: &aavailable"));
    console.sendMessage("");

    if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) papi = true;

    VERSION = Integer.parseInt(Bukkit.getBukkitVersion().split("-")[0].replace(".", "#").split("#")[1]);

    if(getSettings().getConfig().getBoolean("notify")) {
      Updater
              .of(this)
              .number(98941)
              .handle((versionType, version) -> {

                switch(versionType) {

                  case FOUNDED:
                    getLogger().info("There are a new version available: " + version);
                    getLogger().info("https://www.spigotmc.org/resources/announcemessages-1-8-1-18-1.98941/updates");
                    break;

                  case LATEST:
                    getLogger().warning("No are new versions available.");
                    break;

                  case UNAVAILABLE:
                    getLogger().warning("Unable to perform an update check.");
                    break;
                }
              }).check();
    }

    new VersionSupport(this);

    console.sendMessage("");
    console.sendMessage(TextUtil.color("&f Enabled in &a" + (System.currentTimeMillis() - START) + "ms&f."));
    console.sendMessage("");
  }

  @Override
  public void onDisable() {
    Bukkit.getScheduler().cancelTasks(this);
  }

  public PluginDescriptionFile getPDF() { return PDF; }
  public Settings getSettings() { return settings; }
  public Actions getActions() { return actions; }
  public int getVersionNumber() { return VERSION; }

  private void setupCommands() {
    getCommand("announcemessages").setExecutor(new PluginCommand(this));
    getCommand("title").setExecutor(new TitleCommand(this));
    getCommand("alert").setExecutor(new AlertCommand(this));
    getCommand("actionbar").setExecutor(new ActionBarCommand(this));
  }

  private void setupListeners() {
    PluginManager pm = Bukkit.getPluginManager();

    pm.registerEvents(new PlayerListener(this), this);
    pm.registerEvents(new MotdListener(this), this);
    pm.registerEvents(new NetworksMedia(this), this);
  }

  private void chatProvider() {
    RegisteredServiceProvider<Chat> rspChat = getServer().getServicesManager().getRegistration(Chat.class);
    if(rspChat == null) return;

    chat = rspChat.getProvider();
  }

  private void permissionProvider() {
    RegisteredServiceProvider<Permission> rspPermission = getServer().getServicesManager().getRegistration(Permission.class);
    if(rspPermission == null) return;

    permission = rspPermission.getProvider();
  }
}

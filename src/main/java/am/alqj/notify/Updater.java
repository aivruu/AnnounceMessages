package am.alqj.notify;

import am.alqj.AnnounceMessagesPlugin;
import am.alqj.enums.UpdateType;
import com.google.common.base.Preconditions;
import com.google.common.io.Resources;
import com.google.common.net.HttpHeaders;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.function.BiConsumer;

public class Updater {

  private static final String API_URL = "https://api.spigotmc.org/legacy/update.php?resource=%d";

  private final AnnounceMessagesPlugin plugin;

  private String currentVersion;
  private int resourceNumber = -1;
  private BiConsumer<UpdateType, String> versionType;

  private Updater(AnnounceMessagesPlugin plugin) {
    this.plugin = Objects.requireNonNull(plugin, "AnnounceMessages");
    this.currentVersion = plugin.getPDF().getVersion();
  }

  public static Updater of(AnnounceMessagesPlugin plugin) { return new Updater(plugin); }

  public Updater current(String currentVersion) {
    this.currentVersion = currentVersion;
    return this;
  }

  public Updater number(int resourceNumber) {
    this.resourceNumber = resourceNumber;
    return this;
  }

  public Updater handle(BiConsumer<UpdateType, String> versionType) {
    this.versionType = versionType;
    return this;
  }

  public void check() {
    Objects.requireNonNull(plugin, "AnnounceMessages");
    Objects.requireNonNull(currentVersion, "currentVersion");
    Preconditions.checkState(resourceNumber != 1, "resource number not set");
    Objects.requireNonNull(versionType, "versionType");

    Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
      try {
        HttpURLConnection connection = (HttpURLConnection) new URL(String.format(API_URL, resourceNumber)).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty(HttpHeaders.USER_AGENT, "Mozilla/5.0");

        String fetched = Resources.toString(connection.getURL(), Charset.defaultCharset());

        boolean latest = fetched.equalsIgnoreCase(currentVersion);

        Bukkit.getScheduler().runTask(plugin, () -> versionType.accept(latest ? UpdateType.LATEST : UpdateType.FOUNDED, latest ? currentVersion : fetched));
      } catch(IOException ex) {
        ex.printStackTrace();

        Bukkit.getScheduler().runTask(plugin, () -> versionType.accept(UpdateType.UNAVAILABLE, null));
      }
    });
  }
}

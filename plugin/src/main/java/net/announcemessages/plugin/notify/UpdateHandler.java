package net.announcemessages.plugin.notify;

import net.announcemessages.plugin.util.LogUtils;
import net.xconfig.bukkit.config.BukkitConfigurationHandler;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateHandler {
	private final BukkitConfigurationHandler configurationHandler;
	private final int resourceNumber;
	
	public UpdateHandler(BukkitConfigurationHandler configurationHandler, int resourceNumber) {
		this.configurationHandler = Objects.requireNonNull(configurationHandler, "The BukkitConfigurationHandler object is null.");
		this.resourceNumber = resourceNumber;
	}
	
	public void check(Consumer<String> consumer) {
		try {
			Scanner scanner = new Scanner(new URL("https://api.spigotmc.org/legacy/update.php?resource=" + resourceNumber).openStream());
			if (scanner.hasNext()) consumer.accept(scanner.next());
		} catch (IOException exception) {
			LogUtils.error("Unable to check for updates available.");
			if (configurationHandler.condition("", "config.yml", "config.debug-mode")) exception.printStackTrace();
		}
	}
}

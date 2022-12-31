package net.announcemessages.plugin.loaders;

import net.announcemessages.plugin.AnnounceMessages;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.Objects;

public class EventLoader {
	private EventLoader() {}
	
	public static class Builder {
		private final AnnounceMessages plugin;
		
		private Listener[] listeners;
		
		public Builder(AnnounceMessages plugin) {
			this.plugin = Objects.requireNonNull(plugin, "The AnnounceMessages instance is null.");
		}
		
		public Builder from(Listener... listeners) {
			this.listeners = Objects.requireNonNull(listeners, "The listeners to register are null.");
			return this;
		}
		
		public void register() {
			if (listeners == null) return;
			
			PluginManager pluginManager = plugin.getServer().getPluginManager();
			for (Listener listener : listeners) pluginManager.registerEvents(listener, plugin);
		}
	}
}

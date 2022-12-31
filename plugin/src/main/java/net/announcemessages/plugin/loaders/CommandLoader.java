package net.announcemessages.plugin.loaders;

import com.google.common.base.Preconditions;
import net.announcemessages.plugin.AnnounceMessages;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;

import java.util.Objects;

public class CommandLoader {
	private CommandLoader() {}
	
	public static class Builder {
		private final AnnounceMessages plugin;
		
		private String commandName;
		private CommandExecutor executor;
		private TabCompleter completer;
		
		public Builder(AnnounceMessages plugin) {
			this.plugin = Objects.requireNonNull(plugin, "The AnnounceMessages instance is null.");
		}
		
		public Builder command(String commandName) {
			this.commandName = Objects.requireNonNull(commandName, "The command name is null.");
			Preconditions.checkArgument(!commandName.isEmpty(), "The command name is empty.");
			return this;
		}
		
		public Builder executor(CommandExecutor executor) {
			this.executor = Objects.requireNonNull(executor, "The command executor is null.");
			return this;
		}
		
		public Builder completer(TabCompleter completer) {
			this.completer = Objects.requireNonNull(completer, "The tab completer is null.");
			return this;
		}
		
		public void build() {
			if (commandName == null) return;
			
			if (executor == null) return;
			
			PluginCommand command = plugin.getCommand(commandName);
			if (command == null) return;
			
			if (completer != null) command.setTabCompleter(completer);
			
			command.setExecutor(executor);
		}
	}
}

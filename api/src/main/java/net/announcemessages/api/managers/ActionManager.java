package net.announcemessages.api.managers;

import net.announcemessages.api.actions.ActionContext;
import net.announcemessages.api.actions.ActionExecutable;
import org.bukkit.entity.Player;

import java.util.List;

public interface ActionManager {
	default void register(ActionExecutable... executables) {
		for (ActionExecutable executable : executables) {
			register(executable.getContext(), executable);
		}
	}
	
	default void execute(Player player, List<String> containers) {
		for (String container : containers) {
			execute(player, container);
		}
	}
	
	void register(ActionContext context, ActionExecutable executable);
	
	void execute(Player player, String container);
	
	void unregister(ActionContext context);
	
	void unregisterAll();
}

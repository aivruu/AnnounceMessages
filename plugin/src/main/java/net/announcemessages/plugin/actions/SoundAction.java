package net.announcemessages.plugin.actions;

import net.announcemessages.api.actions.ActionContext;
import net.announcemessages.api.actions.ActionExecutable;
import net.announcemessages.plugin.util.LogUtils;
import net.announcemessages.plugin.xseries.XSound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class SoundAction
implements ActionExecutable {
	public SoundAction() {}
	
	@Override
	public ActionContext getContext() {
		return ActionContext.SOUND;
	}
	
	@Override
	public void execute(JavaPlugin plugin, Player player, String container) {
		String[] parts = container.split(";", 3);
		
		Optional<XSound> optionalXSound = XSound.matchXSound(parts[0]);
		if (!optionalXSound.isPresent()) {
			LogUtils.error("Cannot execute the action because the sound required is not present.");
			return;
		}
		
		int volume;
		int pitch;
		try {
			volume = Integer.parseInt(parts[1]);
			pitch = Integer.parseInt(parts[2]);
		} catch (NumberFormatException exception) {
			LogUtils.error("Cannot parse the sound volume levels because the values are not valid.");
			return;
		}
		
		player.playSound(player.getLocation(), optionalXSound.get().parseSound(), volume, pitch);
	}
}

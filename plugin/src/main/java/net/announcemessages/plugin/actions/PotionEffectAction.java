package net.announcemessages.plugin.actions;

import net.announcemessages.api.actions.ActionContext;
import net.announcemessages.api.actions.ActionExecutable;
import net.announcemessages.plugin.util.LogUtils;
import net.announcemessages.plugin.xseries.XPotion;
import net.xconfig.bukkit.config.BukkitConfigurationHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.util.Objects;
import java.util.Optional;

public class PotionEffectAction
implements ActionExecutable {
	private final BukkitConfigurationHandler configurationHandler;
	
	public PotionEffectAction(BukkitConfigurationHandler configurationHandler) {
		this.configurationHandler = Objects.requireNonNull(configurationHandler, "The BukkitConfigurationHandler object is null.");
	}
	
	@Override
	public ActionContext getContext() {
		return ActionContext.POTION_EFFECT;
	}
	
	@Override
	public void execute(JavaPlugin plugin, Player player, String container) {
		String[] parts = container.split(";", 3);
		
		Optional<XPotion> optionalXPotion = XPotion.matchXPotion(parts[0]);
		if (!optionalXPotion.isPresent()) {
			LogUtils.error("Cannot execute the action because the potion effect required is not present.");
			return;
		}
		
		int duration;
		int amplifier;
		try {
			if (parts[1].equals("max")) duration = Integer.MAX_VALUE;
			else duration = Integer.parseInt(parts[1]);
			amplifier = Integer.parseInt(parts[2]);
		} catch (NumberFormatException exception) {
			LogUtils.error("Cannot parse the potion effect duration parameters because the values are not valid.");
			if (configurationHandler.condition("", "config.yml", "config.debug-mode")) exception.printStackTrace();
			return;
		}
		
		player.addPotionEffect(new PotionEffect(optionalXPotion.get().getPotionEffectType(), duration, amplifier));
	}
}

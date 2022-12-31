package net.announcemessages.plugin.actions;

import net.announcemessages.api.actions.ActionContext;
import net.announcemessages.api.actions.ActionExecutable;
import net.announcemessages.plugin.util.LogUtils;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class FireworkAction
implements ActionExecutable {
	public FireworkAction() {}
	
	@Override
	public ActionContext getContext() {
		return ActionContext.FIREWORK;
	}
	
	@Override
	public void execute(JavaPlugin plugin, Player player, String container) {
		String[] parts = container.split(";", 4);
		
		Firework firework = player.getWorld().spawn(player.getLocation(), Firework.class);
		FireworkMeta meta = firework.getFireworkMeta();
		
		meta.addEffect(FireworkEffect.builder()
			 .flicker(Boolean.parseBoolean(parts[0]))
			 .trail(Boolean.parseBoolean(parts[1]))
			 .with(FireworkEffect.Type.valueOf(parts[2]))
			 .withColor(
				  Color.BLUE,
				  Color.GREEN,
				  Color.AQUA
			 )
			 .build()
		);
		
		int power;
		try { power = Integer.parseInt(parts[3]); }
		catch (NumberFormatException exception) {
			LogUtils.error("Cannot parse the firework power parameter because is not valid.");
			return;
		}
		
		meta.setPower(power);
		firework.setFireworkMeta(meta);
	}
}

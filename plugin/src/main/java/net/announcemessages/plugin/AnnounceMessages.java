package net.announcemessages.plugin;

import net.announcemessages.api.managers.ActionManager;
import net.announcemessages.api.managers.AnnounceManager;
import net.announcemessages.plugin.actions.ActionBarAction;
import net.announcemessages.plugin.actions.BroadcastAction;
import net.announcemessages.plugin.actions.CommandAction;
import net.announcemessages.plugin.actions.FireworkAction;
import net.announcemessages.plugin.actions.MessageAction;
import net.announcemessages.plugin.actions.PotionEffectAction;
import net.announcemessages.plugin.actions.SoundAction;
import net.announcemessages.plugin.actions.TitleAction;
import net.announcemessages.plugin.commands.ActionBarCommand;
import net.announcemessages.plugin.commands.AlertCommand;
import net.announcemessages.plugin.commands.MainCommand;
import net.announcemessages.plugin.commands.TitleCommand;
import net.announcemessages.plugin.factories.HandlerFactory;
import net.announcemessages.plugin.factories.LoaderFactory;
import net.announcemessages.plugin.factories.ManagerFactory;
import net.announcemessages.plugin.listeners.PlayerListener;
import net.announcemessages.plugin.loaders.CommandLoader;
import net.announcemessages.plugin.util.LogUtils;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.xconfig.bukkit.XConfigBukkit;
import net.xconfig.bukkit.config.BukkitConfigurationHandler;
import net.xconfig.bukkit.config.BukkitConfigurationModel;
import net.xtitle.api.AdaptManager;
import net.xtitle.api.TitleManager;
import net.xtitle.lib.XTitle;
import org.bukkit.plugin.java.JavaPlugin;

public final class AnnounceMessages
extends JavaPlugin {
	public final String release = getDescription().getVersion();
	
	private static AnnounceMessages plugin;
	
	private BukkitConfigurationModel configurationManager;
	private BukkitConfigurationHandler configurationHandler;
	private LuckPerms luckPerms;
	private AdaptManager adaptManager;
	private TitleManager titleManager;
	private ActionManager actionManager;
	private AnnounceManager announceManager;
	
	public static AnnounceMessages getPlugin() {
		if (plugin == null) throw new IllegalStateException("Cannot get the AnnounceMessages instance.");
		
		return plugin;
	}
	
	public TitleManager getTitleManager() {
		return titleManager;
	}
	
	public BukkitConfigurationModel getConfigurationManager() {
		return configurationManager;
	}
	
	public BukkitConfigurationHandler getConfigurationHandler() {
		return configurationHandler;
	}
	
	public ActionManager getActionManager() {
		return actionManager;
	}
	
	public AnnounceManager getAnnounceManager() {
		return announceManager;
	}
	
	@Override
	public void onLoad() {
		plugin = this;
		
		LogUtils.info(
			 "Booting up plugin internal components...",
			 "Developed by InitSync - " + release
		);
		
		new Metrics(plugin, 17280);
		
		configurationManager = XConfigBukkit.manager(plugin);
		configurationHandler = XConfigBukkit.handler(configurationManager);
		adaptManager = XTitle.newAdaptManager();
		adaptManager.findAdapt();
		titleManager = XTitle.newTitleManager(adaptManager.getAdapt());
		actionManager = ManagerFactory.newActionManager(plugin);
	}
	
	@Override
	public void onEnable() {
		long startTime = System.currentTimeMillis();
		
		configurationManager.build("", "config.yml", "messages.yml");
		
		luckPerms = LuckPermsProvider.get();
		announceManager = ManagerFactory.newAnnounceManager(configurationHandler, luckPerms.getUserManager(), actionManager);
		
		actionManager.register(
			 new SoundAction(configurationHandler),
			 new PotionEffectAction(configurationHandler),
			 new TitleAction(configurationHandler),
			 new ActionBarAction(configurationHandler),
			 new FireworkAction(configurationHandler),
			 new CommandAction(),
			 new BroadcastAction(),
			 new MessageAction()
		);
		
		CommandLoader.Builder commandLoader = LoaderFactory.newCommandLoader(plugin);
		commandLoader.command("announcemessages")
			 .executor(new MainCommand(configurationManager, configurationHandler))
			 .build();
		commandLoader.command("title")
			 .executor(new TitleCommand(configurationHandler))
			 .build();
		commandLoader.command("actionbar")
			 .executor(new ActionBarCommand(plugin, configurationHandler))
			 .build();
		commandLoader.command("alert")
			 .executor(new AlertCommand(configurationHandler))
			 .build();
		
		LoaderFactory.newEventLoader(plugin)
			 .from(new PlayerListener(configurationHandler, announceManager))
			 .register();
		
		LogUtils.info(
			 "Enabled plugin successful in " + (System.currentTimeMillis() - startTime) + "ms.",
			 "Running on " + getServer().getBukkitVersion(),
			 "Developed by InitSync - " + release
		);
		
		if (configurationHandler.condition("", "config.yml", "config.notify")) {
			HandlerFactory.newUpdateHandler(configurationHandler, 98941).check(latestVersion -> {
				int latestRelease = Integer.parseInt(latestVersion.split("\\.", 2)[0]);
				int currentRelease = Integer.parseInt(release.split("\\.", 2)[0]);
				
				if (latestRelease > currentRelease) {
					LogUtils.info("There is a new update available: " + latestVersion);
					return;
				}
				
				if (latestRelease == currentRelease) LogUtils.info("There is not a new update pending.");
			});
		}
	}
	
	@Override
	public void onDisable() {
		LogUtils.info(
			 "Disabling plugin components...",
			 "Developed by InitSync - " + release
		);
		
		if (configurationManager != null) configurationManager = null;
		if (configurationHandler != null) configurationHandler = null;
		
		if (luckPerms != null) luckPerms = null;
		
		if (adaptManager != null) adaptManager = null;
		if (titleManager != null) titleManager = null;
		
		if (actionManager != null) {
			actionManager.unregisterAll();
			actionManager = null;
		}
		if (announceManager != null) announceManager = null;
		
		if (plugin != null) plugin = null;
	}
}

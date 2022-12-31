package net.announcemessages.api.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;

public class ServerAnnouncementEvent
extends Event
implements Cancellable {
	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	private final String message;
	
	private boolean cancelled;
	
	public ServerAnnouncementEvent(String message) {
		this.message = Objects.requireNonNull(message, "The message can't be null.");
	}
	
	public String getMessage() {
		return message;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
	
	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}
	
	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}
}

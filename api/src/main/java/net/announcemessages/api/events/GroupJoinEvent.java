package net.announcemessages.api.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Objects;
import java.util.UUID;

public class GroupJoinEvent
extends Event
implements Cancellable {
	private static final HandlerList HANDLER_LIST = new HandlerList();
	
	private final UUID playerId;
	private final String groupName;
	
	private boolean cancelled;
	
	public GroupJoinEvent(UUID playerId, String groupName) {
		this.playerId = Objects.requireNonNull(playerId, "The player uuid is null.");
		this.groupName = Objects.requireNonNull(groupName, "The player group name is null.");
	}
	
	public UUID getPlayerId() {
		return playerId;
	}
	
	public String getGroupName() {
		return groupName;
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

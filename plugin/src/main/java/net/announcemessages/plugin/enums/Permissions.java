package net.announcemessages.plugin.enums;

public enum Permissions {
	COMMAND_LIST("announcemessages.list"),
	COMMAND_RELOAD("announcemessages.reload"),
	COMMAND_ALERT("announcemessages.alert"),
	COMMAND_TITLE("announcemessages.title"),
	COMMAND_ACTIONBAR("announcemessages.actionbar"),
	UPDATE_LOG("announcemessages.update");
	
	private final String permission;
	
	Permissions(String permission) {
		this.permission = permission;
	}
	
	public String getPermission() { return permission; }
}

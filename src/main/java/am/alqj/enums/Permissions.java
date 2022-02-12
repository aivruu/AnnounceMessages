package am.alqj.enums;

public enum Permissions {

  COMMAND_LIST("command.list"),
  COMMAND_RELOAD("command.reload"),
  COMMAND_ALERT("command.alert"),
  COMMAND_TITLE("command.title"),
  COMMAND_ACTIONBAR("command.actionbar"),
  UPDATE_LOG("log.update");

  private final String permission;

  Permissions(String permission) {
    this.permission = permission;
  }

  public final String getPermission() { return "amsg." + permission; }
}

package me.rich.nations.permission;

import java.util.HashMap;
import java.util.Map;

import me.rich.nations.NationsPlugin;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

public class PermissionManager {

	private NationsPlugin plugin;
	private Map<String, PermissionAttachment> permissionAttachment;

	public PermissionManager(NationsPlugin plugin) {
		this.plugin = plugin;
		this.permissionAttachment = new HashMap<String, PermissionAttachment>();
	}

	public void addAttachment(Player player) {
		this.permissionAttachment.put(player.getName(), player.addAttachment(plugin));
	}

	public boolean hasAttachment(Player player) {
		return this.permissionAttachment.containsKey(player.getName());
	}

	public void removeAttachment(Player player) {
		if (!this.hasAttachment(player)) {
			return;
		}
		this.permissionAttachment.remove(player.getName());
	}
	
	public void addPermission(Player player, String permission) {
		if (this.hasAttachment(player)) {
			this.permissionAttachment.get(player.getName()).setPermission(permission, true);
		}
	}
	
	public boolean hasPermission(Player player, String permission) {
		return player.hasPermission(permission);
	}
	
	public void removePermission(Player player, String permission) {
		if (this.hasAttachment(player)) {
			this.permissionAttachment.get(player.getName()).setPermission(permission, false);
		}
	}
}

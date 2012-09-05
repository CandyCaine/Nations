package me.rich.nations.listener;

import me.rich.nations.NationsPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

	private NationsPlugin plugin;
	
	public PlayerListener(NationsPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler()
	public void onPlayerJoin(final PlayerJoinEvent event) {
		this.plugin.getPermissionManager().addAttachment(event.getPlayer());
	}
	
	@EventHandler()
	public void onPlayerKick(final PlayerKickEvent event) {
		this.plugin.getPermissionManager().removeAttachment(event.getPlayer());
	}
	
	@EventHandler()
	public void onPlayerQuit(final PlayerQuitEvent event) {
		this.plugin.getPermissionManager().removeAttachment(event.getPlayer());
	}
}

package me.rich.nations.region;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SelectSession {

	private String name;
	
	private Location pos1, pos2;
	
	public SelectSession(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(name);
	}
	
	public Location getPositionOne() {
		return this.pos1;
	}
	
	public void setPositionOne(Location loc) {
		this.pos1 = loc;
	}
	
	public Location getPositionTwo() {
		return this.pos2;
	}
	
	public void setPositionTwo(Location loc) {
		this.pos2 = loc;
	}
	
	public Region createRegion() {
		if (pos1 == null || pos2 == null) {
			return null;
		}
		return new Region(name, this.getPlayer().getWorld().getName(), pos1, pos2);
	}
}

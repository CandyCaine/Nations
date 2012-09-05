package me.rich.nations.region;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import me.rich.nations.NationsPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class Region implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 356227677290853651L;

	private String name;
	private String world;

	private int minX;
	private int minY;
	private int minZ;

	private int maxX;
	private int maxY;
	private int maxZ;

	public Region() {
	}

	public Region(String name, String world, int x1, int y1, int z1, int x2, int y2, int z2) {
		this.name = name;
		this.world = world;
		this.minX = Math.min(x1, x2);
		this.minY = Math.min(y1, y2);
		this.minZ = Math.min(z1, z2);

		this.maxX = Math.max(x1, x2);
		this.maxY = Math.max(y1, y2);
		this.maxZ = Math.max(z1, z2);
	}

	public Region(String name, String world, Location loc1, Location loc2) {
		this.name = name;
		this.world = world;
		this.minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
		this.minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
		this.minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

		this.maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
		this.maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
		this.maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
	}

	public String getName() {
		return this.name;
	}

	public String getWorldName() {
		return this.world;
	}

	public World getWorld() {
		return Bukkit.getWorld(world);
	}

	public Location getMinLocation() {
		return new Location(Bukkit.getWorld(world), minX, minY, minZ);
	}

	public int getMinX() {
		return this.minX;
	}

	public int getMinY() {
		return this.minY;
	}

	public int getMinZ() {
		return this.minZ;
	}

	public Location getMaxLocation() {
		return new Location(Bukkit.getWorld(world), maxX, maxY, maxZ);
	}

	public int getMaxX() {
		return this.maxX;
	}

	public int getMaxY() {
		return this.maxY;
	}

	public int getMaxZ() {
		return this.maxZ;
	}
	
	public void setLocations(Location loc1, Location loc2) {
		this.minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
		this.minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
		this.minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

		this.maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
		this.maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
		this.maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
	}

	public boolean containsLocation(Location location) {
		if (!location.getWorld().getName().equals(this.world)) {
			return false;
		}

		int x = location.getBlockX(), y = location.getBlockY(), z = location.getBlockZ();

		if (x >= minX && x <= maxX && y >= minY && y <= maxY && z >= minZ && z <= maxZ) {
			return true;
		}

		return false;
	}

	public boolean containsBlock(Block block) {
		return this.containsLocation(block.getLocation());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Region) {
			Region reg = (Region) obj;

			Location locMin = reg.getMinLocation(), locMax = reg.getMaxLocation();
			if (locMin.getBlockX() == minX && locMin.getBlockY() == minY && locMin.getBlockZ() == minZ) {
				if (locMax.getBlockX() == maxX && locMax.getBlockY() == maxY && locMax.getBlockZ() == maxZ) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		String hashcode = "" + minX + "" + minY + "" + minZ + "" + maxX + "" + maxY + "" + maxZ;
		return hashcode.hashCode();
	}

	public void serialize(File folder, String name) {
		File file = new File(folder, name + ".ser");
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deserialize(String name) {
		File file = new File(NationsPlugin.dataFolder, name + ".ser");
		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Region r = (Region) in.readObject();
			this.name = r.getName();
			this.world = r.getWorldName();
			this.setLocations(r.getMinLocation(), r.getMaxLocation());
			in.close();
			fileIn.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}

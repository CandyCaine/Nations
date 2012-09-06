package me.rich.nations.persistence;

import lib.PatPeter.SQLibrary.SQLite;
import me.rich.nations.NationsPlugin;

public class Database {

	private NationsPlugin plugin;
	private SQLite sqlite;

	public Database(NationsPlugin plugin, String name) {
		this.plugin = plugin;
		this.sqlite = new SQLite(this.plugin.getLogger(), this.plugin.getDescription().getFullName(), name, this.plugin.getDataFolder().getAbsolutePath());

		try {
			this.sqlite.open();
		} catch (Exception e) {
			this.plugin.getLogger().info(e.getMessage());
			this.plugin.getPluginLoader().disablePlugin(this.plugin);
		}
		
		this.checkTable();
	}
	
	private void checkTable() {
		if (this.sqlite.checkTable("nation")) {
			return;
		} else {
			String query = "CREATE TABLE nation (id INT PRIMARY KEY, nationname VARCHAR(50), description VARCHAR(150));";
			this.sqlite.query(query);
		}
		
		if (this.sqlite.checkTable("player")) {
			return;
		} else {
			String query = "CREATE TABLE player (id INT PRIMARY KEY, playername VARCHAR(50), nationname VARCHAR(150), rank VARCHAR(30));";
			this.sqlite.query(query);
		}
	}

	public void close() {
		this.sqlite.close();
	}
}

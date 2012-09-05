package me.rich.nations.persistence;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class FileManager {

	private Plugin plugin;
	
	private File configFile;
	private FileConfiguration config;
	
	public FileManager(Plugin plugin) {
		this.plugin = plugin;
	}
	
	public void load() {
		if (this.plugin.getDataFolder().exists()) {
			this.plugin.getDataFolder().mkdirs();
		}
		
		this.configFile = new File(this.plugin.getDataFolder(), "config.yml");
		if (!this.configFile.exists()) {
			this.copyFile(this.plugin.getResource("config.yml"), this.configFile);
		}
		
		this.config = new YamlConfiguration();
		try {
			this.config.load(this.configFile);
		} catch (Exception e) {
			this.plugin.getLogger().log(Level.SEVERE, "Config file could not be created.");
			e.printStackTrace();
		}
	}
	
	private void copyFile(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

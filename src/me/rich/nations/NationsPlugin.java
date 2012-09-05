package me.rich.nations;

import java.io.File;

import me.rich.nations.listener.BlockListener;
import me.rich.nations.listener.EntityListener;
import me.rich.nations.listener.PlayerListener;
import me.rich.nations.permission.PermissionManager;
import me.rich.nations.persistence.FileManager;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.minecraft.util.commands.CommandException;
import com.sk89q.minecraft.util.commands.CommandPermissionsException;
import com.sk89q.minecraft.util.commands.CommandUsageException;
import com.sk89q.minecraft.util.commands.CommandsManager;
import com.sk89q.minecraft.util.commands.MissingNestedCommandException;
import com.sk89q.minecraft.util.commands.WrappedCommandException;

public class NationsPlugin extends JavaPlugin {

	public static File dataFolder;

	private FileManager fileManager;
	private CommandsManager<CommandSender> commandsManager;
	private PermissionManager permissionManager;

	@Override
	public void onEnable() {
		dataFolder = this.getDataFolder();
		this.fileManager = new FileManager(this);
		this.fileManager.load();

		this.setupListeners();
		this.setupCommands();
		this.permissionManager = new PermissionManager(this);
	}

	private void setupListeners() {
		this.getServer().getPluginManager().registerEvents(new BlockListener(this), this);
		this.getServer().getPluginManager().registerEvents(new EntityListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
	}

	private void setupCommands() {
		this.commandsManager = new CommandsManager<CommandSender>() {

			@Override
			public boolean hasPermission(CommandSender sender, String permission) {
				return sender.hasPermission(permission);
			}

		};

		// Register commands...
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		try {
			this.commandsManager.execute(command.getName(), args, sender, sender);
		} catch (CommandPermissionsException e) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to perform this command.");
		} catch (MissingNestedCommandException e) {
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch (CommandUsageException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
			sender.sendMessage(ChatColor.RED + e.getUsage());
		} catch (WrappedCommandException e) {
			if (e.getCause() instanceof NumberFormatException) {
				sender.sendMessage(ChatColor.RED + "Number expected, String recieved instead.");
			} else {
				sender.sendMessage(ChatColor.RED + "An error has occurred. Check server console.");
				e.printStackTrace();
			}
		} catch (CommandException e) {
			sender.sendMessage(ChatColor.RED + e.getMessage());
		}

		return true;
	}

	public FileManager getFileManager() {
		return this.fileManager;
	}

	public PermissionManager getPermissionManager() {
		return this.permissionManager;
	}
}

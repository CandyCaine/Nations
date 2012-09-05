package me.rich.nations.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sk89q.minecraft.util.commands.Command;
import com.sk89q.minecraft.util.commands.CommandContext;
import com.sk89q.minecraft.util.commands.CommandPermissions;

public class RegionCommands {

	@Command(aliases = {"defineregion", "def"},
			desc = "Defines a new region",
			usage = "/defineregion [name]",
			min = 1,
			max = 1
			)
	@CommandPermissions("nations.region.define")
	public static boolean defineRegion(CommandContext args, CommandSender sender) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Command must be sent from a player.");
		}
		Player player = (Player) sender;
		
		
		
		return true;
	}
}

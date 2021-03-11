package me.creatos.voucher.commands.tabcompleter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.creatos.voucher.VoucherPlugin;
import me.creatos.voucher.core.Voucher;

public class VoucherTabCompleter implements TabCompleter {

	private VoucherPlugin plugin;
	
	
	
	public VoucherTabCompleter(VoucherPlugin plugin) {
		this.plugin = plugin;
	}



	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
		if(!command.getName().equalsIgnoreCase("voucher"))
			return null;
		
		if(!(sender instanceof Player))
			return null;
		
		ArrayList<String> list = new ArrayList<>();
		
		//voucher give <player> <id>
		if(args.length == 1) {
			list.clear();
			if(sender.hasPermission("voucher.give")) {
				list.add("give");
			}
		}
		
		//voucher give <player> <id>
		if(args.length == 2 && args[0].equalsIgnoreCase("give")) {
			list.clear();
			if(sender.hasPermission("voucher.give")) {
				for(Player player : Bukkit.getServer().getOnlinePlayers()) {
					list.add(player.getName());
				}
				list.sort(String.CASE_INSENSITIVE_ORDER);
			}
		}
		
		//voucher give <player> <id>
		if(args.length == 3 && args[0].equalsIgnoreCase("give")) {
			list.clear();
			if(sender.hasPermission("voucher.give")) {
				for(Voucher voucher : plugin.getVoucherManager().getVouchers()) {
					list.add(voucher.getId());
				}
				
				list.sort(String.CASE_INSENSITIVE_ORDER);
			}
		}
		
		return list;
	}

}

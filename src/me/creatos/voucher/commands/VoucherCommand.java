package me.creatos.voucher.commands;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.creatos.voucher.VoucherPlugin;
import me.creatos.voucher.core.Voucher;
import me.creatos.voucher.inventory.main.VoucherInventory;
import me.creatos.voucher.utils.Message;
import me.lucko.luckperms.common.sender.Sender;

public class VoucherCommand implements CommandExecutor {

	private VoucherPlugin plugin;

	public VoucherCommand(VoucherPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		

		if (args.length == 0) {
			if (!(sender instanceof Player))
				return true;
			Player player = (Player) sender;
			if (!player.hasPermission("voucher.edit")) {
				player.sendMessage(Message.NO_COMMAND_PERMISSION.getMessage(plugin, false));
				return true;
			}
			VoucherInventory.INVENTORY.open(player);
			return true;
		}

		if (args.length > 1) {
			String text = args[0].toLowerCase();

			if (text.equalsIgnoreCase("give")) {

				if(sender instanceof Player) {
					
					Player player = (Player) sender;
					
					if (!player.hasPermission("voucher.give")) {
						player.sendMessage(Message.NO_COMMAND_PERMISSION.getMessage(plugin, false));
						return true;
					}
					
				}
				
				
				Player targetPlayer = null;
				String voucherId = null;
				int amount = 1;

				// voucher give <player> <id> <amount>
				// - 0 1 2 3
				// 0 1 2 3 4

				if (args.length == 3) {
					targetPlayer = Bukkit.getPlayer(args[1]);
					voucherId = args[2];
				}

				if (args.length == 4) {
					targetPlayer = Bukkit.getPlayer(args[1]);
					voucherId = args[2];
					if (StringUtils.isNumeric(args[3])) {
						int i = Integer.parseInt(args[3]);
						amount = i;
					}

				}

				give(voucherId, targetPlayer, amount, sender);

			}
			
			if (text.equalsIgnoreCase("load")) {

				if(sender instanceof Player) {
					
					Player player = (Player) sender;
					
					if (!player.hasPermission("voucher.load")) {
						player.sendMessage(Message.NO_COMMAND_PERMISSION.getMessage(plugin, false));
						return true;
					}
					
				}
				
				
				plugin.getVoucherManager().load();

			}
		}

		return true;
	}

	private void give(String voucherId, Player targetPlayer, int amount, CommandSender sender) {
		Voucher voucher = plugin.getVoucherManager().getVoucherWithId(voucherId);
		if (voucher == null) {
			// No voucher found with ID: voucherId
			sender.sendMessage(Message.VOUCHER_NOT_FOUND.getMessage(plugin, voucher));
			return;
		}

		if (targetPlayer == null) {
			// No player with name targetPlayer found
			// Default to sender;
			sender.sendMessage(Message.PLAYER_NOT_FOUND.getMessage(plugin, voucher, null));
			return;
		}

		for (int i = 0; i < amount; i++) {
			targetPlayer.getInventory().addItem(voucher.getItem());
		}

		sender.sendMessage(Message.VOUCHER_GIVE.getMessage(plugin, voucher, targetPlayer.getName(), amount));

	}

}

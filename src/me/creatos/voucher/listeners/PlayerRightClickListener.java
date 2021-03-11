package me.creatos.voucher.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.creatos.voucher.VoucherPlugin;
import me.creatos.voucher.core.Voucher;
import me.creatos.voucher.files.VoucherManager;

public class PlayerRightClickListener implements Listener {

	private VoucherPlugin plugin;

	public PlayerRightClickListener(VoucherPlugin plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent event) {
		VoucherManager vm = plugin.getVoucherManager();
		
		ItemStack item = event.getItem();
		
		
		if(!(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)))
			return;
		
		if(item == null || item.getType().equals(Material.AIR))
			return;

		
		Voucher voucher = vm.getVoucherFromItemStack(item);
		
		if(voucher == null)
			return;

		voucher.redeem(event.getPlayer(), plugin, false);
		
		
	}

	
	
	
	
}

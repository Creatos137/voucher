package me.creatos.voucher;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import fr.minuskube.inv.InventoryManager;
import me.creatos.voucher.commands.VoucherCommand;
import me.creatos.voucher.commands.tabcompleter.VoucherTabCompleter;
import me.creatos.voucher.files.VoucherManager;
import me.creatos.voucher.listeners.PlayerChatListener;
import me.creatos.voucher.listeners.PlayerRightClickListener;

public class VoucherPlugin extends JavaPlugin {

	private static VoucherPlugin instance;
	private VoucherManager voucherManager;
	private InventoryManager inventoryManager;

	@Override
	public void onEnable() {
		instance = this;
		registerFiles();
		registerEvents();
		registerCommands();
		voucherManager.load();
	}
	
	

	@Override
	public void onDisable() {
		voucherManager.save();
	}
	
	
	
	private void registerFiles() {
		saveDefaultConfig();
		voucherManager = new VoucherManager(this);
		inventoryManager = new InventoryManager(this);
		inventoryManager.init();
		
	}

	public InventoryManager getInventoryManager() {
		return inventoryManager;
	}



	private void registerCommands() {
		getCommand("voucher").setExecutor(new VoucherCommand(this));
		getCommand("voucher").setTabCompleter(new VoucherTabCompleter(this));
		
	}

	private void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(new PlayerChatListener(this), this);
		pm.registerEvents(new PlayerRightClickListener(this), this);
		
	}



	public VoucherManager getVoucherManager() {
		return voucherManager;
	}



	public static VoucherPlugin getInstance() {
		// TODO Auto-generated method stub
		return instance;
	}
	

}

package me.creatos.voucher.inventory.main;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.Pagination;
import fr.minuskube.inv.content.SlotIterator;
import me.creatos.voucher.VoucherPlugin;
import me.creatos.voucher.core.Voucher;
import me.creatos.voucher.files.VoucherManager;
import me.creatos.voucher.inventory.Content;
import me.creatos.voucher.utils.Message;

public class VoucherInventory implements InventoryProvider {

	private static final String TITLE = "Voucher GUI";

	public static final SmartInventory INVENTORY = SmartInventory.builder().id("main").provider(new VoucherInventory())
			.size(6, 9).title(TITLE).manager(VoucherPlugin.getInstance().getInventoryManager()).build();

	@Override
	public void init(Player player, InventoryContents contents) {
		Content.setVoucherInventoryContent(player, contents);
		contents.newIterator("test", SlotIterator.Type.HORIZONTAL, 0, 0).allowOverride(false);
		fillInventory(player, contents);
	}

	@Override
	public void update(Player player, InventoryContents contents) {
	}

	private void fillInventory(Player player, InventoryContents contents) {
		Pagination pagination = contents.pagination();
		SlotIterator iter = contents.iterator("test").get();
		
		VoucherManager vm = VoucherPlugin.getInstance().getVoucherManager();
		ArrayList<Voucher> vouchers = vm.getVouchers();
		ClickableItem[] items = new ClickableItem[vouchers.size()];
		
		for(int i = 0; i < vouchers.size(); i++) {
			items[i] = Content.voucherListElement(player, vouchers.get(i));
		}
		
		pagination.setItems(items);
		pagination.setItemsPerPage(45);
		
		pagination.addToIterator(iter);
		
		if(items.length > pagination.getPageItems().length) {
			
			
			if(!pagination.isFirst()) {
				ItemStack iprev = new ItemStack(Material.ARROW);
				ItemMeta prev = iprev.getItemMeta();
				prev.setDisplayName(Message.color("&fPREVIOUS PAGE"));
				iprev.setItemMeta(prev);
				contents.set(5,  0,  ClickableItem.of(iprev, e -> INVENTORY.open(player, pagination.previous().getPage())));
			}
			
			if(!pagination.isLast()) {
				ItemStack inext = new ItemStack(Material.ARROW);
				ItemMeta next = inext.getItemMeta();
				next.setDisplayName(Message.color("&fNEXT PAGE"));
				inext.setItemMeta(next);
				contents.set(5,  8,  ClickableItem.of(inext, e -> INVENTORY.open(player, pagination.next().getPage())));
			}
			
			
		}
		
		
	}
}

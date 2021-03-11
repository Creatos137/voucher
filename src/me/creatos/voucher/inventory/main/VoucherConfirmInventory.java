package me.creatos.voucher.inventory.main;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotIterator;
import me.creatos.voucher.VoucherPlugin;
import me.creatos.voucher.core.Voucher;
import me.creatos.voucher.utils.EditGuiItem;
import me.creatos.voucher.utils.Message;

public class VoucherConfirmInventory implements InventoryProvider {

	private static final String TITLE = "Confirm";

private Voucher voucher;

public VoucherConfirmInventory(Voucher voucher) {
this.voucher = voucher;
}

public static SmartInventory getInventory(Voucher voucher) {
return SmartInventory.builder().manager(VoucherPlugin.getInstance().getInventoryManager()).id("edit_voucher")
		.provider(new VoucherConfirmInventory(voucher)).size(3, 9)
		.title(TITLE).build();
}

@Override
public void init(Player player, InventoryContents contents) {
	initContent(player, contents);
	contents.newIterator("fill_no_overlap", SlotIterator.Type.HORIZONTAL, 0, 0).allowOverride(false);
	
	while(!contents.iterator("fill_no_overlap").get().ended()) {
		
		contents.iterator("fill_no_overlap").get().set(ClickableItem.empty(EditGuiItem.getBorder())).next();
		
	}
	
	contents.iterator("fill_no_overlap").get().set(ClickableItem.empty(EditGuiItem.getBorder()));
}

private void initContent(Player player, InventoryContents contents) {
	ItemStack confirm = new ItemStack(Material.EMERALD_BLOCK);
	ItemStack cancel = new ItemStack(Material.REDSTONE_BLOCK);
	
	ItemMeta meta = confirm.getItemMeta();
	meta.setDisplayName(Message.color("&a&lCONFIRM"));
	confirm.setItemMeta(meta);
	
	meta = cancel.getItemMeta();
	meta.setDisplayName(Message.color("&c&lCANCEL"));
	cancel.setItemMeta(meta);
	
	contents.set(1, 2, ClickableItem.of(confirm, e -> {
		getInventory(voucher).close(player);
		voucher.redeem(player, VoucherPlugin.getInstance(), true);
	}));
	contents.set(1, 6, ClickableItem.of(cancel, e -> getInventory(voucher).close(player)));
	
}

@Override
public void update(Player arg0, InventoryContents arg1) {
	// TODO Auto-generated method stub
	
}
	
}

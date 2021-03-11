package me.creatos.voucher.inventory.edit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.minuskube.inv.content.SlotIterator;
import me.creatos.voucher.VoucherPlugin;
import me.creatos.voucher.core.Voucher;
import me.creatos.voucher.core.enums.EditType;
import me.creatos.voucher.core.enums.Option;
import me.creatos.voucher.inventory.Content;
import me.creatos.voucher.inventory.main.VoucherEditInventory;
import me.creatos.voucher.utils.EditGuiItem;

public class ElementEditInventory implements InventoryProvider {
	
	private static String getTitle(Option option) {
		switch (option) {
		case SOUND:
			return "Sound GUI                     " + ChatColor.WHITE + ChatColor.BOLD.toString() + "EDIT";
			
		case COMMAND:
			return "Command GUI                  " + ChatColor.WHITE + ChatColor.BOLD.toString() + "EDIT";
			
		case MESSAGE:
			return "Message GUI                  " + ChatColor.WHITE + ChatColor.BOLD.toString() + "EDIT";
			
		case TITLE:
			return "Title GUI                       " + ChatColor.WHITE + ChatColor.BOLD.toString() + "EDIT";
			
		case PERMISSION:
			return "Permission GUI               " + ChatColor.WHITE + ChatColor.BOLD.toString() + "EDIT";
			
		case LORE:
			return "Lore GUI                       " + ChatColor.WHITE + ChatColor.BOLD.toString() + "EDIT";

		default:
			return "Voucher GUI                  " + ChatColor.WHITE + ChatColor.BOLD.toString() + "EDIT";
		}
	}
	
	private Voucher voucher;
	private Option option;
	private int index;
	
	public ElementEditInventory(Voucher voucher, Option option, int index) {
		this.voucher = voucher;
		this.option = option;
		this.index = index;
	}
	
	public static SmartInventory getInventory(Voucher voucher, Option option, int index) {
		return SmartInventory.builder().manager(VoucherPlugin.getInstance().getInventoryManager()).id("edit_element")
				.provider(new ElementEditInventory(voucher, option, index)).size(3, 9)
				.title(getTitle(option)).build();
	}
	
	@Override
	public void init(Player player, InventoryContents contents) {
		contents.newIterator("fill_no_override", SlotIterator.Type.HORIZONTAL, 0, 0).allowOverride(false);
		
		initBorder(player, contents);
		initContent(player, contents);
		
		SlotIterator iter = contents.iterator("fill_no_override").get();
		
		while(!iter.ended()) {
			iter.set(ClickableItem.empty(EditGuiItem.getBorder())).next();
		}
		
	}

	private void initContent(Player player, InventoryContents contents) {
		switch (option) {
		case PERMISSION:
			initPermissionContent(player, contents);
			break;
		case LORE:
			initLoreContent(player, contents);
			break;
		case SOUND:
			initSoundContent(player, contents);
			break;
		case MESSAGE:
			initMessageContent(player, contents);
			break;
		case COMMAND:
			initCommandContent(player, contents);
			break;
		case TITLE:
			initEditTitle(player, contents);
			break;

		default:
			break;
		}
		
	}

	private void initEditTitle(Player player, InventoryContents contents) {
		contents.set(1, 1, Content.editElement(voucher, option, index, player, EditType.TITLE));
		contents.set(1, 2, Content.editElement(voucher, option, index, player, EditType.SUBTITLE));
		contents.set(1, 4, Content.editFadeIn(voucher, index, player, VoucherPlugin.getInstance()));
		contents.set(1, 5, Content.editStay(voucher, index, player, VoucherPlugin.getInstance()));
		contents.set(1, 6, Content.editFadeOut(voucher, index, player, VoucherPlugin.getInstance()));
		contents.set(1, 7, Content.editDelay(voucher, option, index, player, VoucherPlugin.getInstance()));
		
	}

	private void initCommandContent(Player player, InventoryContents contents) {
		contents.set(1, 3, Content.editElement(voucher, option, index, player, EditType.COMMAND));
		contents.set(1, 5, Content.editCommandSender(voucher, index, player));
		
	}

	private void initMessageContent(Player player, InventoryContents contents) {
		contents.set(1, 3, Content.editElement(voucher, option, index, player, EditType.MESSAGE));
		contents.set(1,  5, Content.editMessageType(voucher, index, player));
		
	}

	private void initSoundContent(Player player, InventoryContents contents) {
		contents.set(1, 1, Content.editElement(voucher, option, index, player, EditType.SOUND));
		contents.set(1, 3, Content.editPitch(voucher, index, player));
		contents.set(1, 4, Content.editVolume(voucher, index, player));
		contents.set(1, 5, Content.editDelay(voucher, Option.SOUND, index, player, VoucherPlugin.getInstance()));
		contents.set(1, 7, Content.editSoundPlayer(voucher, index, player));
		
	}

	private void initLoreContent(Player player, InventoryContents contents) {
		contents.set(1, 4, Content.editElement(voucher, option, index, player, EditType.LORE));
		
	}

	private void initPermissionContent(Player player, InventoryContents contents) {
		
		contents.set(1, 4, Content.editElement(voucher, option, index, player, EditType.PERMISSION));
		
	}

	private void initBorder(Player player, InventoryContents contents) {
		contents.fillBorders(ClickableItem.empty(EditGuiItem.getBorder()));
		contents.set(2, 0, Content.getBackElementWithParent(VoucherEditInventory.getInventory(voucher, option), player));
		contents.set(2, 8, Content.getRemoveElement(voucher, option, index, player));
	}

	@Override
	public void update(Player arg0, InventoryContents arg1) {
		// TODO Auto-generated method stub
		
	}

}

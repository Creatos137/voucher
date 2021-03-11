package me.creatos.voucher.utils;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.creatos.voucher.VoucherPlugin;
import me.creatos.voucher.core.Voucher;
import me.creatos.voucher.core.enums.EditType;
import me.creatos.voucher.core.enums.MessageType;
import me.creatos.voucher.core.enums.Option;
import me.creatos.voucher.core.enums.SenderType;
import me.creatos.voucher.core.enums.SoundPlayer;
import me.creatos.voucher.core.wrappers.VoucherCommand;
import me.creatos.voucher.core.wrappers.VoucherMessage;
import me.creatos.voucher.core.wrappers.VoucherSound;
import me.creatos.voucher.core.wrappers.VoucherTitle;
import me.creatos.voucher.inventory.main.VoucherEditInventory;

public class EditGuiItem {

	public static ItemStack getEditCommandSender(Voucher voucher, int index) {
		VoucherCommand command = voucher.getCommands().get(index);
		
		ItemStack item = new ItemStack(Material.COMMAND);
		
		if(command.getSender().equals(SenderType.PLAYER))
			item.setType(Material.SKULL_ITEM);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Edit Command Sender");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(Message.color("&fCommand Sender: &7" + command.getSender()));
		lore.add(Message.color("&7Left-Click to toggle command sender"));
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getEditMessageType(Voucher voucher, int index) {
		VoucherMessage message = voucher.getMessages().get(index);
		
		ItemStack item = new ItemStack(Material.BOOK);
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Edit Message Type");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(Message.color("&fMessage Type: &7" + message.getType()));
		lore.add(Message.color("&7Left-Click to toggle message type"));
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getEditSoundPlayer(Voucher voucher, int index) {
		VoucherSound sound = voucher.getSounds().get(index);
		
		ItemStack item = new ItemStack(Material.SKULL_ITEM);
		
		if(sound.getSoundPlayer().equals(SoundPlayer.WORLD)) {
			item.setType(Material.GRASS);
		}
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Edit Sound Player");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(Message.color("&fSound Player: &7" + sound.getSoundPlayer()));
		lore.add(Message.color("&7Left-Click to toggle sound player"));
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getEditPitch(Voucher voucher, int index) {
		VoucherSound sound = voucher.getSounds().get(index);
		
		ItemStack item = new ItemStack(Material.NOTE_BLOCK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Edit Pitch");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(Message.color(String.format("&fPitch: &7%.2f", sound.getPitch())));
		lore.add(Message.color("&7Left-Click to increase by 0.1"));
		lore.add(Message.color("&7Right-Click to decrease by 0.1"));
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getEditVolume(Voucher voucher, int index) {
		VoucherSound sound = voucher.getSounds().get(index);
		
		ItemStack item = new ItemStack(Material.NOTE_BLOCK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Edit Volume");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(Message.color(String.format("&fVolume: &7%.2f", sound.getVolume())));
		lore.add(Message.color("&7Left-Click to increase by 0.1"));
		lore.add(Message.color("&7Right-Click to decrease by 0.1"));
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getEditFadeIn(Voucher voucher, int index) {
		VoucherTitle title = voucher.getTitles().get(index);
		
		
		
		ItemStack item = new ItemStack(Material.WATCH);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Edit Fade-In");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(Message.color("&fFade-In: &7" + title.getFadeIn()));
		lore.add(Message.color("&7Left-Click to increase by 1 tick"));
		lore.add(Message.color("&7Right-Click to decrease by 1 tick"));
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getEditStay(Voucher voucher, int index) {
		VoucherTitle title = voucher.getTitles().get(index);
		
		
		
		ItemStack item = new ItemStack(Material.WATCH);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Edit Stay");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(Message.color("&fStay: &7" + title.getStay()));
		lore.add(Message.color("&7Left-Click to increase by 1 tick"));
		lore.add(Message.color("&7Right-Click to decrease by 1 tick"));
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getEditFadeOut(Voucher voucher, int index) {
		VoucherTitle title = voucher.getTitles().get(index);
		
		
		
		ItemStack item = new ItemStack(Material.WATCH);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Edit Fade-Out");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(Message.color("&fFade-Out: &7" + title.getFadeOut()));
		lore.add(Message.color("&7Left-Click to increase by 1 tick"));
		lore.add(Message.color("&7Right-Click to decrease by 1 tick"));
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getEditDelay(Voucher voucher, Option option, int index) {
		int delay = 0;
		Object element = voucher.getList(option).get(index);
		if(element != null) {
			if(element instanceof VoucherTitle)
				delay = ((VoucherTitle) element).getDelay();
			
			if(element instanceof VoucherSound)
				delay = ((VoucherSound) element).getDelay();
		}
		
		
		
		ItemStack item = new ItemStack(Material.WATCH);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Edit Delay");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(Message.color("&fDelay: &7" + delay));
		lore.add(Message.color("&7Left-Click to increase by 1 tick"));
		lore.add(Message.color("&7Right-Click to decrease by 1 tick"));
		
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getEditPermission(Voucher voucher, int index) {
		String perm = voucher.getPermissions().get(index);
		
		ItemStack item = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Edit Permission");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(Message.color("&fPermission: &7" + perm));
		lore.add(Message.color("&7Left-Click to edit"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getEditLore(Voucher voucher, int index) {
		String line = voucher.getLore().get(index);
		
		ItemStack item = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Edit Line");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(Message.color("&fLore line: &7" + line));
		lore.add(Message.color("&7Left-Click to edit"));
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getEditSound(Voucher voucher, int index) {
		String sound = voucher.getSounds().get(index).getSound().toString();
		
		ItemStack item = new ItemStack(Material.JUKEBOX);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Edit Sound");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(Message.color("&fSound: &7" + sound));
		lore.add(Message.color("&7Left-Click to edit"));
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getEditCommand(Voucher voucher, int index) {
		String command = voucher.getCommands().get(index).getCommand();
		
		ItemStack item = new ItemStack(Material.NAME_TAG);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Edit Command");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(Message.color("&fCommand: &7/" + command));
		lore.add(Message.color("&7Left-Click to edit"));
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getEditTitle(Voucher voucher, int index) {
		String title = voucher.getTitles().get(index).getTitle();
		
		ItemStack item = new ItemStack(Material.EYE_OF_ENDER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Edit Title");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(Message.color("&fTitle: &7" + title));
		lore.add(Message.color("&7Left-Click to edit"));
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getEditSubtitle(Voucher voucher, int index) {
		String title = voucher.getTitles().get(index).getSubtitle();
		
		ItemStack item = new ItemStack(Material.EYE_OF_ENDER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Edit Subtitle");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(Message.color("&fSubtitle: &7" + title));
		lore.add(Message.color("&7Left-Click to edit"));
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getEditMessage(Voucher voucher, int index) {
		String message = voucher.getMessages().get(index).getMessage();
		
		ItemStack item = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Edit Message");
		ArrayList<String> lore = new ArrayList<String>();
		lore.add(" ");
		lore.add(Message.color("&fMessage: &7" + message));
		lore.add(Message.color("&7Left-Click to edit"));
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		return item;
	}
	
	public static ItemStack getEditMessageType(MessageType type) {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("Edit Message Type");
		ArrayList<String> lore = new ArrayList<>();
		lore.add(" ");
		lore.add(Message.color("&fMessage Type: &7" + type));
		lore.add(Message.color("&7Left-Click to edit"));
		meta.setLore(lore);
		
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getRemove() {
		ItemStack item = new ItemStack(Material.REDSTONE_BLOCK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Remove");
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getBack() {
		ItemStack item = new ItemStack(Material.BARRIER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.RED + "Back");
		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getVoucher() {
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Voucher");
		meta.setLore(new ArrayList<>());

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getSounds() {
		ItemStack item = new ItemStack(Material.GOLD_RECORD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Sounds");
		meta.setLore(null);

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getCommands() {
		ItemStack item = new ItemStack(Material.NAME_TAG);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Commands");
		meta.setLore(new ArrayList<>());

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getMessages() {
		ItemStack item = new ItemStack(Material.MAP);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Messages");
		meta.setLore(new ArrayList<>());

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getLore() {
		ItemStack item = new ItemStack(Material.BOOK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Lore");
		meta.setLore(new ArrayList<>());

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getTitles() {
		ItemStack item = new ItemStack(Material.EYE_OF_ENDER);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Title");
		meta.setLore(new ArrayList<>());

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getPermissions() {
		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Permissions");
		meta.setLore(new ArrayList<>());

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getBorder() {
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		meta.setLore(new ArrayList<>());

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getSelectionIndicator() {
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");
		meta.setLore(new ArrayList<>());

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getAddCommand() {
		ItemStack item = new ItemStack(Material.NAME_TAG);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Add Command");
		meta.setLore(new ArrayList<>());
		meta.addEnchant(Enchantment.DURABILITY, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

		item.setItemMeta(meta);
		return item;

	}

	public static ItemStack getAddPermission() {
		ItemStack item = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Add Permission");
		meta.setLore(new ArrayList<>());
		meta.addEnchant(Enchantment.DURABILITY, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getAddMessage() {
		ItemStack item = new ItemStack(Material.EMPTY_MAP);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Add Message");
		meta.setLore(new ArrayList<>());
		meta.addEnchant(Enchantment.DURABILITY, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getAddLore() {
		ItemStack item = new ItemStack(Material.BOOK_AND_QUILL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Add Lore");
		meta.setLore(new ArrayList<>());
		meta.addEnchant(Enchantment.DURABILITY, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getAddSound() {
		ItemStack item = new ItemStack(Material.GREEN_RECORD);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Add Sound");
		meta.setLore(new ArrayList<>());
		meta.addEnchant(Enchantment.DURABILITY, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getAddTitle() {
		ItemStack item = new ItemStack(Material.ENDER_PEARL);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Add Title");
		meta.setLore(new ArrayList<>());
		meta.addEnchant(Enchantment.DURABILITY, 1, false);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getChangeId(Voucher voucher) {
		ItemStack item = new ItemStack(Material.NAME_TAG);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Change ID");

		ArrayList<String> lore = new ArrayList<>();
		lore.add(" ");
		lore.add(ChatColor.WHITE + "Current ID: " + ChatColor.GRAY + voucher.getId());

		meta.setLore(lore);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getChangeName(Voucher voucher) {
		ItemStack item = new ItemStack(Material.SIGN);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(ChatColor.WHITE + "Change Name");

		ArrayList<String> lore = new ArrayList<>();
		lore.add(" ");
		lore.add(Message.color("&fCurrent Name: &7" + voucher.getName()));

		meta.setLore(lore);

		item.setItemMeta(meta);
		return item;
	}

	public static ItemStack getBoolean(Voucher voucher, String s) {
		boolean bool = false;
		String option = null;

		switch (s.toLowerCase()) {
		case "send_chat":
			bool = voucher.sendChatMessage();
			option = "Send Chat";
			break;
		case "send_broadcast":
			bool = voucher.sendBroadcastMessage();
			option = "Send Broadcast";
			break;
		case "send_action_bar":
			bool = voucher.sendActionbarMessage();
			option = "Send Actionbar";
			break;
		case "send_title":
			bool = voucher.sendTitle();
			option = "Send Title";
			break;
		case "send_sound":
			bool = voucher.playSound();
			option = "Send Sound";
			break;
		case "send_command":
			bool = voucher.sendCommand();
			option = "Send Command";
			break;
		case "send_permission":
			bool = voucher.sendPermission();
			option = "Send Permission";
			break;
		case "confirm":
			bool = voucher.confirm();
			option = "Require Confirm";
			break;
		case "consume":
			bool = voucher.consume();
			option = "Consume";
			break;
		case "set_lore":
			bool = voucher.doSetLore();
			option = "Set Lore";
			break;

		default:
			break;
		}

		short color = bool ? (short) 5 : (short) 14;
		String addition = bool ? "Enabled" : "Disabled";

		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, color);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(option + ": " + addition);
		item.setItemMeta(meta);

		return item;
	}

	public static void addSound(InventoryClickEvent e, Player player, Voucher voucher, Option option) {
		if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
			VoucherEditInventory.getInventory(voucher, option).close(player);
			// Register player to chat editor
			player.sendMessage(Message.VOUCHER_START_EDIT_SOUND.getMessage(VoucherPlugin.getInstance(), voucher));
			VoucherPlugin.getInstance().getVoucherManager().startEditing(player, voucher, EditType.SOUND);
		}

	}

	public static void addTitle(InventoryClickEvent e, Player player, Voucher voucher, Option option) {
		if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
			VoucherEditInventory.getInventory(voucher, option).close(player);
			// Register player to chat editor
			player.sendMessage(Message.VOUCHER_START_EDIT_TITLE.getMessage(VoucherPlugin.getInstance(), voucher));
			VoucherPlugin.getInstance().getVoucherManager().startEditing(player, voucher, EditType.TITLE);
		}

	}

	public static void addPerm(InventoryClickEvent e, Player player, Voucher voucher, Option option) {
		if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
			VoucherEditInventory.getInventory(voucher, option).close(player);
			// Register player to chat editor
			player.sendMessage(Message.VOUCHER_START_EDIT_PERMISSION.getMessage(VoucherPlugin.getInstance(), voucher));
			VoucherPlugin.getInstance().getVoucherManager().startEditing(player, voucher, EditType.PERMISSION);
		}

	}

	public static void addMsg(InventoryClickEvent e, Player player, Voucher voucher, Option option) {
		if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
			VoucherEditInventory.getInventory(voucher, option).close(player);
			// Register player to chat editor
			player.sendMessage(Message.VOUCHER_START_EDIT_MESSAGE.getMessage(VoucherPlugin.getInstance(), voucher));
			VoucherPlugin.getInstance().getVoucherManager().startEditing(player, voucher, EditType.MESSAGE);
		}

	}

	public static void addLo(InventoryClickEvent e, Player player, Voucher voucher, Option option) {
		if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
			VoucherEditInventory.getInventory(voucher, option).close(player);
			// Register player to chat editor
			player.sendMessage(Message.VOUCHER_START_EDIT_LORE.getMessage(VoucherPlugin.getInstance(), voucher));
			VoucherPlugin.getInstance().getVoucherManager().startEditing(player, voucher, EditType.LORE);
		}

	}

	public static void addCmd(InventoryClickEvent e, Player player, Voucher voucher, Option option) {
		if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
			VoucherEditInventory.getInventory(voucher, option).close(player);
			// Register player to chat editor
			player.sendMessage(Message.VOUCHER_START_EDIT_COMMAND.getMessage(VoucherPlugin.getInstance(), voucher));
			VoucherPlugin.getInstance().getVoucherManager().startEditing(player, voucher, EditType.COMMAND);
		}

	}

	

	

}

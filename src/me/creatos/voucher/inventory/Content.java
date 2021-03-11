package me.creatos.voucher.inventory;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.changeme.nbtapi.NBTItem;
import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import me.creatos.voucher.VoucherPlugin;
import me.creatos.voucher.core.Voucher;
import me.creatos.voucher.core.enums.EditType;
import me.creatos.voucher.core.enums.Option;
import me.creatos.voucher.core.wrappers.VoucherCommand;
import me.creatos.voucher.core.wrappers.VoucherMessage;
import me.creatos.voucher.core.wrappers.VoucherSound;
import me.creatos.voucher.core.wrappers.VoucherTitle;
import me.creatos.voucher.inventory.edit.ElementEditInventory;
import me.creatos.voucher.inventory.main.VoucherEditInventory;
import me.creatos.voucher.inventory.main.VoucherInventory;
import me.creatos.voucher.utils.EditGuiItem;
import me.creatos.voucher.utils.Message;

public class Content {

	public static ClickableItem getVoucherMaterial(Player player, Voucher voucher) {
		ItemStack item = new ItemStack(Material.ITEM_FRAME);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Message.color("&fChange Item"));
		ArrayList<String> lore = new ArrayList<>();
		lore.add(Message.color("&fVoucher Item: &7" + voucher.getMaterial().toString()));
		lore.add(" ");
		lore.add(Message.color("&7Left-Click to edit"));
		meta.setLore(lore);
		item.setItemMeta(meta);
		
		return ClickableItem.of(item, e -> {
			
			VoucherEditInventory.getInventory(voucher, Option.VOUCHER).close(player);
			// Register player to chat editor
			player.sendMessage(Message.VOUCHER_START_EDIT_ITEM.getMessage(VoucherPlugin.getInstance(), voucher));
			VoucherPlugin.getInstance().getVoucherManager().startEditing(player, voucher, EditType.ITEM);
			
		});
		
	}
	
	public static void setVoucherInventoryContent(Player player, InventoryContents contents) {
		contents.set(5, 0, ClickableItem.empty(borderItem()));
		contents.set(5, 1, ClickableItem.empty(borderItem()));
		contents.set(5, 2, ClickableItem.empty(borderItem()));
		contents.set(5, 3, createNewVoucher(player));
		contents.set(5, 4, ClickableItem.empty(borderItem()));
		contents.set(5, 5, info(player));
		contents.set(5, 6, ClickableItem.empty(borderItem()));
		contents.set(5, 7, ClickableItem.empty(borderItem()));
		contents.set(5, 8, ClickableItem.empty(borderItem()));
	}

	private static ClickableItem createNewVoucher(Player player) {
		ItemStack item = new ItemStack(Material.NETHER_STAR);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Message.color("&f&lNEW VOUCHER"));
		item.setItemMeta(meta);

		ClickableItem cItem = ClickableItem.of(item, e -> {
			VoucherInventory.INVENTORY.close(player);
			player.sendMessage(Message.VOUCHER_START_CREATE.getMessage(VoucherPlugin.getInstance(), null));
			VoucherPlugin.getInstance().getVoucherManager().startEditing(player, null, EditType.CREATE);
		});

		return cItem;
	}

	private static ClickableItem info(Player player) {
		ItemStack item = new ItemStack(Material.CHEST);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Message.color("&6&lINFO"));
		ArrayList<String> lore = new ArrayList<>();
		lore.add(" ");
		lore.add(Message.color("&7Version: &6&l" + VoucherPlugin.getInstance().getDescription().getVersion()));
		lore.add(Message.color("&7Author: &e&l" + VoucherPlugin.getInstance().getDescription().getAuthors()));
		meta.setLore(lore);
		item.setItemMeta(meta);

		ClickableItem cItem = ClickableItem.empty(item);

		return cItem;
	}

	private static ItemStack borderItem() {
		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(" ");

		item.setItemMeta(meta);
		return item;
	}

	public static ClickableItem voucherOptionElement(Player player, Voucher voucher, Option option, int index) {
		Material material = Material.PAPER;
		String display = null;
		ArrayList<String> lore = new ArrayList<>();

		switch (option) {
		case VOUCHER:
			material = Material.PAPER;
			display = "Voucher";
			lore.clear();
			break;
		case MESSAGE:
			material = Material.MAP;
			display = "Message";
			lore.clear();
			lore.add(" ");
			lore.add(Message.color("&fText: &7" + voucher.getMessages().get(index).getMessage()));
			lore.add(Message.color("&fType: &7" + voucher.getMessages().get(index).getType()));
			break;
		case SOUND:
			material = Material.GOLD_RECORD;
			display = "Sound";
			lore.clear();
			lore.add(" ");
			lore.add(Message.color("&fSound: &7" + voucher.getSounds().get(index).getSound()));
			lore.add(Message.color("&fVolume: &7" + voucher.getSounds().get(index).getVolume()));
			lore.add(Message.color("&fPitch: &7" + voucher.getSounds().get(index).getPitch()));
			lore.add(Message.color("&fDelay: &7" + voucher.getSounds().get(index).getDelay()));
			lore.add(Message.color("&fSound Player: &7" + voucher.getSounds().get(index).getSoundPlayer()));
			break;
		case COMMAND:
			material = Material.NAME_TAG;
			display = "Command";
			lore.clear();
			lore.add(" ");
			lore.add(Message.color("&fCommand: &7/" + voucher.getCommands().get(index).getCommand()));
			lore.add(Message.color("&fSender: &7" + voucher.getCommands().get(index).getSender()));
			break;
		case LORE:
			material = Material.BOOK;
			display = "Lore line";
			lore.clear();
			lore.add(" ");
			lore.add(Message.color("&fLine: &7" + voucher.getLore().get(index)));
			break;
		case PERMISSION:
			material = Material.ENCHANTED_BOOK;
			display = "Permission";
			lore.clear();
			lore.add(" ");
			lore.add(Message.color("&fPermission: &7" + voucher.getPermissions().get(index)));
			break;
		case TITLE:
			material = Material.EYE_OF_ENDER;
			display = "Title";
			lore.clear();
			lore.add(" ");
			lore.add(Message.color("&fTitle: &7" + Message.color(voucher.getTitles().get(index).getTitle())));
			lore.add(Message.color("&fSubtitle: &7" + Message.color(voucher.getTitles().get(index).getSubtitle())));
			lore.add(Message.color("&fFade-in: &7" + voucher.getTitles().get(index).getFadeIn()));
			lore.add(Message.color("&fStay: &7" + voucher.getTitles().get(index).getStay()));
			lore.add(Message.color("&fFade-out: &7" + voucher.getTitles().get(index).getFadeOut()));
			lore.add(Message.color("&fDelay: &7" + voucher.getTitles().get(index).getDelay()));
			break;
		default:
			break;
		}
		
		lore.add(" ");
		lore.add(Message.color("&7Left-Click to edit"));
		lore.add(Message.color("&7Middle-Click to duplicate"));

		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(display);
		meta.setLore(lore);
		item.setItemMeta(meta);

		NBTItem nbt = new NBTItem(item);
		nbt.setString("id", voucher.getId());
		nbt.setObject("option", option);
		nbt.setInteger("index", index);

		@SuppressWarnings("unchecked")
		ClickableItem cItem = ClickableItem.of(nbt.getItem(), event -> {
			// TODO: Open edit menu

			if (event.getAction().equals(InventoryAction.PICKUP_ALL)) {
				ElementEditInventory.getInventory(voucher, option, index).open(player);
			}
			
			if(event.getAction().equals(InventoryAction.CLONE_STACK)) {
				voucher.getList(option).add(voucher.getList(option).get(index));
				VoucherEditInventory.getInventory(voucher, option).open(player);
			}
		});

		return cItem;
	}

	public static ClickableItem voucherListElement(Player player, Voucher voucher) {
		ArrayList<String> lore = new ArrayList<>();

		lore.add(Message.color("&fVoucher ID: &7" + voucher.getId()));
		lore.add(" ");
		lore.add(Message.color("&7Left-Click to edit"));
		lore.add(Message.color("&7Middle-Click to duplicate"));

		// Creating Item
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta meta = item.getItemMeta();
		if (voucher.getName() == null) {
			meta.setDisplayName(" ");
		} else {
			meta.setDisplayName(Message.color(voucher.getName()));
		}

		meta.setLore(lore);
		item.setItemMeta(meta);

		// Adding NBT data
		NBTItem i = new NBTItem(item);
		i.setString("id", voucher.getId());

		// get item with nbt data
		ClickableItem cItem = ClickableItem.of(i.getItem(), e -> {
			if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
				VoucherEditInventory.getInventory(voucher, Option.VOUCHER).open(player);
			}
			
			if(e.getAction().equals(InventoryAction.CLONE_STACK)) {
				voucher.duplicate();
				VoucherInventory.INVENTORY.open(player);
			}

		});

		return cItem;
	}

	public static ClickableItem getRemoveVoucher(Player player, Voucher voucher, VoucherPlugin plugin) {
		ClickableItem item = ClickableItem.of(EditGuiItem.getRemove(), event -> {

			plugin.getVoucherManager().unregister(voucher);
			VoucherInventory.INVENTORY.open(player);

		});
		return item;
	}

	public static ClickableItem getBackElement(SmartInventory inv, Player player) {
		ClickableItem item = ClickableItem.of(EditGuiItem.getBack(), event -> {

			SmartInventory parent = inv.getParent().get();

			if (parent == null)
				return;

			inv.open(player);

		});
		return item;
	}

	public static ClickableItem getBackElementWithParent(SmartInventory parent, Player player) {
		ClickableItem item = ClickableItem.of(EditGuiItem.getBack(), event -> {
			parent.open(player);

		});
		return item;
	}

	@SuppressWarnings("unchecked")
	public static ClickableItem getRemoveElement(Voucher voucher, Option option, int index, Player player) {
		ClickableItem item = ClickableItem.of(EditGuiItem.getRemove(), event -> {

			ArrayList<Object> list = voucher.getList(option);

			if (list.size() >= index) {
				list.remove(index);
			}

			SmartInventory inv = VoucherEditInventory.getInventory(voucher, option);

			inv.open(player);

		});
		return item;
	}

//	public static ClickableItem editPermissionElement(Voucher voucher, Option option, int index, Player player) {
//
//		ClickableItem element = ClickableItem.of(EditGuiItem.getEditPermission(voucher, index), e -> {
//
//			if (!e.getAction().equals(InventoryAction.PLACE_ALL))
//				return;
//
//			ArrayList<Object> list = voucher.getList(option);
//
//			if (list.size() < index)
//				return;
//
//			VoucherPlugin.getInstance().getVoucherManager().startEditing(player, voucher, EditType.PERMISSION, index);
//			ElementEditInventory.getInventory(voucher, option, index).close(player);
//
//		});
//
//		return element;
//	}

	public static ClickableItem editElement(Voucher voucher, Option option, int index, Player player, EditType type) {
		ItemStack item = null;
		switch (type) {
		case PERMISSION:
			item = EditGuiItem.getEditPermission(voucher, index);
			break;
		case COMMAND:
			item = EditGuiItem.getEditCommand(voucher, index);
			break;
		case MESSAGE:
			item = EditGuiItem.getEditMessage(voucher, index);
			break;
		case TITLE:
			item = EditGuiItem.getEditTitle(voucher, index);
			break;
		case SUBTITLE:
			item = EditGuiItem.getEditSubtitle(voucher, index);
			break;
		case LORE:
			item = EditGuiItem.getEditLore(voucher, index);
			break;
		case SOUND:
			item = EditGuiItem.getEditSound(voucher, index);
			break;

		default:
			break;
		}

		ClickableItem element = ClickableItem.of(item, e -> {

			if (!e.getAction().equals(InventoryAction.PICKUP_ALL))
				return;

			@SuppressWarnings("unchecked")
			ArrayList<Object> list = voucher.getList(option);

			if (list.size() < index)
				return;

			VoucherPlugin.getInstance().getVoucherManager().startEditing(player, voucher, type, index);
			ElementEditInventory.getInventory(voucher, option, index).close(player);
			player.sendMessage(getEditMessage(option));

		});

		return element;
	}

	private static String getEditMessage(Option option) {
		VoucherPlugin plugin = VoucherPlugin.getInstance();
		switch (option) {
		case MESSAGE:
			return Message.VOUCHER_START_EDIT_MESSAGE.getMessage(plugin, null);
		case SOUND:
			return Message.VOUCHER_START_EDIT_SOUND.getMessage(plugin, null);
		case LORE:
			return Message.VOUCHER_START_EDIT_LORE.getMessage(plugin, null);
		case COMMAND:
			return Message.VOUCHER_START_EDIT_COMMAND.getMessage(plugin, null);
		case PERMISSION:
			return Message.VOUCHER_START_EDIT_PERMISSION.getMessage(plugin, null);
		case TITLE:
			return Message.VOUCHER_START_EDIT_TITLE.getMessage(plugin, null);
		default:
			return Message.VOUCHER_START_EDIT_SUBTITLE.getMessage(plugin, null);
		}
	}

	public static ClickableItem editMessageType(Voucher voucher, int index, Player player) {

		ArrayList<VoucherMessage> list = voucher.getMessages();

		if (list.size() < index)
			return null;

		ClickableItem element = ClickableItem
				.of(EditGuiItem.getEditMessageType(voucher.getMessages().get(index).getType()), e -> {

					if (!e.getAction().equals(InventoryAction.PICKUP_ALL))
						return;

					VoucherMessage message = list.get(index);
					message.toggle();
					ElementEditInventory.getInventory(voucher, Option.MESSAGE, index).open(player);

				});

		return element;
	}

	public static ClickableItem editFadeIn(Voucher voucher, int index, Player player, VoucherPlugin plugin) {
		ArrayList<VoucherTitle> list = voucher.getTitles();

		if (list.size() < index)
			return null;

		ClickableItem element = ClickableItem.of(EditGuiItem.getEditFadeIn(voucher, index), e -> {

			VoucherTitle title = list.get(index);
			int incr = plugin.getVoucherManager().getIncrement(player);

			if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
				title.setFadeIn(title.getFadeIn() + incr);
				ElementEditInventory.getInventory(voucher, Option.TITLE, index).open(player);
			} else if (e.getAction().equals(InventoryAction.PICKUP_HALF)) {
				if (title.getFadeIn() - incr < 0)
					return;
				title.setFadeIn(title.getFadeIn() - incr);
				ElementEditInventory.getInventory(voucher, Option.TITLE, index).open(player);
			} else if (e.getAction().equals(InventoryAction.CLONE_STACK)) {
				plugin.getVoucherManager().toggleIncrement(player);
			}

		});

		return element;
	}

	public static ClickableItem editStay(Voucher voucher, int index, Player player, VoucherPlugin plugin) {
		ArrayList<VoucherTitle> list = voucher.getTitles();

		if (list.size() < index)
			return null;

		ClickableItem element = ClickableItem.of(EditGuiItem.getEditStay(voucher, index), e -> {

			VoucherTitle title = list.get(index);
			int incr = plugin.getVoucherManager().getIncrement(player);

			if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
				title.setStay(title.getStay() + incr);
				ElementEditInventory.getInventory(voucher, Option.TITLE, index).open(player);
			} else if (e.getAction().equals(InventoryAction.PICKUP_HALF)) {
				if (title.getStay() - incr < 0)
					return;
				title.setStay(title.getStay() - incr);
				ElementEditInventory.getInventory(voucher, Option.TITLE, index).open(player);
			} else if (e.getAction().equals(InventoryAction.CLONE_STACK)) {
				plugin.getVoucherManager().toggleIncrement(player);
			}

		});

		return element;
	}

	public static ClickableItem editFadeOut(Voucher voucher, int index, Player player, VoucherPlugin plugin) {
		ArrayList<VoucherTitle> list = voucher.getTitles();

		if (list.size() < index)
			return null;

		ClickableItem element = ClickableItem.of(EditGuiItem.getEditFadeOut(voucher, index), e -> {

			VoucherTitle title = list.get(index);
			int incr = plugin.getVoucherManager().getIncrement(player);

			if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
				title.setFadeOut(title.getFadeOut() + incr);
				ElementEditInventory.getInventory(voucher, Option.TITLE, index).open(player);
			} else if (e.getAction().equals(InventoryAction.PICKUP_HALF)) {
				if (title.getFadeOut() - incr < 0)
					return;
				title.setFadeOut(title.getFadeOut() - incr);
				ElementEditInventory.getInventory(voucher, Option.TITLE, index).open(player);
			} else if (e.getAction().equals(InventoryAction.CLONE_STACK)) {
				plugin.getVoucherManager().toggleIncrement(player);
			}

		});

		return element;
	}

	public static ClickableItem editDelay(Voucher voucher, Option option, int index, Player player,
			VoucherPlugin plugin) {
		@SuppressWarnings("rawtypes")
		ArrayList list = voucher.getList(option);

		if (list.size() < index)
			return null;

		ClickableItem element = ClickableItem.of(EditGuiItem.getEditDelay(voucher, option, index), e -> {

			if (e.getAction().equals(InventoryAction.CLONE_STACK)) {
				plugin.getVoucherManager().toggleIncrement(player);
			}

			int incr = plugin.getVoucherManager().getIncrement(player);

			if (list.get(index) instanceof VoucherSound) {
				VoucherSound sound = (VoucherSound) list.get(index);

				if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
					sound.setDelay(sound.getDelay() + incr);
					ElementEditInventory.getInventory(voucher, option, index).open(player);
					System.out.println("delay");
				} else if (e.getAction().equals(InventoryAction.PICKUP_HALF)) {
					if (sound.getDelay() - incr < 0)
						return;
					sound.setDelay(sound.getDelay() - incr);
					ElementEditInventory.getInventory(voucher, option, index).open(player);
					System.out.println("delay");
				}

			} else if (list.get(index) instanceof VoucherTitle) {
				VoucherTitle title = (VoucherTitle) list.get(index);

				if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
					title.setDelay(title.getDelay() + incr);
					ElementEditInventory.getInventory(voucher, option, index).open(player);
				} else if (e.getAction().equals(InventoryAction.PICKUP_HALF)) {
					if (title.getDelay() - incr < 0)
						return;
					title.setDelay(title.getDelay() - incr);
					ElementEditInventory.getInventory(voucher, option, index).open(player);
				}
			}

		});

		return element;

	}

	public static ClickableItem editPitch(Voucher voucher, int index, Player player) {
		ArrayList<VoucherSound> list = voucher.getSounds();

		if (list.size() < index)
			return null;

		ClickableItem element = ClickableItem.of(EditGuiItem.getEditPitch(voucher, index), e -> {

			VoucherSound sound = list.get(index);

			if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
				if (sound.getPitch() + 0.1 > 2) {
					sound.setPitch(2);
				} else {
					sound.setPitch((float) (sound.getPitch() + 0.1));
				}

				ElementEditInventory.getInventory(voucher, Option.SOUND, index).open(player);
			} else if (e.getAction().equals(InventoryAction.PICKUP_HALF)) {
				if (sound.getPitch() - 0.1 < 0.5) {
					sound.setPitch(0.5f);
				} else {
					sound.setPitch((float) (sound.getPitch() - 0.1));
				}

				ElementEditInventory.getInventory(voucher, Option.SOUND, index).open(player);
			}

		});

		return element;
	}

	public static ClickableItem editVolume(Voucher voucher, int index, Player player) {
		ArrayList<VoucherSound> list = voucher.getSounds();

		if (list.size() < index)
			return null;

		ClickableItem element = ClickableItem.of(EditGuiItem.getEditVolume(voucher, index), e -> {

			VoucherSound sound = list.get(index);

			if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
				if (sound.getVolume() + 0.1 > 1) {
					sound.setVolume(1);
				} else {
					sound.setVolume((float) (sound.getVolume() + 0.1));
				}

				ElementEditInventory.getInventory(voucher, Option.SOUND, index).open(player);
			} else if (e.getAction().equals(InventoryAction.PICKUP_HALF)) {
				if (sound.getVolume() - 0.1 < 0) {
					sound.setVolume(0f);
				} else {
					sound.setVolume((float) (sound.getVolume() - 0.1));
				}

				ElementEditInventory.getInventory(voucher, Option.SOUND, index).open(player);
			}

		});

		return element;
	}

	public static ClickableItem editSoundPlayer(Voucher voucher, int index, Player player) {
		ArrayList<VoucherSound> list = voucher.getSounds();

		if (list.size() < index)
			return null;

		ClickableItem element = ClickableItem.of(EditGuiItem.getEditSoundPlayer(voucher, index), e -> {

			VoucherSound sound = list.get(index);

			if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
				sound.toggleSoundPlayer();

				ElementEditInventory.getInventory(voucher, Option.SOUND, index).open(player);
			}

		});

		return element;
	}

	public static ClickableItem editCommandSender(Voucher voucher, int index, Player player) {
		ArrayList<VoucherCommand> list = voucher.getCommands();

		if (list.size() < index)
			return null;

		ClickableItem element = ClickableItem.of(EditGuiItem.getEditCommandSender(voucher, index), e -> {

			VoucherCommand command = list.get(index);

			if (e.getAction().equals(InventoryAction.PICKUP_ALL)) {
				command.toggle();
				ElementEditInventory.getInventory(voucher, Option.COMMAND, index).open(player);
			}

		});

		return element;
	}

}

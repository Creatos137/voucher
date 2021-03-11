package me.creatos.voucher.inventory.main;

import java.util.ArrayList;
import java.util.function.Consumer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
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
import me.creatos.voucher.core.enums.EditType;
import me.creatos.voucher.core.enums.Option;
import me.creatos.voucher.inventory.Content;
import me.creatos.voucher.utils.EditGuiItem;
import me.creatos.voucher.utils.Message;

public class VoucherEditInventory implements InventoryProvider {

	private static final String TITLE = "Voucher GUI                  " + ChatColor.WHITE + ChatColor.BOLD.toString()
			+ "EDIT";

	private Voucher voucher;
	private Option option;

	public VoucherEditInventory(Voucher voucher, Option option) {
		this.voucher = voucher;
		this.option = option;
	}

	public static SmartInventory getInventory(Voucher voucher, Option option) {
		return SmartInventory.builder().manager(VoucherPlugin.getInstance().getInventoryManager()).id("edit_voucher")
				.provider(new VoucherEditInventory(voucher, option)).size(6, 9).parent(VoucherInventory.INVENTORY)
				.title(TITLE).build();
	}

	@Override
	public void init(Player player, InventoryContents contents) {
		
		contents.newIterator("iter", SlotIterator.Type.HORIZONTAL, 0, 0).allowOverride(false);
		contents.newIterator("border", SlotIterator.Type.HORIZONTAL, 0, 0).allowOverride(false);

		switch (option) {
		case VOUCHER:
			initVoucherPage(player, contents);
			break;
		case SOUND:
			initSoundPage(player, contents);
			break;
		case MESSAGE:
			initMessagePage(player, contents);
			break;
		case TITLE:
			initTitlePage(player, contents);
			break;
		case COMMAND:
			initCmdPage(player, contents);
			break;
		case PERMISSION:
			initPermPage(player, contents);
			break;
		case LORE:
			initLorePage(player, contents);
			break;

		default:
			break;
		}

	}

	private void initVoucherPage(Player player, InventoryContents contents) {
		initHotbar(player, contents, option);
		addOptions(player, contents);

		SlotIterator iter = contents.iterator("border").get();

		while (!iter.ended()) {
			iter.set(ClickableItem.empty(EditGuiItem.getBorder())).next();
		}
		
	}

	private void initSoundPage(Player player, InventoryContents contents) {
		initHotbar(player, contents, option);
		addElements(player, contents);

		

	}

	private void initMessagePage(Player player, InventoryContents contents) {
		initHotbar(player, contents, option);
		addElements(player, contents);

	}

	private void initTitlePage(Player player, InventoryContents contents) {
		initHotbar(player, contents, option);
		addElements(player, contents);

	}

	private void initCmdPage(Player player, InventoryContents contents) {
		initHotbar(player, contents, option);
		addElements(player, contents);

	}

	private void initPermPage(Player player, InventoryContents contents) {
		initHotbar(player, contents, option);
		addElements(player, contents);

	}

	private void initLorePage(Player player, InventoryContents contents) {
		initHotbar(player, contents, option);
		addElements(player, contents);

	}

	private void initHotbar(Player player, InventoryContents contents, Option option) {
		contents.set(5, 0, Content.getBackElementWithParent(VoucherInventory.INVENTORY, player));
		contents.set(5, 1,
				ClickableItem.of(EditGuiItem.getVoucher(), e -> getInventory(voucher, Option.VOUCHER).open(player)));
		contents.set(5, 2,
				ClickableItem.of(EditGuiItem.getSounds(), e -> getInventory(voucher, Option.SOUND).open(player)));
		contents.set(5, 3,
				ClickableItem.of(EditGuiItem.getMessages(), e -> getInventory(voucher, Option.MESSAGE).open(player)));
		contents.set(5, 4,
				ClickableItem.of(EditGuiItem.getLore(), e -> getInventory(voucher, Option.LORE).open(player)));
		contents.set(5, 5,
				ClickableItem.of(EditGuiItem.getTitles(), e -> getInventory(voucher, Option.TITLE).open(player)));
		contents.set(5, 6,
				ClickableItem.of(EditGuiItem.getCommands(), e -> getInventory(voucher, Option.COMMAND).open(player)));
		contents.set(5, 7, ClickableItem.of(EditGuiItem.getPermissions(),
				e -> getInventory(voucher, Option.PERMISSION).open(player)));
		contents.set(5, 8, ClickableItem.empty(EditGuiItem.getBorder()));
		int i = 1;
		switch (option) {
		case VOUCHER:
			i = 1;
			break;
		case SOUND:
			i = 2;
			break;
		case MESSAGE:
			i = 3;
			break;
		case LORE:
			i = 4;
			break;
		case TITLE:
			i = 5;
			break;
		case COMMAND:
			i = 6;
			break;
		case PERMISSION:
			i = 7;
			break;

		default:
			i = 1;
			break;
		}
		contents.fillRow(4, ClickableItem.empty(EditGuiItem.getBorder()));
		contents.set(4, i, ClickableItem.empty(EditGuiItem.getSelectionIndicator()));
	}

	private void addOptions(Player player, InventoryContents contents) {
		ItemStack voucherItem = voucher.getItem();
		ItemMeta meta = voucherItem.getItemMeta();

		ArrayList<String> lore = (ArrayList<String>) meta.getLore();

		if (lore == null)
			lore = new ArrayList<>();

		lore.add(null);
		lore.add(ChatColor.LIGHT_PURPLE + ChatColor.BOLD.toString() + "PREVIEW");

		meta.setLore(lore);
		voucherItem.setItemMeta(meta);

		contents.set(0, 0, ClickableItem.empty(voucherItem));

		contents.set(0, 1, ClickableItem.of(EditGuiItem.getChangeId(voucher), event -> changeIdAction(player, event)));

		contents.set(0, 2,
				ClickableItem.of(EditGuiItem.getChangeName(voucher), event -> changeNameAction(player, event)));
		
		contents.set(0, 3, Content.getVoucherMaterial(player, voucher));

		contents.set(0, 8, Content.getRemoveVoucher(player, voucher, VoucherPlugin.getInstance()));

		contents.set(1, 1, ClickableItem.of(EditGuiItem.getBoolean(voucher, "send_chat"),
				event -> toggleBoolean(player, event, "send_chat")));

		contents.set(1, 2, ClickableItem.of(EditGuiItem.getBoolean(voucher, "send_broadcast"),
				event -> toggleBoolean(player, event, "send_broadcast")));

		contents.set(1, 3, ClickableItem.of(EditGuiItem.getBoolean(voucher, "send_action_bar"),
				event -> toggleBoolean(player, event, "send_action_bar")));

		contents.set(1, 4, ClickableItem.of(EditGuiItem.getBoolean(voucher, "send_title"),
				event -> toggleBoolean(player, event, "send_title")));

		contents.set(1, 5, ClickableItem.of(EditGuiItem.getBoolean(voucher, "send_sound"),
				event -> toggleBoolean(player, event, "send_sound")));

		contents.set(1, 6, ClickableItem.of(EditGuiItem.getBoolean(voucher, "send_command"),
				event -> toggleBoolean(player, event, "send_command")));

		contents.set(1, 7, ClickableItem.of(EditGuiItem.getBoolean(voucher, "send_permission"),
				event -> toggleBoolean(player, event, "send_permission")));

		contents.set(2, 1, ClickableItem.of(EditGuiItem.getBoolean(voucher, "confirm"),
				event -> toggleBoolean(player, event, "confirm")));

		contents.set(2, 2, ClickableItem.of(EditGuiItem.getBoolean(voucher, "consume"),
				event -> toggleBoolean(player, event, "consume")));

		contents.set(2, 3, ClickableItem.of(EditGuiItem.getBoolean(voucher, "set_lore"),
				event -> toggleBoolean(player, event, "set_lore")));

	}

	private void addElements(Player player, InventoryContents contents) {

		SlotIterator iter = contents.iterator("iter").get();

		switch (option) {
		case SOUND:
			setElementsFrom(voucher.getSounds(), iter, player, EditGuiItem.getAddSound(),
					e -> EditGuiItem.addSound(e, player, voucher, option));
			break;
		case MESSAGE:
			setElementsFrom(voucher.getMessages(), iter, player, EditGuiItem.getAddMessage(),
					e -> EditGuiItem.addMsg(e, player, voucher, option));
			break;
		case COMMAND:
			setElementsFrom(voucher.getCommands(), iter, player, EditGuiItem.getAddCommand(),
					e -> EditGuiItem.addCmd(e, player, voucher, option));
			break;
		case PERMISSION:
			setElementsFrom(voucher.getPermissions(), iter, player, EditGuiItem.getAddPermission(),
					e -> EditGuiItem.addPerm(e, player, voucher, option));
			break;
		case TITLE:
			setElementsFrom(voucher.getTitles(), iter, player, EditGuiItem.getAddTitle(),
					e -> EditGuiItem.addTitle(e, player, voucher, option));
			break;
		case LORE:
			setElementsFrom(voucher.getLore(), iter, player, EditGuiItem.getAddLore(),
					e -> EditGuiItem.addLo(e, player, voucher, option));
			break;
		default:
			break;
		}

	}

	private void toggleBoolean(Player player, InventoryClickEvent event, String s) {
		switch (s.toLowerCase()) {
		case "send_chat":
			if (voucher.sendChatMessage()) {
				voucher.setSendChatMessage(false);
			} else {
				voucher.setSendChatMessage(true);
			}

			getInventory(voucher, Option.VOUCHER).open(player);
			break;
		case "send_broadcast":
			if (voucher.sendBroadcastMessage()) {
				voucher.setSendBroadcastMessage(false);
			} else {
				voucher.setSendBroadcastMessage(true);
			}

			getInventory(voucher, Option.VOUCHER).open(player);
			break;
		case "send_action_bar":
			if (voucher.sendActionbarMessage()) {
				voucher.setSendActionbarMessage(false);
			} else {
				voucher.setSendActionbarMessage(true);
			}

			getInventory(voucher, Option.VOUCHER).open(player);
			break;
		case "send_title":
			if (voucher.sendTitle()) {
				voucher.setSendTitle(false);
			} else {
				voucher.setSendTitle(true);
			}

			getInventory(voucher, Option.VOUCHER).open(player);
			break;
		case "send_sound":
			if (voucher.playSound()) {
				voucher.setPlaySound(false);
			} else {
				voucher.setPlaySound(true);
			}

			getInventory(voucher, Option.VOUCHER).open(player);
			break;
		case "send_command":
			if (voucher.sendCommand()) {
				voucher.setSendCommand(false);
			} else {
				voucher.setSendCommand(true);
			}

			getInventory(voucher, Option.VOUCHER).open(player);
			break;
		case "send_permission":
			if (voucher.sendPermission()) {
				voucher.setSendPermission(false);
			} else {
				voucher.setSendPermission(true);
			}

			getInventory(voucher, Option.VOUCHER).open(player);
			break;
		case "confirm":
			if (voucher.confirm()) {
				voucher.setConfirm(false);
			} else {
				voucher.setConfirm(true);
			}

			getInventory(voucher, Option.VOUCHER).open(player);
			break;
		case "consume":
			if (voucher.consume()) {
				voucher.setConsume(false);
			} else {
				voucher.setConsume(true);
			}

			getInventory(voucher, Option.VOUCHER).open(player);
			break;
		case "set_lore":
			if (voucher.doSetLore()) {
				voucher.setSetLore(false);
			} else {
				voucher.setSetLore(true);
			}

			getInventory(voucher, Option.VOUCHER).open(player);
			break;

		default:
			break;
		}
	}

	private void changeNameAction(Player player, InventoryClickEvent event) {
		if (event.getAction().equals(InventoryAction.PICKUP_ALL)) {
			getInventory(voucher, Option.VOUCHER).close(player);
			// Register player to chat editor
			player.sendMessage(Message.VOUCHER_START_EDIT_NAME.getMessage(VoucherPlugin.getInstance(), voucher));
			VoucherPlugin.getInstance().getVoucherManager().startEditing(player, voucher, EditType.ITEM);
		}
	}

	private void changeIdAction(Player player, InventoryClickEvent event) {
		if (event.getAction().equals(InventoryAction.PICKUP_ALL)) {
			getInventory(voucher, Option.VOUCHER).close(player);
			// Register player to chat editor
			player.sendMessage(Message.VOUCHER_START_EDIT_ID.getMessage(VoucherPlugin.getInstance(), voucher));
			VoucherPlugin.getInstance().getVoucherManager().startEditing(player, voucher, EditType.ID);
		}
		;
	}

	@Override
	public void update(Player player, InventoryContents contents) {
		// TODO Auto-generated method stub

	}

	public void setElementsFrom(ArrayList<?> list, SlotIterator iterator, Player player, ItemStack item,
			Consumer<InventoryClickEvent> consumer) {

		if (!list.isEmpty()) {

			for (int i = 0; i < list.size(); i++) {

				if (iterator.ended())
					return;

				if (iterator.row() >= 5)
					return;

				iterator.next();

				iterator.set(Content.voucherOptionElement(player, voucher, option, i));

			}

		}

		if (iterator.ended())
			return;

		if (iterator.row() >= 5)
			return;
		iterator.next();
		iterator.set(ClickableItem.of(item, consumer));

		return;
	}

}

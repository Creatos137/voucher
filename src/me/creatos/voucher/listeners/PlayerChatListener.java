package me.creatos.voucher.listeners;

import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.creatos.voucher.VoucherPlugin;
import me.creatos.voucher.core.Voucher;
import me.creatos.voucher.core.enums.EditType;
import me.creatos.voucher.core.enums.MessageType;
import me.creatos.voucher.core.enums.Option;
import me.creatos.voucher.core.enums.SenderType;
import me.creatos.voucher.core.wrappers.VoucherCommand;
import me.creatos.voucher.core.wrappers.VoucherMessage;
import me.creatos.voucher.core.wrappers.VoucherSound;
import me.creatos.voucher.core.wrappers.VoucherTitle;
import me.creatos.voucher.files.VoucherManager;
import me.creatos.voucher.inventory.edit.ElementEditInventory;
import me.creatos.voucher.inventory.main.VoucherEditInventory;
import me.creatos.voucher.inventory.main.VoucherInventory;
import me.creatos.voucher.utils.Message;

public class PlayerChatListener implements Listener {

	private VoucherPlugin plugin;

	public PlayerChatListener(VoucherPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		VoucherManager vm = plugin.getVoucherManager();

		if (vm.isEditing(event.getPlayer())) {
			event.setCancelled(true);
		} else {
			return;
		}

		

		Voucher voucher = vm.getEditingVoucher(event.getPlayer());
		EditType type = vm.getEditType(event.getPlayer());
		int index = vm.getEditIndex(event.getPlayer());

		if (event.getMessage().equalsIgnoreCase("cancel")) {
			vm.stopEditing(event.getPlayer());
			event.getPlayer().sendMessage(Message.CANCEL_EDIT.getMessage(plugin, voucher));
			return;
		}
		
		switch (type) {
		case ID:
			editId(vm, event, voucher);
			break;
		case NAME:
			editName(vm, event, voucher);
			break;
		case ITEM:
			editItem(vm, event, voucher);
			break;
		case COMMAND:
			if(index != -1) {
				editCommand(vm, event, voucher, index);
			} else {
				addCommand(vm, event, voucher);
			}
			
			break;
		case TITLE:
			if(index != -1) {
				editTitle(vm, event, voucher, index);
			} else {
				addTitle(vm, event, voucher);
			}
			break;
		case SUBTITLE:
			if(index != -1) {
				editSubtitle(vm, event, voucher, index);
			}
			break;
		case PERMISSION:
			if(index != -1) {
				editPermission(vm, event, voucher, index);
			} else {
				addPermission(vm, event, voucher);
			}
			
			break;
		case MESSAGE:
			if(index != -1) {
				editMessage(vm, event, voucher, index);
			} else {
				addMessage(vm, event, voucher);
			}
			break;
		case LORE:
			if(index != -1) {
				editLore(vm, event, voucher, index);
			} else {
				addLore(vm, event, voucher);
			}
			break;
		case SOUND:
			if(index != -1) {
				editSound(vm, event, voucher, index);
			} else {
				addSound(vm, event, voucher);
			}
			break;
		case CREATE:
			create(vm, event, voucher);

		default:
			break;
		}

		vm.stopEditing(event.getPlayer());
	}

	

	private void editPermission(VoucherManager vm, AsyncPlayerChatEvent event, Voucher voucher, int index) {
		if(voucher.getPermissions() == null || voucher.getPermissions().isEmpty())
			return;
		
		if(voucher.getPermissions().size() < index)
			return;
		
		voucher.getPermissions().set(index, event.getMessage());
		ElementEditInventory.getInventory(voucher, Option.PERMISSION, index).open(event.getPlayer());
		
	}
	
	private void editCommand(VoucherManager vm, AsyncPlayerChatEvent event, Voucher voucher, int index) {
		if(voucher.getCommands() == null || voucher.getCommands().isEmpty())
			return;
		
		if(voucher.getCommands().size() < index)
			return;
		
		voucher.getCommands().get(index).setCommand(event.getMessage());
		ElementEditInventory.getInventory(voucher, Option.COMMAND, index).open(event.getPlayer());
		
	}
	
	private void editMessage(VoucherManager vm, AsyncPlayerChatEvent event, Voucher voucher, int index) {
		if(voucher.getMessages() == null || voucher.getMessages().isEmpty())
			return;
		
		if(voucher.getPermissions().size() < index)
			return;
		
		voucher.getMessages().get(index).setMessage(event.getMessage());
		ElementEditInventory.getInventory(voucher, Option.MESSAGE, index).open(event.getPlayer());
		
	}
	
	private void editSound(VoucherManager vm, AsyncPlayerChatEvent event, Voucher voucher, int index) {
		if(voucher.getSounds() == null || voucher.getSounds().isEmpty())
			return;
		
		if(voucher.getSounds().size() < index)
			return;
		
		if (!EnumUtils.isValidEnum(Sound.class, event.getMessage().toUpperCase())) {
			ElementEditInventory.getInventory(voucher, Option.SOUND, index).open(event.getPlayer());
			return;
		}

		Sound sound = Sound.valueOf(event.getMessage().toUpperCase());
		
		voucher.getSounds().get(index).setSound(sound);
		ElementEditInventory.getInventory(voucher, Option.SOUND, index).open(event.getPlayer());
		
	}
	
	private void editTitle(VoucherManager vm, AsyncPlayerChatEvent event, Voucher voucher, int index) {
		if(voucher.getTitles() == null || voucher.getTitles().isEmpty())
			return;
		
		if(voucher.getTitles().size() < index)
			return;
		
		voucher.getTitles().get(index).setTitle(event.getMessage());;
		ElementEditInventory.getInventory(voucher, Option.TITLE, index).open(event.getPlayer());
		
	}
	
	private void editSubtitle(VoucherManager vm, AsyncPlayerChatEvent event, Voucher voucher, int index) {
		if(voucher.getTitles() == null || voucher.getTitles().isEmpty())
			return;
		
		if(voucher.getTitles().size() < index)
			return;
		
		voucher.getTitles().get(index).setSubtitle(event.getMessage());
		ElementEditInventory.getInventory(voucher, Option.TITLE, index).open(event.getPlayer());
		
	}

	private void editLore(VoucherManager vm, AsyncPlayerChatEvent event, Voucher voucher, int index) {
		if(voucher.getLore() == null || voucher.getLore().isEmpty())
			return;
		
		if(voucher.getLore().size() < index)
			return;
		
		voucher.getLore().set(index, event.getMessage());
		ElementEditInventory.getInventory(voucher, Option.LORE, index).open(event.getPlayer());
		
	}

	private void editItem(VoucherManager vm, AsyncPlayerChatEvent event, Voucher voucher) {
		
		System.out.println(1);
		
		if (!EnumUtils.isValidEnum(Material.class, event.getMessage().toUpperCase())) {
			VoucherEditInventory.getInventory(voucher, Option.VOUCHER).open(event.getPlayer());
			return;
		}

		System.out.println(2);
		Material material = Material.valueOf(event.getMessage().toUpperCase());
		
		voucher.setMaterial(material);
		VoucherEditInventory.getInventory(voucher, Option.VOUCHER).open(event.getPlayer());
		
		System.out.println(material);
		
	}

	private void editName(VoucherManager vm, AsyncPlayerChatEvent event, Voucher voucher) {
		voucher.setName(event.getMessage());
		VoucherEditInventory.getInventory(voucher, Option.VOUCHER).open(event.getPlayer());
	}

	private void addSound(VoucherManager vm, AsyncPlayerChatEvent event, Voucher voucher) {
		if (!EnumUtils.isValidEnum(Sound.class, event.getMessage().toUpperCase())) {
			VoucherEditInventory.getInventory(voucher, Option.SOUND).open(event.getPlayer());
			return;
		}

		Sound sound = Sound.valueOf(event.getMessage().toUpperCase());

		voucher.getSounds().add(new VoucherSound(sound));
		VoucherEditInventory.getInventory(voucher, Option.SOUND).open(event.getPlayer());
	}

	private void addMessage(VoucherManager vm, AsyncPlayerChatEvent event, Voucher voucher) {
		voucher.getMessages().add(new VoucherMessage(event.getMessage(), MessageType.CHAT));
		VoucherEditInventory.getInventory(voucher, Option.MESSAGE).open(event.getPlayer());
	}

	private void addLore(VoucherManager vm, AsyncPlayerChatEvent event, Voucher voucher) {
		voucher.getLore().add(event.getMessage());
		VoucherEditInventory.getInventory(voucher, Option.LORE).open(event.getPlayer());
	}

	private void addPermission(VoucherManager vm, AsyncPlayerChatEvent event, Voucher voucher) {
		voucher.getPermissions().add(event.getMessage());
		VoucherEditInventory.getInventory(voucher, Option.PERMISSION).open(event.getPlayer());
	}

	private void addTitle(VoucherManager vm, AsyncPlayerChatEvent event, Voucher voucher) {
		voucher.getTitles().add(new VoucherTitle(event.getMessage(), null, 1, 20, 1, 0));
		VoucherEditInventory.getInventory(voucher, Option.TITLE).open(event.getPlayer());
	}

	private void addCommand(VoucherManager vm, AsyncPlayerChatEvent event, Voucher voucher) {
		voucher.getCommands().add(new VoucherCommand(event.getMessage(), SenderType.PLAYER));
		VoucherEditInventory.getInventory(voucher, Option.COMMAND).open(event.getPlayer());

	}

	private void editId(VoucherManager vm, AsyncPlayerChatEvent event, Voucher voucher) {
		if (voucher == null) {
			return;
		}

		if (vm.containsVoucherWithId(event.getMessage())) {
			event.getPlayer().sendMessage(Message.VOUCHER_ALREADY_EXISTS.getMessage(plugin, voucher));
			return;
		}
			
		vm.unregister(voucher);

		voucher.setId(event.getMessage());
		
		vm.register(voucher);
		VoucherEditInventory.getInventory(voucher, Option.VOUCHER).open(event.getPlayer());

	}
	
	private void create(VoucherManager vm, AsyncPlayerChatEvent event, Voucher voucher) {
		String id = event.getMessage().replaceAll(" ", "_");
		voucher = new Voucher(id);
		
		if(vm.containsVoucherWithId(voucher.getId())) {
			event.getPlayer().sendMessage(Message.VOUCHER_ALREADY_EXISTS.getMessage(plugin, voucher));
			return;
		}
		
		vm.register(voucher);
		vm.stopEditing(event.getPlayer());
		VoucherInventory.INVENTORY.open(event.getPlayer());
		
	}

}

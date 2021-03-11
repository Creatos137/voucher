package me.creatos.voucher.core;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.creatos.voucher.VoucherPlugin;
import me.creatos.voucher.core.enums.Option;
import me.creatos.voucher.core.wrappers.VoucherCommand;
import me.creatos.voucher.core.wrappers.VoucherMessage;
import me.creatos.voucher.core.wrappers.VoucherSound;
import me.creatos.voucher.core.wrappers.VoucherTitle;
import me.creatos.voucher.files.VoucherManager;
import me.creatos.voucher.inventory.main.VoucherConfirmInventory;
import me.creatos.voucher.utils.Message;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;

public class Voucher {

	private String id;
	private String name;
	
	private Material material;

	private ArrayList<VoucherMessage> messages;
	private ArrayList<VoucherTitle> titles;
	private ArrayList<VoucherCommand> commands;
	private ArrayList<VoucherSound> sounds;
	private ArrayList<String> permissions;
	private ArrayList<String> lore;

	// Text booleans
	private boolean sendChatMessage;
	private boolean sendActionbarMessage;
	private boolean sendBroadcastMessage;
	private boolean sendTitle;

	// Sound boolean
	private boolean playSound;

	// Command and permission boolean
	private boolean sendCommand;
	private boolean sendPermission;

	// Other boolean
	private boolean confirm;
	private boolean consume;
	private boolean setLore;

	// TODO: Create constructor

	public Voucher(String id, String name, Material material, ArrayList<VoucherMessage> messages, ArrayList<VoucherTitle> titles,
			ArrayList<VoucherCommand> commands, ArrayList<VoucherSound> sounds, ArrayList<String> permissions,
			ArrayList<String> lore, boolean sendChatMessage, boolean sendActionbarMessage, boolean sendBroadcastMessage,
			boolean sendTitle, boolean playSound, boolean sendCommand, boolean sendPermission, boolean confirm,
			boolean consume, boolean setLore) {
		super();
		this.id = id;
		this.name = name;
		this.material = material;
		this.messages = messages;
		this.titles = titles;
		this.commands = commands;
		this.sounds = sounds;
		this.permissions = permissions;
		this.lore = lore;
		this.sendChatMessage = sendChatMessage;
		this.sendActionbarMessage = sendActionbarMessage;
		this.sendBroadcastMessage = sendBroadcastMessage;
		this.sendTitle = sendTitle;
		this.playSound = playSound;
		this.sendCommand = sendCommand;
		this.sendPermission = sendPermission;
		this.confirm = confirm;
		this.consume = consume;
		this.setLore = setLore;
	}

	public Voucher(String id) {
		this(id, null, Material.PAPER, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), true, true, true, true, true, true, true, false, true, true);
	}

	// METHODS

	public void redeem(Player player, VoucherPlugin plugin, boolean ignoreConfirm) {
		if (plugin.getVoucherManager().getVoucherFromItemStack(player.getInventory().getItemInHand()) == null)
			return;

		if (confirm && (!ignoreConfirm)) {
			VoucherConfirmInventory
					.getInventory(
							plugin.getVoucherManager().getVoucherFromItemStack(player.getInventory().getItemInHand()))
					.open(player);
			return;
		}

		// ORDER: messages, titles, sound, permission, commands, consume
		if (sendChatMessage || sendActionbarMessage || sendBroadcastMessage) {
			if (!(messages == null || messages.isEmpty())) {
				for (VoucherMessage m : messages) {
					m.send(player, this);
				}
			}
		}

		if (sendTitle) {
			if (!(titles == null || titles.isEmpty())) {
				for (VoucherTitle title : titles) {
					title.send(player, this, plugin);
				}
			}
		}

		if (playSound) {
			if (!(sounds == null || sounds.isEmpty())) {
				for (VoucherSound s : sounds) {
					s.play(player, plugin);
				}
			}
		}

		if (sendPermission) {
			if (!(permissions == null || permissions.isEmpty())) {
				LuckPerms api = LuckPermsProvider.get();
				User user = api.getUserManager().getUser(player.getUniqueId());
				for (String perm : permissions) {
					Node node = Node.builder(perm).build();
					user.data().add(node);
				}
				api.getUserManager().saveUser(user);
			}
		}

		if (sendCommand) {
			if (!(commands == null || commands.isEmpty())) {
				for (VoucherCommand cmd : commands) {
					cmd.perform(player);
				}
			}
		}

		if (consume) {
			ItemStack item = player.getInventory().getItemInHand();

			if (item.getAmount() > 1) {
				player.getInventory().getItemInHand().setAmount(item.getAmount() - 1);
			} else if (item.getAmount() == 1) {
				player.getInventory().setItemInHand(null);

			}
		}

	}

	public void sendConfirm() {

	}

	// Getters and setters

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean sendChatMessage() {
		return sendChatMessage;
	}

	public boolean sendActionbarMessage() {
		return sendActionbarMessage;
	}

	public boolean sendBroadcastMessage() {
		return sendBroadcastMessage;
	}

	public boolean sendTitle() {
		return sendTitle;
	}

	public boolean playSound() {
		return playSound;
	}

	public boolean sendCommand() {
		return sendCommand;
	}

	public boolean sendPermission() {
		return sendPermission;
	}

	public boolean confirm() {
		return confirm;
	}

	public boolean consume() {
		return consume;
	}

	public boolean doSetLore() {
		return setLore;
	}

	public void setSendChatMessage(boolean sendChatMessage) {
		this.sendChatMessage = sendChatMessage;
	}

	public void setSendActionbarMessage(boolean sendActionbarMessage) {
		this.sendActionbarMessage = sendActionbarMessage;
	}

	public void setSendBroadcastMessage(boolean sendBroadcastMessage) {
		this.sendBroadcastMessage = sendBroadcastMessage;
	}

	public void setSendTitle(boolean sendTitle) {
		this.sendTitle = sendTitle;
	}

	public void setPlaySound(boolean playSound) {
		this.playSound = playSound;
	}

	public void setSendCommand(boolean sendCommand) {
		this.sendCommand = sendCommand;
	}

	public void setSendPermission(boolean sendPermission) {
		this.sendPermission = sendPermission;
	}

	public void setConfirm(boolean confirm) {
		this.confirm = confirm;
	}

	public void setConsume(boolean consume) {
		this.consume = consume;
	}

	public void setSetLore(boolean setLore) {
		this.setLore = setLore;
	}

	public ArrayList<VoucherTitle> getTitles() {
		return titles;
	}

	public void setTitles(ArrayList<VoucherTitle> titles) {
		this.titles = titles;
	}

	public ArrayList<String> getLore() {
		return lore;
	}

	public void setLore(ArrayList<String> lore) {
		this.lore = lore;
	}

	public ArrayList<VoucherMessage> getMessages() {
		return messages;
	}

	public ArrayList<VoucherCommand> getCommands() {
		return commands;
	}

	public ArrayList<VoucherSound> getSounds() {
		return sounds;
	}

	public ArrayList<String> getPermissions() {
		return permissions;
	}

	public void setMessages(ArrayList<VoucherMessage> messages) {
		this.messages = messages;
	}

	public void setCommands(ArrayList<VoucherCommand> commands) {
		this.commands = commands;
	}

	public void setSounds(ArrayList<VoucherSound> sounds) {
		this.sounds = sounds;
	}

	public void setPermissions(ArrayList<String> permissions) {
		this.permissions = permissions;
	}

	public ItemStack getItem() {
		ArrayList<String> l = null;
		if (setLore) {

			if (!(getLore() == null || getLore().isEmpty())) {

				l = new ArrayList<>();

				for (String s : getLore()) {
					String translated = Message.keyToValue(s, "%empty%", "&f");
					translated = Message.color(translated);
					l.add(translated);
				}
			}

		}

		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(Message.color(name));

		if (l != null) {
			meta.setLore(l);
		}

		item.setItemMeta(meta);

		NBTItem nbtItem = new NBTItem(item);
		nbtItem.setString("id", id);

		return nbtItem.getItem();
	}

	@SuppressWarnings("rawtypes")
	public ArrayList getList(Option option) {
		switch (option) {
		case SOUND:
			return getSounds();
		case MESSAGE:
			return getMessages();
		case LORE:
			return getLore();
		case TITLE:
			return getTitles();
		case COMMAND:
			return getCommands();
		case PERMISSION:
			return getPermissions();

		default:
			return null;
		}
	}

	public void duplicate() {
		VoucherManager voucherManager = VoucherPlugin.getInstance().getVoucherManager();
		String id = this.id;
		
		while (voucherManager.getVoucherWithId(id) != null) {
			id += "_copy";
		}

		Voucher voucher = new Voucher(id, name, material != null ? material : Material.PAPER, messages, titles, commands, sounds, permissions, lore, sendChatMessage,
				sendActionbarMessage, sendBroadcastMessage, sendTitle, playSound, sendCommand, sendPermission, confirm,
				consume, setLore);

		voucherManager.register(voucher);
		return;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

}

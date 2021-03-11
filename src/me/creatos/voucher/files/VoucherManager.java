package me.creatos.voucher.files;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;

import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.creatos.voucher.VoucherPlugin;
import me.creatos.voucher.core.Voucher;
import me.creatos.voucher.core.enums.EditType;
import me.creatos.voucher.core.enums.MessageType;
import me.creatos.voucher.core.enums.SenderType;
import me.creatos.voucher.core.enums.SoundPlayer;
import me.creatos.voucher.core.wrappers.VoucherCommand;
import me.creatos.voucher.core.wrappers.VoucherMessage;
import me.creatos.voucher.core.wrappers.VoucherSound;
import me.creatos.voucher.core.wrappers.VoucherTitle;

public class VoucherManager {

	private static ArrayList<Voucher> vouchers = new ArrayList<>();
	private static HashMap<Player, Voucher> editing = new HashMap<>();
	private static HashMap<Player, EditType> editType = new HashMap<>();
	private static HashMap<Player, Integer> editIndex = new HashMap<>();
	private static HashMap<Player, Integer> editIncrement = new HashMap<>();

	private final String FILE_NAME = "voucher_data.yml";
	private final String FOLDER_NAME = "/data/";

	private VoucherPlugin plugin;
	private FileConfiguration buildConfig = null;
	private File configFile = null;

	public VoucherManager(VoucherPlugin plugin) {
		this.plugin = plugin;
		saveDefaultConfig();
	}

	// Create the config if there is no config, check if everything is correct
	public void reloadConfig() {
		if (this.buildConfig == null)
			this.configFile = new File(this.plugin.getDataFolder() + FOLDER_NAME, FILE_NAME);

		this.buildConfig = YamlConfiguration.loadConfiguration(this.configFile);

		InputStream defaultStream = this.plugin.getResource(FILE_NAME);

		if (defaultStream != null) {
			YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
			this.buildConfig.setDefaults(defaultConfig);
		}
	}

	// Get the config file
	public FileConfiguration getConfig() {
		if (this.buildConfig == null)
			reloadConfig();
		return this.buildConfig;
	}

	// You are going to use this after you add stuff to the config file
	public void saveConfig() {
		if (this.buildConfig == null || this.configFile == null)
			return;

		try {
			this.getConfig().save(this.configFile);
		} catch (IOException e) {
			this.plugin.getLogger().log(Level.SEVERE, "Could not save config to " + this.configFile, e);
		}
	}

	// Initialize and save the file
	public void saveDefaultConfig() {
		if (this.configFile == null)
			this.configFile = new File(this.plugin.getDataFolder(), FILE_NAME);

		if (!this.configFile.exists()) {
			this.plugin.saveResource(FILE_NAME, false);
		}

	}

	public ArrayList<Voucher> getVouchers() {
		return vouchers;
	}

	public void register(Voucher voucher) {
		if (vouchers == null || vouchers.isEmpty())
			vouchers = new ArrayList<>();

		for (Voucher v : vouchers) {
			if (voucher.getId().equalsIgnoreCase(v.getId()))
				return;
		}

		vouchers.add(voucher);
	}

	public void unregister(Voucher voucher) {
		if (vouchers == null || vouchers.isEmpty())
			return;

		if (containsVoucherWithId(voucher.getId())) {
			if (vouchers.contains(getVoucherWithId(voucher.getId()))) {
				vouchers.remove(getVoucherWithId(voucher.getId()));
			}
		}
	}

	public boolean isEditing(Player player) {
		if (!editing.containsKey(player))
			return false;

		if (!editType.containsKey(player))
			return false;
		return true;
	}

	public void stopEditing(Player player) {
		if (editing.containsKey(player))
			editing.remove(player);

		if (editType.containsKey(player))
			editType.remove(player);
	}

	public void startEditing(Player player, Voucher voucher, EditType type, int index) {
		editing.put(player, voucher);
		editType.put(player, type);
		editIndex.put(player, index);
	}

	public void startEditing(Player player, Voucher voucher, EditType type) {
		startEditing(player, voucher, type, -1);
	}

	public Voucher getEditingVoucher(Player player) {
		if (!isEditing(player))
			return null;

		return editing.get(player);
	}

	public EditType getEditType(Player player) {
		if (!isEditing(player))
			return null;

		if (editType.containsKey(player))
			return editType.get(player);

		return null;
	}

	public int getEditIndex(Player player) {
		if (editIndex.containsKey(player))
			return editIndex.get(player);

		return -1;
	}

	public boolean containsVoucherWithId(String id) {
		for (Voucher voucher : vouchers) {
			if (voucher.getId().equalsIgnoreCase(id))
				return true;
		}

		return false;
	}

	public Voucher getVoucherWithId(String id) {
		for (Voucher voucher : vouchers) {
			if (voucher.getId().equalsIgnoreCase(id))
				return voucher;
		}

		return null;
	}

	public Voucher getVoucherFromItemStack(ItemStack item) {
		if (!item.hasItemMeta())
			return null;

		NBTItem nbtItem = new NBTItem(item);

		if (!nbtItem.hasNBTData())
			return null;

		if (!nbtItem.hasKey("id"))
			return null;

		String id = nbtItem.getString("id");

		if (id == null)
			return null;

		for (Voucher voucher : vouchers) {
			if (voucher.getId().equalsIgnoreCase(id))
				return voucher;
		}

		return null;
	}

	public Voucher getVoucher(NBTItem nbt) {
		String voucherId = nbt.getString("voucher_id");

		if (voucherId == null)
			return null;

		Voucher voucher = getVoucherWithId(voucherId);

		if (voucher == null)
			return null;

		return voucher;

	}

	public int getIncrement(Player player) {
		if (!editIncrement.containsKey(player))
			return 1;

		return editIncrement.get(player);

	}

	public void toggleIncrement(Player player) {
		if (!editIncrement.containsKey(player))
			editIncrement.put(player, 1);

		if (editIncrement.get(player) == 1) {
			editIncrement.put(player, 10);
		} else {
			editIncrement.put(player, 1);
		}
	}

	public void load() {
		if (getConfig().get("vouchers") == null)
			return;

		for (String id : getConfig().getConfigurationSection("vouchers").getKeys(false)) {
			String path = "vouchers." + id;
			String name = getConfig().getString(path + ".name");
			Material material = Material.PAPER;
			if(EnumUtils.isValidEnum(Material.class, getConfig().getString(path + ".material"))) {
				material = Material.valueOf(getConfig().getString(path + ".material"));
			}
			boolean sendChatMessage = getConfig().getBoolean(path + ".send_chat_message");
			boolean sendActionbarMessage = getConfig().getBoolean(path + ".send_actionbar_message");
			boolean sendBroadcastMessage = getConfig().getBoolean(path + ".send_broadcast_message");
			boolean sendTitle = getConfig().getBoolean(path + ".send_title");
			boolean playSound = getConfig().getBoolean(path + ".play_sound");
			boolean sendCommand = getConfig().getBoolean(path + ".send_command");
			boolean sendPermission = getConfig().getBoolean(path + ".send_permission");
			boolean confirm = getConfig().getBoolean(path + ".confirm");
			boolean consume = getConfig().getBoolean(path + ".consume");
			boolean setLore = getConfig().getBoolean(path + ".set_lore");

			ArrayList<VoucherMessage> messages = new ArrayList<>();
			if(getConfig().get("vouchers." + id + ".messages") != null) {
				for (String index : getConfig().getConfigurationSection("vouchers." + id + ".messages").getKeys(false)) {
					path = "vouchers." + id + ".messages." + index;
					String message = getConfig().getString(path + ".message");
					MessageType type = MessageType.valueOf(getConfig().getString(path + ".type"));
					VoucherMessage vm = new VoucherMessage(message, type);
					messages.add(vm);
				}
			}
			

			ArrayList<VoucherTitle> titles = new ArrayList<>();
			if(getConfig().get("vouchers." + id + ".titles") != null) {
				for (String index : getConfig().getConfigurationSection("vouchers." + id + ".titles").getKeys(false)) {
					path = "vouchers." + id + ".titles." + index;
					String title = getConfig().getString(path + ".title");
					String subtitle = getConfig().getString(path + ".subtitle");
					int fadeIn = getConfig().getInt(path + ".fade_in");
					int stay = getConfig().getInt(path + ".stay");
					int fadeOut = getConfig().getInt(path + ".fade_out");
					int delay = getConfig().getInt(path + ".delay");
					VoucherTitle vt = new VoucherTitle(title, subtitle, fadeIn, stay, fadeOut, delay);
					titles.add(vt);
				}
			}
			

			ArrayList<VoucherCommand> commands = new ArrayList<>();
			if(getConfig().get("vouchers." + id + ".commands") != null) {
				for (String index : getConfig().getConfigurationSection("vouchers." + id + ".commands").getKeys(false)) {
					path = "vouchers." + id + ".commands." + index;
					String command = getConfig().getString(path + ".command");
					SenderType sender = SenderType.valueOf(getConfig().getString(path + ".sender"));
					VoucherCommand vc = new VoucherCommand(command, sender);
					commands.add(vc);
				}
			}
			

			ArrayList<VoucherSound> sounds = new ArrayList<>();
			if(getConfig().get("vouchers." + id + ".sounds") != null) {
				for (String index : getConfig().getConfigurationSection("vouchers." + id + ".sounds").getKeys(false)) {
					path = "vouchers." + id + ".sounds." + index;
					Sound sound = Sound.valueOf(getConfig().getString(path + ".sound"));
					int volume = getConfig().getInt(path + ".volume");
					int pitch = getConfig().getInt(path + ".pitch");
					int delay = getConfig().getInt(path + ".delay");
					SoundPlayer soundPlayer = SoundPlayer.valueOf(getConfig().getString(path + ".sound_player"));
					VoucherSound vs = new VoucherSound(sound, pitch, volume, delay, soundPlayer);
					sounds.add(vs);
				}
			}
			

			ArrayList<String> permissions = new ArrayList<>();
			if(getConfig().get("vouchers." + id + ".permissions") != null) {
				for (String index : getConfig().getConfigurationSection("vouchers." + id + ".permissions").getKeys(false)) {
					path = "vouchers." + id + ".permissions." + index;
					String permission = getConfig().getString(path);
					permissions.add(permission);
				}
			}
			

			ArrayList<String> lore = new ArrayList<>();
			if(getConfig().get("vouchers." + id + ".lore") != null) {
				for (String index : getConfig().getConfigurationSection("vouchers." + id + ".lore").getKeys(false)) {
					System.out.println(index);
					path = "vouchers." + id + ".lore." + index;
					String line = getConfig().getString(path);
					lore.add(line);
				}
			}
			

			Voucher voucher = new Voucher(id, name, material, messages, titles, commands, sounds, permissions, lore,
					sendChatMessage, sendActionbarMessage, sendBroadcastMessage, sendTitle, playSound, sendCommand,
					sendPermission, confirm, consume, setLore);
			register(voucher);
			
		}
	}

	public void save() {
		if (vouchers == null || vouchers.isEmpty())
			return;

		getConfig().set("vouchers", null);

		for (Voucher voucher : vouchers) {
			String path = "vouchers." + voucher.getId();
			getConfig().set(path + ".name", voucher.getName());
			getConfig().set(path + ".material", voucher.getMaterial().toString());

			// Booleans
			getConfig().set(path + ".send_chat_message", voucher.sendChatMessage());
			getConfig().set(path + ".send_actionbar_message", voucher.sendActionbarMessage());
			getConfig().set(path + ".send_broadcast_message", voucher.sendBroadcastMessage());
			getConfig().set(path + ".send_title", voucher.sendTitle());

			getConfig().set(path + ".play_sound", voucher.playSound());
			getConfig().set(path + ".send_command", voucher.sendCommand());
			getConfig().set(path + ".send_permission", voucher.sendPermission());

			getConfig().set(path + ".confirm", voucher.confirm());
			getConfig().set(path + ".consume", voucher.consume());
			getConfig().set(path + ".set_lore", voucher.doSetLore());

			// Lists
			if (!(voucher.getMessages() == null || voucher.getMessages().isEmpty())) {
				for (int i = 0; i < voucher.getMessages().size(); i++) {
					getConfig().set(path + ".messages." + i + ".message", voucher.getMessages().get(i).getMessage());
					getConfig().set(path + ".messages." + i + ".type", voucher.getMessages().get(i).getType().toString());
				}
			}

			if (!(voucher.getTitles() == null || voucher.getTitles().isEmpty())) {
				for (int i = 0; i < voucher.getTitles().size(); i++) {
					getConfig().set(path + ".titles." + i + ".title", voucher.getTitles().get(i).getTitle());
					getConfig().set(path + ".titles." + i + ".subtitle", voucher.getTitles().get(i).getSubtitle());
					getConfig().set(path + ".titles." + i + ".fade_in", voucher.getTitles().get(i).getFadeIn());
					getConfig().set(path + ".titles." + i + ".stay", voucher.getTitles().get(i).getStay());
					getConfig().set(path + ".titles." + i + ".fade_out", voucher.getTitles().get(i).getFadeOut());
					getConfig().set(path + ".titles." + i + ".delay", voucher.getTitles().get(i).getDelay());
				}
			}

			if (!(voucher.getCommands() == null || voucher.getCommands().isEmpty())) {
				for (int i = 0; i < voucher.getCommands().size(); i++) {
					getConfig().set(path + ".commands." + i + ".command", voucher.getCommands().get(i).getCommand());
					getConfig().set(path + ".commands." + i + ".sender", voucher.getCommands().get(i).getSender().toString());
				}
			}

			if (!(voucher.getSounds() == null || voucher.getSounds().isEmpty())) {
				for (int i = 0; i < voucher.getSounds().size(); i++) {
					getConfig().set(path + ".sounds." + i + ".sound", voucher.getSounds().get(i).getSound().toString());
					getConfig().set(path + ".sounds." + i + ".volume", voucher.getSounds().get(i).getVolume());
					getConfig().set(path + ".sounds." + i + ".pitch", voucher.getSounds().get(i).getPitch());
					getConfig().set(path + ".sounds." + i + ".delay", voucher.getSounds().get(i).getDelay());
					getConfig().set(path + ".sounds." + i + ".sound_player",
							voucher.getSounds().get(i).getSoundPlayer().toString());
				}
			}

			if (!(voucher.getPermissions() == null || voucher.getPermissions().isEmpty())) {
				for (int i = 0; i < voucher.getPermissions().size(); i++) {
					getConfig().set(path + ".permissions." + i, voucher.getPermissions().get(i));
				}
			}
			
			if (!(voucher.getLore() == null || voucher.getLore().isEmpty())) {
				for (int i = 0; i < voucher.getLore().size(); i++) {

					getConfig().set(path + ".lore." + i, voucher.getLore().get(i));
				}
			}

		}
		
		saveConfig();

	}

}

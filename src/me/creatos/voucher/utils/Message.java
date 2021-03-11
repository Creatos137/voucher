package me.creatos.voucher.utils;

import me.creatos.voucher.VoucherPlugin;
import me.creatos.voucher.core.Voucher;
import net.md_5.bungee.api.ChatColor;

public enum Message {

	VOUCHER_START_CREATE("message.voucher_start_create"),
	VOUCHER_START_EDIT_ID("message.voucher_start_edit_id"),
	VOUCHER_START_EDIT_NAME("message.voucher_start_edit_name"),
	VOUCHER_START_EDIT_MESSAGE("message.voucher_start_edit_message"), VOUCHER_START_EDIT_ITEM("message.voucher_start_edit_item"),
	VOUCHER_START_EDIT_SOUND("message.voucher_start_edit_sound"),
	VOUCHER_START_EDIT_TITLE("message.voucher_start_edit_title"),
	VOUCHER_START_EDIT_SUBTITLE("message.voucher_start_edit_subtitle"),
	VOUCHER_START_EDIT_COMMAND("message.voucher_start_edit_command"),
	VOUCHER_START_EDIT_PERMISSION("message.voucher_start_edit_permission"),
	VOUCHER_START_EDIT_LORE("message.voucher_start_edit_lore"), VOUCHER_CREATE("message.voucher_create"),
	VOUCHER_REMOVE("message.voucher_remove"), VOUCHER_NOT_FOUND("message.voucher_not_found"),
	VOUCHER_ALREADY_EXISTS("message.voucher_already_exists"), PLAYER_NOT_FOUND("message.player_not_found"),
	VOUCHER_GIVE("message.voucher_give"),
	NO_COMMAND_PERMISSION("message.no_command_permission"), CANCEL_EDIT("message.cancel_edit");

	private String path;

	private Message(String path) {
		this.path = path;
	}

	public String getRawMessage(VoucherPlugin plugin) {

		if (plugin == null)
			return null;

		plugin.reloadConfig();
		String s = plugin.getConfig().getString(path);

		if (s == null)
			return null;

		return s;
	}

	public String getMessage(VoucherPlugin plugin, boolean prefix) {
		String text = getRawMessage(plugin);

		if (plugin.getConfig().getBoolean("message.enable_prefix")) {
			if(prefix) {
				text = plugin.getConfig().getString("message.prefix") + " " + text;
			}
			
		}

		text = color(text);
		return text;
	}
	
	public String getMessage(VoucherPlugin plugin, Voucher voucher) {
		String text = getRawMessage(plugin);
		text = translate(text, voucher);

		if (plugin.getConfig().getBoolean("message.enable_prefix")) {
			text = plugin.getConfig().getString("message.prefix") + " " + text;
		}

		text = color(text);
		return text;

	}

	public String getMessage(VoucherPlugin plugin, Voucher voucher, String playerName) {
		String text = getRawMessage(plugin);
		text = translate(text, voucher, playerName);

		if (plugin.getConfig().getBoolean("message.enable_prefix")) {
			text = plugin.getConfig().getString("message.prefix") + " " + text;
		}

		text = color(text);
		return text;

	}

	public String getMessage(VoucherPlugin plugin, Voucher voucher, String playerName, Object variable) {
		String text = getRawMessage(plugin);
		text = translate(text, voucher, playerName, variable);

		if (plugin.getConfig().getBoolean("message.enable_prefix")) {
			text = plugin.getConfig().getString("message.prefix") + " " + text;
		}

		text = color(text);
		return text;

	}

	public static String keyToValue(String text, String key, String value) {
		if (text == null || key == null)
			return null;

		if (text.contains(key)) {
			if (value != null)
				return text.replaceAll(key, value);

			if (value == null) {
				System.out.println("Value is null, removed " + key + " from string.");
				if (text.contains(" " + key)) {
					return text.replaceAll(" " + key, "");
				} else if (text.contains(key + " ")) {
					return text.replaceAll(key + " ", "");
				}

				return text.replaceAll(key, "");
			}

		}

		return text;
	}

	public static String color(String text) {
		if (text == null)
			return null;

		return ChatColor.translateAlternateColorCodes('&', text);
	}

	public static String translate(String text, Voucher voucher) {
		if (voucher == null)
			return Message.color(text);
		text = keyToValue(text, "%id%", voucher.getId());
		text = keyToValue(text, "%name%", voucher.getName());
		return Message.color(text);
	}

	public static String translate(String text, Voucher voucher, String playerName) {
		if (voucher != null) {
			text = keyToValue(text, "%id%", voucher.getId());
			text = keyToValue(text, "%name%", voucher.getName());
		}
			

		text = keyToValue(text, "%player%", playerName);
		return Message.color(text);
	}

	private String translate(String text, Voucher voucher, String playerName, Object variable) {
		if (voucher != null) {
			text = keyToValue(text, "%id%", voucher.getId());
			text = keyToValue(text, "%name%", voucher.getName());
		}

		if (variable != null)
			text = keyToValue(text, "%var%", variable.toString());

		text = keyToValue(text, "%player%", playerName);
		return Message.color(text);
	}

	

}

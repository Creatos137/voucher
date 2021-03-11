package me.creatos.voucher.core.wrappers;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import me.creatos.voucher.core.Voucher;
import me.creatos.voucher.core.enums.MessageType;
import me.creatos.voucher.utils.Message;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class VoucherMessage {

	private String message;
	private MessageType type;

	public VoucherMessage(String message, MessageType type) {
		this.message = message;
		this.type = type;
	}

	public void send(Player player, Voucher voucher) {
		String colorMessage = Message.translate(message, voucher, player.getName());
		switch (type) {
		case CHAT:
			if (voucher.sendChatMessage()) {
				player.sendMessage(colorMessage);
			}
			break;
		case ACTION_BAR:
			if (voucher.sendActionbarMessage()) {
				PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(colorMessage), (byte) 2);
				((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
			}
			break;
		case BROADCAST:
			if (voucher.sendBroadcastMessage()) {
				for (Player p : Bukkit.getOnlinePlayers()) {
					p.sendMessage(colorMessage);
				}
			}
			break;

		default:
			break;
		}
	}

	public void toggle() {
		switch (type) {
		case CHAT:
			type = MessageType.ACTION_BAR;
			break;
		case ACTION_BAR:
			type = MessageType.BROADCAST;
			break;
		case BROADCAST:
			type = MessageType.CHAT;
			break;

		default:
			break;
		}
	}

	public String getMessage() {
		return message;
	}

	public MessageType getType() {
		return type;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

}

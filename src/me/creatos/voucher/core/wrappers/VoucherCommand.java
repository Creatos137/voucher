package me.creatos.voucher.core.wrappers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.creatos.voucher.core.enums.SenderType;
import me.creatos.voucher.utils.Message;

public class VoucherCommand {

	private String command;
	private SenderType sender;
	
	
	
	public VoucherCommand(String command, SenderType sender) {
		this.command = command;
		this.sender = sender;
	}
	
	
	public void perform(Player player) {
		
		String translatedCommand = Message.keyToValue(command, "%player%", player.getName());
		
		if(sender.equals(SenderType.PLAYER)) {
			Bukkit.dispatchCommand(player, translatedCommand);
		} else {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), translatedCommand);
		}
		
		
	}
	
	public void toggle() {
		if(sender.equals(SenderType.PLAYER)) {
			sender = SenderType.SERVER;
		} else {
			sender = SenderType.PLAYER;
		}
	}
	
	
	public String getCommand() {
		return command;
	}
	public SenderType getSender() {
		return sender;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public void setSender(SenderType sender) {
		this.sender = sender;
	}
	
	
	
}

package me.creatos.voucher.core.wrappers;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.connorlinfoot.titleapi.TitleAPI;

import me.creatos.voucher.VoucherPlugin;
import me.creatos.voucher.core.Voucher;
import me.creatos.voucher.core.enums.SoundPlayer;
import me.creatos.voucher.utils.Message;

public class VoucherTitle {

	private String title;
	private String subtitle;

	private int fadeIn;
	private int stay;
	private int fadeOut;

	private int delay;

	public VoucherTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut, int delay) {
		this.title = title;
		this.subtitle = subtitle;
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
		this.setDelay(delay);
	}

	public void send(Player player, Voucher voucher, VoucherPlugin plugin) {
		String translatdTitle = Message.translate(title, voucher, player.getName());
		String translatdSubtitle = Message.translate(subtitle, voucher, player.getName());
		if (delay == 0) {

			TitleAPI.sendTitle(player, fadeIn, stay, fadeOut, translatdTitle, translatdSubtitle);

			return;
		}

		new BukkitRunnable() {

			@Override
			public void run() {
				
				TitleAPI.sendTitle(player, fadeIn, stay, fadeOut, translatdTitle, translatdSubtitle);

			}
		}.runTaskLater(plugin, delay);

	}

	public String getTitle() {
		return title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public int getFadeIn() {
		return fadeIn;
	}

	public int getStay() {
		return stay;
	}

	public int getFadeOut() {
		return fadeOut;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public void setFadeIn(int fadeIn) {
		this.fadeIn = fadeIn;
	}

	public void setStay(int stay) {
		this.stay = stay;
	}

	public void setFadeOut(int fadeOut) {
		this.fadeOut = fadeOut;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

}

package me.creatos.voucher.core.wrappers;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.creatos.voucher.VoucherPlugin;
import me.creatos.voucher.core.enums.SoundPlayer;

public class VoucherSound {

	private Sound sound;
	private float pitch;
	private float volume;
	private int delay;
	private SoundPlayer soundPlayer;
	
	public VoucherSound(Sound sound, float pitch, float volume, int delay, SoundPlayer soundPlayer) {
		this.sound = sound;
		this.pitch = pitch;
		this.volume = volume;
		this.delay = delay;
		this.soundPlayer = soundPlayer;
	}
	
	public VoucherSound(Sound sound) {
		this(sound, 1, 1, 0, SoundPlayer.PLAYER);
	}
	
	
	public void play(Player player, VoucherPlugin plugin) {
		
		if(delay == 0) {
			
			if(soundPlayer.equals(SoundPlayer.PLAYER)) {
				player.playSound(player.getLocation(), sound, volume, pitch);
			} else {
				player.getWorld().playSound(player.getLocation(),  sound, volume, pitch);
			}
			
			return;
		}
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(soundPlayer.equals(SoundPlayer.PLAYER)) {
					player.playSound(player.getLocation(),  sound, volume, pitch);
				} else {
					player.getWorld().playSound(player.getLocation(),  sound, volume, pitch);
				}
				
			}
		}.runTaskLater(plugin, delay);
	}
	
	public Sound getSound() {
		return sound;
	}
	public float getPitch() {
		return pitch;
	}
	public float getVolume() {
		return volume;
	}
	public int getDelay() {
		return delay;
	}
	public void setSound(Sound sound) {
		this.sound = sound;
	}
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	public void setVolume(float volume) {
		this.volume = volume;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}

	public SoundPlayer getSoundPlayer() {
		return soundPlayer;
	}

	public void setSoundPlayer(SoundPlayer soundPlayer) {
		this.soundPlayer = soundPlayer;
	}
	
	public void toggleSoundPlayer() {
		if(soundPlayer.equals(SoundPlayer.PLAYER)) {
			this.soundPlayer = SoundPlayer.WORLD;
		} else {
			this.soundPlayer = SoundPlayer.PLAYER;
		}
	}
	
	
}

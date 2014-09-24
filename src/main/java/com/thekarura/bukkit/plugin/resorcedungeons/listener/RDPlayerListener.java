package com.thekarura.bukkit.plugin.resorcedungeons.listener;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;
import com.thekarura.bukkit.plugin.resorcedungeons.util.MessageFormats;

public class RDPlayerListener implements Listener {
	
	public static final Logger log = ResorceDungeons.log;
	private static final String logPrefix = ResorceDungeons.logPrefix;
	private static final String msgPrefix = ResorceDungeons.msgPrefix;
	
	// instance
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	public RDPlayerListener(ResorceDungeons plugin) {
		this.instance = plugin;
	}
	
	/**
	 * 自分向けのメッセージを送信
	 * @param event PlayerJoinEventから習得
	 */
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerMoveEvent(PlayerJoinEvent event){
			
			MessageFormats format = new MessageFormats(instance);
			
			String auther = instance.getConfigs().getAuther();
			final Player player = event.getPlayer();
			
			final String server_name = instance.getServer().getServerName();
			final String plugin_name = instance.getConfigs().getName();
			
			final String mes = format.MessageFormat("&a \"" + server_name + "\" は " + plugin_name + "を導入しています。", player.getName());
			
			if (player.equals(Bukkit.getPlayer(auther))){
				
				new BukkitRunnable(){
					
					@Override
					public void run(){
						
						player.sendMessage(msgPrefix + mes);
						
					}
					
				}.runTaskLater(instance, 60);
				
			}
		
	}
	
	
	
}

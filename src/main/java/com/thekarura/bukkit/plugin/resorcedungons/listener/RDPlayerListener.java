package com.thekarura.bukkit.plugin.resorcedungons.listener;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.thekarura.bukkit.plugin.resorcedungons.ResorceDungeons;

public class RDPlayerListener implements Listener {
	
	public static final Logger log = ResorceDungeons.log;
	private static final String logPrefix = ResorceDungeons.logPrefix;
	private static final String msgPrefix = ResorceDungeons.msgPrefix;
	
	// instance
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	public RDPlayerListener(ResorceDungeons plugin) {
		this.plugin = plugin;
	}
	
	public ResorceDungeons plugin;
	
	/**
	 * 管理人向けのメッセージを送信
	 * @param event PlayerJoinEventから習得
	 */
	@EventHandler(ignoreCancelled = true)
	public void onPlayerMoveEvent(PlayerJoinEvent event){
			
			String auther = plugin.getConfigs().getAuther();
			Player player = event.getPlayer();
			
			if (player == Bukkit.getPlayer(auther)){
				
				player.sendMessage(msgPrefix+ChatColor.GREEN+Bukkit.getServerName()+"はResorceDungeonsを導入しています。");
				
			}
		
	}
	
	
	
}

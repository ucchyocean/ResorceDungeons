package com.thekarura.bukkit.plugin.resorcedungeons.listener;

import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;
import com.thekarura.bukkit.plugin.resorcedungeons.util.MessageFormats;

public class RDSignListener implements Listener {
	
	public Logger log = ResorceDungeons.log;
	private String logPrefix = ResorceDungeons.logPrefix;
	private String msgPrefix = ResorceDungeons.msgPrefix;
	
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	public RDSignListener(ResorceDungeons resorcedungeons) {
		this.instance = resorcedungeons;
	}
	
	/**
	 * 自動生成部分に使われている看板の記述を防ぎます。
	 * @param event
	 */
	@EventHandler
	public void SignChengeEvent(SignChangeEvent event){
		
		log.info(event.getEventName());
		
		MessageFormats format = new MessageFormats(instance);
		Player player = event.getPlayer();
		String name = player.getName();
		FileConfiguration config = instance.getConfig();
		
		if (event.getLine(0).equalsIgnoreCase("[RDungeons]")){
			
			event.setLine(0, "");
			
			player.sendMessage(msgPrefix + format.MessageFormat(config.getString(
					"message.sign.not_chenge", "&aDo not describe the [RDungeons]!"), name));
			
		}
		
	}
	
}

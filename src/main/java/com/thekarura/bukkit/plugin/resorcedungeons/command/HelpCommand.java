package com.thekarura.bukkit.plugin.resorcedungeons.command;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;
import com.thekarura.bukkit.plugin.resorcedungeons.util.MessageFormats;

public class HelpCommand implements CommandExecutor {
	
	// Prefixを参照
	public static final Logger log = ResorceDungeons.log;
	private static final String logPrefix = ResorceDungeons.logPrefix;
	private static final String msgPrefix = ResorceDungeons.msgPrefix;
	
	// instance
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	public HelpCommand(ResorceDungeons plugin){
		this.instance = plugin;
	}
	
	/**
	 * コマンド(作者、使い方、バージョンを表記)
	 * 
	 * @author karura
	 **/
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		MessageFormats format = new MessageFormats(instance);
		
		String senderName = ChatColor.stripColor(sender.getName());
		
		if (!(sender instanceof Player)) {
			
			senderName = "Console";
			
		} else {
			
			senderName = sender.getName();
			
		}
		
		if (args.length == 0) {
			
			List<String> help_0 = instance.getConfig().getStringList("message.help.0");
			for (String help : help_0){
				sender.sendMessage(msgPrefix + format.MessageFormat(help, senderName));
			}
			
			return true;
			
		}
		if (args[0].equals("1")) {
			
			List<String> help_1 = instance.getConfig().getStringList("message.help.1");
			for (String help : help_1){
				sender.sendMessage(msgPrefix + format.MessageFormat(help, senderName));
			}
			
			return true;
			
		} 
		if (args[0].equals("2")) {
			
			List<String> help_2 = instance.getConfig().getStringList("message.help.2");
			for (String help : help_2){
				sender.sendMessage(msgPrefix + format.MessageFormat(help, senderName));
			}
			
			return true;
				
		} else{
			
			String error = instance.getConfig().getString("message.help.error_args");
			sender.sendMessage(msgPrefix + format.MessageFormat(error , senderName));
		
		}
		
	return false;
		
    }

}

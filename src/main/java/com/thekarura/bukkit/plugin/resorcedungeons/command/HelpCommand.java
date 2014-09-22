package com.thekarura.bukkit.plugin.resorcedungeons.command;

import java.util.List;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
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
		String name = ChatColor.stripColor(sender.getName());
		FileConfiguration config = instance.getConfig();
		
		if (!(sender instanceof Player)) {
			
			name = "Console";
			
		} else {
			
			name = sender.getName();
			
		}
		
		if (args.length == 0) {
			
			if (!config.getString("message.help.0").equals(null)){
				
				List<String> help_0 = config.getStringList("message.help.0");
				for (String help : help_0){
					sender.sendMessage(msgPrefix + format.MessageFormat(help, name));
				}
				
			} else {
				
				sender.sendMessage(msgPrefix + format.MessageFormat("&athis Plugin helps", name));
				sender.sendMessage(msgPrefix + format.MessageFormat("&a******************************", name));
				sender.sendMessage(msgPrefix + format.MessageFormat("&a*     ResorceDungeons", name));
				sender.sendMessage(msgPrefix + format.MessageFormat("&a*     %version%'", name));
				sender.sendMessage(msgPrefix + format.MessageFormat("&a*     Auther : %auther%", name));
				sender.sendMessage(msgPrefix + format.MessageFormat("&a******************************", name));
				sender.sendMessage(msgPrefix + format.MessageFormat("/RDhelp 1", name));
				
				
			}
			
			return true;
			
		}
		if (args[0].equals("1")) {
			
			if (!config.getString("message.help.1").equals(null)){
				
				List<String> help_1 = config.getStringList("message.help.1");
				for (String help : help_1){
					sender.sendMessage(msgPrefix + format.MessageFormat(help, name));
				}
				
			} else {
				
				sender.sendMessage(msgPrefix + format.MessageFormat("&a/BDGenerate [Dungeon]", name));
				sender.sendMessage(msgPrefix + format.MessageFormat("&aGenerating Dungeon. Allow Generate World %dungeon_world% .", name));
				sender.sendMessage(msgPrefix + format.MessageFormat("&aCan not is Undo!", name));
				sender.sendMessage(msgPrefix + format.MessageFormat("&anext /RDHelp 2", name));
				
			}
			
			return true;
			
		} 
		if (args[0].equals("2")) {
			
			if (!config.getString("message.help.2").equals(null)){
				
				List<String> help_2 = config.getStringList("message.help.2");
				for (String help : help_2){
					sender.sendMessage(msgPrefix + format.MessageFormat(help, name));
				}
				
			} else {
				
				sender.sendMessage(msgPrefix + format.MessageFormat("&a/RDReload [option]", name));
				sender.sendMessage(msgPrefix + format.MessageFormat("&aThis Plugin Reload.", name));
				sender.sendMessage(msgPrefix + format.MessageFormat("&a[option] : disable is plugin stoping. Only console.", name));
				sender.sendMessage(msgPrefix + format.MessageFormat("&anext /RDHelp 3", name));
				
				
			}
			
			return true;
				
		} else{
			
			String error = config.getString("message.help.error_args", "&aNo argment.");
			sender.sendMessage(msgPrefix + format.MessageFormat(error , name));
		
		}
		
	return false;
		
    }

}

package com.thekarura.bukkit.plugin.resorcedungons.command;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thekarura.bukkit.plugin.resorcedungons.ResorceDungeons;

/**
 * リロード関連を扱います
 * 
 * @author karura
 */
public class ReloadCommand implements CommandExecutor {
	
	public static final Logger log = ResorceDungeons.log;
	private static final String logPrefix = ResorceDungeons.logPrefix;
	private static final String msgPrefix = ResorceDungeons.msgPrefix;
	
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length == 0){
			
			sender.sendMessage("ReloadもしくはDisableのどちらかを入れてください！");
			return false;
			
		}
		
		if (args[0].equalsIgnoreCase("Reload")){
			
			instance.reloadConfig();
			sender.sendMessage("configファイルをリロードしました。");
			
		}
		
		if (args[0].equalsIgnoreCase("Disable")){
			
			if (!(sender instanceof Player)){
				log.info(logPrefix+"Plguinを停止しました。Reload時に戻ります。");
				instance.getPluginLoader().disablePlugin(instance);
				return true;
			} else {
				sender.sendMessage(msgPrefix+ChatColor.RED+"この引数はコンソール限定です！");
			}
			
		}
		
		return false;
	}

}

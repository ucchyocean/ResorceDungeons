package com.thekarura.bukkit.plugin.resorcedungeons.command;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;
import com.thekarura.bukkit.plugin.resorcedungeons.util.MessageFormats;

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
	
	public ReloadCommand(ResorceDungeons plugin){
		this.instance = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		MessageFormats format = new MessageFormats(instance);
		FileConfiguration config = instance.getConfig();
		
		if (args.length == 0){
			
			sender.sendMessage(format.MessageFormat(msgPrefix + config.getString(
					"message.reload.no_args", "&aNo argment."), sender.getName()));
			return false;
			
		}
		
		if (args[0].equalsIgnoreCase("Reload")){
			
			try {
				
				instance.getConfigs().load();
				sender.sendMessage(format.MessageFormat(config.getString(
						"message.reload.reload", "&aPlugin has reloaded!"), sender.getName()));
				
			} catch (Exception e) {
				
				e.printStackTrace();
				e.getMessage();
				return false;
				
			}
			
		}
		
		if (args[0].equalsIgnoreCase("Disable")){
			
			if (!(sender instanceof Player)){
				
				log.info(logPrefix+"Plguinを停止しました。Reload時に戻ります。");
				instance.getPluginLoader().disablePlugin(instance);
				return true;
				
			} else {
				sender.sendMessage(msgPrefix + format.MessageFormat(config.getString(
						"message.reload.disable_console", "&aConsole Only argment!"), sender.getName()));
			}
			
		}
		
		return false;
	}

}

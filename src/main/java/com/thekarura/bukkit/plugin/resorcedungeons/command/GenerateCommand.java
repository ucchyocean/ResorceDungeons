package com.thekarura.bukkit.plugin.resorcedungeons.command;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;
import com.thekarura.bukkit.plugin.resorcedungeons.manager.DungeonFloating;
import com.thekarura.bukkit.plugin.resorcedungeons.manager.DungeonMossy;
import com.thekarura.bukkit.plugin.resorcedungeons.manager.DungeonTest;
import com.thekarura.bukkit.plugin.resorcedungeons.util.MessageFormats;

public class GenerateCommand implements CommandExecutor {
	
	// Prefix
	public static final Logger log = ResorceDungeons.log;
	private static final String logPrefix = ResorceDungeons.logPrefix;
	private static final String msgPrefix = ResorceDungeons.msgPrefix;
	
	// instance
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		MessageFormats format = new MessageFormats(instance);
		FileConfiguration config = instance.getConfig();
		
		if (!(sender instanceof Player)) {
			
			sender.sendMessage(logPrefix + config.getString("&Console dney command!"));
			return true;
			
		} else {
			
			//Player情報を習得します
			Player player = (Player) sender;
			Location loc = player.getLocation();
			String name = player.getName();
			
			//埋まり防止の為一段下げます。
			loc.setY(loc.getY() - 1);
			
			if ( args.length == 0 ) {
				
				player.sendMessage(msgPrefix + format.MessageFormat(config.getString("message.generator.no_args"), name));
				return true;
				
			} else {
				
				boolean DUNGEON_GENERATE_COMMAND = instance.getConfigs().getDUNGEON_GENERATE_COMMAND();
				
				if (DUNGEON_GENERATE_COMMAND == false || player.getWorld().getName().equals(instance.getConfigs().getDungeonWorld())){
					
					//確認用であり実用性はないです。(安全の為製作者限定にしています)
					//他の鯖では権限によりthe_karuraのIDでも実行出来ないので問題ありません
					//ID変更が可能なoffline-mode環境を封じます
					if ( args[0].equals("Test") ) {
						if ( player == Bukkit.getPlayer(instance.getConfigs().getAuther())){
							//なりすまし対策
							if (Bukkit.getServer().getOnlineMode()){
								new DungeonTest().createDungeonTest(loc);
							} else {
								player.sendMessage(msgPrefix + format.MessageFormat(config.getString(
										"message.generator.test_onlinemode_off", "&aPlease server config is online-mode true!"), name));
							}
							
						} else {
							player.sendMessage(msgPrefix + format.MessageFormat(config.getString(
									"message.generator.test_other_player", "&aYou can not generate dungeon!"), name));
						}
						return true;
					}
					
					if ( args[0].equals("Mossy")){
						
						new DungeonMossy().setDungeonMossy(loc);
						return true;
					}
					
					if ( args[0].equals("Ruin") ){
						
						
						
					}
					
					if ( args[0].equals("Tower")){
						
					}
					
					if ( args[0].equals("Floating")){
						new DungeonFloating().createDungeonFloating(loc);
						return true;
					}
					
					else {
						
						sender.sendMessage(msgPrefix + format.MessageFormat(config.getString(
								"message.generator.error_not_exist_dungeon", "&aNot exist Dungeon name!"), name));
						
					}
					
				} else {
					
					sender.sendMessage(msgPrefix + format.MessageFormat(config.getString(
							"message.generator.error_not_dungeonworld", "&aDo not Dungeon Generate World!"), name));
					
				}
				
			}
			
			return true;
			
		}
		
	}
	
}

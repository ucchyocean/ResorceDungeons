package com.thekarura.bukkit.plugin.resorcedungeons.command;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;
import com.thekarura.bukkit.plugin.resorcedungeons.manager.DungeonFloating;
import com.thekarura.bukkit.plugin.resorcedungeons.manager.DungeonMossy;
import com.thekarura.bukkit.plugin.resorcedungeons.manager.DungeonTest;

public class GenerateCommand implements CommandExecutor {
	
	// Prefix
	public static final Logger log = ResorceDungeons.log;
	private static final String logPrefix = ResorceDungeons.logPrefix;
	private static final String msgPrefix = ResorceDungeons.msgPrefix;
	
	// instance
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		if (!(sender instanceof Player)) {
			
			sender.sendMessage(logPrefix + "このコマンドはコンソールでの実行は出来ません");
			return true;
			
		} else {
			
			//Player情報を習得します
			Player player = (Player) sender;
			Location loc = player.getLocation();
			loc.setY(loc.getY() - 1);
			
			if ( args.length == 0 ) {
				
				player.sendMessage(msgPrefix + ChatColor.GREEN + "ダンジョン名を入力してください");
				player.sendMessage(msgPrefix + ChatColor.GREEN + "/RDGenerate [dungeon]");
				return true;
				
			} else {
				
				if (player.getWorld().getName().equals(instance.getConfigs().getDungeonWorld())){
					
					//確認用であり実用性はないです。(安全の為製作者限定にしています)
					//他の鯖では権限によりthe_karuraのIDでも実行出来ないので問題ありません
					if ( args[0].equals("Test") ) {
						if ( player.getName().equals("the_karura") ){
							new DungeonTest().createDungeonTest(loc);
						} else {
							player.sendMessage("");
						}
						return true;
					}
					
					if ( args[0].equals("Mossy")){
						new DungeonMossy().setDungeonMossy(loc);
						return true;
					}
					
					if ( args[0].equals("Tower")){
						
					}
					
					if ( args[0].equals("Floating")){
						new DungeonFloating().createDungeonFloating(loc);
						return true;
					}
					
					else {
						
						sender.sendMessage(msgPrefix+ChatColor.GREEN+"登録されたダンジョンではありません！");
						
					}
					
				} else {
					
					sender.sendMessage(msgPrefix+ChatColor.GREEN+"ダンジョン生成が可能なワールドではありません！configファイルを確認してください");
					
				}
				
			}
			
			return true;
			
		}
		
	}
	
}

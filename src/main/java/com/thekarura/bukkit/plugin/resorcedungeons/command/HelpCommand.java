package com.thekarura.bukkit.plugin.resorcedungeons.command;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;

public class HelpCommand implements CommandExecutor {
	
	// Prefixを参照
	public static final Logger log = ResorceDungeons.log;
	private static final String logPrefix = ResorceDungeons.logPrefix;
	private static final String msgPrefix = ResorceDungeons.msgPrefix;
	
	// instance
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	/**
	 * コマンド
	 * 
	 * getCommand("RDHelp").setExecutor(new HelpCommand());
	 **/
	
	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		
		String senderName = ChatColor.stripColor(sender.getName());
		
		//ConfigrationManagerからの読み込み
		String auther = instance.getConfigs().getAuther();
		String version = instance.getConfigs().getVersion();
		String worldname = instance.getConfigs().getDungeonWorld();
		
		if (!(sender instanceof Player)) {
			
			senderName = "Console";
			
		} else {
			
			if (args.length == 0) {
				
				log.info(logPrefix + ChatColor.GREEN + "plugin helps.");
				sender.sendMessage(msgPrefix + ChatColor.GREEN + "plugin helps.");
				sender.sendMessage(ChatColor.GREEN +"******************************");
				sender.sendMessage(ChatColor.GREEN +"*     ResorceDungeons");
				sender.sendMessage(ChatColor.GREEN +"*     " + version);
				sender.sendMessage(ChatColor.GREEN +"*     Auther :" + auther);
				sender.sendMessage(ChatColor.GREEN +"******************************");
				sender.sendMessage(ChatColor.BLUE + "/RDhelp 1");
				
				return true;
				
			}
			if (args[0].equals("1")) {
				
				log.info(logPrefix + ChatColor.GREEN + "help " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " page");
				sender.sendMessage(msgPrefix + ChatColor.GREEN + "help " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " page");
				sender.sendMessage("");
				sender.sendMessage(ChatColor.GREEN + "/BDGenerate [Dungeon]");
				sender.sendMessage(ChatColor.GREEN + "ダンジョンを生成します。登録されている[Dungeon]を指定してください。");
				sender.sendMessage(ChatColor.RED + "※一度生成すると取り消す事は出来ません！");
				sender.sendMessage(ChatColor.RED + "※\""+worldname+"\"のワールドのみ実行可能です。");
				sender.sendMessage(ChatColor.BLUE + "/RDhelp 2");
				
				return true;
				
			} 
			if (args[0].equals("2")) {
				
				log.info(logPrefix + ChatColor.GREEN + "help " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " page");
				sender.sendMessage(msgPrefix + ChatColor.GREEN + "help " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " page");
				sender.sendMessage(ChatColor.GREEN + "/RDReload");
				sender.sendMessage(ChatColor.GREEN + "設定をリロードします。");
				sender.sendMessage(ChatColor.BLUE + "/RDhelp 3");
				
				return true;
				
			}
			if (args[0].equals("3")) {
				
				log.info(logPrefix + ChatColor.GREEN + "help " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " page");
				sender.sendMessage(msgPrefix + ChatColor.GREEN + "help " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " page");
				sender.sendMessage(ChatColor.GREEN + "まだ");
				sender.sendMessage(ChatColor.BLUE + "/RDhelp");
				
				return true;
				
			} else{
				
				log.info(logPrefix + ChatColor.GREEN + "引数が違います！");
				sender.sendMessage(msgPrefix + ChatColor.GREEN + "引数が違います！");
			
			}
				
		}
	return false;
		
    }

}

package com.thekarura.bukkit.plugin.resorcedungeons.util;

import org.bukkit.ChatColor;

import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;

/**
 * メッセージの色付けなどをします。
 * 
 * @author karura
 */
public class MessageFormats {
	
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	//内部設定の習得
	private String auther = instance.getConfigs().getAuther();
	private String version = instance.getConfigs().getVersion();
	private String name = instance.getConfigs().getName();
	
	//コンフィグファイルから習得
	private String dungeon_name = instance.getConfig().getString("DUNGEON_GENERATE_WORLD");
	
	public MessageFormats(ResorceDungeons plugin){
		this.instance = plugin;
		this.auther = 		instance.getConfigs().getAuther();
		this.version = 		instance.getConfigs().getVersion();
		this.name = 		instance.getConfigs().getName();
		this.dungeon_name = instance.getConfig().getString("DUNGEON_GENERATE_WORLD");
	}
	
	/**
	 * 特定の文字列に反応し自動的に置換します。
	 * ダンジョン生成関連のコマンドです。
	 * @param mes
	 * @param dungeon
	 * @return
	 */
	public String MessageFormat(String mes, String player){
		
		if (check(auther))			{ mes = mes.replace("%auther%", auther);}
		if (check(version))			{ mes = mes.replace("%version%", version);}
		if (check(name))			{ mes = mes.replace("%name%", name);}
		if (check(dungeon_name))	{ mes = mes.replace("%dungeon_world%", dungeon_name); }
		if (check(player))			{ mes = mes.replace("%player_name%", player);}
		
		return MessageColor(mes);
	}
	
	/**
	 * &によりカラー制御をします。
	 * @param mes 文
	 * @return
	 */
	private static String MessageColor(String mes){
		return ChatColor.translateAlternateColorCodes('&', mes);
	}
	
	/**
	 * 対象のオブジェクトがあるかないかを確認
	 * @param check
	 * @return
	 */
	private boolean check(Object check){
		if (check != null){
			return true;
		}
		return false;
	}
	
}

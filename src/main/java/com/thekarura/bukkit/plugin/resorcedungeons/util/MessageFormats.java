package com.thekarura.bukkit.plugin.resorcedungeons.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * メッセージの色付けなどをします。
 * 
 * @author karura
 */
public class MessageFormats {
	
	/**
	 * 特定の文字列に反応し自動的に置換します。
	 * 第二引数からはnullを入れても問題ありません。
	 * @param mes
	 * @param dungeon
	 * @return
	 */
	public static String MessageFormat(String mes, String dungeon, Player player){
		
		if (check(dungeon)){ mes = mes.replace("%dungeon_name%", dungeon); }
		if (check(player)){ mes = mes.replace("%player_name%", player.getName());}
		
		return MessageColor(mes);
	}
	
	/**
	 * &によりカラー制御をします。
	 * @param mes 文
	 * @return
	 */
	public static String MessageColor(String mes){
		return ChatColor.translateAlternateColorCodes('&', mes);
	}
	
	/**
	 * 対象のオブジェクトがあるかないか確認
	 * @param check
	 * @return
	 */
	private static boolean check(Object check){
		if (!check.equals(null)){
			return true;
		}
		return false;
	}
	
}

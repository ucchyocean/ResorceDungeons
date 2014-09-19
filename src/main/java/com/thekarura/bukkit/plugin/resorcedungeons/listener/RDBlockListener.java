package com.thekarura.bukkit.plugin.resorcedungeons.listener;

import java.util.logging.Logger;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.thekarura.bukkit.plugin.resorcedungeons.PlayerMoveCheckTask;
import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;

/**
 * ResorceDungeonsのブロック関連を扱う部分です。
 *
 * @author karura
 */
public class RDBlockListener implements Listener {
	
	// ++ Logger ++ //
	public static final Logger log = ResorceDungeons.log;
	private static final String logPrefix = ResorceDungeons.logPrefix;
	private static final String msgPrefix = ResorceDungeons.msgPrefix;
	
	// ++ Instance ++ //
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	// ++ Constructor ++ //
	public RDBlockListener(final ResorceDungeons plugin) {
		this.instance = plugin;
	}
	
	/**
	 * プレイヤーがサーバーに参加したときに発生するイベント
	 * @param event
	 */
	@EventHandler(ignoreCancelled=true)
	public void onPlayerJoin(PlayerJoinEvent event) {
		
		// 参加したプレイヤーに、1秒毎に動作する非同期処理タイマーを仕掛ける。
		PlayerMoveCheckTask task = new PlayerMoveCheckTask(event.getPlayer());
		
		// タスクを非同期で1秒ごとに実行する
		task.runTaskTimerAsynchronously(ResorceDungeons.getInstance(), 20, 20);
	}
}

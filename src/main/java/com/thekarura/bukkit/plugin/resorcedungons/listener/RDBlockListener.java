package com.thekarura.bukkit.plugin.resorcedungons.listener;

import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.thekarura.bukkit.plugin.resorcedungons.ResorceDungeons;
import com.thekarura.bukkit.plugin.resorcedungons.manager.DungeonMossy;

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
	 * 一定範囲内にプレイヤーが入ると反応する
	 * トリガーブロック
	 * @param event PlayerMoveEvent
	 */
	@EventHandler(ignoreCancelled = true)
	public void onPlayerMove(PlayerMoveEvent event){
		
		final Player player = event.getPlayer();
		
		//ダンジョンワールドのみ有効化
		if (player.getWorld().getName().equals(instance.getConfigs().getDungeonWorld())){
			
			//デバッグ中なのでこれは隠おきましょう
			//クリエイティブモードであれば実行させない
			//if (player.getGameMode().equals(GameMode.CREATIVE)){ return; }
			
			//感知範囲(コンフィグで調整予定)
			int radius = 50;
			
			//プレイヤーの位置を検出します
			World world = player.getWorld();
			int x = player.getLocation().getBlockX();
			int z = player.getLocation().getBlockZ();
			int y = player.getLocation().getBlockY();
			
			//ループ処理で特定のブロックを探します
			for (int i = x - radius ; i < x + radius ; i++ ){
				for (int j = z - radius ; j < z + radius ; j++ ){
					for (int y_ =  y - radius ; y_ < y + radius ; y_++ ){
						
						/* コマンドブロックの上に立っている看板を探します
						 * 図式
						 * 
						 * [POST]
						 * [COMB]
						 * 
						 */
						if(world.getBlockAt(i, y, j).getType() == Material.COMMAND
						&& world.getBlockAt(i, y + 1, j).getType() == Material.SIGN_POST){
							
							//看板の情報を上記のSIGN_POSTに設定します。
							Sign sign = (Sign) world.getBlockAt(i, y + 1, j).getState();
							
							//生成位置を決めるため位置情報を登録します。
							Location loc = new Location(world, i, y, j);
							
							//[RDungeons]と書かれている場合の条件
							if(sign.getLine(0).equals("[RDungeons]")){
								
								//MossyDungeonsを生成します
								if (sign.getLine(1).equals("Mossy")){
									
									remove(world, i, y, j);
									new DungeonMossy().setDungeonMossy(loc);
									
								}
								
							}
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * 看板削除用メソッド
	 * @param w
	 * @param i
	 * @param y
	 * @param j
	 */
	private void remove(World w ,int i ,int y ,int j){
		for (int y_ = 2 ; y_ != -1 ; y_-- ){
			w.getBlockAt(i, y + y_, j).setType(Material.AIR);
		}
	}
	
}

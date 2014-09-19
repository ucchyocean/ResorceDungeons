package com.thekarura.bukkit.plugin.resorcedungeons;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.thekarura.bukkit.plugin.resorcedungeons.ResorceDungeons;
import com.thekarura.bukkit.plugin.resorcedungeons.manager.DungeonMossy;

/**
 * プレイヤーの移動を1秒毎にチェックするタスク処理クラス
 */
public class PlayerMoveCheckTask extends BukkitRunnable {
	
	// パフォーマンスチェック用のデバッグフラグ
	// trueにすると、コンソールにパフォーマンス情報を出力します
	private static final boolean DEBUG = true;
	
	// 感知範囲(コンフィグで調整予定)
	private static final int radius = 50;
	
	// 追跡対象のプレイヤー
	private Player player;
	
	// 1秒前にプレイヤーが居た位置 の保存用変数
	private Location prev;
	
	/**
	 * コンストラクタ
	 * @param player 追跡対象のプレイヤー
	 */
	public PlayerMoveCheckTask(Player player) {
		this.player = player;
	}
	
	/**
	 * タスク処理が実行されるときに呼び出されるメソッド
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		// 処理の開始時間の記録（デバッグ用）
		long startTime = System.currentTimeMillis();
		
		// もしプレイヤーがサーバーから抜けたなら、このタイマーを終了する
		if ( !player.isOnline() ) {
			cancel();
			return;
		}
		
		// プレイヤーがチェック対象ワールドにいない、または死亡中なら、処理をしない
		String dungeonWorldName = ResorceDungeons.getInstance().getConfigs().getDungeonWorld();
		if ( !player.getWorld().getName().equals(dungeonWorldName) || player.isDead() ) {
			prev = null;
			return;
		}
		
		Location loc = player.getLocation();
		int count = 0;
		if ( prev != null ) {
			// 1秒前の位置キャッシュがある場合
			
			// 1秒前と完全に同じ位置なら、チェックをしない
			if ( loc.getBlockX() == prev.getBlockX() &&
					loc.getBlockY() == prev.getBlockY() &&
					loc.getBlockZ() == prev.getBlockZ() ) {
				prev = loc;
				return;
			}
			
			Location[] minmax = makeMinMaxLocations(loc, prev);
			Location min = minmax[0];
			Location max = minmax[1];
			
			// x方向に移動した場合、"移動した分のブロックだけ"をチェックする
			if ( loc.getBlockX() != prev.getBlockX() ) {
				// min{x, y-50, z-50} から max{x, y+50, z+50} の範囲をサーチ
				count += search(min.clone().add(0, -radius, -radius), max.clone().add(0, radius, radius));
			}
			// y方向に移動した場合も同様。
			if ( loc.getBlockY() != prev.getBlockY() ) {
				// min{x-50, y, z-50} から max{x+50, y, z+50} の範囲をサーチ
				count += search(min.clone().add(-radius, 0, -radius), max.clone().add(radius, 0, radius));
			}
			// z方向に移動した場合も同様。
			if ( loc.getBlockZ() != prev.getBlockZ() ) {
				// min{x-50, y-50, z} から max{x+50, y+50, z} の範囲をサーチ
				count += search(min.clone().add(-radius, -radius, 0), max.clone().add(radius, radius, 0));
			}
			
		} else {
			// 1秒前の位置キャッシュが無い場合、しかたがないので全範囲をサーチする
			Location min = loc.clone().add(-radius, -radius, -radius);
			Location max = loc.clone().add(radius, radius, radius);
			count += search(min, max);
			
		}
		
		// 今回のサーチした位置を保存しておく
		prev = loc;
		
		// デバッグがオンになっている場合は、デバッグ情報をコンソールに出力する
		if ( DEBUG ) {
			long time = System.currentTimeMillis() - startTime;
			System.out.println(String.format("PlayerMoveCheckTask{%s}, %d ms, %d block checked.",
					player.getName(), time, count));
		}
	}
	
	/**
	 * 2つの地点から、(x, y, z) それぞれの最小地点と最大地点を作成して返します。<br>
	 * 例）loc1{3, 80, 100}, loc2{10, 70, 90} ==> min{3, 70, 90}, max{10, 80, 100}
	 * @param loc1 地点1
	 * @param loc2 地点2
	 * @return {min, max}の配列
	 */
	private Location[] makeMinMaxLocations(Location loc1, Location loc2) {
		
		int min_x, min_y, min_z, max_x, max_y, max_z;
		if ( loc1.getBlockX() < loc2.getBlockX() ) {
			min_x = loc1.getBlockX();
			max_x = loc2.getBlockX();
		} else {
			min_x = loc2.getBlockX();
			max_x = loc1.getBlockX();
		}
		if ( loc1.getBlockY() < loc2.getBlockY() ) {
			min_y = loc1.getBlockY();
			max_y = loc2.getBlockY();
		} else {
			min_y = loc2.getBlockY();
			max_y = loc1.getBlockY();
		}
		if ( loc1.getBlockZ() < loc2.getBlockZ() ) {
			min_z = loc1.getBlockZ();
			max_z = loc2.getBlockZ();
		} else {
			min_z = loc2.getBlockZ();
			max_z = loc1.getBlockZ();
		}
		Location min = new Location(loc1.getWorld(), min_x, min_y, min_z);
		Location max = new Location(loc1.getWorld(), max_x, max_y, max_z);
		return new Location[]{min, max};
	}

	/**
	 * minの位置からmaxの位置まで、コマンドブロックの上に立っている看板を探し、
	 * 対象物が見つかったら削除して、ダンジョンを生成します。
	 * @param min サーチ開始位置
	 * @param max サーチ終了位置
	 * @return サーチしたブロックの個数
	 */
	private int search(Location min, Location max) {
		
		//ループ処理で特定のブロックを探します
		World world = min.getWorld();
		int count = 0;
		for (int x = min.getBlockX(); x < max.getBlockX(); x++ ){
			for (int y = min.getBlockY(); y < max.getBlockY(); y++ ){
				for (int z = min.getBlockZ(); z < max.getBlockZ(); z++ ){
					count++;
					
					/* コマンドブロックの上に立っている看板を探します
					 * 図式
					 *
					 * [POST]
					 * [COMB]
					 *
					 */
					if(world.getBlockAt(x, y, z).getType() == Material.COMMAND
					&& world.getBlockAt(x, y+1, z).getType() == Material.SIGN_POST){
						
						//看板の情報を上記のSIGN_POSTに設定します。
						Sign sign = (Sign) world.getBlockAt(x, y+1, z).getState();
						
						//生成位置を決めるため位置情報を登録します。
						Location loc = new Location(world, x, y, z);
						
						//[RDungeons]と書かれている場合の条件
						if(sign.getLine(0).equals("[RDungeons]")){
							
							//MossyDungeonsを生成します
							if (sign.getLine(1).equals("Mossy")){
								
								remove(world, x, y, z);
								new DungeonMossy().setDungeonMossy(loc);
							}
						}
					}
				}
			}
		}

		return count;
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

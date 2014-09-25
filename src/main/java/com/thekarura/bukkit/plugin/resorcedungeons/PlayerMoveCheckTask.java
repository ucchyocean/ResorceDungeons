package com.thekarura.bukkit.plugin.resorcedungeons;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.thekarura.bukkit.plugin.resorcedungeons.manager.DungeonMossy;
import com.thekarura.bukkit.plugin.resorcedungeons.manager.DungeonRuins;

/**
 * プレイヤーの移動を1秒毎にチェックするタスク処理クラス
 * @author ucchyocean
 * thanks for developing! by.the_karura
 */
public class PlayerMoveCheckTask extends BukkitRunnable {
	
	// パフォーマンスチェック用のデバッグフラグ
	// trueにすると、コンソールにパフォーマンス情報を出力します
	private static final boolean DEBUG = false;
	
	// 感知範囲
	private int radius;
	
	// 追跡対象のプレイヤー
	private Player player;
	
	// 1秒前にプレイヤーが居た位置 の保存用変数
	private Location prev;
	
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	/**
	 * コンストラクタ
	 * @param player 追跡対象のプレイヤー
	 */
	public PlayerMoveCheckTask(ResorceDungeons resorcedungeons, Player player) {
		this.instance = resorcedungeons;
		this.player = player;
		radius = instance.getConfigs().getDUNGEON_GENERATE_TRIGGER_RADIUS();
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
		
		ArrayList<Location> dungeonLocs = new ArrayList<Location>();
		Location loc = player.getLocation();
		@SuppressWarnings("unused")
		int count = 0;
		if ( prev != null && prev.distanceSquared(loc) < radius * radius ) {
			// 1秒前の位置キャッシュがあり、移動距離がradiusの範囲内である（テレポートしていない）場合
			
			// 1秒前と完全に同じ位置なら、チェックをしない
			if ( loc.getBlockX() == prev.getBlockX() &&
					loc.getBlockY() == prev.getBlockY() &&
					loc.getBlockZ() == prev.getBlockZ() ) {
				prev = loc;
				return;
			}
			
			// 移動した差分のみをチェックする。例えば、radius = 60 の時に、
			// (0, 70, 0) から (3, 70, 0) に移動したなら、（x座標プラス方向への移動）
			// 1秒前に既に (-60, 10, -60) から (60, 130, 60) の範囲を既に調査済みなのだから、
			// (61, 10, -60) から (63, 130, 60) の範囲だけを調査する。
			// (0, 70, 0) から (-3, 70, 0) に移動したなら、（x座標マイナス方向への移動）
			// (-63, 10, -60) から (-61, 130, 60) の範囲だけを調査する。
			// (0, 70, 0) から (3, 65, 0) に移動したなら、（x座標プラス方向とy座標マイナス方向への移動）
			// (61, 5, -60) x (63, 130, 60) の範囲と、(-60, 5, -60) x (63, 9, 60) の範囲を調査する。
			
			Location[] minmax = makeMinMaxLocations(prev, loc);
			Location min = minmax[0].add(-radius, -radius, -radius);
			Location max = minmax[1].add(radius, radius, radius);
			
			// x方向に移動した場合、"移動した分のブロックだけ"をチェックする
			if ( loc.getBlockX() > prev.getBlockX() ) {
				count += search(loc.getWorld(),
						prev.getBlockX()+radius+1, min.getBlockY(), min.getBlockZ(),
						loc.getBlockX()+radius,    max.getBlockY(), min.getBlockZ(),
						dungeonLocs);
			} else if ( loc.getBlockX() < prev.getBlockX() ) {
				count += search(loc.getWorld(),
						loc.getBlockX()-radius,    min.getBlockY(), min.getBlockZ(),
						prev.getBlockX()-radius-1, max.getBlockY(), min.getBlockZ(),
						dungeonLocs);
			}
			// y方向、z方向に移動した場合も同様。
			if ( loc.getBlockY() > prev.getBlockY() ) {
				count += search(loc.getWorld(),
						min.getBlockX(), prev.getBlockY()+radius+1, min.getBlockZ(),
						max.getBlockX(), loc.getBlockY()+radius,    min.getBlockZ(),
						dungeonLocs);
			} else if ( loc.getBlockY() < prev.getBlockY() ) {
				count += search(loc.getWorld(),
						min.getBlockX(), loc.getBlockY()-radius,    min.getBlockZ(),
						max.getBlockX(), prev.getBlockY()-radius-1, min.getBlockZ(),
						dungeonLocs);
			}
			if ( loc.getBlockZ() > prev.getBlockZ() ) {
				count += search(loc.getWorld(),
						min.getBlockX(), min.getBlockY(), prev.getBlockZ()+radius+1,
						max.getBlockX(), max.getBlockY(), loc.getBlockZ()+radius,
						dungeonLocs);
			} else if ( loc.getBlockZ() < prev.getBlockZ() ) {
				count += search(loc.getWorld(),
						min.getBlockX(), min.getBlockY(), loc.getBlockZ()-radius,
						max.getBlockX(), max.getBlockY(), prev.getBlockZ()-radius-1,
						dungeonLocs);
			}
			
		} else {
			// 1秒前の位置キャッシュが無い場合、しかたがないので全範囲をサーチする
			count += search(loc.getWorld(),
					loc.getBlockX()-radius, loc.getBlockY()-radius, loc.getBlockZ()-radius,
					loc.getBlockX()+radius, loc.getBlockY()+radius, loc.getBlockZ()+radius,
					dungeonLocs);
			
		}
		
		// ダンジョン生成位置が見つかった場合、ダンジョンを生成する
		if ( dungeonLocs.size() >= 1 ) {
			setDungeons(dungeonLocs);
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
	 * @param dungeonLocs 見つかったダンジョン生成位置を返すための配列
	 * @return サーチしたブロックの個数
	 */
	private int search(World world, int min_x, int min_y, int min_z,
			int max_x, int max_y, int max_z, ArrayList<Location> dungeonLocs) {
		
		//ループ処理で特定のブロックを探します
		if ( min_y < 0 ) {
			min_y = 0;
		}
		if ( max_y > world.getMaxHeight() ) {
			max_y = world.getMaxHeight();
		}
		int count = 0;
		for (int x = min_x; x < max_x; x++ ){
			for (int y = min_y; y < max_y; y++ ){
				for (int z = min_z; z < max_z; z++ ){
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
							
							//ダンジョン生成の指示であることを確認
							if (sign.getLine(1).equals("Dungeon")){
								
								// 生成位置を保存する
								dungeonLocs.add(loc);
							}
						}
					}
				}
			}
		}
		
		return count;
	}
	
	/**
	 * ダンジョンを同期処理で生成します。
	 * @param locations
	 */
	private void setDungeons(final ArrayList<Location> locations) {
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				
				for ( Location loc : locations ) {
					
					// 既にダンジョン生成済みになっている可能性があるので、
					// ブロックを再チェックする
					if(loc.getBlock().getType() == Material.COMMAND
					&& loc.getBlock().getRelative(BlockFace.UP).getType() == Material.SIGN_POST){
						
						// 看板のBlockStateを取得する
						Sign sign = (Sign)loc.getBlock().getRelative(BlockFace.UP).getState();
						
						// ダンジョンIDを検索
						switch (sign.getLine(2)){
						
						//MossyDungeon
						case "Mossy":
							setMossyDungeon(loc);
						break;
						
						//Ruins
						case "Ruin":
							setRuinDungeon(loc);
						break;
						
						}
					}
				}
			}
		}.runTask(instance);
	}
	
	/**
	 * MossyDungeonを生成します。
	 * @param loc 生成場所
	 */
	private void setMossyDungeon(Location loc) {
		
		// 看板とコマンドブロックを削除
		loc.getBlock().getRelative(BlockFace.UP).setType(Material.AIR);
		loc.getBlock().setType(Material.AIR);
		
		// ダンジョン生成
		new DungeonMossy(instance).setDungeonMossy(loc);
	}
	
	/**
	 * RuinDungeonを生成します。
	 * @param loc 生成場所
	 */
	private void setRuinDungeon(Location loc) {
		
		// 看板とコマンドブロックを削除
		loc.getBlock().getRelative(BlockFace.UP).setType(Material.AIR);
		loc.getBlock().setType(Material.AIR);
		
		// ダンジョン生成
		new DungeonRuins(instance).setDungeonRuins(loc);
	}
}

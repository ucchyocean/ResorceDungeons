package com.thekarura.bukkit.plugin.resorcedungons.listener;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import com.thekarura.bukkit.plugin.resorcedungons.ResorceDungeons;

public class RDEntityListener implements Listener {
	
	private ResorceDungeons instance = ResorceDungeons.getInstance();
	
	public RDEntityListener(final ResorceDungeons resorcedungeons) {
		this.instance = resorcedungeons;
	}
	
	//特殊スポーンレート
	@EventHandler(ignoreCancelled = true)
	public void JungleCaveEntityChenge(CreatureSpawnEvent event){
		
		//ダンジョンワールドのみ有効化
		if (event.getEntity().getWorld().equals(instance.getConfigs().getDungeonWorld())){
			
			//自然湧きのみ固定
			if (event.getSpawnReason().equals(SpawnReason.NATURAL)){
				
				//バイオーム指定
				switch (event.getLocation().getBlock().getBiome()){
				
				//ジャングルのみ
				case JUNGLE:
				case JUNGLE_HILLS:
					
					//EntityType検索
					switch (event.getEntityType()){
					
					//毒グモへ置換するモブ
					case CREEPER:
					case ENDERMAN:
					case SLIME:
						
						event.setCancelled(true);
						
						//群生設定
						for (int limit = 0; limit < 3 ; limit++){
							event.getLocation().getWorld().spawnEntity(event.getLocation(), EntityType.CAVE_SPIDER);
						}
						
					break;
					//ゾンビのレートを蜘蛛に変更
					case ZOMBIE:
						
						event.setCancelled(true);
						event.getLocation().getWorld().spawnEntity(event.getLocation(), EntityType.SPIDER);
						
					break;
					
					//動物の湧を一部消します。
					case COW:
					case SHEEP:
					case CHICKEN:
					case PIG:
						
						event.setCancelled(true);
						
					default:break;
					}
					
				break;
				default:break;
				
				}
				
			}
			
		}
		
	}
	
}

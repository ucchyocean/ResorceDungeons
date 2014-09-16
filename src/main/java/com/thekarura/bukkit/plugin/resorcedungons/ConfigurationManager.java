package com.thekarura.bukkit.plugin.resorcedungons;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigurationManager {
	
	/* 
	 * Configration一覧にある設定はconfig.ymlの代理扱いです
	 * 追加の度に変更するのが大変なので
	 */
	
	// Prefixの参照 //
	public static final Logger log = ResorceDungeons.log;
	private static final String logPrefix = ResorceDungeons.logPrefix;
	private static final String msgPrefix = ResorceDungeons.msgPrefix;
	
	// ** 階層 ** //
	private static File pluginDir = new File("plugins", "ResorceDungeons");
	
	// ** 内部設定 ** //
	private String auther = "the_karura";
	private String version = "1.6.4_0001";
	private String name = "ResorceDungeons";
	
	// ** ファイル ** //
	//ダンジョンディレクトリー
	private String plugindir = "plugins/Dungeons/";
	private String dungeondir = plugindir+"dungeons/";
	//チェストディレクトリー
	private String CHEST_FILE = plugindir+"chests/";
	//Mobsディレクトリー
	private String MOBS_FILE = plugindir+"mobs/";
	//spawnerディレクトリー
	private String MOBSPAWNER_FILE = plugindir+"spawner/";
	
	private JavaPlugin plugin;
	private FileConfiguration confing;
	
	// ** 初期設定 ** //
	
	//初期設定項目
	private boolean ENABLE_PLUGIN = new Boolean(true); //pluginを有効化させるかどうか
	private String DUNGEON_GENERATE_WORLD = "dungeon"; //ダンジョン自動生成がされるワールド
	private boolean DUNGEON_GENERATE_COMMAND = new Boolean(true); //コマンド実行に上を適応させるか
	
	//ダンジョン生成
	private boolean ENABLE_DUNGEON_MOSSY = new Boolean(true);		//MossyDungeonの自動生成を許可
	private boolean ENABLE_DUNGEON_STRONGHORLD = new Boolean(true);	//
	private boolean ENABLE_DUNGEON_VILLAGE = new Boolean(true);		//
	
	//ダンジョン個別設定
	//MossyDungeon
	private int DUNGEON_MOSSY_DISTRCT_MIN = 3;	//生成する部屋数(削除予定)
	private int DUNGEON_MOSSY_DISTRCT_MAX = 3;	//生成する部屋数(削除予定)
	
	private int DUNGEON_GENERATE_DISTANCE = new Integer(100);
	private int DUNGEON_GENERATE_SPAWND_ISTANCE = new Integer(50);
	
	// ** コンストラクター ** //
	
	public ConfigurationManager(ResorceDungeons plugin) {
		this.plugin = plugin ;
		pluginDir = this.plugin.getDataFolder();
	}
	
	public void save() throws Exception{
		log.info(logPrefix + "is Config save");
		plugin.saveConfig();
	}
	
	public void load() throws Exception{
		
		log.info(logPrefix + "is File load");
		createDirs();
		
		createDir(new File(this.getPlugindir()));
		createDir(new File(this.getDungeondir()));
		createDir(new File(this.getChestfile()));
		createDir(new File(this.getMobsfile()));
		createDir(new File(this.getMobsfile()));
		
		plugin.reloadConfig();
		
		log.info(logPrefix + "is Config load");
		plugin.reloadConfig();
		
		File file = new File(pluginDir, "config.yml");
		System.out.println(file);
		
	}
	
	private void createDirs() {
		createDir(plugin.getDataFolder());
	}


	private static void createDir(File dir) {
		// 既に存在すれば作らない
		if (dir.isDirectory()) { return; }
		if (!dir.mkdir()) { log.warning(logPrefix + "Can't create directory: " + dir.getName()); }
	}

	// ** ゲッター **
	
	public String getAuther(){
		return this.auther;
	}
	
	public String getVersion(){
		return this.version;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getPlugindir(){
		return this.plugindir;
	}
	
	public String getDungeondir(){
		return this.dungeondir;
	}
	
	public String getChestfile() {
		return this.CHEST_FILE;
	}
	
	public String getMobsfile() {
		return this.MOBS_FILE;
	}
	
	public String getSpawnerFile() {
		return this.MOBSPAWNER_FILE;
	}
	
	public boolean getEnablePlugin(){
		return this.ENABLE_PLUGIN;
	}
	
	public String getDungeonWorld(){
		return this.DUNGEON_GENERATE_WORLD;
	}
	
	public boolean getEnableDungeon_Mossy(){
		return this.ENABLE_DUNGEON_MOSSY;
	}
	
	public boolean getEnableDungeon_Stronghold(){
		return this.ENABLE_DUNGEON_STRONGHORLD;
	}
	
	public boolean getEnableDungeon_Village(){
		return this.ENABLE_DUNGEON_VILLAGE;
	}
	
}

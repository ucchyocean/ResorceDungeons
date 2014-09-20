package com.thekarura.bukkit.plugin.resorcedungeons;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
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
	
	private JavaPlugin plugin;
	private FileConfiguration confing;
	
	// ** 初期設定 ** //
	
	//重要設定項目
	private boolean ENABLE_PLUGIN =				new Boolean(true);	//pluginを有効化させるかどうか
	private String DUNGEON_GENERATE_WORLD =		"dungeon";	//ダンジョン自動生成がされるワールド
	private boolean DUNGEON_GENERATE_COMMAND =	new Boolean(true);	//コマンド実行に上を適応させるか
	
	//ダンジョン生成
	private boolean ENABLE_DUNGEON_MOSSY = 		new Boolean(true);	//MossyDungeonの自動生成を許可
	private boolean ENABLE_DUNGEON_JUNGLECAVE = new Boolean(true);	//JungleCaveの自動生成を許可
	private boolean ENABLE_DUNGEON_RUINS = 		new Boolean(true);	//Ruinsの自動生成を許可
	private boolean ENABLE_DUNGEON_TOWERS = 	new Boolean(true);	//Towersの自動生成を許可
	
	//ダンジョン個別設定
	//MossyDungeon
	private int DUNGEON_MOSSY_DISTRCT_MIN = 3;	//生成する部屋数(削除予定)
	private int DUNGEON_MOSSY_DISTRCT_MAX = 3;	//生成する部屋数(削除予定)
	
	//生成関連
	private int DUNGEON_GENERATE_TRIGGER_RADIUS = new Integer(60);	//ダンジョントリガー検知範囲
	private int DUNGEON_GENERATE_DISTANCE = new Integer(100);		//ダンジョンとダンジョンの距離
	private int DUNGEON_GENERATE_SPAWND_ISTANCE = new Integer(50);	//ダンジョンを生成しない範囲(spawn基準)
	private int DUNGEON_GENERATE_UNDERGROUND_HIGTH = new Integer(62);//地下の限界高度を指定します
	
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
		plugin.saveDefaultConfig();
		
		// == フォルダー生成 == //
		createDir(new File(this.getChestfile()));
		createDir(new File(this.getMobsfile()));
		createDir(new File(this.getMobsfile()));
		
		plugin.reloadConfig();
		
		// == 内部的な設定 == //
		DUNGEON_GENERATE_COMMAND	= plugin.getConfig().getBoolean("DUNGEON_GENERATE_COMMAND");
		DUNGEON_GENERATE_COMMAND	= plugin.getConfig().getBoolean("DUNGEON_GENERATE_COMMAND");
		
		// == Dungeon生成関連 == //
		ENABLE_DUNGEON_MOSSY		= plugin.getConfig().getBoolean("Generate.ENABLE_DUNGEON_MOSSY");
		ENABLE_DUNGEON_JUNGLECAVE	= plugin.getConfig().getBoolean("Generate.ENABLE_DUNGEON_JUNGLECAVE");
		ENABLE_DUNGEON_RUINS		= plugin.getConfig().getBoolean("Generate.ENABLE_DUNGEON_RUINS");
		ENABLE_DUNGEON_TOWERS		= plugin.getConfig().getBoolean("Generate.ENABLE_DUNGEON_TOWERS");
		
		DUNGEON_GENERATE_TRIGGER_RADIUS		= plugin.getConfig().getInt("DUNGEON_GENERATE_TRIGGER_RADIUS");
		DUNGEON_GENERATE_UNDERGROUND_HIGTH	= plugin.getConfig().getInt("DUNGEON_GENERATE_UNDERGROUND_HIGTH");
		
		//指定されたワールドがなければ警告します。
		if (Bukkit.getWorld(DUNGEON_GENERATE_WORLD) == null) {
			log.info(logPrefix + "this Server is not Dungeon World! :" + DUNGEON_GENERATE_WORLD);
		}
		
	}
	
	/**
	 * pluginsフォルダー内にResorceDungeonsフォルダーを生成します。
	 */
	private void createDirs() {
		createDir(plugin.getDataFolder());
	}
	
	/**
	 * フォルダーを生成します。
	 * @param dir 生成する階層と名前
	 */
	private static void createDir(File dir) {
		// 既に存在すれば作らない
		if (dir.isDirectory()) { return; }
		if (!dir.mkdirs()) { log.warning(logPrefix + "Can't create directory: " + dir.getName()); }
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
	
	public boolean getEnablePlugin(){
		return this.ENABLE_PLUGIN;
	}
	
	public String getDungeonWorld(){
		return this.DUNGEON_GENERATE_WORLD;
	}
	
	public boolean getDUNGEON_GENERATE_COMMAND(){
		return this.DUNGEON_GENERATE_COMMAND;
	}
	
	public int getDUNGEON_GENERATE_TRIGGER_RADIUS(){
		return this.DUNGEON_GENERATE_TRIGGER_RADIUS;
	}
	
	public boolean getENABLE_DUNGEON_MOSSY(){
		return this.ENABLE_DUNGEON_MOSSY;
	}
	
	public boolean getENABLE_DUNGEON_JUNGLECAVE(){
		return this.ENABLE_DUNGEON_JUNGLECAVE;
	}
	
	public boolean getENABLE_DUNGEON_RUINS(){
		return this.ENABLE_DUNGEON_RUINS;
	}
	public boolean getENABLE_DUNGEON_TOWERS(){
		return this.ENABLE_DUNGEON_TOWERS;
	}
	
}

package xyhj.knkiss.scoreboard;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import xyhj.knkiss.McXyhj;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ScoreboardConfig {
	
	private static Plugin plugin;
	private static File scoreboardFile = null;
	private static FileConfiguration scoreboardConfig = null;
	
	public ScoreboardConfig(){
		ScoreboardConfig.plugin = McXyhj.plugin;
		scoreboardConfig = getScoreboardConfig();
		Scoreboard.playerList = scoreboardConfig.getStringList("noUI");
	}
	
	public static void saveToConfig(List<String> playerList){
		scoreboardConfig.set("noUI",playerList);
	}
	
	//配置文件默认方法 不必更改
	public static FileConfiguration getScoreboardConfig() {
		if (scoreboardConfig == null) {
			if (scoreboardFile == null) {
				scoreboardFile = new File(plugin.getDataFolder(), "ScoreboardInfo.yml");
			}
			scoreboardConfig = YamlConfiguration.loadConfiguration(scoreboardFile);
		}
		return scoreboardConfig;
	}
	
	public static void saveCustomConfig() {
		if (scoreboardConfig == null || scoreboardFile == null) {
			return;
		}
		try {
			getScoreboardConfig().save(scoreboardFile);
		} catch (IOException ex) {
			plugin.getLogger().warning("Could not save config to " + scoreboardFile);
		}
	}
}

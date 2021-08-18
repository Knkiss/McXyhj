package cn.mcxyhj.knkiss.statistics;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatisticsManager {
	private static Plugin plugin;
	private static List<String> InfoString = new ArrayList<>();
	
	public StatisticsManager(Plugin plugin){
		StatisticsManager.plugin = plugin;
		
		if(checkDataFolder()){
			ServerTps serverTps = new ServerTps();
			serverTps.run();
			Bukkit.getPluginManager().registerEvents(new StatisticsListener(),plugin);
			addStartOrStopInfo(true);
		}
	}
	
	private static boolean checkDataFolder(){
		File file = new File(plugin.getDataFolder(),"");
		return file.mkdirs();
	}
	
	public static void addJoinOrQuitInfo(String name,String tps,boolean isJoin){
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd,HH:mm:ss");
		String now = df.format(new Date());
		String info;
		if(isJoin){
			info = now + ",Join,"+ name +","+ tps +"\n";
		}else {
			info = now + ",Quit,"+ name +","+ tps +"\n";
		}
		append(info);
	}
	
	public static void addStartOrStopInfo(boolean isStart){
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd,HH:mm:ss");
		String now = df.format(new Date());
		String info;
		if(isStart){
			info = now + ",Start" +"\n";
		}else {
			info = now + ",Stop" +"\n";
		}
		append(info);
	}
	
	public static void append(String content) {
		try {
			RandomAccessFile randomFile = new RandomAccessFile(plugin.getDataFolder().getAbsolutePath()+"/statistics.csv", "rw");
			long fileLength = randomFile.length();
			randomFile.seek(fileLength);
			randomFile.writeBytes(content);
			randomFile.close();
		} catch (IOException e) {
			InfoString.add(content);
		}
	}
}

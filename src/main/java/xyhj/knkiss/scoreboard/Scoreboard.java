package xyhj.knkiss.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import xyhj.knkiss.McXyhj;

import java.util.ArrayList;
import java.util.List;

public class Scoreboard implements Listener, CommandExecutor {
	public static List<String> playerList = new ArrayList<>();
	public static ScoreboardConfig scoreboardConfig;
	
	public Scoreboard(){
		scoreboardConfig = new ScoreboardConfig();
		Bukkit.getPluginManager().registerEvents(this, McXyhj.plugin);
		Bukkit.getServer().getOnlinePlayers().forEach(player -> {
			if(playerList.contains(player.getName().toLowerCase()))toggleScoreboard(player);
		});
	}
	
	public static void onDisable(){
		ScoreboardConfig.saveToConfig(playerList);
		ScoreboardConfig.saveCustomConfig();
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		if(playerList.contains(e.getPlayer().getName().toLowerCase()))toggleScoreboard(e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent e){
		if(playerList.contains(e.getPlayer().getName().toLowerCase()))toggleScoreboard(e.getPlayer());
	}
	
	private void toggleScoreboard(Player p){
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(McXyhj.plugin, new Runnable() {
			@Override
			public void run() {
				if(!p.isOp()){
					p.setOp(true);
					try{
						p.performCommand("scoreboard");
					}catch (Exception exception){
						p.setOp(false);
					}
					p.setOp(false);
				}else{
					p.performCommand("scoreboard");
				}
			}
		}, 1L);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player){
			Player p = (Player) sender;
			String name = p.getName().toLowerCase();
			if(playerList.contains(name)) {
				playerList.remove(name);
				toggleScoreboard(p);
				p.sendMessage("已为你开启右侧信息板");
			}else{
				playerList.add(name);
				toggleScoreboard(p);
				p.sendMessage("已为你关闭右侧信息板");
			}
		}
		return true;
	}
}

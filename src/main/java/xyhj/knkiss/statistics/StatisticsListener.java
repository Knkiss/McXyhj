package xyhj.knkiss.statistics;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class StatisticsListener implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		String name = e.getPlayer().getName();
		double tps = ServerTps.getTPS();
		StatisticsManager.addJoinOrQuitInfo(name,tps+"",true);
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		String name = e.getPlayer().getName();
		double tps = ServerTps.getTPS();
		StatisticsManager.addJoinOrQuitInfo(name,tps+"",false);
	}
}

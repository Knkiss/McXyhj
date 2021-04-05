package xyhj.knkiss.flyEnergy;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class FlyEnergyListener implements Listener {
	Plugin plugin;
	public FlyEnergyListener(Plugin plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
		FlyEnergyConfig.getPlayerConfig(e.getPlayer());
		if(!e.getPlayer().isOp()){
			e.getPlayer().setAllowFlight(false);
			e.getPlayer().setFlying(false);
		}
	}
	
	//玩家离开服务器 终止飞行
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e){
		String name = e.getPlayer().getName();
		if(FlyEnergyManager.inFly(name)){
			FlyEnergyManager.flyTime.remove(name);
			double num = FlyEnergyManager.fly.get(name);
			FlyEnergyManager.walk.put(name,num);
			FlyEnergyManager.fly.remove(name);
		}
	}
	
	//玩家切换世界 终止飞行
	@EventHandler
	public void onPlayerChangeWorld(PlayerChangedWorldEvent e){
		String name = e.getPlayer().getName();
		if(FlyEnergyManager.inFly(name)){
			if(e.getFrom().getName().equalsIgnoreCase("world")){
				FlyEnergyManager.flyTime.remove(name);
				double num = FlyEnergyManager.fly.get(name);
				FlyEnergyManager.walk.put(name,num);
				FlyEnergyManager.fly.remove(name);
				e.getPlayer().sendMessage("检测到你切换到非主世界，飞行已被关闭");
				e.getPlayer().setFlying(false);
				e.getPlayer().setAllowFlight(false);
			}
		}
	}
}

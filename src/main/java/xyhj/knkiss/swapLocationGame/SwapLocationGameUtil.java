package xyhj.knkiss.swapLocationGame;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Time;
import java.util.*;

public class SwapLocationGameUtil {

    public static Queue<Player> all;
    public static Queue<Player> players;
    public static Queue<Player> ready;
    static Plugin plugin;
    static int time;

    public static void start(Queue<Player> playerQueue){
        while(playerQueue.peek() != null){
            Player p = playerQueue.poll();
            p.setGameMode(GameMode.SURVIVAL);
            p.getInventory().clear();
            Random r = new Random();
            World world = p.getWorld();
            Location l = new Location(world,r.nextInt(100001),255,r.nextInt(100001));
            PotionEffect pe = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,10,255);
            p.teleport(l);
            p.addPotionEffect(pe);
            time = 300;
            BukkitRunnable br = new BukkitRunnable() {
                @Override
                public void run() {
                    if(time - 1 == 60){
                        for(Player p : players){
                            p.sendMessage("60秒后交换位置");
                        }
                    }
                    else if(time - 1 == 10){
                        for(Player p : players){
                            p.sendMessage("10秒后交换位置");
                        }
                    }
                    else if(time - 1 == 0){
                        for(Player p : players){
                            p.sendMessage("位置已交换");
                            swap(players);
                            time = 300;
                        }
                    }
                }
            };
            br.runTaskTimer(plugin,0,20);
        }
    }

    public static Queue<Player> swap(Queue<Player> playerQueue){
        if(playerQueue.peek()==null){
            return playerQueue;
        }
        Queue<Player> tempQueue = new LinkedList<>();
        boolean bool = true;
        Location l = new Location(playerQueue.peek().getWorld(),0,0,0);
        while(playerQueue.peek() != null){
            Player p = playerQueue.poll();
            if(bool){
                l = p.getLocation();
                tempQueue.add(p);
                bool = false;
            }
            else{
                p.teleport(l);
                l = p.getLocation();
                tempQueue.add(p);
                if(playerQueue.peek() == null){
                    p = tempQueue.poll();
                    p.teleport(l);
                    tempQueue.add(p);
                }
            }
        }
        return tempQueue;
    }

    public static Queue<Player> death(Queue<Player> playerQueue,Player dp){
        Queue<Player> tempQueue = new LinkedList<>();
        dp.setGameMode(GameMode.SPECTATOR);
        while(playerQueue.peek() != null){
            Player p = playerQueue.poll();
            if(!dp.getName().equals(p.getName())){
                tempQueue.add(p);
            }
        }
        return tempQueue;
    }

}

package xyhj.knkiss.swapLocationGame;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Time;
import java.util.*;


public class SwapLocationGameManager {

    public static Queue<Player> all = new LinkedList<>();
    public static Queue<Player> players = new LinkedList<>();
    public static Queue<Player> ready = new LinkedList<>();
    //总量是all，准备好是ready，players是实时状态
    static Plugin plugin;
    static int time = 300;

    public static BukkitRunnable br = new BukkitRunnable() {
        @Override
        public void run() {
            time -= 1;
            if (time == 180) {
                for (Player p : all) {
                    p.sendMessage("180秒后交换位置");
                }
            } else if (time == 60) {
                for (Player p : all) {
                    p.sendMessage("60秒后交换位置");
                }
            } else if(time == 10) {
                for(Player p : all) {
                    p.sendMessage("10秒后交换位置");
                }
            } else if (time == 0) {
                for (Player p : all) {
                    p.sendMessage("位置已交换");
                    time = 300;
                }
                players = swap(players);
            }
        }
    };

    public static void onEnable(Plugin plugin){
        SwapLocationGameManager.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(new SwapLocationGameListener(plugin),plugin);
        br.runTaskTimer(plugin, 0, 20);
    }

    public static void start(Queue<Player> playerQueue) {
        PotionEffect pe = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 200, 255);
        int x = 0,z = 0;
        boolean isOcean = true;
        for(Player p : playerQueue) {
            p.setGameMode(GameMode.SURVIVAL);
            p.getInventory().clear();
            Random r = new Random(new Date().getTime());
            World world = p.getWorld();
            while(isOcean){
                x = r.nextInt(100001);
                z = r.nextInt(100001);
                //plugin.getLogger().info("xyz");
                Biome biome = world.getBiome(x,200,z);
                if(!biome.equals(Biome.OCEAN)
                &&!biome.equals(Biome.DEEP_OCEAN)
                &&!biome.equals(Biome.COLD_OCEAN)
                &&!biome.equals(Biome.DEEP_COLD_OCEAN)
                &&!biome.equals(Biome.FROZEN_OCEAN)
                &&!biome.equals(Biome.DEEP_FROZEN_OCEAN)
                &&!biome.equals(Biome.LUKEWARM_OCEAN)
                &&!biome.equals(Biome.DEEP_LUKEWARM_OCEAN)
                &&!biome.equals(Biome.WARM_OCEAN)
                &&!biome.equals(Biome.DEEP_WARM_OCEAN)){
                    isOcean = false;
                }
            }
            Location l = new Location(world, x, 255, z);
            p.teleport(l);
            isOcean = true;
            p.addPotionEffect(pe);
        }
        time = 300;
    }

    public static Queue<Player> swap(Queue<Player> playerQueue){
        if(playerQueue.peek()==null){
            return playerQueue;
        }
        List<Location> lastLocation = new ArrayList<>();
        Location l = new Location(playerQueue.peek().getWorld(),0,0,0);
        for(Player p : playerQueue) lastLocation.add(p.getLocation());
        Random rand = new Random();
        List<Location> randomLocation = new ArrayList<>(lastLocation);
        Collections.shuffle(randomLocation,rand);
        for(int i = 0; i < lastLocation.size(); i++){
            if(lastLocation.get(i) == randomLocation.get(i)){
                if(i != lastLocation.size()-1){
                    Location temp = randomLocation.get(i);
                    randomLocation.set(i,randomLocation.get(i+1));
                    randomLocation.set(i+1,temp);
                    i = 0;
                }
                else if(i == lastLocation.size()-1){
                    Location temp = randomLocation.get(i);
                    randomLocation.set(i,randomLocation.get(0));
                    randomLocation.set(0,temp);
                    i = 0;
                }
            }
        }
        int i = 0;
        for(Player p : playerQueue){
            p.teleport(randomLocation.get(i));
            i++;
        }
        return playerQueue;
    }

    public static Queue<Player> death(Queue<Player> playerQueue, Player dp){
        Queue<Player> tempQueue = new LinkedList<>();
        dp.setGameMode(GameMode.SPECTATOR);
        for(Player p : players)
            if(!p.getName().equals(dp.getName()))
                tempQueue.add(p);
        return tempQueue;
    }
}

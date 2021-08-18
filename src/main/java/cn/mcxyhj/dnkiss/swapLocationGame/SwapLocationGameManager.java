/*package xyhj.knkiss.swapLocationGame;

import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.sql.DatabaseMetaData;
import java.sql.Time;
import java.util.*;


public class SwapLocationGameManager {

    public static Queue<Player> all = new LinkedList<>();
    public static Queue<Player> players = new LinkedList<>();
    public static Queue<Player> ready = new LinkedList<>();
    //总量是all，准备好是ready，players是实时状态
    static Plugin plugin;
    static int time = 180;
    public static boolean state = false;
    public static BossBar bossBar = Bukkit.createBossBar("下一次交换 : "+ time + " 秒    剩余玩家 : " + players.size(), BarColor.RED, BarStyle.SOLID);

    public static World slgWorld = Bukkit.getWorld("slg");
    public static World mainWorld = Bukkit.getWorld("world");



    //static ScoreboardManager manager = Bukkit.getScoreboardManager();
    //static Scoreboard scoreboard = manager.getNewScoreboard();
    //static Objective objective = scoreboard.registerNewObjective("slg","dummy","/slg开始游戏");
    //static Score score;

    public static BukkitRunnable br = new BukkitRunnable() {
        @Override
        public void run() {
            if(!state) return;
            time -= 1;
            bossBar.setTitle("下一次交换 : " + time + " 秒    剩余玩家 : " + players.size());
            bossBar.setProgress(1/180.0*(time+1));
            if (time == 120) {
                for (Player p : all) {
                    p.sendTitle("","120秒后交换位置",20,40,20);
                }
            } else if (time == 60) {
                for (Player p : all) {
                    p.sendTitle("","60秒后交换位置",20,40,20);
                }
            } else if(time == 10) {
                for(Player p : all) {
                    p.sendTitle("","10秒后交换位置",20,40,20);
                }
            } else if (time == 0) {
                for (Player p : all) {
                    p.sendTitle("","位置已交换",20,40,20);
                    time = 180;
                }
                players = swap(players);
            }
        }
    };

    public static void onEnable(Plugin plugin){
        SwapLocationGameManager.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(new SwapLocationGameListener(plugin),plugin);
        br.runTaskTimer(plugin, 0, 20);
        bossBar.setVisible(false);
        Bukkit.getOnlinePlayers().forEach(player -> {
            bossBar.addPlayer(player);
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(new Location(mainWorld,-26.5,80,-150));
        });
        slgWorld = Bukkit.createWorld(new WorldCreator("slg").seed(new Date().getTime()));
        mainWorld = Bukkit.createWorld(new WorldCreator("world"));
        plugin.getLogger().info(String.valueOf(mainWorld.getUID()));
        plugin.getLogger().info(slgWorld.getUID().toString());

    }

    public static void start(Queue<Player> playerQueue) {
        slgWorld = Bukkit.createWorld(new WorldCreator("slg").seed(new Date().getTime()));
        slgWorld.setTime(0);
        state = true;
        bossBar.setVisible(true);
        int x = 0,z = 0;
        boolean isOcean = true;
        for(Player p : playerQueue) {
            p.setGameMode(GameMode.SURVIVAL);
            p.getInventory().clear();
            Random r = new Random(new Date().getTime());
            World world = p.getWorld();
            while(isOcean){
                x = r.nextInt(5001);
                z = r.nextInt(5001);
                //plugin.getLogger().info("xyz");
                Biome biome = slgWorld.getBiome(x,200,z);
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
            Location l = new Location(slgWorld, x, 255, z);
            p.teleport(l);
            p.sendMessage("180秒后交换位置");
            isOcean = true;
            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 400, 255));
            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,400,255));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION,400,255));
        }
        time = 180;
    }

    public static Queue<Player> swap(Queue<Player> playerQueue){
        if(playerQueue.peek()==null){
            return playerQueue;
        }
        List<Location> lastLocation = new ArrayList<>();
        Location l = new Location(slgWorld,0,0,0);
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
        for(Player p : playerQueue)
            if(!p.getName().equals(dp.getName()))
                tempQueue.add(p);
        return tempQueue;
    }
}*/

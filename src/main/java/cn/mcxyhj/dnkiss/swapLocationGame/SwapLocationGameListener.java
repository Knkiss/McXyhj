package cn.mcxyhj.dnkiss.swapLocationGame;

/*import java.util.LinkedList;
import java.util.Queue;

public class SwapLocationGameListener implements Listener {
    Plugin plugin;
    public SwapLocationGameListener(Plugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e){
        SwapLocationGameManager.players = SwapLocationGameManager.death(SwapLocationGameManager.players,e.getEntity().getPlayer());
        if(SwapLocationGameManager.players.size() == 1){
            SwapLocationGameManager.mainWorld = Bukkit.createWorld(new WorldCreator("world"));
            Player winner = SwapLocationGameManager.players.poll();
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendTitle("胜利者是" + winner.getName() + "!!","",20,80,20);
            });
            for(Player p : SwapLocationGameManager.all) {
                p.setGameMode(GameMode.SPECTATOR);
                if(!p.getName().equals(e.getEntity().getPlayer().getName())){
                    p.teleport(new Location(SwapLocationGameManager.mainWorld,-26.5,80,-150));
                }
            }
            SwapLocationGameManager.players.clear();
            SwapLocationGameManager.ready.clear();
            SwapLocationGameManager.all.clear();
            SwapLocationGameManager.time = 180;
            SwapLocationGameManager.state = false;
            SwapLocationGameManager.bossBar.setVisible(false);
            //SwapLocationGameManager.score.setScore(180);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.getPlayer().setGameMode(GameMode.SPECTATOR);
        SwapLocationGameManager.bossBar.addPlayer(e.getPlayer());
        e.getPlayer().teleport(new Location(SwapLocationGameManager.mainWorld,-26.5,80,-150));
        //SwapLocationGameManager.score = SwapLocationGameManager.objective.getScore("时间");
        //SwapLocationGameManager.score.setScore(180);
        //e.getPlayer().setScoreboard(SwapLocationGameManager.scoreboard);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        SwapLocationGameManager.all = SwapLocationGameManager.death(SwapLocationGameManager.all,e.getPlayer());
        SwapLocationGameManager.ready = SwapLocationGameManager.death(SwapLocationGameManager.ready,e.getPlayer());
        SwapLocationGameManager.players = SwapLocationGameManager.death(SwapLocationGameManager.players,e.getPlayer());
        if(SwapLocationGameManager.players.size() == 1 && SwapLocationGameManager.state){
            SwapLocationGameManager.mainWorld = Bukkit.createWorld(new WorldCreator("world"));
            Player winner = SwapLocationGameManager.players.poll();
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.sendTitle("胜利者是" + winner.getName() + "!!","",20,80,20);
            });
            for(Player p : SwapLocationGameManager.all) {
                p.setGameMode(GameMode.SPECTATOR);
                if(!p.getName().equals(e.getPlayer().getName())){
                    p.teleport(new Location(SwapLocationGameManager.mainWorld,-26.5,80,-150));
                }
            }
            SwapLocationGameManager.players.clear();
            SwapLocationGameManager.ready.clear();
            SwapLocationGameManager.all.clear();
            SwapLocationGameManager.time = 180;
            SwapLocationGameManager.state = false;
            SwapLocationGameManager.bossBar.setVisible(false);
            //SwapLocationGameManager.score.setScore(180);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
        e.getPlayer().teleport(new Location(SwapLocationGameManager.mainWorld,-26.5,80,-150));
    }
}*/

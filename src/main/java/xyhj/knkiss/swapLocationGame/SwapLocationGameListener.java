package xyhj.knkiss.swapLocationGame;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.LinkedList;
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
            Player winner = SwapLocationGameManager.players.poll();
            for(Player p : SwapLocationGameManager.all) {
                p.sendMessage("胜利者是" + winner.getName() + "!!");
                p.setGameMode(GameMode.SPECTATOR);
            }
            SwapLocationGameManager.players.clear();
            SwapLocationGameManager.ready.clear();
            SwapLocationGameManager.all.clear();
            SwapLocationGameManager.time = 10;
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        e.getPlayer().setGameMode(GameMode.SPECTATOR);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        Queue<Player> temp = new LinkedList<>();
        while(SwapLocationGameManager.ready.peek()!=null){
            if(SwapLocationGameManager.ready.poll().getName().equals(p.getName()));
            else temp.add(SwapLocationGameManager.ready.poll());
        }
        while(SwapLocationGameManager.all.peek()!=null){
            if(SwapLocationGameManager.all.poll().getName().equals(p.getName()));
            else temp.add(SwapLocationGameManager.all.poll());
        }
    }
}

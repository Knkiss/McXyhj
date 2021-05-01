package xyhj.knkiss.swapLocationGame;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
        SwapLocationGameUtil.death(SwapLocationGameUtil.players,e.getEntity().getPlayer());
        if(SwapLocationGameUtil.players.size() == 1){
            while(SwapLocationGameUtil.all.peek() != null){
                SwapLocationGameUtil.all.poll().sendMessage("胜利者是"+SwapLocationGameUtil.players.poll().getName()+"!!");
            }
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
        while(SwapLocationGameUtil.ready.peek()!=null){
            if(SwapLocationGameUtil.ready.poll().getName().equals(p.getName()));
            else temp.add(SwapLocationGameUtil.ready.poll());
        }
        while(SwapLocationGameUtil.all.peek()!=null){
            if(SwapLocationGameUtil.all.poll().getName().equals(p.getName()));
            else temp.add(SwapLocationGameUtil.all.poll());
        }
    }
}

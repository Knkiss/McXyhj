package xyhj.knkiss.flyEnergy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyhj.knkiss.McXyhj;
import xyhj.knkiss.Utils;

public class FlyEnergyCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(!(sender instanceof Player)){
			sender.sendMessage("控制台无法使用此指令");
			return true;
		}
		Player p = (Player) sender;
		String name = p.getName();
		if(args.length == 0){
			if(FlyEnergyManager.fly.containsKey(name))FlyEnergyManager.stopFly(p);
			else FlyEnergyManager.startFly(p); }
		else if(args[0].equalsIgnoreCase("on"))FlyEnergyManager.startFly(p);
		else if(args[0].equalsIgnoreCase("off"))FlyEnergyManager.stopFly(p);
		else if(args[0].equalsIgnoreCase("buy")){
			if(args.length == 1){
				p.sendMessage("请输入要购买的能量数量 /fe buy <number>");
			}else if(Utils.isInteger(args[1])){
				int num = Integer.parseInt(args[1]);
				if(num<=0){
					p.sendMessage("请输入一个正确的购买数量 /fe buy <number>");
				}else{
					double valuePerPower = FlyEnergyManager.valuePer1Power;
					if(p.hasPermission("mcxyhj.zhengban.fly")){
						valuePerPower = valuePerPower/2;
					}
					
					if(McXyhj.econ.getBalance(name) >= valuePerPower*num){
						McXyhj.econ.withdrawPlayer(name,num * valuePerPower);
						double sum;
						if(FlyEnergyManager.inFly(name)){
							FlyEnergyManager.fly.replace(name,FlyEnergyManager.fly.get(name)+num);
							sum = FlyEnergyManager.fly.get(name);
						}else{
							FlyEnergyManager.walk.replace(name,FlyEnergyManager.walk.get(name)+num);
							sum = FlyEnergyManager.walk.get(name);
						}
						p.sendMessage("你花费 "+ valuePerPower*num +" 元，购买了 "+num+" 点能量，剩余 "+sum+ " 点能量");
					}else{
						p.sendMessage("你没有足够的钱，需要 "+valuePerPower*num+" 元");
					}
				}
			}
		}else if(args[0].equalsIgnoreCase("info")){
			if(FlyEnergyManager.inFly(name))p.sendMessage("你还有 "+FlyEnergyManager.fly.get(name)+" 点能量");
			else p.sendMessage("你还有 "+FlyEnergyManager.walk.get(name)+" 点能量");
		}else if(args[0].equalsIgnoreCase("reload")){
			if(p.hasPermission("mcxyhj.admin")){
				FlyEnergyConfig.reloadConfig();
				p.sendMessage("已重载配置文件");
			}
		}else{
			p.sendMessage("--------------------------------");
			p.sendMessage("/fe 切换飞行");
			p.sendMessage("/fe on 开启飞行");
			p.sendMessage("/fe off 关闭飞行");
			p.sendMessage("/fe buy <number> 购买飞行能量");
			p.sendMessage("/fe info 查看自己的飞行能量");
			p.sendMessage("--------------------------------");
			
			if(p.hasPermission("mcxyhj.admin")){
				p.sendMessage("/fe reload 重载配置文件");
				p.sendMessage("--------------------------------");
			}
		}
		return true;
	}
}

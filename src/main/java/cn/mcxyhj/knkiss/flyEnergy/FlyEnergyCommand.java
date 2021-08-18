package cn.mcxyhj.knkiss.flyEnergy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import cn.mcxyhj.knkiss.McXyhj;
import cn.mcxyhj.knkiss.Utils;

public class FlyEnergyCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			String name = p.getName();
			if (args.length == 0) {
				if (FlyEnergyManager.fly.containsKey(name)) FlyEnergyManager.stopFly(p);
				else FlyEnergyManager.startFly(p);
			} else if (args[0].equalsIgnoreCase("on")) FlyEnergyManager.startFly(p);
			else if (args[0].equalsIgnoreCase("off")) FlyEnergyManager.stopFly(p);
			else if (args[0].equalsIgnoreCase("buy")) {
				if (args.length == 1) {
					p.sendMessage("请输入要购买的能量数量 /fe buy <number>");
				} else if (Utils.isInteger(args[1])) {
					int num = Integer.parseInt(args[1]);
					if (num <= 0) {
						p.sendMessage("请输入一个正确的购买数量 /fe buy <number>");
					} else {
						double valuePerPower = FlyEnergyManager.valuePer1Power;
						if (p.hasPermission("mcxyhj.zhengban.fly")) {
							valuePerPower = valuePerPower / 2;
						}

						if (McXyhj.econ.getBalance(name) >= valuePerPower * num) {
							McXyhj.econ.withdrawPlayer(name, num * valuePerPower);
							double sum;
							if (FlyEnergyManager.inFly(name)) {
								FlyEnergyManager.fly.replace(name, FlyEnergyManager.fly.get(name) + num);
								sum = FlyEnergyManager.fly.get(name);
							} else {
								FlyEnergyManager.walk.replace(name, FlyEnergyManager.walk.get(name) + num);
								sum = FlyEnergyManager.walk.get(name);
							}
							p.sendMessage("你花费 " + valuePerPower * num + " 元，购买了 " + num + " 点能量，剩余 " + sum + " 点能量");
						} else {
							p.sendMessage("你没有足够的钱，需要 " + valuePerPower * num + " 元");
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("info")) {
				if (FlyEnergyManager.inFly(name)) p.sendMessage("你还有 " + FlyEnergyManager.fly.get(name) + " 点能量");
				else p.sendMessage("你还有 " + FlyEnergyManager.walk.get(name) + " 点能量");
			} else if (args[0].equalsIgnoreCase("give")) {
				if (p.hasPermission("mcxyhj.admin")) {
					if (args.length != 3) {
						p.sendMessage("/fe give 玩家名 能量数");
					} else {
						String playerName = args[1];
						int num = Integer.parseInt(args[2]);
						if (num <= 0) {
							p.sendMessage("请输入正确的能量数值");
						} else {
							double valuePerPower = FlyEnergyManager.valuePer1Power;
							double sum;
							if(FlyEnergyManager.inFly(playerName)){
								FlyEnergyManager.fly.replace(playerName,FlyEnergyManager.fly.get(playerName)+num);
								sum = FlyEnergyManager.fly.get(playerName);
							}else{
								FlyEnergyManager.walk.replace(playerName,FlyEnergyManager.walk.get(playerName)+num);
								sum = FlyEnergyManager.walk.get(playerName);
							}
							p.sendMessage("你给予了"+playerName+" "+sum+"点能量");
						}
					}
				} else {
					p.sendMessage("你没有权限");
				}
			} else if (args[0].equalsIgnoreCase("reload")) {
				if (p.hasPermission("mcxyhj.admin")) {
					FlyEnergyConfig.reloadConfig();
					p.sendMessage("已重载配置文件");
				}
			} else {
				p.sendMessage("--------------------------------");
				p.sendMessage("/fe 切换飞行");
				p.sendMessage("/fe on 开启飞行");
				p.sendMessage("/fe off 关闭飞行");
				p.sendMessage("/fe buy <number> 购买飞行能量");
				p.sendMessage("/fe info 查看自己的飞行能量");
				p.sendMessage("--------------------------------");

				if (p.hasPermission("mcxyhj.admin")) {
					p.sendMessage("/fe reload 重载配置文件");
					p.sendMessage("--------------------------------");
				}
			}
		}
		//控制台可使用
		else if(sender instanceof ConsoleCommandSender) {
			if (args[0].equalsIgnoreCase("give")) {
					if (args.length != 3) {
						sender.sendMessage("/fe give 玩家名 能量数");
					} else {
						String playerName = args[1];
						int num = Integer.parseInt(args[2]);
						if (num <= 0) {
							sender.sendMessage("请输入正确的能量数值");
						}else {
							double valuePerPower = FlyEnergyManager.valuePer1Power;
							double sum;
							if(FlyEnergyManager.inFly(playerName)){
								FlyEnergyManager.fly.replace(playerName,FlyEnergyManager.fly.get(playerName)+num);
								sum = FlyEnergyManager.fly.get(playerName);
							}else{
								FlyEnergyManager.walk.replace(playerName,FlyEnergyManager.walk.get(playerName)+num);
								sum = FlyEnergyManager.walk.get(playerName);
							}
							sender.sendMessage("你给予了"+playerName+" "+num+"点能量");
						}
					}
			} else if (args[0].equalsIgnoreCase("reload")) {
					FlyEnergyConfig.reloadConfig();
					sender.sendMessage("已重载配置文件");
			}
		}

		return true;
	}
}

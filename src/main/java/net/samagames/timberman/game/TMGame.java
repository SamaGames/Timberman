package net.samagames.timberman.game;

import java.util.ArrayList;
import java.util.List;

import net.samagames.api.games.Game;
import net.samagames.timberman.Timberman;
import net.samagames.timberman.util.ItemsUtil;
import net.samagames.timberman.util.RulesUtil;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TMGame extends Game<TMPlayer>
{
	private Timberman plugin;
	
	public TMGame(Timberman main)
	{
		super("timberman", "TimberMan", "", TMPlayer.class);
		plugin = main;
	}

	@Override
	public void handleLogin(Player p)
	{
		super.handleLogin(p);
		p.teleport(plugin.getSpawn());
		p.setGameMode(GameMode.ADVENTURE);
		p.setAllowFlight(false);
		p.setCanPickupItems(false);
		p.setExp(0);
		p.setFlying(false);
		p.setFoodLevel(20);
		p.setHealth(20);
		p.setLevel(0);
		giveWaitingInventory(p);
	}
	
	public void giveWaitingInventory(Player p)
	{
		if (p == null)
			return ;
		Inventory inv = p.getInventory();
		inv.clear();
		inv.setItem(4, RulesUtil.getRulesBook());
		inv.setItem(8, coherenceMachine.getLeaveItem());
	}
	
	public void givePlayingInventory(Player p)
	{
		if (p == null)
			return ;
		Inventory inv = p.getInventory();
		inv.clear();
		inv.setItem(0, ItemsUtil.AXE);
		inv.setItem(4, RulesUtil.getRulesBook());
		inv.setItem(8, coherenceMachine.getLeaveItem());
		p.setLevel(0);
		p.setExp(0);
	}
	
	@Override
	public void startGame()
	{
		if (this.isGameStarted())
			return ;
		super.startGame();
		startTime = System.currentTimeMillis();
		for (TMPlayer tmp : this.getInGamePlayers().values())
		{
			if (tmp.isModerator() || tmp.isSpectator() || !tmp.isOnline())
				continue ;
			Player p = tmp.getPlayerIfOnline();
			givePlayingInventory(p);
			tmp.startGame(plugin, p);
		}
	}
	
	public void win(TMPlayer tmp)
	{
		if (tmp == null || !tmp.isOnline())
			return ;
		plugin.getServer().broadcastMessage(coherenceMachine.getGameTag() + ChatColor.YELLOW + " " + tmp.getOfflinePlayer().getName() + ChatColor.WHITE + " a gagné !");
		coherenceMachine.getTemplateManager().getPlayerWinTemplate().execute(tmp.getPlayerIfOnline());
		tmp.addCoins(30, "Victoire !");
		tmp.addStars(1, "Victoire !");
		this.handleGameEnd();
	}

	public void lose(TMPlayer tmp)
	{
		if (tmp == null || !tmp.isOnline())
			return ;
		plugin.getServer().broadcastMessage(coherenceMachine.getGameTag() + ChatColor.YELLOW + " " + tmp.getOfflinePlayer().getName() + ChatColor.WHITE + " est éliminé !");
		tmp.setSpectator();
		List<TMPlayer> players = new ArrayList<TMPlayer>();
		for (TMPlayer t : this.getInGamePlayers().values())
		{
			if (t == null || t.isModerator() || t.isSpectator() || !t.isOnline())
				continue ;
			players.add(t);
		}
		if (players.size() == 0)
			handleGameEnd();
		else if (players.size() == 1)
			win(players.get(0));
	}
}

package net.samagames.timberman.game;

import java.util.HashSet;
import java.util.Set;

import net.samagames.api.games.Status;
import net.samagames.timberman.Timberman;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardManager
{
	private Timberman plugin;
	private Set<TMPlayer> players;
	private int time;
	
	public ScoreboardManager(Timberman main)
	{
		plugin = main;
		players = new HashSet<>();
		time = 0;
		plugin.getServer().getScheduler().runTaskTimer(plugin, () -> {
			if (plugin.getGame().getStatus() == Status.IN_GAME || plugin.getGame().getStatus() == Status.FINISHED)
				time++;
		}, 20, 20);
	}
	
	public void addReceiver(TMPlayer tmp)
	{
		if (!players.contains(tmp))
			players.add(tmp);
		update();
	}
	
	public void removeReceiver(TMPlayer tmp)
	{
		if (players.contains(tmp))
			players.remove(tmp);
		update();
	}
	
	public void update()
	{
		Scoreboard sc = plugin.getServer().getScoreboardManager().getNewScoreboard();
		Objective obj = sc.registerNewObjective(ChatColor.GOLD + "= Timberman =", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		int ingame = 0;
		for (TMPlayer tmp : players)
			if (tmp.isOnline() && !tmp.isSpectator() && !tmp.isModerator())
				ingame++;
		
		if (plugin.getGame().getStatus() == Status.IN_GAME || plugin.getGame().getStatus() == Status.FINISHED)
		{
			obj.getScore(" ").setScore(-4);
			obj.getScore(" " + (time < 60 ? "0" : "") + (time / 60) + ":" + (time % 60 < 10 ? "0" : "") + (time % 60)).setScore(-3);
			obj.getScore(ChatColor.GRAY + "Temps :").setScore(-2);
			obj.getScore("  ").setScore(-1);
			for (TMPlayer tmp : players)
			{
				if (tmp.isModerator())
					continue ;
				String name = ChatColor.stripColor(tmp.getOfflinePlayer().getName());
				if (name.length() > 13)
					name = name.substring(0, 13);
				if (!tmp.isSpectator())
					obj.getScore(ChatColor.WHITE + " " + name).setScore((int)(tmp.getProgression() * 100));
				else
					obj.getScore(" " + ChatColor.RED + name).setScore(0);
			}
			obj.getScore(ChatColor.GRAY + "Progression (%):").setScore(101);
		}
		obj.getScore("   ").setScore(102);
		obj.getScore(" " + ingame).setScore(103);
		obj.getScore(ChatColor.GRAY + "Joueurs :").setScore(104);
		obj.getScore("    ").setScore(105);
		for (TMPlayer tmp : players)
			if (tmp.isOnline())
				tmp.getPlayerIfOnline().setScoreboard(sc);
	}
}

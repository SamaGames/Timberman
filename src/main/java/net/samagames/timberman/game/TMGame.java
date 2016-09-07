package net.samagames.timberman.game;

import java.util.List;
import java.util.stream.Collectors;

import net.samagames.api.games.Game;
import net.samagames.api.games.Status;
import net.samagames.timberman.Timberman;
import net.samagames.timberman.util.ItemsUtil;
import net.samagames.timberman.util.RulesUtil;
import net.samagames.tools.Titles;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitTask;

public class TMGame extends Game<TMPlayer>
{
    private Timberman plugin;
    private int countdown;
    private BukkitTask countdownTask;
    private int time;

    public TMGame(Timberman main)
    {
        super("timberman", "TimberMan", "", TMPlayer.class);
        this.plugin = main;
        this.countdown = 10;
        this.time = 0;
    }

    @Override
    public void handleLogin(Player p)
    {
        super.handleLogin(p);
        p.teleport(this.plugin.getSpawn());
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

    private void giveWaitingInventory(Player p)
    {
        if (p == null)
            return ;
        Inventory inv = p.getInventory();
        inv.clear();
        inv.setItem(4, RulesUtil.getRulesBook());
    }

    private void givePlayingInventory(Player p)
    {
        if (p == null)
            return ;
        Inventory inv = p.getInventory();
        inv.clear();
        inv.setItem(0, ItemsUtil.AXE);
        inv.setItem(4, RulesUtil.getRulesBook());
        p.setLevel(0);
        p.setExp(0);
    }

    @Override
    public void startGame()
    {
        if (this.isGameStarted() || this.isStarted())
            return ;
        super.startGame();
        this.startTime = System.currentTimeMillis();
        for (TMPlayer tmp : this.getInGamePlayers().values())
        {
            if (!tmp.isOnline())
                continue ;
            Player p = tmp.getPlayerIfOnline();
            givePlayingInventory(p);
            tmp.startGame(this.plugin, p);
        }
        this.countdownTask = this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, this::countdown, 0, 20);
        this.plugin.getServer().getScheduler().runTaskTimer(this.plugin, () ->
        {
            this.time++;
            this.gamePlayers.forEach((uuid, player) -> player.updateScoreboard());
        }, 20, 20);
    }

    private void countdown()
    {
        for (Player player : this.plugin.getServer().getOnlinePlayers())
            if (this.countdown == 0)
                Titles.sendTitle(player, 0, 20, 0, "", ChatColor.GOLD + "Coupez !");
            else if (this.countdown == 10 || this.countdown < 6)
                Titles.sendTitle(player, 0, 20, 0, "", ChatColor.GOLD + "Début dans " + this.countdown + (this.countdown == 1 ? " seconde" : " secondes"));
        if (this.countdown == 0)
        {
            this.coherenceMachine.getMessageManager().writeCustomMessage(ChatColor.YELLOW + "Coupez !", true);
            this.countdown--;
            this.countdownTask.cancel();
            return ;
        }
        else if (this.countdown == 10 || this.countdown < 9)
            this.coherenceMachine.getMessageManager().writeCustomMessage(ChatColor.YELLOW + "Début dans " + ChatColor.RED + this.countdown + " seconde" + (this.countdown == 1 ? "" : "s") + ChatColor.YELLOW + ".", true);
        this.countdown--;
    }

    @Override
    public void handleLogout(Player p)
    {
        TMPlayer tmp = getPlayer(p.getUniqueId());
        super.handleLogout(p);
        if (this.status == Status.IN_GAME)
            lose(tmp);
    }

    void win(TMPlayer tmp)
    {
        if (tmp == null || !tmp.isOnline())
            return ;
        String name = tmp.getDisplayName();
        this.plugin.getServer().broadcastMessage(this.coherenceMachine.getGameTag() + " " + name + ChatColor.WHITE + " a gagné !");
        for (Player user : this.plugin.getServer().getOnlinePlayers())
            Titles.sendTitle(user, 0, 60, 5, ChatColor.RED + "Fin du jeu", ChatColor.YELLOW + "Victoire de " + name);
        this.coherenceMachine.getTemplateManager().getPlayerWinTemplate().execute(tmp.getPlayerIfOnline());
        tmp.addCoins(30, "Victoire !");
        tmp.addStars(1, "Victoire !");
        this.handleGameEnd();
    }

    void lose(TMPlayer tmp)
    {
        if (tmp == null || tmp.isSpectator())
            return ;
        this.plugin.getServer().broadcastMessage(this.coherenceMachine.getGameTag() + " " + tmp.getDisplayName() + ChatColor.WHITE + " est éliminé !");
        tmp.setSpectator();
        List<TMPlayer> players = this.getInGamePlayers().values().stream().filter(t -> !(t.isSpectator() || !t.isOnline())).collect(Collectors.toList());
        if (players.isEmpty())
            handleGameEnd();
        else if (players.size() == 1)
            win(players.get(0));
    }

    public boolean isStarted()
    {
        return this.countdown == -1;
    }

    int getTime()
    {
        return this.time;
    }
}

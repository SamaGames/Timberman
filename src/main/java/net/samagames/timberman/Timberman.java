package net.samagames.timberman;

import net.samagames.api.SamaGamesAPI;
import net.samagames.timberman.game.TMGame;
import net.samagames.timberman.game.TreePattern;
import net.samagames.timberman.listener.PlayerListener;
import net.samagames.timberman.listener.WorldListener;
import net.samagames.timberman.util.JsonUtils;

import org.apache.commons.lang3.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/*
 * This file is part of Timberman.
 *
 * Timberman is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Timberman is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Timberman.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Timberman extends JavaPlugin
{
    public static final String NAME_BI_COLOR = ChatColor.GOLD + "" + ChatColor.BOLD + "Timberman";

    private Location spawn;
    private List<Location> gameSpawn;
    private List<Location> treeSpawn;
    private int[] spawnIndex = {0, 0};
    private TreePattern tree;
    private TMGame game;
    private int treeShowHeight;

    @Override
    public void onEnable()
    {
        SamaGamesAPI api = SamaGamesAPI.get();
        this.spawn = JsonUtils.getLocation(api.getGameManager().getGameProperties().getConfig("spawn", null));
        this.gameSpawn = new ArrayList<>();
        this.treeSpawn = new ArrayList<>();
        int i = 0;
        while (true)
        {
            Location tmp = JsonUtils.getLocation(api.getGameManager().getGameProperties().getConfig("gamespawn-" + i, null));
            Location tmp2 = JsonUtils.getLocation(api.getGameManager().getGameProperties().getConfig("treespawn-" + i, null));
            if (tmp == null || tmp2 == null)
                break ;
            tmp2.getBlock().setType(Material.LOG);
            this.gameSpawn.add(tmp);
            this.treeSpawn.add(tmp2);
            i++;
        }
        this.tree = new TreePattern(api.getGameManager().getGameProperties().getConfig("height", null).getAsInt());
        this.treeShowHeight = api.getGameManager().getGameProperties().getConfig("showheight", null).getAsInt();
        Validate.notNull(spawn);

        this.game = new TMGame(this);
        api.getGameManager().registerGame(this.game);
        api.getGameManager().setMaxReconnectTime(-1);
        api.getGameManager().setKeepPlayerCache(true);

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getServer().getPluginManager().registerEvents(new WorldListener(), this);
    }

    public Location getSpawn()
    {
        return this.spawn;
    }

    public Location newGameSpawn()
    {
        Location loc = this.gameSpawn.get(this.spawnIndex[0]);
        this.spawnIndex[0]++;
        return loc;
    }

    public Location newTreeSpawn()
    {
        Location loc = this.treeSpawn.get(this.spawnIndex[1]);
        this.spawnIndex[1]++;
        return loc;
    }

    public TreePattern getTreePattern()
    {
        return this.tree;
    }

    public TMGame getGame()
    {
        return this.game;
    }

    public int getTreeShowHeight()
    {
        return this.treeShowHeight;
    }
}
package net.samagames.timberman;

import net.samagames.api.SamaGamesAPI;
import net.samagames.timberman.game.TMGame;
import net.samagames.timberman.game.TreePattern;
import net.samagames.timberman.listener.PlayerListener;
import net.samagames.timberman.util.JsonUtils;

import org.apache.commons.lang3.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Timberman extends JavaPlugin
{
    public static final String NAME_BICOLOR = ChatColor.GOLD + "" + ChatColor.BOLD + "Timberman";

    private Location spawn;
    private List<Location> gamespawn;
    private List<Location> treespawn;
    private int spawnindex[] = {0, 0};
    private TreePattern tree;
    private TMGame game;
    private int treeshowheight;

    @Override
    public void onEnable()
    {
        SamaGamesAPI api = SamaGamesAPI.get();
        spawn = JsonUtils.getLocation(api.getGameManager().getGameProperties().getConfig("spawn", null));
        gamespawn = new ArrayList<>();
        treespawn = new ArrayList<>();
        int i = 0;
        while (true)
        {
            Location tmp = JsonUtils.getLocation(api.getGameManager().getGameProperties().getConfig("gamespawn-" + i, null));
            Location tmp2 = JsonUtils.getLocation(api.getGameManager().getGameProperties().getConfig("treespawn-" + i, null));
            if (tmp == null || tmp2 == null)
                break ;
            tmp2.getBlock().setType(Material.LOG);
            gamespawn.add(tmp);
            treespawn.add(tmp2);
            i++;
        }
        tree = new TreePattern(api.getGameManager().getGameProperties().getConfig("height", null).getAsInt());
        treeshowheight = api.getGameManager().getGameProperties().getConfig("showheight", null).getAsInt();
        Validate.notNull(spawn);

        game = new TMGame(this);
        api.getGameManager().registerGame(game);
        api.getGameManager().setMaxReconnectTime(-1);

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    public Location getSpawn()
    {
        return spawn;
    }

    public Location newGameSpawn()
    {
        Location loc = gamespawn.get(spawnindex[0]);
        spawnindex[0]++;
        return loc;
    }

    public Location newTreeSpawn()
    {
        Location loc = treespawn.get(spawnindex[1]);
        spawnindex[1]++;
        return loc;
    }

    public TreePattern getTreePattern()
    {
        return tree;
    }

    public TMGame getGame()
    {
        return game;
    }

    public int getTreeShowHeight()
    {
        return treeshowheight;
    }
}
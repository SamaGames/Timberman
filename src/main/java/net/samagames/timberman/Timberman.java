package net.samagames.timberman;

import net.samagames.api.SamaGamesAPI;
import net.samagames.timberman.game.TMGame;
import net.samagames.timberman.game.TreePattern;
import net.samagames.timberman.listener.PlayerListener;
import net.samagames.timberman.util.JsonUtils;

import org.apache.commons.lang3.Validate;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class Timberman extends JavaPlugin
{
	public static final String NAME_BICOLOR = ChatColor.GOLD + "" + ChatColor.BOLD + "Timberman";
	public static Timberman INSTANCE;
	
	private Location spawn;
	private Location gamespawn;
	private Location treespawn;
	private Location offset;
	private TreePattern tree;
	private SamaGamesAPI api;
	private TMGame game;
	private int treeshowheight;
	
	@Override
	public void onEnable()
	{
		INSTANCE = this;
		
		api = SamaGamesAPI.get();
		spawn = JsonUtils.getLocation(api.getGameManager().getGameProperties().getOption("spawn", null));
		gamespawn = JsonUtils.getLocation(api.getGameManager().getGameProperties().getOption("gamespawn", null));
		treespawn = JsonUtils.getLocation(api.getGameManager().getGameProperties().getOption("treespawn", null));
		offset = JsonUtils.getLocation(api.getGameManager().getGameProperties().getOption("offset", null));
		tree = new TreePattern(api.getGameManager().getGameProperties().getOption("height", null).getAsInt());
		treeshowheight = api.getGameManager().getGameProperties().getOption("showheight", null).getAsInt();
		Validate.noNullElements(new Object[]{spawn, gamespawn, treespawn, offset});
		
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
		Location loc = gamespawn.clone();
		gamespawn.add(offset);
		return loc;
	}
	
	public Location newTreeSpawn()
	{
		Location loc = treespawn.clone();
		treespawn.add(offset);
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
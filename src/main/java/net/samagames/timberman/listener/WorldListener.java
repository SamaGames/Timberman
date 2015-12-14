package net.samagames.timberman.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.world.StructureGrowEvent;

public class WorldListener implements Listener
{
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent ev)
	{
		if (ev.toWeatherState())
			ev.setCancelled(true);
	}
	
	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent ev)
	{
		switch (ev.getEntityType())
		{
		case ARMOR_STAND:
		case PLAYER:
			return ;
		default:
			ev.setCancelled(true);
		}
	}
	
	@Override
    public int hashCode()
    {
        return super.hashCode();
    }

    @EventHandler
    public void onBlockGrowEvent(BlockGrowEvent event)
    {
        event.setCancelled(true);
    }

    @EventHandler
    public void onLeavesDecayEvent(LeavesDecayEvent event)
    {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockFadeEvent(BlockFadeEvent event)
    {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPhysicsEvent(BlockPhysicsEvent event)
    {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockRedstoneEvent(BlockRedstoneEvent event)
    {
        event.setNewCurrent(event.getOldCurrent());
    }

    @EventHandler
    public void onBlockSpreadEvent(BlockSpreadEvent event)
    {
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockFormEvent(BlockFormEvent event)
    {
        event.setCancelled(true);
    }

    @EventHandler
    public void onStructureGrowEvent(StructureGrowEvent event)
    {
        event.setCancelled(true);
    }
}

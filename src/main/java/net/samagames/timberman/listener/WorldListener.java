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

package net.samagames.timberman.listener;

import net.samagames.api.games.Status;
import net.samagames.timberman.Timberman;
import net.samagames.timberman.game.TMPlayer;
import net.samagames.timberman.util.ItemsUtil;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;

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
public class PlayerListener implements Listener
{
    private Timberman plugin;

    public PlayerListener(Timberman main)
    {
        this.plugin = main;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent ev)
    {
        if (this.plugin.getGame().getStatus() == Status.IN_GAME)
        {
            TMPlayer tmp = this.plugin.getGame().getPlayer(ev.getPlayer().getUniqueId());

            if (tmp == null || tmp.isSpectator())
                return ;

            Block b = ev.getTo().clone().subtract(0, 1, 0).getBlock();

            if (b.hasMetadata("pos"))
            {
                double x = ev.getFrom().getX() - ev.getTo().getX();

                if (x > 0)
                    tmp.moveRight();
                else if (x < 0)
                    tmp.moveLeft();
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent ev)
    {
        TMPlayer tmp = plugin.getGame().getPlayer(ev.getPlayer().getUniqueId());

        Player p = ev.getPlayer();
        ItemStack i = p.getItemInHand();

        if (i == null)
            return ;

        ev.setCancelled(!i.getType().equals(Material.WRITTEN_BOOK));

        if (i.getType() == ItemsUtil.AXE.getType() && tmp != null
                && this.plugin.getGame().getStatus() == Status.IN_GAME
                && this.plugin.getGame().isStarted())
            tmp.treeBreak(this.plugin, p);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent ev)
    {
        ev.setCancelled(true);
    }

    @EventHandler
    public void onFoodLevelChanged(FoodLevelChangeEvent ev)
    {
        ev.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent ev)
    {
        ev.setCancelled(true);
    }

    @EventHandler
    public void onSecondHand(PlayerSwapHandItemsEvent ev)
    {
        ev.setCancelled(true);
    }
}

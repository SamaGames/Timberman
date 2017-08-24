package net.samagames.timberman.util;

import java.util.Arrays;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

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
public class ItemsUtil
{
    public static final ItemStack AXE = ItemsUtil.setItemMeta(Material.DIAMOND_AXE, 1, (short)0, "&b&lHache de bucheron", null);

    private ItemsUtil()
    {
    }

    private static ItemStack setItemMeta(ItemStack item, String name, String[] lore)
    {
        ItemMeta meta = item.getItemMeta();
        if (name != null)
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        if (lore != null)
        {
            String[] colored = new String[lore.length];
            int i = 0;
            for (String l : lore)
            {
                colored[i] = ChatColor.translateAlternateColorCodes('&', l);
                i++;
            }
            meta.setLore(Arrays.asList(colored));
        }
        item.setItemMeta(meta);
        return item;
    }

    private static ItemStack setItemMeta(Material material, int i, short j, String name, String[] lore)
    {
        ItemStack item = new ItemStack(material, i);
        item.setDurability(j);
        return setItemMeta(item, name, lore);
    }

    public static ItemStack createHead(String user)
    {
        ItemStack head = new ItemStack(Material.SKULL_ITEM);
        head.setDurability((short)3);
        SkullMeta meta = (SkullMeta)head.getItemMeta();
        meta.setOwner(user);
        meta.setDisplayName(ChatColor.GOLD + user);
        head.setItemMeta(meta);
        return head;
    }
}

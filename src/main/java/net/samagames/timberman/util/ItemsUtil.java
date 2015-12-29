package net.samagames.timberman.util;

import java.util.Arrays;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemsUtil
{
	public static final ItemStack AXE = setItemMeta(Material.DIAMOND_AXE, 1, (short)0, "&b&lHache de bucheron", null);
	
	private ItemsUtil(){}
	
	public static ItemStack setItemMeta(ItemStack item, String name, String[] lore)
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

	public static ItemStack setItemMeta(Material material, int i, short j, String name, String[] lore)
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

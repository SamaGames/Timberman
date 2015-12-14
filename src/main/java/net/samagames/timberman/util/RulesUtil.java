package net.samagames.timberman.util;

import net.samagames.timberman.Timberman;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class RulesUtil
{
	private RulesUtil() {}

	public static ItemStack getRulesBook()
	{
		String[] raw = new String[]{
				"\n   ]--------------[" +
				"\n      " + Timberman.NAME_BICOLOR + "§0" +
				"\n    par §lSamaGames§0" +
				"\n   ]--------------[" +
				"\n" +
				"\n" +
				"\n §11.§0 Comment jouer ?",
				
				"\n §lComment jouer ?§0\n" +
				"\n ",
				
				"\n\nJeu développé par :" +
				"\n\n - §lRigner§0" +
				"\n\nInspiré par le jeu de" +
				"\n\n - §lJohnSHEPARD§0" +
				"\n\n\n      SamaGames" + 
				"\n Tout droits réservés."
		};
		ItemStack item = ItemsUtil.setItemMeta(Material.WRITTEN_BOOK, 1, (short)0, "&6&lLivre de règles", null);
		BookMeta meta = (BookMeta)item.getItemMeta();
		meta.addPage(raw);
		item.setItemMeta(meta);
		return item;
	}
}

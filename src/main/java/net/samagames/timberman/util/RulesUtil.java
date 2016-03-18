package net.samagames.timberman.util;

import net.samagames.timberman.Timberman;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class RulesUtil
{
    private static ItemStack book = null;

    private RulesUtil() {
    }

    public static ItemStack getRulesBook()
    {
        if (book != null)
            return book;

        String[] raw = new String[]{
                "\n   ]--------------[" +
                "\n      " + Timberman.NAME_BICOLOR + "§0" +
                "\n    par §lSamaGames§0" +
                "\n   ]--------------[" +
                "\n" +
                "\n" +
                "\n §11.§0 Objectif\n" +
                "\n §12.§0 Comment jouer ?",

                "\n    §lObjectif§0\n" +
                "\n Vous devez couper" +
                "\n un arbre sans vous" +
                "\n prendre de branche.",

                "\n §lComment jouer ?§0\n" +
                " Utilisez la hache" +
                "\n pour couper, et" +
                "\n déplacez vous de" +
                "\n droite à gauche" +
                "\n pour esquiver.",

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

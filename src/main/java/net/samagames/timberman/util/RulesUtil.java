package net.samagames.timberman.util;

import net.samagames.timberman.Timberman;

import net.samagames.tools.RulesBook;
import org.bukkit.inventory.ItemStack;

public class RulesUtil
{
    private static ItemStack book = null;

    private RulesUtil() {
    }

    public static ItemStack getRulesBook()
    {
        if (book != null)
            return book;
        book = new RulesBook(Timberman.NAME_BICOLOR)
                .addOwner("Rigner").addContributor("JonhSHEPARD")
                .addPage("Objectifs",
                        " Vous devez couper\n" +
                        " un arbre sans vous\n" +
                        " prendre de branche.")
                .addPage("Comment jouer",
                        " Utilisez la hache\n" +
                        " pour couper, et\n" +
                        " déplacez vous de\n" +
                        " droite à gauche\n" +
                        " pour esquiver.")
                .toItemStack();
        return book;
    }
}

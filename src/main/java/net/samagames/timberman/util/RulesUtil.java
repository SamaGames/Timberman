package net.samagames.timberman.util;

import net.samagames.timberman.Timberman;

import net.samagames.tools.RulesBook;
import org.bukkit.inventory.ItemStack;

public class RulesUtil
{
    private static ItemStack BOOK = null;

    private RulesUtil()
    {
    }

    public static ItemStack getRulesBook()
    {
        return RulesUtil.BOOK;
    }

    static
    {
        RulesUtil.BOOK = new RulesBook(Timberman.NAME_BI_COLOR)
            .addOwner("JonhSHEPARD")
            .addContributor("Rigner")
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
    }
}

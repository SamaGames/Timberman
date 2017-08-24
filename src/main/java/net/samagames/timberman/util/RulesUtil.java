package net.samagames.timberman.util;

import net.samagames.timberman.Timberman;

import net.samagames.tools.RulesBook;
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

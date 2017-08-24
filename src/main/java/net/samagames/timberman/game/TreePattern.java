package net.samagames.timberman.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.block.BlockFace;

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
public class TreePattern
{
    private Map<Integer, BlockFace> branches = new HashMap<>();
    private int height;

    public TreePattern(int height)
    {
        this.height = height;

        Random r = new Random();

        int wait = 3;

        for (int i = 0; i < height; i++)
        {
            if (wait > 0)
            {
                wait--;
                this.branches.put(i, BlockFace.SELF);
            }
            else
            {
                int value = r.nextInt(9);

                switch (value)
                {
                case 1:
                case 6:
                case 3:
                    this.branches.put(i, BlockFace.EAST);
                    wait = 2;
                    break ;
                case 5:
                case 8:
                case 7:
                    this.branches.put(i, BlockFace.WEST);
                    wait = 2;
                    break ;
                default:
                    this.branches.put(i, BlockFace.SELF);
                    break ;
                }
            }
        }
    }

    Map<Integer, BlockFace> getBranches()
    {
        return this.branches;
    }

    int getHeight()
    {
        return this.height;
    }
}

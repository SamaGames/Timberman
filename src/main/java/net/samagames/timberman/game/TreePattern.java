package net.samagames.timberman.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.block.BlockFace;

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

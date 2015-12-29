package net.samagames.timberman.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.block.BlockFace;

public class TreePattern
{	
    private HashMap<Integer, BlockFace> branchs = new HashMap<Integer, BlockFace>();
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
                wait --;
                branchs.put(i, BlockFace.SELF);
            }
            else
            {
                int value = r.nextInt(9);

                switch (value) {
                case 1:
                case 6:
                case 3:
                    branchs.put(i, BlockFace.EAST);
                    wait = 2;
                    break;
                case 5:
                case 8:
                case 7:
                    branchs.put(i, BlockFace.WEST);
                    wait = 2;
                    break;
                default:
                    branchs.put(i, BlockFace.SELF);
                    break;
                }
            }
        }
    }

    public Map<Integer, BlockFace> getBranchs()
    {
        return branchs;
    }

    public int getHeight()
    {
        return height;
    }
}

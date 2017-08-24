package net.samagames.timberman.game;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import net.samagames.api.games.GamePlayer;
import net.samagames.api.games.Status;
import net.samagames.timberman.Timberman;
import net.samagames.timberman.util.ItemsUtil;
import net.samagames.timberman.util.RulesUtil;
import net.samagames.tools.chat.fanciful.FancyMessage;

import net.samagames.tools.scoreboards.ObjectiveSign;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffectType;

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
public class TMPlayer extends GamePlayer
{
    private Map<Integer, Map<Integer, Material>> treeBlocks;
    private Location treeLocation;
    private ArmorStand standLeft;
    private ArmorStand standRight;
    private int currentPosition;
    private int toDown;
    private ObjectiveSign objective;
    private TMGame game;

    public TMPlayer(Player player)
    {
        super(player);
        this.objective = new ObjectiveSign("timberman", ChatColor.GOLD + "Timberman");
        this.objective.addReceiver(this.getOfflinePlayer());
        game = Timberman.getPlugin(Timberman.class).getGame();
        this.updateScoreboard();
    }

    void startGame(Timberman plugin, Player p)
    {
        Location gameLocation = plugin.newGameSpawn();
        this.treeLocation = plugin.newTreeSpawn();
        this.currentPosition = 0;
        this.toDown = 0;
        this.treeBlocks = new HashMap<>();

        TreePattern tree = plugin.getTreePattern();
        for (int i = 0; i < tree.getHeight(); i++)
        {
            Map<Integer, Material> blocks = new HashMap<>();

            blocks.put(0, Material.LOG);

            BlockFace bF = tree.getBranches().get(i);

            if (bF == BlockFace.EAST)
            {
                blocks.put(1, Material.LOG);
                blocks.put(-1, Material.AIR);
            }
            else if (bF == BlockFace.WEST)
            {
                blocks.put(1, Material.AIR);
                blocks.put(-1, Material.LOG);
            }
            else
            {
                blocks.put(-1, Material.AIR);
                blocks.put(1, Material.AIR);
            }

            this.treeBlocks.put(this.toDown, blocks);

            this.toDown++;
        }

        Location left = this.treeLocation.clone().add(1.3, 0, 0);
        left.setYaw(90);
        this.standLeft = (ArmorStand) this.treeLocation.getWorld().spawnEntity(left, EntityType.ARMOR_STAND);
        this.standLeft.setArms(true);
        this.standLeft.setBasePlate(false);
        this.standLeft.setCanPickupItems(false);
        this.standLeft.setGravity(false);
        this.standLeft.setVisible(false);
        this.standLeft.setRemoveWhenFarAway(false);

        Location right = this.treeLocation.clone().add(-1.3, 0, 0);
        right.setYaw(-90);
        this.standRight = (ArmorStand) treeLocation.getWorld().spawnEntity(right, EntityType.ARMOR_STAND);
        this.standRight.setArms(true);
        this.standRight.setBasePlate(false);
        this.standRight.setCanPickupItems(false);
        this.standRight.setGravity(false);
        this.standRight.setVisible(false);
        this.standRight.setRemoveWhenFarAway(false);

        moveLeft();

        gameLocation.clone().add(0, -1, 0).getBlock().setMetadata("pos", new FixedMetadataValue(plugin, "Rigner is god"));

        p.teleport(gameLocation);
        p.addPotionEffect(PotionEffectType.JUMP.createEffect(Integer.MAX_VALUE, 128));
        showTree(plugin, p);
    }

    public void moveLeft()
    {
        if (!this.standLeft.isVisible())
        {
            this.standRight.setHelmet(new ItemStack(Material.AIR));
            this.standRight.setChestplate(new ItemStack(Material.AIR));
            this.standRight.setLeggings(new ItemStack(Material.AIR));
            this.standRight.setBoots(new ItemStack(Material.AIR));
            this.standRight.setItemInHand(new ItemStack(Material.AIR));
            this.standRight.setVisible(false);

            this.standLeft.setItemInHand(new ItemStack(Material.DIAMOND_AXE));
            this.standLeft.setVisible(true);
            this.standLeft.setHelmet(ItemsUtil.createHead(this.getOfflinePlayer().getName()));
            this.standLeft.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
            this.standLeft.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
            this.standLeft.setBoots(new ItemStack(Material.LEATHER_BOOTS));
        }
    }

    public void moveRight()
    {
        if (!this.standRight.isVisible())
        {
            this.standLeft.setHelmet(new ItemStack(Material.AIR));
            this.standLeft.setChestplate(new ItemStack(Material.AIR));
            this.standLeft.setLeggings(new ItemStack(Material.AIR));
            this.standLeft.setBoots(new ItemStack(Material.AIR));
            this.standLeft.setItemInHand(new ItemStack(Material.AIR));
            this.standLeft.setVisible(false);

            this.standRight.setItemInHand(new ItemStack(Material.DIAMOND_AXE));
            this.standRight.setVisible(true);
            this.standRight.setHelmet(ItemsUtil.createHead(this.getOfflinePlayer().getName()));
            this.standRight.setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
            this.standRight.setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
            this.standRight.setBoots(new ItemStack(Material.LEATHER_BOOTS));
        }
    }

    @SuppressWarnings("deprecation")
    private void showTree(Timberman plugin, Player p)
    {
        int startPosition = currentPosition;
        int maxShow = plugin.getTreeShowHeight();

        for (int i = 0; i <= maxShow; i++)
        {
            Map<Integer, Material> blocs = new HashMap<>();

            if (this.treeBlocks.containsKey(startPosition + i))
                blocs = this.treeBlocks.get(startPosition + i);
            else
            {
                blocs.put(-1, Material.AIR);
                blocs.put(0, Material.AIR);
                blocs.put(1, Material.AIR);
            }

            for (Entry<Integer, Material> entry : blocs.entrySet())
                p.sendBlockChange(this.treeLocation.clone().add(entry.getKey(), (double) i + 1, 0), entry.getValue(), (byte) 0);
        }

    }

    public void treeBreak(Timberman plugin, Player p)
    {
        if (this.spectator)
            return ;
        this.currentPosition++;

        showTree(plugin, p);

        p.playSound(this.treeLocation, Sound.BLOCK_WOOD_BREAK, 1.0F, 1.0F);
        p.playEffect(this.treeLocation, Effect.STEP_SOUND, Material.LOG);

        Material left = Material.AIR;
        if (this.treeBlocks.containsKey(this.currentPosition) && this.treeBlocks.get(this.currentPosition).containsKey(1))
            left = this.treeBlocks.get(this.currentPosition).get(1);

        Material right = Material.AIR;
        if (this.treeBlocks.containsKey(this.currentPosition) && this.treeBlocks.get(this.currentPosition).containsKey(-1))
            right = this.treeBlocks.get(this.currentPosition).get(-1);
        treeBreak2(plugin, p, left, right);
    }

    private void treeBreak2(Timberman plugin, Player p, Material left, Material right)
    {
        if ((this.standLeft.isVisible() && left != Material.AIR) || (this.standRight.isVisible() && right != Material.AIR))
            plugin.getGame().lose(this);
        else
        {
            p.setExp((float) this.currentPosition / (float) this.toDown);

            if (this.currentPosition >= this.toDown)
                plugin.getGame().win(this);
        }
    }

    @Override
    public void setSpectator()
    {
        this.spectator = true;
        Player bukkitPlayer = this.getPlayerIfOnline();
        if (bukkitPlayer == null)
            return ;

        bukkitPlayer.getInventory().clear();
        bukkitPlayer.getInventory().setItem(4, RulesUtil.getRulesBook());
        bukkitPlayer.setGameMode(GameMode.ADVENTURE);
        bukkitPlayer.setAllowFlight(true);
        bukkitPlayer.setFlying(true);
        bukkitPlayer.removePotionEffect(PotionEffectType.JUMP);

        for (TMPlayer player : this.game.getInGamePlayers().values())
            player.getPlayerIfOnline().hidePlayer(bukkitPlayer);
        new FancyMessage("Cliquez ").color(ChatColor.YELLOW).style(ChatColor.BOLD).then("[ICI]").command("/hub").color(ChatColor.AQUA).style(ChatColor.BOLD).then(" pour retourner au hub !").color(ChatColor.YELLOW).style(ChatColor.BOLD).send(bukkitPlayer);
    }

    private float getProgression()
    {
        return (float) this.currentPosition / (float) this.toDown;
    }

    String getDisplayName()
    {
        Player p = getPlayerIfOnline();
        if (p == null)
            return getOfflinePlayer().getName();
        return p.getDisplayName();
    }

    void updateScoreboard()
    {
        List<TMPlayer> players = new ArrayList<>();
        players.addAll(this.game.getInGamePlayers().values());
        players.addAll(this.game.getSpectatorPlayers().values().stream().filter(player -> !player.isModerator()).collect(Collectors.toList()));
        this.objective.setLine(1, " ");
        this.objective.setLine(2, ChatColor.WHITE + (players.size() > 1 ? " Joueurs : " : " Joueur : ") + ChatColor.GRAY + players.size());

        if (this.game.getStatus() == Status.IN_GAME || this.game.getStatus() == Status.FINISHED)
        {
            this.objective.setLine(3, ChatColor.WHITE + " Temps : " + ChatColor.GRAY + this.game.getTime() + "s");
            this.objective.setLine(4, "  ");
            this.objective.setLine(5, ChatColor.WHITE + " Progression (%) :");
            int i = 6;
            Collections.sort(players, new TMPlayerComparator());
            for (TMPlayer player : players)
            {
                this.objective.setLine(i, "  " + (player.isSpectator() ? ChatColor.RED : ChatColor.WHITE) + player.getOfflinePlayer().getName() + " : " + ChatColor.GRAY + (int) (player.getProgression() * 100));
                i++;
            }
            this.objective.setLine(i, "   ");
        }
        else
            this.objective.setLine(3, "  ");
        this.objective.updateLines();
    }

    private static class TMPlayerComparator implements Comparator<TMPlayer>
    {
        @Override
        public int compare(TMPlayer first, TMPlayer second)
        {
            if ((int)(first.getProgression() * 100) != (int)(second.getProgression() * 100))
                return (int)((second.getProgression() - first.getProgression()) * 100);
            if (first.isSpectator() != second.isSpectator())
                return first.isSpectator() ? -1 : 1;
            return 0;
        }
    }
}

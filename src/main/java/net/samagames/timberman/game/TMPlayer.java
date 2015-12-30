package net.samagames.timberman.game;

import java.util.HashMap;
import java.util.Map.Entry;

import net.samagames.api.games.GamePlayer;
import net.samagames.timberman.Timberman;
import net.samagames.timberman.util.ItemsUtil;
import net.samagames.tools.chat.fanciful.FancyMessage;

import org.bukkit.Bukkit;
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

public class TMPlayer extends GamePlayer {
    private HashMap<Integer, HashMap<Integer, Material>> treeBlocks;
    private Location gameloc;
    private Location treeloc;
    private ArmorStand standleft;
    private ArmorStand standright;
    private int currentPosition;
    private int toDown;

    public TMPlayer(Player player) {
        super(player);
    }

    public void startGame(Timberman plugin, Player p) {
        gameloc = plugin.newGameSpawn();
        treeloc = plugin.newTreeSpawn();
        currentPosition = 0;
        toDown = 0;
        treeBlocks = new HashMap<Integer, HashMap<Integer, Material>>();

        TreePattern tree = plugin.getTreePattern();
        for (int i = 0; i < tree.getHeight(); i++) {

            HashMap<Integer, Material> blocks = new HashMap<Integer, Material>();

            blocks.put(0, Material.LOG);

            BlockFace bF = tree.getBranchs().get(i);

            if (bF == BlockFace.EAST) {
                blocks.put(1, Material.LOG);
                blocks.put(-1, Material.AIR);
            } else if (bF == BlockFace.WEST) {
                blocks.put(1, Material.AIR);
                blocks.put(-1, Material.LOG);
            } else {
                blocks.put(-1, Material.AIR);
                blocks.put(1, Material.AIR);
            }

            treeBlocks.put(toDown, blocks);

            toDown++;
        }

        Location left = treeloc.clone().add(1.3, 0, 0);
        left.setYaw(90);
        this.standleft = (ArmorStand) treeloc.getWorld().spawnEntity(left,
                EntityType.ARMOR_STAND);
        this.standleft.setArms(true);
        this.standleft.setBasePlate(false);
        this.standleft.setCanPickupItems(false);
        this.standleft.setGravity(false);
        this.standleft.setVisible(false);
        this.standleft.setRemoveWhenFarAway(false);

        Location right = treeloc.clone().add(-1.3, 0, 0);
        right.setYaw(-90);
        this.standright = (ArmorStand) treeloc.getWorld().spawnEntity(right,
                EntityType.ARMOR_STAND);
        this.standright.setArms(true);
        this.standright.setBasePlate(false);
        this.standright.setCanPickupItems(false);
        this.standright.setGravity(false);
        this.standright.setVisible(false);
        this.standright.setRemoveWhenFarAway(false);

        moveLeft();

        gameloc.clone()
                .add(0, -1, 0)
                .getBlock()
                .setMetadata("pos",
                        new FixedMetadataValue(plugin, "Rigner is god"));

        p.teleport(gameloc);
        p.addPotionEffect(PotionEffectType.JUMP.createEffect(Integer.MAX_VALUE,
                128));
        showTree(plugin, p);
    }

    public void moveLeft() {
        if (!this.standleft.isVisible()) {
            this.standright.setHelmet(new ItemStack(Material.AIR));
            this.standright.setChestplate(new ItemStack(Material.AIR));
            this.standright.setLeggings(new ItemStack(Material.AIR));
            this.standright.setBoots(new ItemStack(Material.AIR));
            this.standright.setItemInHand(new ItemStack(Material.AIR));
            this.standright.setVisible(false);

            this.standleft.setItemInHand(new ItemStack(Material.DIAMOND_AXE));
            this.standleft.setVisible(true);
            this.standleft.setHelmet(ItemsUtil.createHead(this
                    .getOfflinePlayer().getName()));
            this.standleft.setChestplate(new ItemStack(
                    Material.LEATHER_CHESTPLATE));
            this.standleft
                    .setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
            this.standleft.setBoots(new ItemStack(Material.LEATHER_BOOTS));
        }
    }

    public void moveRight() {
        if (!this.standright.isVisible()) {
            this.standleft.setHelmet(new ItemStack(Material.AIR));
            this.standleft.setChestplate(new ItemStack(Material.AIR));
            this.standleft.setLeggings(new ItemStack(Material.AIR));
            this.standleft.setBoots(new ItemStack(Material.AIR));
            this.standleft.setItemInHand(new ItemStack(Material.AIR));
            this.standleft.setVisible(false);

            this.standright.setItemInHand(new ItemStack(Material.DIAMOND_AXE));
            this.standright.setVisible(true);
            this.standright.setHelmet(ItemsUtil.createHead(this
                    .getOfflinePlayer().getName()));
            this.standright.setChestplate(new ItemStack(
                    Material.LEATHER_CHESTPLATE));
            this.standright
                    .setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
            this.standright.setBoots(new ItemStack(Material.LEATHER_BOOTS));
        }
    }

    @SuppressWarnings("deprecation")
    public void showTree(Timberman plugin, Player p) {
        int startpos = currentPosition;
        int maxshow = plugin.getTreeShowHeight();

        for (int i = 0; i <= maxshow; i++) {
            HashMap<Integer, Material> blocs = new HashMap<Integer, Material>();

            if (treeBlocks.containsKey(startpos + i)) {
                blocs = treeBlocks.get(startpos + i);
            } else {
                blocs.put(-1, Material.AIR);
                blocs.put(0, Material.AIR);
                blocs.put(1, Material.AIR);
            }

            for (Entry<Integer, Material> entry : blocs.entrySet()) {
                p.sendBlockChange(
                        this.treeloc.clone().add(entry.getKey(),
                                (double) i + 1, 0), entry.getValue(), (byte) 0);
            }
        }

    }

    public void treeBreak(Timberman plugin, Player p) {
        currentPosition++;

        showTree(plugin, p);

        p.playSound(treeloc, Sound.DIG_WOOD, 1.0F, 1.0F);
        p.playEffect(treeloc, Effect.STEP_SOUND, Material.LOG);

        Material left = Material.AIR;
        if (treeBlocks.containsKey(currentPosition)
                && treeBlocks.get(currentPosition).containsKey(1))
            left = treeBlocks.get(currentPosition).get(1);

        Material right = Material.AIR;
        if (treeBlocks.containsKey(currentPosition)
                && treeBlocks.get(currentPosition).containsKey(-1))
            right = treeBlocks.get(currentPosition).get(-1);

        boolean value = false;

        if (this.standleft.isVisible() && left != Material.AIR)
            value = true;
        if (this.standright.isVisible() && right != Material.AIR)
            value = true;

        if (value) {
            plugin.getGame().lose(this);
            return;
        }

        p.setExp((float) currentPosition / (float) toDown);

        if (currentPosition >= toDown) {
            plugin.getGame().win(this);
        }
    }

    @Override
    public void setSpectator() {
        this.spectator = true;
        Player bukkitPlayer = this.getPlayerIfOnline();
        if (bukkitPlayer == null)
            return;
        bukkitPlayer.setGameMode(GameMode.ADVENTURE);
        bukkitPlayer.setAllowFlight(true);
        bukkitPlayer.setFlying(true);
        bukkitPlayer.removePotionEffect(PotionEffectType.JUMP);
        for (Player player : Bukkit.getOnlinePlayers())
            player.hidePlayer(bukkitPlayer);
        new FancyMessage("Cliquez ").color(ChatColor.YELLOW)
                .style(ChatColor.BOLD).then("[ICI]").command("/hub")
                .color(ChatColor.AQUA).style(ChatColor.BOLD)
                .then(" pour retourner au hub !").color(ChatColor.YELLOW)
                .style(ChatColor.BOLD).send(bukkitPlayer);
    }

    public float getProgression() {
        return (float) currentPosition / (float) toDown;
    }

    public String getDisplayName() {
        Player p = getPlayerIfOnline();
        if (p == null)
            return getOfflinePlayer().getName();
        return p.getDisplayName();
    }
}

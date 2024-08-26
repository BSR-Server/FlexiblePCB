package heyblack.flexiblepcb.util.rule.remoteRedstone;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class RemoteRedstoneManager {
    private static final Map<BlockPos, RemoteRedstone> posMap = new HashMap<>();
    private static final Map<DyeColor, List<RemoteRedstone>> groupMap = new EnumMap<>(DyeColor.class);

    public static boolean hasRemoteRedstone(BlockPos pos) {
        return posMap.containsKey(pos);
    }

    public static RemoteRedstone getRemoteRedstone(BlockPos pos) {
        return posMap.get(pos);
    }

    public static void addRemoteRedstone(World world, BlockPos pos) {
        if (!posMap.containsKey(pos)) {
            RemoteRedstone remoteRedstone = new RemoteRedstone(null, world, pos);
            posMap.put(pos, remoteRedstone);

            // put the new remote redstone into its group
            DyeColor group = remoteRedstone.getGroup();
            if (groupMap.containsKey(group)) {
                List<RemoteRedstone> list = groupMap.get(group);
                if (!list.contains(remoteRedstone)) {
                    list.add(remoteRedstone);
                    groupMap.put(group, list);
                }
            } else {
                List<RemoteRedstone> list1 = new ArrayList<>();
                list1.add(remoteRedstone);
                groupMap.put(group, list1);
            }

            // send message to players
            for (PlayerEntity player : world.getPlayers()) {
                if (player.isCreativeLevelTwoOp()) {
                    player.sendMessage(
                            new LiteralText("Remote redstone has been added on [" + pos.toShortString() + "] in group " + remoteRedstone.getGroup().asString())
                                    .formatted(getFormatting(remoteRedstone.getGroup())),
                            false
                    );
                }
            }
        } else {
            // remove the original one and add a new one
            RemoteRedstone remoteRedstone = posMap.get(pos);

            DyeColor group = remoteRedstone.getGroup();
            List<RemoteRedstone> list = groupMap.get(group);
            list.remove(remoteRedstone);
            groupMap.put(group, list);

            posMap.remove(pos);

            addRemoteRedstone(world, pos);
        }
    }

    public static void changeGroup(RemoteRedstone remoteRedstone, DyeColor newGroup) {
        DyeColor oldGroup = remoteRedstone.getGroup();
        List<RemoteRedstone> list = groupMap.get(oldGroup);
        list.remove(remoteRedstone);
        groupMap.put(oldGroup, list);

        List<RemoteRedstone> newList = groupMap.get(newGroup);
        if (newList == null) {
            newList = new ArrayList<>();
        }
        newList.add(remoteRedstone);
        groupMap.put(newGroup, newList);
        remoteRedstone.setGroup(newGroup);

        for (PlayerEntity player : remoteRedstone.getWorld().getPlayers()) {
            if (player.isCreativeLevelTwoOp()) {
                player.sendMessage(
                        new LiteralText("Changed the group of remote redstone on [" + remoteRedstone.getPos().toShortString() + "] to " + remoteRedstone.getGroup().asString())
                                .formatted(getFormatting(remoteRedstone.getGroup())),
                        false
                );
            }
        }
    }

    public static List<RemoteRedstone> getInstanceListByGroup(DyeColor group) {
        return groupMap.get(group);
    }

    public static void remove(RemoteRedstone remoteRedstone) {
        DyeColor group = remoteRedstone.getGroup();
        List<RemoteRedstone> list = getInstanceListByGroup(group);
        list.remove(remoteRedstone);
        groupMap.put(group, list);
        posMap.remove(remoteRedstone.getPos());

        for (PlayerEntity player : remoteRedstone.getWorld().getPlayers()) {
            if (player.isCreativeLevelTwoOp()) {
                player.sendMessage(
                        new LiteralText("Removed remote redstone on [" + remoteRedstone.getPos().toShortString() + "] which is in group " + remoteRedstone.getGroup().asString())
                                .formatted(getFormatting(remoteRedstone.getGroup())),
                        false
                );
            }
        }
    }

    public static void removeAll() {
        posMap.clear();
        groupMap.clear();
    }

    private static final Map<DyeColor, Formatting> colorToFormatting = new HashMap<>();

    static {
        colorToFormatting.put(DyeColor.WHITE, Formatting.WHITE);
        colorToFormatting.put(DyeColor.ORANGE, Formatting.GOLD);
        colorToFormatting.put(DyeColor.MAGENTA, Formatting.LIGHT_PURPLE);
        colorToFormatting.put(DyeColor.LIGHT_BLUE, Formatting.AQUA);
        colorToFormatting.put(DyeColor.YELLOW, Formatting.YELLOW);
        colorToFormatting.put(DyeColor.LIME, Formatting.GREEN);
        colorToFormatting.put(DyeColor.GRAY, Formatting.DARK_GRAY);
        colorToFormatting.put(DyeColor.LIGHT_GRAY, Formatting.GRAY);
        colorToFormatting.put(DyeColor.CYAN, Formatting.DARK_AQUA);
        colorToFormatting.put(DyeColor.PURPLE, Formatting.DARK_PURPLE);
        colorToFormatting.put(DyeColor.BLUE, Formatting.BLUE);
        colorToFormatting.put(DyeColor.RED, Formatting.RED);
        colorToFormatting.put(DyeColor.PINK, Formatting.LIGHT_PURPLE);
        colorToFormatting.put(DyeColor.BROWN, Formatting.DARK_RED);
        colorToFormatting.put(DyeColor.GREEN, Formatting.DARK_GREEN);
        colorToFormatting.put(DyeColor.BLACK, Formatting.BLACK);
    }

    public static Formatting getFormatting(DyeColor color) {
        return colorToFormatting.get(color);
    }
}

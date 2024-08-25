package heyblack.flexiblepcb.util.rule.remoteRedstone;

import net.minecraft.util.DyeColor;
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
            putInGroup(remoteRedstone);
        } else {
            RemoteRedstone remoteRedstone = posMap.get(pos);

            DyeColor group = remoteRedstone.getGroup();
            List<RemoteRedstone> list = groupMap.get(group);
            list.remove(remoteRedstone);
            groupMap.put(group, list);

            posMap.remove(pos);

            addRemoteRedstone(world, pos);
        }
    }

    public static void putInGroup(RemoteRedstone remoteRedstone) {
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
    }

    public boolean firstInGroup(RemoteRedstone remoteRedstone, DyeColor group) {
        return groupMap.get(group).isEmpty();
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
    }

    public void removeAll() {
        posMap.clear();
        groupMap.clear();
    }
}

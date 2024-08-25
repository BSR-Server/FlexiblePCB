package heyblack.flexiblepcb.util.rule.remoteRedstone;

import net.minecraft.block.Blocks;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class RemoteRedstone {
    private DyeColor group;
    private final BlockPos pos;
    private boolean sender;
    private int remoteSignal;
    private final World world;

    public RemoteRedstone(DyeColor group, World world, BlockPos pos) {
        this.group = group == null ? DyeColor.WHITE : group;
        this.pos = pos;
        this.sender = false;
        this.remoteSignal = 0;
        this.world = world;
    }

    public void notifySignalToGroup(int i) {
        List<RemoteRedstone> list = RemoteRedstoneManager.getInstanceListByGroup(this.group);
        list.remove(this);

        for (RemoteRedstone r : list) {
            if (r != null) {
                r.setRemoteSignal(i);
                r.update();
            }
        }
    }

    public void update() {
        if (this.world.getBlockState(this.pos).isOf(Blocks.REDSTONE_WIRE)) {
            this.world.getBlockState(this.pos).neighborUpdate(world, pos, Blocks.REDSTONE_WIRE, pos, false);
        }
    }

    public void setRemoteSignal(int i) {
        this.remoteSignal = i;
    }

    public int getRemoteSignal() {
        return this.remoteSignal;
    }

    public BlockPos getPos() {
        return this.pos;
    }

    public DyeColor getGroup() {
        return this.group;
    }

    public void setGroup(DyeColor newGroup) {
        RemoteRedstoneManager.changeGroup(this, newGroup);
        this.group = newGroup;
    }

    public boolean isSender() {
        return this.sender;
    }

    public void flipSenderState() {
        this.sender = !this.sender;
    }
}

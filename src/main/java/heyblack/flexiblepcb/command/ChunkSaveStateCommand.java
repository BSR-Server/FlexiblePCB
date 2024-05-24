package heyblack.flexiblepcb.command;

import carpet.settings.SettingsManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import heyblack.flexiblepcb.FlexiblePCBSettings;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ChunkSaveStateCommand {
    static Map<World, ArrayList<ChunkPos>> map = new HashMap<>();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("chunksavestate")
                .requires((player -> SettingsManager.canUseCommand(player, FlexiblePCBSettings.commandChunkSaveState)))
                .then(CommandManager.literal("add")
                        .executes(context -> markChunkForSaveState(context.getSource()))
                )
                .then(CommandManager.literal("remove")
                        .executes(context -> removeChunkForSaveState(context.getSource(), new ChunkPos(context.getSource().getPlayer().getBlockPos())))

                        .then(CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                                .executes(context -> removeChunkForSaveState(context.getSource(), new ChunkPos(BlockPosArgumentType.getBlockPos(context, "pos"))))
                        )
                        .then(CommandManager.literal("all")
                                .executes(context -> removeAll(context.getSource()))
                        )
                )
                .then(CommandManager.literal("list")
                        .executes(context -> listChunkForSaveState(context.getSource())))
        );
    }

    public static int markChunkForSaveState(ServerCommandSource source) throws CommandSyntaxException {
        World world = source.getPlayer().getEntityWorld();
        ChunkPos pos = new ChunkPos(source.getPlayer().getBlockPos());

        String addedText = String.format("Added %s in %s to the savestate list", pos.toString(), getDimensionID(world));

        if (map.get(world) != null && !map.get(world).contains(pos)) {
            ArrayList<ChunkPos> list = map.get(world);
            list.add(pos);
            map.put(world, list);
            source.getPlayer().sendMessage(Text.of(addedText), false);
        }
        else if (map.get(world) != null) {
            source.getPlayer().sendMessage(Text.of("This chunk has already been added to the savestate list!"), false);
        }
        else {
            ArrayList<ChunkPos> list = new ArrayList<>();
            list.add(pos);
            map.put(world, list);
            source.getPlayer().sendMessage(Text.of(addedText), false);
        }

        return 1;
    }

    public static int removeChunkForSaveState(ServerCommandSource source, ChunkPos targetPos) throws CommandSyntaxException {
        World world = source.getPlayer().getEntityWorld();

        if (map.get(world) != null && map.get(world).contains(targetPos)) {
            ArrayList<ChunkPos> list = map.get(world);
            list.remove(targetPos);
            map.put(world, list);
            source.getPlayer().sendMessage(Text.of(String.format("Removed %s in %s from the savestate list", targetPos.toString(), getDimensionID(world))), false);
        }
        else if (map.get(world) != null) {
            source.getPlayer().sendMessage(Text.of("This chunk has not been added to the savestate list!"), false);
        }
        else {
            ArrayList<ChunkPos> list = new ArrayList<>();
            map.put(world, list);
            source.getPlayer().sendMessage(Text.of("Current world has empty savestate list!"), false);
        }

        return 1;
    }

    public static int removeAll(ServerCommandSource source) throws CommandSyntaxException {
        map = new HashMap<>();
        source.getPlayer().sendMessage(Text.of("Removed all chunks from the savestate list"), false);

        return 1;
    }

    public static int listChunkForSaveState(ServerCommandSource source) throws CommandSyntaxException {
        PlayerEntity player = source.getPlayer();
        ArrayList<ChunkPos> list = new ArrayList<>();
        Set<World> set = map.keySet();

        if (!set.isEmpty()) {
            for (World world : set) {
                list = map.get(world);

                if (list != null && !list.isEmpty()) {
                    player.sendMessage(Text.of(String.format("Chunk savestate list for %s:", getDimensionID(world))), false);

                    for (ChunkPos pos : list) {
                        player.sendMessage(Text.of(pos.toString()), false);
                    }
                }
            }

            return 1;
        }
        player.sendMessage(Text.of("Chunk savestate list is empty"), false);

        return 0;
    }

    public static ArrayList<ChunkPos> getMarkedChunks(World world) {
        return map.get(world);
    }

    public static String getDimensionID(World world) {
        if (world.getRegistryKey() == RegistryKey.of(Registry.WORLD_KEY, new Identifier("overworld"))) {
            return "overworld";
        } else if (world.getRegistryKey() == RegistryKey.of(Registry.WORLD_KEY, new Identifier("the_nether"))) {
            return "the_nether";
        } else if (world.getRegistryKey() == RegistryKey.of(Registry.WORLD_KEY, new Identifier("the_end"))) {
            return "the_end";
        } else {
            return "unidentified";
        }
    }
}

package heyblack.flexiblepcb.hook;

import net.fabricmc.loader.api.FabricLoader;

public class Names {
    public static final String Entity;
    public static final String Entity_class;
    public static final String Entity_move;
    public static final String Entity_move_desc;
    public static final String Vec3d;
    public static final String Block_onEntityLand;
    public static final String Block_onEntityLand_desc;
    public static final String Hooks_checkVelocity_desc;

    static {
        String currentNamespace = FabricLoader.getInstance().getMappingResolver().getCurrentRuntimeNamespace();
        if (!currentNamespace.equals("intermediary")) {
            Entity = "net/minecraft/entity/Entity";
            Entity_move = "move";
            Entity_move_desc = "(Lnet/minecraft/entity/MovementType;Lnet/minecraft/util/math/Vec3d;)V";
            Vec3d = "net/minecraft/util/math/Vec3d";

            Block_onEntityLand = "onEntityLand";
            Block_onEntityLand_desc = "(Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;)V";

            Hooks_checkVelocity_desc = "(Lnet/minecraft/block/Block;Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;)V";
        } else {
            Entity = "net/minecraft/class_1297";
            Entity_move = "method_5784";
            Entity_move_desc = "(Lnet/minecraft/class_1313;Lnet/minecraft/class_243;)V";
            Vec3d = "net/minecraft/class_243";

            Block_onEntityLand = "method_9502";
            Block_onEntityLand_desc = "(Lnet/minecraft/class_1922;Lnet/minecraft/class_1297;)V";
            Hooks_checkVelocity_desc = "(Lnet/minecraft/class_2248;Lnet/minecraft/class_1922;Lnet/minecraft/class_1297;Lnet/minecraft/class_243)V";
        }
        Entity_class = Entity.replace("/", ".");
    }
}

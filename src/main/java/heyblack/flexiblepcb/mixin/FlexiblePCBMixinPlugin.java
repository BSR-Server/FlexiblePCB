package heyblack.flexiblepcb.mixin;

import heyblack.flexiblepcb.hook.Names;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public class FlexiblePCBMixinPlugin implements IMixinConfigPlugin {
    @Override
    public void onLoad(String mixinPackage) {

    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
//        System.out.println("targetClassName = " + targetClassName);
        if (targetClassName.equals(Names.Entity_class)) {
            for (MethodNode method : targetClass.methods) {
                if (method.name.equals(Names.Entity_move) && method.desc.equals(Names.Entity_move_desc)) {
//                    System.out.println("method = " + method);
                    ListIterator<AbstractInsnNode> iter = method.instructions.iterator();
                    while (iter.hasNext()) {
                        AbstractInsnNode insnNode = iter.next();
                        //INVOKEVIRTUAL net/minecraft/block/Block.onEntityLand (Lnet/minecraft/world/BlockView;Lnet/minecraft/entity/Entity;)V
                        if (insnNode instanceof MethodInsnNode
                                && insnNode.getOpcode() == Opcodes.INVOKEVIRTUAL
                                && ((MethodInsnNode) insnNode).name.equals(Names.Block_onEntityLand)
                                && ((MethodInsnNode) insnNode).desc.equals(Names.Block_onEntityLand_desc)
                        ){
                            iter.remove();
                            iter.add(
                                    new VarInsnNode(Opcodes.ALOAD, 3)
                            );
                            iter.add(
                                    new MethodInsnNode(
                                            Opcodes.INVOKESTATIC,
                                            "heyblack/flexiblepcb/hook/Hooks",
                                            "checkVelocity",
                                            Names.Hooks_checkVelocity_desc
                                    )
                            );
                        }
                    }
                }
            }
        }
    }
}

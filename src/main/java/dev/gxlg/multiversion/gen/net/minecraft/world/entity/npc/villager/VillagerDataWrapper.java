package dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class VillagerDataWrapper extends R.RWrapper<VillagerDataWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_3850/net.minecraft.world.entity.npc.villager.VillagerData");

    private int superCall = 0;

    protected VillagerDataWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.core.HolderWrapper profession(){
        return dev.gxlg.multiversion.gen.net.minecraft.core.HolderWrapper.inst(clazz.inst(this.instance).mthd("comp_3521/profession", dev.gxlg.multiversion.gen.net.minecraft.core.HolderWrapper.clazz.self()).invk());
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerProfessionWrapper getProfession(){
        return dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerProfessionWrapper.inst(clazz.inst(this.instance).mthd("method_16924/getProfession", dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerProfessionWrapper.clazz.self()).invk());
    }

    public static VillagerDataWrapper inst(Object instance) {
        return instance == null ? null : new VillagerDataWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") VillagerDataWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
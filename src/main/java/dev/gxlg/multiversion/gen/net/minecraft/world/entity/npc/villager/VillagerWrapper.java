package dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class VillagerWrapper extends dev.gxlg.multiversion.gen.net.minecraft.world.entity.EntityWrapper {
    public static final R.RClass clazz = R.clz("net.minecraft.class_1646/net.minecraft.world.entity.npc.villager.Villager");

    private int superCall = 0;

    protected VillagerWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerDataWrapper getVillagerData(){
        return dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerDataWrapper.inst(clazz.inst(this.instance).mthd("method_7231/getVillagerData", dev.gxlg.multiversion.gen.net.minecraft.world.entity.npc.villager.VillagerDataWrapper.clazz).invk());
    }

    public static VillagerWrapper inst(Object instance) {
        return instance == null ? null : new VillagerWrapper(instance);
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") VillagerWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return dev.gxlg.multiversion.gen.net.minecraft.world.entity.EntityWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
package dev.gxlg.multiversion.gen.net.minecraft.commands.arguments;

import dev.gxlg.multiversion.R;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public class EntityAnchorArgument$AnchorWrapper extends R.RWrapper<EntityAnchorArgument$AnchorWrapper> {
    public static final R.RClass clazz = R.clz("net.minecraft.class_2183$class_2184/net.minecraft.commands.arguments.EntityAnchorArgument$Anchor");

    private int superCall = 0;

    protected EntityAnchorArgument$AnchorWrapper(Object instance) {
        super(instance);
    }

    public dev.gxlg.multiversion.gen.net.minecraft.world.phys.Vec3Wrapper apply(dev.gxlg.multiversion.gen.net.minecraft.world.entity.EntityWrapper entity){
        return dev.gxlg.multiversion.gen.net.minecraft.world.phys.Vec3Wrapper.inst(clazz.inst(this.instance).mthd("method_9302/apply", dev.gxlg.multiversion.gen.net.minecraft.world.phys.Vec3Wrapper.clazz, dev.gxlg.multiversion.gen.net.minecraft.world.entity.EntityWrapper.clazz).invk(entity.unwrap()));
    }

    public static EntityAnchorArgument$AnchorWrapper inst(Object instance) {
        return instance == null ? null : new EntityAnchorArgument$AnchorWrapper(instance);
    }

    public static dev.gxlg.multiversion.gen.net.minecraft.commands.arguments.EntityAnchorArgument$AnchorWrapper EYES() {
        return dev.gxlg.multiversion.gen.net.minecraft.commands.arguments.EntityAnchorArgument$AnchorWrapper.inst(clazz.fld("field_9851/EYES", dev.gxlg.multiversion.gen.net.minecraft.commands.arguments.EntityAnchorArgument$AnchorWrapper.clazz).get());
    }

    public static class Interceptor {
        @SuppressWarnings("unused")
        @RuntimeType
        public static Object intercept(@Origin Method method, @FieldValue("__wrapper") EntityAnchorArgument$AnchorWrapper wrapper, @AllArguments Object[] args, @SuperCall Callable<?> superCall) throws Exception {
            if (wrapper.superCall > 0) {
                wrapper.superCall--;
                return superCall.call();
            }
            String methodName = method.getName();

            return R.RWrapper.Interceptor.intercept(method, wrapper, args, superCall);
        }
    }
}
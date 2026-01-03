package dev.gxlg.librgetter.utils.types.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(OnlyEffective.Container.class)
public @interface OnlyEffective {
    String when();

    String[] equals();

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Container {
        @SuppressWarnings({ "unused", "UnusedReturnValue" }) OnlyEffective[] value();
    }
}

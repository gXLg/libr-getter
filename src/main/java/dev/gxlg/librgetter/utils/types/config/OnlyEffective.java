package dev.gxlg.librgetter.utils.types.config;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(OnlyEffective.Container.class)
public @interface OnlyEffective {
    String when();
    String[] equals();

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Container {
        @SuppressWarnings("unused")
        OnlyEffective[] value();
    }
}

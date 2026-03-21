package dev.gxlg.librgetter.utils.types.config;

import dev.gxlg.librgetter.utils.config.ConfigManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigCategory {
    ConfigManager.Category value();
}

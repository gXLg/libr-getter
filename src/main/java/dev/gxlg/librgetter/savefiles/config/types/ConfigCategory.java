package dev.gxlg.librgetter.savefiles.config.types;

import dev.gxlg.librgetter.savefiles.config.ConfigManager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ConfigCategory {
    ConfigManager.Category value();
}

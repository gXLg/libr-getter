package dev.gxlg.librgetter.commands;

import com.mojang.brigadier.Command;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.exceptions.LibrGetterException;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.com.mojang.brigadier.context.CommandContext;

public class CommandHelper {
    public static Command<?> commandWrapper(CommandRunnable runnable) {
        return ctx -> {
            try {
                runnable.run(R.wrapperInst(CommandContext.class, ctx));
                return 0;
            } catch (LibrGetterException e) {
                Texts.sendMessage(e.getTranslatableErrorMessage());
                return 1;
            }
        };
    }

    @FunctionalInterface
    public interface CommandRunnable {
        void run(CommandContext context) throws LibrGetterException;
    }
}

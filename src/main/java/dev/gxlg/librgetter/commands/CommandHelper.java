package dev.gxlg.librgetter.commands;

import com.mojang.brigadier.Command;
import dev.gxlg.librgetter.utils.chaining.texts.Texts;
import dev.gxlg.librgetter.utils.types.exceptions.librgetter.LibrGetterException;
import dev.gxlg.versiont.api.R;
import dev.gxlg.versiont.gen.com.mojang.brigadier.context.CommandContext;

public class CommandHelper {
    public static Command<?> commandWrapper(CommandRunnable runnable) {
        return ctx -> {
            try {
                runnable.run(R.wrapperInst(CommandContext.class, ctx));
            } catch (LibrGetterException e) {
                Texts.sendMessage(e.getTranslatableErrorMessage());
                return 1;
            }
            return 0;
        };
    }

    @FunctionalInterface
    public interface CommandRunnable {
        void run(CommandContext context) throws LibrGetterException;
    }
}

package dev.gxlg.librgetter.utils.chaining.texts;

import dev.gxlg.librgetter.utils.types.messages.Message;
import dev.gxlg.librgetter.utils.types.messages.translatable.TranslatableMessage;
import dev.gxlg.versiont.api.types.Wrapper;
import dev.gxlg.versiont.gen.net.minecraft.client.Minecraft;
import dev.gxlg.versiont.gen.net.minecraft.client.player.LocalPlayer;
import dev.gxlg.versiont.gen.net.minecraft.locale.Language;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.ClickEvent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.ClickEvent$Action;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.Component;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.HoverEvent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.HoverEvent$Action;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.MutableComponent;
import dev.gxlg.versiont.gen.net.minecraft.network.chat.contents.TranslatableContents;
import dev.gxlg.versiont.gen.net.minecraft.resources.Identifier;

public class Texts_1_17_0 extends Texts.Base {
    protected void sendMessage(Component text, boolean actionbar) {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.getPlayerField();
        if (player == null) {
            return;
        }
        player.displayClientMessage(text, actionbar);
    }

    @Override
    public void sendTranslatable(TranslatableMessage translatableMessage) {
        sendTranslatable(translatableMessage, false);
    }

    @Override
    public void sendTranslatable(TranslatableMessage translatableMessage, boolean actionbar) {
        sendMessage(translatableMessage.getComponent(), actionbar);
    }

    @Override
    public MutableComponent literal(String message) {
        return Component.nullToEmpty(message).plainCopy();
    }

    @Override
    public MutableComponent translatable(String message, Object... args) {
        Object[] convertedArgs = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            if (arg instanceof Wrapper<?> wrapper) {
                // we pass raw objects
                arg = wrapper.unwrap();
            } else if (arg instanceof Message messageObj) {
                // same here: raw Component object instead of ComponentWrapper
                arg = messageObj.getComponent().unwrap();
            }
            convertedArgs[i] = arg;
        }
        return createTranslatable(message, convertedArgs);
    }

    protected MutableComponent createTranslatable(String key, Object... args) {
        return new TranslatableContents(key, args);
    }

    @Override
    public String translateIdentifier(Texts.IdentifierType type, Identifier id) {
        String fullLanguageKey = type.getId() + "." + id.getNamespace() + "." + id.getPath();
        return translateIdentifier(Language.getInstance(), fullLanguageKey);
    }

    protected String translateIdentifier(Language language, String fullLanguageKey) {
        return language.getOrDefault(fullLanguageKey);
    }

    @Override
    public ClickEvent runnable(String command) {
        return new ClickEvent(ClickEvent$Action.RUN_COMMAND(), command);
    }

    @Override
    public ClickEvent paging(int page) {
        String pageString = String.valueOf(page);
        return new ClickEvent(ClickEvent$Action.CHANGE_PAGE(), pageString);
    }

    @Override
    public HoverEvent hoverable(Component text) {
        return new HoverEvent(HoverEvent$Action.SHOW_TEXT(), text);
    }
}

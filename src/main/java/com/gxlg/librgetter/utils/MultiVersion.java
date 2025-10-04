package com.gxlg.librgetter.utils;

import com.gxlg.librgetter.utils.reflection.Minecraft;

import java.util.*;

public class MultiVersion {
    private static final String version;
    private static final Set<ApiLevel> apis = new HashSet<>();

    static {
        version = Minecraft.getVersion();

        Map<ApiLevel, List<String>> map = Map.of(
                ApiLevel.BASE, List.of("1.17"),
                ApiLevel.VILLAGER_PACKET, List.of("1.17.1", "1.18", "1.18.1", "1.18.2"),
                ApiLevel.API_COMMAND_V2, List.of("1.19", "1.19.1", "1.19.2"),
                ApiLevel.TAGS, List.of("1.19.3", "1.19.4", "1.20", "1.20.1"),
                ApiLevel.CUSTOM_PAYLOAD, List.of("1.20.2", "1.20.3", "1.20.4"),
                ApiLevel.COMPONENTS, List.of("1.20.5", "1.20.6"),
                ApiLevel.EFFECTS, List.of("1.21", "1.21.1", "1.21.2", "1.21.3", "1.21.4"),
                ApiLevel.MORE_ABSTRACTION, List.of("1.21.5", "1.21.6", "1.21.7", "1.21.8"),
                ApiLevel.CLIENT_WORLD, List.of("1.21.9")
        );

        for (ApiLevel api : ApiLevel.values()) {
            if (map.get(api).contains(version)) {
                apis.addAll(EnumSet.range(ApiLevel.BASE, api));
                break;
            }
        }
    }

    public static String getVersion() {
        return version;
    }

    public static boolean isApiLevel(ApiLevel level) {
        return apis.contains(level);
    }

    public static List<String> getAPIList() {
        return apis.stream().sorted().map(Enum::name).toList();
    }

    public enum ApiLevel {
        BASE, VILLAGER_PACKET, API_COMMAND_V2, TAGS, CUSTOM_PAYLOAD, COMPONENTS, EFFECTS, MORE_ABSTRACTION, CLIENT_WORLD
    }
}

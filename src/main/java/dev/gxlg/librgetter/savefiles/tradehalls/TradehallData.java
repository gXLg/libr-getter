package dev.gxlg.librgetter.savefiles.tradehalls;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TradehallData {
    public final Map<String, List<String>> tradehalls = new HashMap<>();
    // { "world_name": ["x,y,z", "x,y,z", ...] }
}

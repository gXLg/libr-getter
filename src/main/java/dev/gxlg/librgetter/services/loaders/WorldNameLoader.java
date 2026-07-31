package dev.gxlg.librgetter.services.loaders;

import dev.gxlg.librgetter.savefiles.WorldNameManager;
import dev.gxlg.librgetter.services.ServiceLoader;
import dev.gxlg.librgetter.services.types.Export;

public class WorldNameLoader extends ServiceLoader<WorldNameLoader> {
    public static final Export<WorldNameLoader, WorldNameManager> exportWorldNameManager = new Export<>(s -> s.worldNameManager);

    private WorldNameManager worldNameManager;

    @Override
    public void init() {
        worldNameManager = new WorldNameManager();
    }
}

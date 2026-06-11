package dev.gxlg.librgetter.savefiles;

public class DummySaveFile<T> extends JsonSaveFile<T> {
    public DummySaveFile(T defaultData) {
        super(null, null, defaultData, null);
    }

    @Override
    public void save() {
        // do nothing
    }
}

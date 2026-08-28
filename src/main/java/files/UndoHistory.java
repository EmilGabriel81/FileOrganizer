package files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UndoHistory {

    public static class MoveEntry {
        public File original;
        public File moved;

        public MoveEntry(File original, File moved) {
            this.original = original;
            this.moved = moved;
        }
    }

    private static final List<MoveEntry> history = new ArrayList<>();

    public static void add(File original, File moved) {
        history.add(new MoveEntry(original, moved));
    }

    public static List<MoveEntry> getHistory() {
        return history;
    }

    public static void clear() {
        history.clear();
    }
}

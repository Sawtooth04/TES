package org.sawtooth.utils;

import org.sawtooth.models.solutiontreeitem.SolutionTreeItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class RoomSolutionTreeBuilder {
    public ArrayList<SolutionTreeItem> GetRoomSolutionTree(String path) {
        ArrayList<SolutionTreeItem> result = new ArrayList<>();
        File directory = new File(path);

        for (File file : Objects.requireNonNull(directory.listFiles()))
            result.add(new SolutionTreeItem(file.getName(), file.isDirectory()));
        return result;
    }
}

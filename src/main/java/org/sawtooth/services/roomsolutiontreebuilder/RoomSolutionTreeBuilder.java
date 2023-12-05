package org.sawtooth.services.roomsolutiontreebuilder;

import org.sawtooth.models.solutiontreeitem.SolutionTreeItem;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class RoomSolutionTreeBuilder implements IRoomSolutionTreeBuilder {
    @Override
    public ArrayList<SolutionTreeItem> GetRoomSolutionTree(String path) {
        ArrayList<SolutionTreeItem> result = new ArrayList<>();
        File directory = new File(path);

        for (File file : Objects.requireNonNull(directory.listFiles()))
            result.add(new SolutionTreeItem(file.getName(), file.isDirectory()));
        return result;
    }
}

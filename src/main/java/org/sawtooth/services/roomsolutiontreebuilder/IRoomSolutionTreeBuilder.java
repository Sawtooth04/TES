package org.sawtooth.services.roomsolutiontreebuilder;

import org.sawtooth.models.solutiontreeitem.SolutionTreeItem;

import java.util.ArrayList;

public interface IRoomSolutionTreeBuilder {
    public ArrayList<SolutionTreeItem> GetRoomSolutionTree(String path);
}

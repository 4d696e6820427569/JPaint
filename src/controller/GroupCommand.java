package controller;

import model.interfaces.IUndoable;
import model.ShapeList;
import model.AbstractShape;
import java.util.List;

public class GroupCommand implements IUndoable {

    private final ShapeList SHAPE_LIST;
    private List<AbstractShape> groupedShapes;

    public GroupCommand(ShapeList shapeList) {
        this.SHAPE_LIST = shapeList;
        this.groupedShapes = this.SHAPE_LIST.groupShapes(this.SHAPE_LIST.getSelectedShapes());
    }

    @Override
    public void undo() {
        this.groupedShapes = this.SHAPE_LIST.ungroupShapes(this.groupedShapes);
    }

    @Override
    public void redo() {
        this.groupedShapes = this.SHAPE_LIST.groupShapes(this.groupedShapes);
    }

    @Override
    public void printShapeList() {
        System.out.println("Group CMD");
        this.SHAPE_LIST.printShapeList();
    }
}

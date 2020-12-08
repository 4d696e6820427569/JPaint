package controller;

import model.ShapeList;
import model.AbstractShape;
import model.interfaces.IUndoable;
import java.util.List;

public class UngroupCommand implements IUndoable {
    private final ShapeList SHAPE_LIST;
    private List<AbstractShape> ungrouped;

    public UngroupCommand(ShapeList shapeList) {
        this.SHAPE_LIST = shapeList;
        this.ungrouped = this.SHAPE_LIST.ungroupShapes(this.SHAPE_LIST.getSelectedShapes());
    }

    @Override
    public void undo() {
        this.ungrouped = this.SHAPE_LIST.groupShapes(this.ungrouped);
    }

    @Override
    public void redo() {
        this.ungrouped = this.SHAPE_LIST.ungroupShapes(this.ungrouped);
    }

    @Override
    public void printShapeList() {
        System.out.println("Ungrouped CMD");
        this.SHAPE_LIST.printShapeList();
    }
}

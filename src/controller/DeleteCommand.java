package controller;

import java.util.List;

import model.Shape;
import model.AbstractShape;
import model.ShapeList;
import model.interfaces.IUndoable;

public class DeleteCommand implements IUndoable {
	
	private final ShapeList SHAPE_LIST;
	private List<AbstractShape> deletedShapes;
	
	public DeleteCommand(ShapeList shapeList) {
		this.SHAPE_LIST = shapeList;
		deletedShapes = this.SHAPE_LIST.removeSelectedShape();
	}
	
	@Override
	public void undo() {
		this.SHAPE_LIST.addAllShapes(this.deletedShapes);
	}

	@Override
	public void redo() {
		deletedShapes = this.SHAPE_LIST.removeSelectedShape();
	}

	@Override
	public void printShapeList() {
		System.out.println("Delete CMD");
		this.SHAPE_LIST.printShapeList();
	}
}

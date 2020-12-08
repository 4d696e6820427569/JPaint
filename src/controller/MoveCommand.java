package controller;

import java.util.List;

import model.Shape;
import model.AbstractShape;
import model.ShapeList;
import model.interfaces.IUndoable;

public class MoveCommand implements IUndoable {

	private final int DX;
	private final int DY;
	private List<AbstractShape> selectedShapes;
	private final ShapeList SHAPE_LIST;
	
	public MoveCommand(int dX, int dY, ShapeList shapeListObj) {
		this.DX = dX;
		this.DY = dY;
		this.SHAPE_LIST = shapeListObj;
		this.selectedShapes = this.SHAPE_LIST.moveShapes(DX, DY, null);
	}
	
	@Override
	public void undo() {
		this.selectedShapes = SHAPE_LIST.moveShapes(-DX, -DY, selectedShapes);
	}

	@Override
	public void redo() {
		this.selectedShapes = SHAPE_LIST.moveShapes(DX, DY, selectedShapes);
	}

	@Override
	public void printShapeList() {
		System.out.println("Move CMD");
		this.SHAPE_LIST.printShapeList();
	}
}

package controller;

import model.Shape;
import model.ShapeList;
import model.AbstractShape;

import model.interfaces.IUndoable;

public class DrawShapeCommand implements IUndoable {

	private final AbstractShape SHAPE;
	private final ShapeList SHAPE_LIST;
	
	public DrawShapeCommand(AbstractShape shape, ShapeList shapeList) {
		this.SHAPE = shape;
		this.SHAPE_LIST = shapeList;
		this.SHAPE_LIST.addShape(this.SHAPE);
	}
	
	@Override
	public void undo() {
		//System.out.println("Undo was pressed!");
		SHAPE_LIST.removeShape(this.SHAPE);
		//SHAPE_LIST.printShapeList();
	}

	@Override
	public void redo() {
		//System.out.println("Redo was pressed!");
		SHAPE_LIST.addShape(this.SHAPE);
		//SHAPE_LIST.printShapeList();
	}

	@Override
	public void printShapeList() {
		System.out.println("DrawShape CMD");
		this.SHAPE_LIST.printShapeList();
	}

}

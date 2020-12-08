package controller;

import java.util.List;

import model.Shape;
import model.AbstractShape;
import model.ShapeList;
import model.interfaces.IUndoable;

public class PasteCommand implements IUndoable {

	private final ShapeList SHAPE_LIST;
	private List<AbstractShape> pasteShapes;
	
	public PasteCommand(ShapeList shapeList, List<AbstractShape> pasteShapes) {
		this.SHAPE_LIST = shapeList;
		this.pasteShapes = this.SHAPE_LIST.cloneAndAddAllShapes(pasteShapes);
		this.printShapeList();
	}
	
	@Override
	public void undo() {
		this.SHAPE_LIST.removeShapesList(pasteShapes);
	}

	@Override
	public void redo() {
		this.pasteShapes = this.SHAPE_LIST.cloneAndAddAllShapes(pasteShapes);
	}

	@Override
	public void printShapeList() {
		System.out.println("Paste CMD");
		this.SHAPE_LIST.printShapeList();
	}
}

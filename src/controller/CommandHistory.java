package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import model.GroupShapes;
import model.Shape;
import model.AbstractShape;
import model.ShapeList;
import model.interfaces.IUndoable;

import javax.swing.*;


/**
 * Static class that manages commands, that implement IUndoable, performed on JPaint.
 *
 * Functionalities:
 * 	Undo/Redo commands
 * 	Copy/Paste commands
 * 	Delete command
 */

class CommandHistory {
	private static final Stack<IUndoable> UNDO_STACK = new Stack<>();
	private static final Stack<IUndoable> REDO_STACK = new Stack<>();
	private static List<AbstractShape> copyShapesBuffer = null;

	public static void add(IUndoable cmd) {
		UNDO_STACK.push(cmd);
		REDO_STACK.clear();
	}

	public static boolean undo() {
		System.out.println("Undo was pressed!");
		boolean result = !UNDO_STACK.empty();
		if (result) {
			IUndoable c = UNDO_STACK.pop();
			REDO_STACK.push(c);
			c.undo();
			//c.printShapeList();
		}

		return result;
	}

	public static boolean redo() {
		System.out.println("Redo was pressed!");
		boolean result = !REDO_STACK.empty();
		if (result) {
			IUndoable c = REDO_STACK.pop();
			UNDO_STACK.push(c);
			c.redo();
			//c.printShapeList();
		}
		return result;
	}

	public static void copy(ShapeList shapesListObj) {
		List<AbstractShape> copiedShapes = shapesListObj.getSelectedShapes();
		if (copiedShapes.size() == 0) return;
		Iterator<AbstractShape> iter = copiedShapes.iterator();
		copyShapesBuffer = new ArrayList<>();
		while (iter.hasNext()) {
			AbstractShape cur = iter.next();
			if (cur instanceof Shape) {
				Shape copyCur = new Shape(cur.getPrimaryColor(), cur.getSecondaryColor(),
						cur.getShadingType(), cur.getShapeType(), 0, 0,
						Math.abs(cur.getEndX() - cur.getStartX()),
						Math.abs(cur.getEndY() - cur.getStartY()));
				copyShapesBuffer.add(copyCur);
			} else if (cur instanceof GroupShapes) {
				GroupShapes copyCur = new GroupShapes(cur.getPrimaryColor(), cur.getSecondaryColor(),
						cur.getShadingType(), cur.getShapeType(), 0, 0,
						Math.abs(cur.getEndX() - cur.getStartX()),
						Math.abs(cur.getEndY() - cur.getStartY()),
						copyAbstractShapes(((GroupShapes) cur).getShapes()));
			}
		}
	}

	private static List<AbstractShape> copyAbstractShapes(List<AbstractShape> shapes) {
		List<AbstractShape> componentsClone = new ArrayList<>();

		Iterator<AbstractShape> i = shapes.iterator();
		while (i.hasNext()) {
			AbstractShape cur = i.next();
			if (i instanceof GroupShapes) {
				GroupShapes copyCur = new GroupShapes(cur.getPrimaryColor(), cur.getSecondaryColor(),
						cur.getShadingType(), cur.getShapeType(), 0, 0,
						cur.getEndX(), cur.getEndY(), copyAbstractShapes(((GroupShapes) cur).getShapes()));
				componentsClone.add(copyCur);
			} else {
				Shape copyCur = new Shape(cur.getPrimaryColor(), cur.getSecondaryColor(),
						cur.getShadingType(), cur.getShapeType(), 0, 0,
						cur.getEndX(), cur.getEndY());
				componentsClone.add(copyCur);
			}
		}

		return componentsClone;
	}

	public static void paste(ShapeList shapesListObj) {
		if (copyShapesBuffer != null) {
			shapesListObj.deselectAllShapes();
			IUndoable pasteCmd = new PasteCommand(shapesListObj, copyShapesBuffer);
			UNDO_STACK.push(pasteCmd);
			REDO_STACK.clear();
		}
	}

	public static void delete(ShapeList shapesListObj) {
		if (shapesListObj.getNumberOfSelectedShapes() == 0) return;
		IUndoable deleteCmd = new DeleteCommand(shapesListObj);
		UNDO_STACK.push(deleteCmd);
		REDO_STACK.clear();
	}

	public static void group(ShapeList shapesListObj) {
		if (shapesListObj.getNumberOfSelectedShapes() == 0) return;
		IUndoable groupCmd = new GroupCommand(shapesListObj);
		UNDO_STACK.push(groupCmd);
		REDO_STACK.clear();
	}

	public static void ungroup(ShapeList shapesListObj) {
		if (shapesListObj.getNumberOfSelectedShapes() == 0) return;
		IUndoable ungroupCmd = new UngroupCommand(shapesListObj);
		UNDO_STACK.push(ungroupCmd);
		REDO_STACK.clear();
	}
}

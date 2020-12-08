package model.interfaces;

/**
 * IUndoable interface
 *
 * Responsibility: An interface that's part of the command pattern.
 *
 */

public interface IUndoable {
	void undo();
	void redo();
	void printShapeList();
}

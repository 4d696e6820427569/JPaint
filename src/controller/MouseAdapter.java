package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import model.MouseMode;
import model.Shape;
import model.ShapeList;
import model.interfaces.IApplicationState;
import model.persistence.ApplicationState;

/**
 * MouseAdapter accepts input from the mouse pointer and creates appropriates
 * commands for JPaint.
 */

public class MouseAdapter implements MouseListener {

	private static MouseAdapter uniqueInstance;

	private int startX;
	private int startY;
	private int endX;
	private int endY;

	private IApplicationState applicationState;
	private ShapeList shapes;

	private MouseAdapter(ApplicationState appState, ShapeList shapes) {
		this.applicationState = appState;
		this.shapes = shapes;
	}

	public static MouseAdapter getInstance(ApplicationState appState, ShapeList shapes) {
		if (uniqueInstance == null) {
			uniqueInstance = new MouseAdapter(appState, shapes);
		} else {
			uniqueInstance.applicationState = appState;
			uniqueInstance.shapes = shapes;
		}
		return uniqueInstance;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.startX = arg0.getX();
		this.startY = arg0.getY();

		if (applicationState.getActiveMouseMode() == MouseMode.SELECT) {
			this.shapes.markSelect(this.startX + 1, this.startY + 1, 1, 1);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) { }

	@Override
	public void mouseExited(MouseEvent arg0) { }

	@Override
	public void mousePressed(MouseEvent arg0) {
		this.startX = arg0.getX();
		this.startY = arg0.getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		this.endX = arg0.getX();
		this.endY = arg0.getY();

		int width = Math.abs(this.endX - this.startX);
		int height = Math.abs(this.endY - this.startY);

		int x = Math.min(this.startX, this.endX);
		int y = Math.min(this.startY, this.endY);

		if (applicationState.getActiveMouseMode() == MouseMode.DRAW) {
			Shape newShape = new Shape(applicationState.getActivePrimaryColor(),
					applicationState.getActiveSecondaryColor(), applicationState.getActiveShapeShadingType(),
					applicationState.getActiveShapeType(), x, y, x + width, y + height);

			CommandHistory.add(new DrawShapeCommand(newShape, this.shapes));
		} else if (applicationState.getActiveMouseMode() == MouseMode.SELECT) {

			this.shapes.markSelect(x, y, width, height);
		} else if (applicationState.getActiveMouseMode() == MouseMode.MOVE) {
			int dX = this.endX - this.startX;
			int dY = this.endY - this.startY;

			CommandHistory.add(new MoveCommand(dX, dY, this.shapes));
		}
	}
}

package view.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;

import model.*;
import model.interfaces.IShapeListObserver;
import view.interfaces.IDrawShape;
import view.interfaces.PaintCanvasBase;

/**
 * Drawer class
 *
 * Responsibility: Responsible for selecting specific algorithm to draw shapes and their
 * corresponding boundaries from a ShapeList object.
 *
 * @author Minh Bui
 */

public class Drawer implements IShapeListObserver {

	private static Drawer uniqueInstance;

	private PaintCanvasBase canvas;
	private final EnumMap<ShapeColor, Color> colorMap;
	private final EnumMap<ShapeType, IDrawShape> strategies;
	private IDrawShape strategy;

	private final Stroke selectStroke;

	private Drawer(PaintCanvasBase canvas) {
		this.canvas = canvas;

		colorMap = new EnumMap<>(ShapeColor.class);
		colorMap.put(ShapeColor.BLACK, Color.BLACK);
		colorMap.put(ShapeColor.BLUE, Color.BLUE);
		colorMap.put(ShapeColor.CYAN, Color.CYAN);
		colorMap.put(ShapeColor.DARK_GRAY, Color.DARK_GRAY);
		colorMap.put(ShapeColor.GRAY, Color.GRAY);
		colorMap.put(ShapeColor.GREEN, Color.GREEN);
		colorMap.put(ShapeColor.LIGHT_GRAY, Color.LIGHT_GRAY);
		colorMap.put(ShapeColor.MAGENTA, Color.MAGENTA);
		colorMap.put(ShapeColor.ORANGE, Color.ORANGE);
		colorMap.put(ShapeColor.PINK, Color.PINK);
		colorMap.put(ShapeColor.RED, Color.RED);
		colorMap.put(ShapeColor.WHITE, Color.WHITE);
		colorMap.put(ShapeColor.YELLOW, Color.YELLOW);

		strategies = new EnumMap<>(ShapeType.class);

		strategies.put(ShapeType.RECTANGLE, new DrawRectangle());
		strategies.put(ShapeType.ELLIPSE, new DrawEllipse());
		strategies.put(ShapeType.TRIANGLE, new DrawTriangle());

		strategy = null;

		selectStroke = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
				1, new float[]{9}, 0);
	}

	public static Drawer getInstance(PaintCanvasBase canvas) {
		if (uniqueInstance == null)
			uniqueInstance = new Drawer(canvas);
		else
			uniqueInstance.canvas = canvas;
		return uniqueInstance;
	}

	@Override
	public void update(List<AbstractShape> shapeList) {
		// Clear the canvas for redraw
		Graphics2D graphics2d = canvas.getGraphics2D();
		graphics2d.setColor(canvas.getBackground());
		graphics2d.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

		Iterator<AbstractShape> shapeListIter = shapeList.iterator();
		while (shapeListIter.hasNext()) {
			AbstractShape curShape = shapeListIter.next();
			//this.draw2(curShape, curShape.isSelected());
			this.draw(curShape);
		}
	}

	public void draw2(AbstractShape aShape, boolean isSelected) {
		if (aShape != null) {
			// Draw the abstract shape first.
			strategy = strategies.get(aShape.getShapeType());
			if (strategy != null) {
				if (!aShape.isBoundary() && !aShape.isComposite())
					strategy.draw(aShape, canvas, colorMap, selectStroke, false);
				else
					if (isSelected && !aShape.isInGroup())
						strategy.draw(aShape, canvas, colorMap, selectStroke, true);
			}

			// Draw its children.
			List<AbstractShape> aShapeComponents = aShape.getComponents();
			if (aShapeComponents == null) return;
			Iterator<AbstractShape> componentsIter = aShapeComponents.iterator();

			while(componentsIter.hasNext()) {
				AbstractShape component = componentsIter.next();
				draw2(component, isSelected);
			}
		}
	}

	public void draw(AbstractShape aShape) {
		strategy = strategies.get(aShape.getShapeType());
		if (strategy != null) {
			strategy.draw(aShape, canvas, colorMap, selectStroke, false);

			if (aShape instanceof Shape) {
				Shape shape = (Shape) aShape;
				if (shape.isInGroup() && shape.isSelected()) {
					strategy = strategies.get(ShapeType.RECTANGLE);
					strategy.draw(shape.getGroupBoundary(), canvas, colorMap, selectStroke, true);
				} else if (shape.isSelected()) {
					strategy.draw(shape.getBoundary(), canvas, colorMap, selectStroke, true);
				}
			}
		}
	}
}

package view.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.EnumMap;

import model.AbstractShape;
import model.ShapeColor;
import model.ShapeShadingType;
import view.interfaces.IDrawShape;
import view.interfaces.PaintCanvasBase;

/**
 * DrawEllipse class
 *
 * Responsibility: Contains strategy for drawing an ellipse.
 *
 * @author Minh Bui
 */

public class DrawEllipse implements IDrawShape {

	@Override
	public void draw(AbstractShape shape, PaintCanvasBase canvas, EnumMap<ShapeColor, Color> colorMap,
					 Stroke selectStroke, boolean offset) {
		Graphics2D graphics2d = canvas.getGraphics2D();

		graphics2d.setColor(colorMap.get(shape.getPrimaryColor()));

		Stroke customStroke = new BasicStroke(5f);
		
		int width = Math.abs(shape.getEndX() - shape.getStartX());
		int height = Math.abs(shape.getEndY() - shape.getStartY());

		int x = Math.min(shape.getStartX(), shape.getEndX());
		int y = Math.min(shape.getStartY(), shape.getEndY());

		if (offset) {
			x = x - SELECT_STROKE_OFFSET;
			y = y - SELECT_STROKE_OFFSET;
			width = width + SELECT_WIDTH_HEIGHT_OFFSET;
			height = height + SELECT_WIDTH_HEIGHT_OFFSET;
			graphics2d.setStroke(selectStroke);
		} else {
			graphics2d.setStroke(customStroke);
		}

		if (shape.getShadingType() == ShapeShadingType.FILLED_IN) {
			graphics2d.fillOval(x, y, width, height);
		} else if (shape.getShadingType() == ShapeShadingType.OUTLINE) {
			graphics2d.drawOval(x, y, width, height);
		} else {
			graphics2d.fillOval(x, y, width, height);
			graphics2d.setColor(colorMap.get(shape.getSecondaryColor()));
			graphics2d.drawOval(x, y, width, height);
		}

	}
	
}

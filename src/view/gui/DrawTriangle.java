package view.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.util.EnumMap;

import model.AbstractShape;
import model.Shape;
import model.ShapeColor;
import model.ShapeShadingType;
import view.interfaces.IDrawShape;
import view.interfaces.PaintCanvasBase;

/**
 * DrawTriangle class
 *
 * Responsibility: Contains strategy for drawing a triangle.
 **
 * @author Minh Bui
 */

public class DrawTriangle implements IDrawShape {

	@Override
	public void draw(AbstractShape shape, PaintCanvasBase canvas, EnumMap<ShapeColor, Color> colorMap,
					 Stroke selectStroke, boolean offset) {
		Graphics2D graphics2d = canvas.getGraphics2D();
		graphics2d.setColor(colorMap.get(shape.getPrimaryColor()));
		Stroke customStroke = new BasicStroke(5f);

		//int width = Math.abs(shape.getEndX() - shape.getStartX());
		int height = Math.abs(shape.getEndY() - shape.getStartY());

		int x = Math.min(shape.getStartX(), shape.getEndX());
		int y = Math.min(shape.getStartY(), shape.getEndY());
		
		int xMax = Math.max(shape.getStartX(), shape.getEndX());
		int yMax = Math.max(shape.getStartY(), shape.getEndY());

		int[] xPoints = new int[3];
		int[] yPoints = new int[3];

		if (!offset) {
			xPoints[0] = x; xPoints[1] = xMax; xPoints[2] = x;
			yPoints[0] = y; yPoints[1] = yMax; yPoints[2] = y + height;
			graphics2d.setStroke(customStroke);
		} else {
			xPoints[0] = x - SELECT_STROKE_OFFSET;
			xPoints[1] = xMax + SELECT_STROKE_OFFSET * 2;
			xPoints[2] = x - SELECT_STROKE_OFFSET;

			yPoints[0] = y - SELECT_STROKE_OFFSET * 2;
			yPoints[1] = yMax + SELECT_STROKE_OFFSET;
			yPoints[2] = y - SELECT_STROKE_OFFSET + height + SELECT_WIDTH_HEIGHT_OFFSET;
			graphics2d.setStroke(selectStroke);
		}

		Polygon triangle = new Polygon(xPoints, yPoints, 3);
		if (shape.getShadingType() == ShapeShadingType.FILLED_IN) {
			graphics2d.fillPolygon(triangle);	
		} else if (shape.getShadingType() == ShapeShadingType.OUTLINE) {
			graphics2d.drawPolygon(triangle);
		} else {
			graphics2d.fillPolygon(triangle);
			graphics2d.setColor(colorMap.get(shape.getSecondaryColor()));
			graphics2d.drawPolygon(triangle);
		}
	}
}

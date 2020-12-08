package view.interfaces;

import java.awt.Color;
import java.awt.Stroke;
import java.util.EnumMap;

import model.AbstractShape;
import model.ShapeColor;

/**
 * IDrawShape interface
 *
 * Responsibility: Interface for the strategy pattern for drawing shapes.
 *
 * Note that if offset is true, an algorithm for drawing boundary is used instead.
 *
 * @author Minh Bui
 *
 */

public interface IDrawShape {
	int SELECT_STROKE_OFFSET = 5;
	int SELECT_WIDTH_HEIGHT_OFFSET = 10;

	void draw(AbstractShape shape, PaintCanvasBase canvas, EnumMap<ShapeColor, Color> colorMap, Stroke selectStroke,
			  boolean offset);
}

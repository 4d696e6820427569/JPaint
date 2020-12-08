package model;

import view.interfaces.IDrawShape;

import java.util.List;
import java.util.ArrayList;

/**
 * Shape Class
 *
 * Responsibility: Maintains information/metadata about a generic shape.
 *
 * A shape primary's color, secondary color, type, shaping type, selection, and starting and ending coordinates.
 *
 *
 * @author Minh Bui
 */

public class Shape extends AbstractShape {
	private ShapeColor primaryColor;
	private ShapeColor secondaryColor;
	private ShapeShadingType shadingType;
	private ShapeType shapeType;
	private int startX;
	private int startY;
	private int endX;
	private int endY;
	private boolean isSelected;
	private boolean isInGroup;
	private final boolean IS_BOUNDARY = false;
	private final boolean IS_COMPOSITE = false;
	private ShapeBoundary groupBoundary;
	private ShapeBoundary boundary;

	public Shape(ShapeColor primaryColor, ShapeColor secondaryColor, ShapeShadingType shadingType, ShapeType shapeType,
			int startX, int startY, int endX, int endY) {
		this.primaryColor = primaryColor;
		this.secondaryColor = secondaryColor;
		this.shadingType = shadingType;
		this.shapeType = shapeType;
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.isSelected = false;
		this.groupBoundary = null;
		this.isInGroup = false;
		resetBoundary();
	}

	public boolean isBoundary() { return this.IS_BOUNDARY; }

	/* // Uncomment this if use solution with Composite design pattern.

	@Override
	public boolean isInGroup() { return this.isInGroup; }

	 */

	@Override
	public void setIsInGroup(boolean groupFlag) { this.isInGroup = groupFlag; }
	
	public void resetBoundary() {
		this.boundary = new ShapeBoundary(ShapeColor.BLACK, ShapeColor.BLACK, ShapeShadingType.OUTLINE, this.shapeType,
				startX, startY, endX, endY);
	}

	public void setGroupBoundary(int x, int y, int width, int height) {
		this.groupBoundary = new ShapeBoundary(ShapeColor.BLACK, ShapeColor.BLACK, ShapeShadingType.OUTLINE,
				ShapeType.RECTANGLE, x, y, x + width, y + height);
	}

	public void unsetGroupBoundary() { this.groupBoundary = null; }

	public void updateGroupBoundary(int dx, int dy) {
		int[] groupBoundaryParams = this.getGroupBoundaryParams();

		this.groupBoundary = new ShapeBoundary(ShapeColor.BLACK, ShapeColor.BLACK, ShapeShadingType.OUTLINE,
				ShapeType.RECTANGLE, groupBoundaryParams[0] + dx, groupBoundaryParams[1] + dy,
				groupBoundaryParams[0] + groupBoundaryParams[2] + dx,
				groupBoundaryParams[1] + groupBoundaryParams[3] + dy);
	}

	public int[] getGroupBoundaryParams() {
		int[] groupBoundaryParams = new int[4];
		groupBoundaryParams[0] = this.groupBoundary.getStartX();
		groupBoundaryParams[1] = this.groupBoundary.getStartY();
		groupBoundaryParams[2] = this.groupBoundary.getEndX() - this.groupBoundary.getStartX();
		groupBoundaryParams[3] = this.groupBoundary.getEndY() - this.groupBoundary.getStartY();
		return groupBoundaryParams;
	}

	public AbstractShape getBoundary() { return this.boundary; }
	public AbstractShape getGroupBoundary() { return this.groupBoundary; }


	@Override
	public boolean isInGroup() { return this.groupBoundary != null; }


	public boolean isSelected() { return this.isSelected; }

	public void setIsSelected(boolean isSelected) { this.isSelected = isSelected; }

	@Override
	public int getStartX() { return startX; }

	@Override
	public int getStartY() { return startY; }

	@Override
	public int getEndX() { return endX; }

	@Override
	public int getEndY() { return endY; }

	public ShapeColor getPrimaryColor() { return primaryColor; }

	public ShapeColor getSecondaryColor() { return secondaryColor; }

	public void move(int dx, int dy) {

		// Comment out this block if using solution with Composite design pattern.
		if (this.isInGroup())
			this.updateGroupBoundary(dx, dy);

		this.startX = this.startX + dx;
		this.startY = this.startY + dy;
		this.endX = this.endX + dx;
		this.endY = this.endY + dy;


		// Comment this if using solution with Composite design pattern.
		resetBoundary();

		// Uncomment this if using solution with Composite design pattern.
		//this.boundary.move(dx, dy);
	}

	public ShapeShadingType getShadingType() { return shadingType; }

	public ShapeType getShapeType() { return shapeType; }

	@Override
	public boolean isOverlapping(AbstractShape shape) {
		if (this.groupBoundary != null) {
			return this.groupBoundary.isOverlapping(shape);
		} else {
			return this.boundary.isOverlapping(shape);
		}
	}

	/*
	@Override
	public String toString() {
		String shapeToStr = "";
		shapeToStr += "startX: " + this.startX + "\n";
		shapeToStr += "startY: " + this.startY + "\n";
		shapeToStr += "endX: " + this.endX + "\n";
		shapeToStr += "endY: " + this.endY + "\n";
		shapeToStr += "shadingType: " + this.shadingType + "\n";
		shapeToStr += "shadeType: " + this.shapeType + "\n";
		return shapeToStr;
	}
	*/

	@Override
	public boolean isComposite() { return this.IS_COMPOSITE; }

	@Override
	public List<AbstractShape> getComponents() {
		List<AbstractShape> components = new ArrayList<>();
		if (!this.isInGroup())
			components.add(this.boundary);
		return components;
	}
}

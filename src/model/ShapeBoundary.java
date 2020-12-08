package model;

import view.interfaces.IDrawShape;
import java.util.List;

public class ShapeBoundary extends AbstractShape {

    private ShapeColor primaryColor;
    private ShapeColor secondaryColor;
    private ShapeShadingType shadingType;
    private ShapeType shapeType;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private boolean isInGroup;
    private final boolean IS_BOUNDARY = true;
    private final boolean IS_COMPOSITE = true;

    public ShapeBoundary(ShapeColor primaryColor, ShapeColor secondaryColor, ShapeShadingType shadingType,
                         ShapeType shapeType, int startX, int startY, int endX, int endY) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.shadingType = shadingType;
        this.shapeType = shapeType;
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.isInGroup = false;
    }

    @Override
    public boolean isInGroup() { return this.isInGroup; }

    @Override
    public void setIsInGroup(boolean groupFlag) { this.isInGroup = groupFlag; }

    @Override
    public boolean isBoundary() { return IS_BOUNDARY; }

    @Override
    public boolean isComposite() { return this.IS_COMPOSITE; }

    @Override
    public boolean isOverlapping(AbstractShape shape) {
        if ((startX > shape.getEndX()) || (endX < shape.getStartX()) ||
            startY > shape.getEndY() || endY < shape.getStartY())
            return false;
        return true;
    }

    @Override
    public int getStartX() { return startX; }

    @Override
    public int getStartY() { return startY; }

    @Override
    public int getEndX() { return endX; }

    @Override
    public int getEndY() { return endY; }

    @Override
    public boolean isSelected() { return false; }

    @Override
    public void setIsSelected(boolean isSelected) { }

    @Override
    public void move(int dx, int dy) {
        this.startX = this.startX + dx;
        this.startY = this.startY + dy;
        this.endX = this.endX + dx;
        this.endY = this.endY + dy;
    }

    @Override
    public ShapeType getShapeType() { return this.shapeType; }

    @Override
    public ShapeColor getPrimaryColor() { return this.primaryColor; }

    @Override
    public ShapeColor getSecondaryColor() { return this.secondaryColor; }

    @Override
    public ShapeShadingType getShadingType() { return this.shadingType; }

    @Override
    public List<AbstractShape> getComponents() {
        return null;
    }
}

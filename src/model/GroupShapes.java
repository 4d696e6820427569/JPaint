package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class GroupShapes extends AbstractShape {

    private List<AbstractShape> shapes;
    private ShapeBoundary groupBoundary;

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

    private final boolean IS_COMPOSITE = true;
    private final boolean IS_BOUNDARY = false;

    public GroupShapes(ShapeColor primaryColor, ShapeColor secondaryColor, ShapeShadingType shadingType,
                         ShapeType shapeType, int startX, int startY, int endX, int endY, List<AbstractShape> shapes) {
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.shadingType = shadingType;
        this.shapeType = shapeType;

        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        this.shapes = shapes;
        this.isInGroup = false;
        this.isSelected = true;

        this.resetBoundary();
    }

    public void resetBoundary() {
        this.groupBoundary = new ShapeBoundary(ShapeColor.BLACK, ShapeColor.BLACK, ShapeShadingType.OUTLINE,
                this.shapeType, startX, startY, endX, endY);
    }

    @Override
    public boolean isComposite() { return this.IS_COMPOSITE; }

    @Override
    public boolean isBoundary() { return this.IS_BOUNDARY; }

    @Override
    public boolean isInGroup() { return this.isInGroup; }

    @Override
    public void setIsInGroup(boolean groupFlag) { this.isInGroup = groupFlag; }

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
    public boolean isSelected() { return this.isSelected; }

    @Override
    public void setIsSelected(boolean isSelected) { this.isSelected = isSelected; }

    @Override
    public void move(int dx, int dy) {
        this.startX = this.startX + dx;
        this.startY = this.startY + dy;
        this.endX = this.endX + dx;
        this.endY = this.endY + dy;

        Iterator<AbstractShape> shapeIter = this.shapes.iterator();
        while (shapeIter.hasNext()) {
            shapeIter.next().move(dx, dy);
        }

        this.groupBoundary.move(dx,dy);
    }

    @Override
    public ShapeType getShapeType() { return this.shapeType; }

    @Override
    public ShapeColor getPrimaryColor() { return this.primaryColor; }

    @Override
    public ShapeColor getSecondaryColor() { return this.secondaryColor; }

    @Override
    public ShapeShadingType getShadingType() { return this.shadingType; }

    public List<AbstractShape> getShapes() { return this.shapes; }

    @Override
    public List<AbstractShape> getComponents() {
        List<AbstractShape> components = new ArrayList<>();
        components.addAll(this.shapes);
        if (!this.isInGroup())
            components.add(groupBoundary);
        return components;
    }
}

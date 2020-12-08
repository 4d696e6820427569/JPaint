package model;

import view.interfaces.IDrawShape;
import java.util.List;

public abstract class AbstractShape {
    public abstract int getStartX();
    public abstract int getStartY();
    public abstract int getEndX();
    public abstract int getEndY();
    public abstract boolean isSelected();
    public abstract void setIsSelected(boolean isSelected);
    public abstract boolean isBoundary();
    public abstract boolean isInGroup();
    public abstract void setIsInGroup(boolean groupFlag);
    public abstract void move(int dx, int dy);
    public abstract ShapeType getShapeType();
    public abstract ShapeColor getPrimaryColor();
    public abstract ShapeColor getSecondaryColor();
    public abstract ShapeShadingType getShadingType();
    public abstract boolean isOverlapping(AbstractShape shape);
    public abstract List<AbstractShape> getComponents();
    public abstract boolean isComposite();
}

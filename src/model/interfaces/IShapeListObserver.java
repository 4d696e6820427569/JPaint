package model.interfaces;

import java.util.List;

import model.Shape;
import model.AbstractShape;

public interface IShapeListObserver {
	void update(List<AbstractShape> shapeList);
}

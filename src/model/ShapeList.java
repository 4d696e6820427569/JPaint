package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import model.interfaces.IShapeListObserver;

/**
 * ShapeList
 *
 * Responsibility: Maintains a list of shapes. It's basically a wrapper class for the List data structure.
 * ShapeList is a Singleton since we only need 1 instance.
 *
 * Note to myself: Might change the class name to ShapeManager instead.
 * 
 * @author Minh Bui
 */

public class ShapeList {

	private static ShapeList uniqueInstance;

	private final List<AbstractShape> SHAPE_LIST;
	private final List<IShapeListObserver> OBSERVER_LIST;

	private ShapeList() {
		this.SHAPE_LIST = new ArrayList<>();
		this.OBSERVER_LIST = new ArrayList<>();
	}

	public static ShapeList getInstance() {
		if (uniqueInstance == null)
			uniqueInstance = new ShapeList();
		return uniqueInstance;
	}

	public void registerObserver(IShapeListObserver observer) {
		this.OBSERVER_LIST.add(observer);
	}

	public void removeObserver(IShapeListObserver observer) {
		Iterator<IShapeListObserver> obsIter = OBSERVER_LIST.iterator();
		while (obsIter.hasNext()) {
			IShapeListObserver curObs = obsIter.next();
			if (curObs.equals(observer)) {
				obsIter.remove();
			}
		}
	}

	public void addShape(AbstractShape newShape) {
		this.SHAPE_LIST.add(newShape);
		this.notifyObservers();
	}
	
	public void addAllShapes(List<AbstractShape> shapes) {
		this.SHAPE_LIST.addAll(shapes);
		this.notifyObservers();
	}
	
	public List<AbstractShape> cloneAndAddAllShapes(List<AbstractShape> shapes) {
		List<AbstractShape> clones = new ArrayList<>();
		Iterator<AbstractShape> iter = shapes.iterator();
		while (iter.hasNext()) {
			AbstractShape cur = iter.next();
			if (cur instanceof Shape) {
				Shape copyCur = new Shape(cur.getPrimaryColor(), cur.getSecondaryColor(),
						cur.getShadingType(), cur.getShapeType(), cur.getStartX(), cur.getStartY(),
						cur.getEndX(), cur.getEndY());
				clones.add(copyCur);
			} else if (cur instanceof GroupShapes) {
				System.out.println("hello");
				GroupShapes copyCur = new GroupShapes(cur.getPrimaryColor(), cur.getSecondaryColor(),
						cur.getShadingType(), cur.getShapeType(), cur.getStartX(), cur.getStartY(),
						cur.getEndX(), cur.getEndY(), cloneAndAddComponents(((GroupShapes) cur).getShapes()));
				clones.add(copyCur);
			}
		}
		this.SHAPE_LIST.addAll(clones);
		this.notifyObservers();
		return clones;
	}

	private List<AbstractShape> cloneAndAddComponents(List<AbstractShape> shapes) {
		List<AbstractShape> componentsClone = new ArrayList<>();

		Iterator<AbstractShape> i = shapes.iterator();
		while (i.hasNext()) {
			AbstractShape cur = i.next();
			if (i instanceof GroupShapes) {
				GroupShapes copyCur = new GroupShapes(cur.getPrimaryColor(), cur.getSecondaryColor(),
						cur.getShadingType(), cur.getShapeType(), cur.getStartX(), cur.getStartY(),
						cur.getEndX(), cur.getEndY(), cloneAndAddComponents(((GroupShapes) cur).getShapes()));
				componentsClone.add(copyCur);
			} else {
				Shape copyCur = new Shape(cur.getPrimaryColor(), cur.getSecondaryColor(),
						cur.getShadingType(), cur.getShapeType(), cur.getStartX(), cur.getStartY(),
						cur.getEndX(), cur.getEndY());
				componentsClone.add(copyCur);
			}
		}

		return componentsClone;
	}

	public void removeShape(AbstractShape shape) {
		Iterator<AbstractShape> shapeIter = this.SHAPE_LIST.iterator();
		
		while (shapeIter.hasNext()) {
			AbstractShape curShape = shapeIter.next();
			if (curShape.equals(shape))
				shapeIter.remove();
		}
		this.notifyObservers();
	}
	
	public List<AbstractShape> removeSelectedShape() {
		Iterator<AbstractShape> shapeIter = this.SHAPE_LIST.iterator();
		List<AbstractShape> removedShapes = new ArrayList<>();
		while(shapeIter.hasNext()) {
			AbstractShape curShape = shapeIter.next();
			if (curShape.isSelected()) {
				shapeIter.remove();
				removedShapes.add(curShape);
			}
		}
		this.notifyObservers();
		return removedShapes;
	}

	public void printShapeList() {
		Iterator<AbstractShape> shapeIter = this.SHAPE_LIST.iterator();

		System.out.println("ShapeList currently has: ");
		while(shapeIter.hasNext()) {
			AbstractShape curShape = shapeIter.next();
			System.out.println(curShape + " " + curShape.isBoundary());
		}

		System.out.println("---------------   END OF SHAPE LIST   -----------------");
		System.out.println("----------- BEGIN PRINTING ALL COMPONENTS -------------");

		shapeIter = this.SHAPE_LIST.iterator();
		while (shapeIter.hasNext()) {
			AbstractShape curShape = shapeIter.next();
			System.out.println(curShape + " " + curShape.isBoundary());

			List<AbstractShape> curComponents = curShape.getComponents();
			if (curComponents != null) {
				this.printComponents(curComponents);
			}
		}
		System.out.println();
	}

	private void printComponents(List<AbstractShape> components) {
		if (components != null) {
			System.out.print('\t');

			Iterator<AbstractShape> i = components.iterator();
			while(i.hasNext()) {
				AbstractShape aShape = i.next();
				System.out.println(aShape + " " + aShape.isBoundary());
				printComponents(aShape.getComponents());
			}
		}
	}

	public void markSelect(int x, int y, int width, int height) {
		AbstractShape boundary = new ShapeBoundary(ShapeColor.BLACK, ShapeColor.BLACK, ShapeShadingType.OUTLINE,
				ShapeType.RECTANGLE, x, y, x + width, y + height);
		Iterator<AbstractShape> shapeIter = this.SHAPE_LIST.iterator();
		while (shapeIter.hasNext()) {
			AbstractShape curShape = shapeIter.next();
			if (curShape.isOverlapping(boundary)) {
				curShape.setIsSelected(true);
			} else {
				curShape.setIsSelected(false);
			}
		}
		this.notifyObservers();
	}

	public List<AbstractShape> moveShapes(int dX, int dY, List<AbstractShape> shapesToMove) {
		List<AbstractShape> movedShapes = new ArrayList<>();
		Iterator<AbstractShape> shapeIter = this.SHAPE_LIST.iterator();

		if (shapesToMove == null) {
			while (shapeIter.hasNext()) {
				AbstractShape curShape = shapeIter.next();
				if (curShape.isSelected()) {
					curShape.move(dX, dY);
					movedShapes.add(curShape);
				}
			}
		} else {
			while (shapeIter.hasNext()) {
				AbstractShape curShape = shapeIter.next();
				if (shapesToMove.contains(curShape)) {
					curShape.move(dX, dY);
					movedShapes.add(curShape);
				}
			}
		}

		this.notifyObservers();
		return movedShapes;
	}
	
	public void deselectAllShapes() {
		Iterator<AbstractShape> shapeIter = this.SHAPE_LIST.iterator();
		while(shapeIter.hasNext()) {
			AbstractShape curShape = shapeIter.next();
			curShape.setIsSelected(false);
		}
		this.notifyObservers();
	}
	
	public void removeShapesList(List<AbstractShape> shapes) {
		this.SHAPE_LIST.removeAll(shapes);
		this.notifyObservers();
	}

	public List<AbstractShape> getSelectedShapes() {
		List<AbstractShape> selectedShapes = new ArrayList<>();
		Iterator<AbstractShape> shapeIter = this.SHAPE_LIST.iterator();
		while (shapeIter.hasNext()) {
			AbstractShape curShape = shapeIter.next();
			if (curShape.isSelected())
				selectedShapes.add(curShape);
		}
		return selectedShapes;
	}


	public List<AbstractShape> groupShapes2(List<AbstractShape> shapes) {
		Iterator<AbstractShape> shapeIter = shapes.iterator();

		int xMax = Integer.MIN_VALUE;
		int yMax = Integer.MIN_VALUE;
		int xMin = Integer.MAX_VALUE;
		int yMin = Integer.MAX_VALUE;

		while (shapeIter.hasNext()) {
			AbstractShape curShape = shapeIter.next();
			xMax = max(curShape.getStartX(), curShape.getEndX(), xMax);
			yMax = max(curShape.getStartY(), curShape.getEndY(), yMax);
			xMin = min(curShape.getStartX(), curShape.getEndX(), xMin);
			yMin = min(curShape.getStartY(), curShape.getEndY(), yMin);
			curShape.setIsInGroup(true);
		}

		GroupShapes newGroup = new GroupShapes(ShapeColor.BLACK, ShapeColor.BLACK, ShapeShadingType.OUTLINE,
				ShapeType.RECTANGLE, xMin, yMin, xMax, yMax, shapes);

		this.removeShapesList(shapes);
		this.addShape(newGroup);
		this.notifyObservers();

		List<AbstractShape> grouped = new ArrayList<>();
		grouped.add(newGroup);
		//this.printShapeList();
		return grouped;
	}

	public List<AbstractShape> ungroupShapes2(List<AbstractShape> shapes) {
		Iterator<AbstractShape> shapeIter = shapes.iterator();

		// List of Shapes to be added back to the ShapeList and returned by this method.
		List<AbstractShape> ungroup = new ArrayList<>();

		while (shapeIter.hasNext()) {
			AbstractShape curShape = shapeIter.next();
			curShape.setIsInGroup(false);
			if (curShape instanceof GroupShapes) {
				// Removes the current GroupShapes object and re-add the shapes that are held
				// by the removed GroupShapes object.

				this.removeShape(curShape);
				GroupShapes group = (GroupShapes) curShape;

				List<AbstractShape> ushapes = group.getShapes();
				Iterator<AbstractShape> shapeIter2 = ushapes.iterator();
				while(shapeIter2.hasNext()) {
					AbstractShape aShape = shapeIter2.next();
					aShape.setIsInGroup(false);
				}

				this.addAllShapes(ushapes);
				ungroup.addAll(ushapes);
			} else {

			}
		}
		//this.printShapeList();
		this.notifyObservers();
		return ungroup;
	}

	public List<AbstractShape> groupSelectedShapes() {
		List<AbstractShape> selectedShape = this.getSelectedShapes();

		if (selectedShape.isEmpty()) return selectedShape;

		Iterator<AbstractShape> shapeIter = selectedShape.iterator();

		int xMax = Integer.MIN_VALUE;
		int yMax = Integer.MIN_VALUE;
		int xMin = Integer.MAX_VALUE;
		int yMin = Integer.MAX_VALUE;

		while (shapeIter.hasNext()) {
			AbstractShape curShape = shapeIter.next();
			if (curShape.isSelected()) {
				xMax = max(curShape.getStartX(), curShape.getEndX(), xMax);
				yMax = max(curShape.getStartY(), curShape.getEndY(), yMax);
				xMin = min(curShape.getStartX(), curShape.getEndX(), xMin);
				yMin = min(curShape.getStartY(), curShape.getEndY(), yMin);
			}
		}

		shapeIter = selectedShape.iterator();
		while (shapeIter.hasNext()) {
			Shape curShape = (Shape) shapeIter.next();
			curShape.setGroupBoundary(xMin, yMin, xMax - xMin, yMax - yMin);
		}
		this.notifyObservers();
		return selectedShape;
	}

	public List<AbstractShape> groupShapes(List<AbstractShape> shapes) {
		Iterator<AbstractShape> shapeIter = shapes.iterator();

		int xMax = Integer.MIN_VALUE;
		int yMax = Integer.MIN_VALUE;
		int xMin = Integer.MAX_VALUE;
		int yMin = Integer.MAX_VALUE;

		while (shapeIter.hasNext()) {
			AbstractShape curShape = shapeIter.next();
				xMax = max(curShape.getStartX(), curShape.getEndX(), xMax);
				yMax = max(curShape.getStartY(), curShape.getEndY(), yMax);
				xMin = min(curShape.getStartX(), curShape.getEndX(), xMin);
				yMin = min(curShape.getStartY(), curShape.getEndY(), yMin);
		}

		shapeIter = shapes.iterator();
		while (shapeIter.hasNext()) {
			Shape curShape = (Shape) shapeIter.next();
			curShape.setGroupBoundary(xMin, yMin, xMax - xMin, yMax - yMin);
		}
		this.notifyObservers();
		return shapes;
	}

	public List<AbstractShape> ungroupSelectedShapes() {
		Iterator<AbstractShape> shapeIter = this.SHAPE_LIST.iterator();
		List<AbstractShape> ungrouped = new ArrayList<>();
		while (shapeIter.hasNext()) {
			Shape curShape = (Shape) shapeIter.next();
			if (curShape.isSelected() && curShape.isInGroup()) {
				curShape.unsetGroupBoundary();
				ungrouped.add(curShape);
			}
		}
		this.notifyObservers();
		return ungrouped;
	}

	public List<AbstractShape> ungroupShapes(List<AbstractShape> shapes) {
		Iterator<AbstractShape> shapeIter = shapes.iterator();

		while (shapeIter.hasNext()) {

			//AbstractShape curShape = shapeIter.next();
			Shape curShape = (Shape) shapeIter.next();
			if (curShape.isInGroup()) {
				curShape.unsetGroupBoundary();
			}
		}
		this.notifyObservers();
		return shapes;
	}

	public int getNumberOfSelectedShapes() {
		int count = 0;
		Iterator<AbstractShape> shapeIter = this.SHAPE_LIST.iterator();
		while (shapeIter.hasNext()) {
			if (shapeIter.next().isSelected())
				count++;
		}

		return count;
	}

	public void notifyObservers() {
		Iterator<IShapeListObserver> obsIter = OBSERVER_LIST.iterator();
		while (obsIter.hasNext()) {
			IShapeListObserver curObs = obsIter.next();
			curObs.update(SHAPE_LIST);
		}
	}

	private int max(int a, int b, int c) { return Math.max(a, Math.max(b, c)); }

	private int min(int a, int b, int c) { return Math.min(a, Math.min(b, c)); }
}

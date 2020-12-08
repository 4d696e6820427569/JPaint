package main;

import controller.IJPaintController;
import controller.JPaintController;
import model.persistence.ApplicationState;
import view.gui.Drawer;
import view.gui.Gui;
import view.gui.GuiWindow;
import view.gui.PaintCanvas;
import view.interfaces.IGuiWindow;
import view.interfaces.PaintCanvasBase;
import view.interfaces.IUiModule;
import controller.MouseAdapter;
import model.ShapeList;


public class Main {
    public static void main(String[] args){
        ShapeList shapes = ShapeList.getInstance();
        PaintCanvasBase paintCanvas = new PaintCanvas();
        IGuiWindow guiWindow = new GuiWindow(paintCanvas);
        IUiModule uiModule = new Gui(guiWindow);
        ApplicationState appState = new ApplicationState(uiModule);
        MouseAdapter mouseController = MouseAdapter.getInstance(appState, shapes);
        paintCanvas.addMouseListener(mouseController);
        Drawer drawer = Drawer.getInstance(paintCanvas);
        shapes.registerObserver(drawer);
        IJPaintController controller = new JPaintController(uiModule, appState, shapes);
        controller.setup();
    }
}

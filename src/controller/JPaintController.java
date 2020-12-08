package controller;

import model.ShapeList;
import model.interfaces.IApplicationState;
import view.EventName;
import view.interfaces.IUiModule;

public class JPaintController implements IJPaintController {
    private final IUiModule UI_MODULE;
    private final IApplicationState APP_STATE;
    private final ShapeList SHAPE_LIST;
    

    public JPaintController(IUiModule uiModule, IApplicationState applicationState, ShapeList shapeList) {
        this.UI_MODULE = uiModule;
        this.APP_STATE = applicationState;
        this.SHAPE_LIST = shapeList;
    }

    @Override
    public void setup() {
        setupEvents();
    }

    private void setupEvents() {
        UI_MODULE.addEvent(EventName.CHOOSE_SHAPE, () -> APP_STATE.setActiveShape());
        UI_MODULE.addEvent(EventName.CHOOSE_PRIMARY_COLOR, () -> APP_STATE.setActivePrimaryColor());
        UI_MODULE.addEvent(EventName.CHOOSE_SECONDARY_COLOR, () -> APP_STATE.setActiveSecondaryColor());
        UI_MODULE.addEvent(EventName.CHOOSE_SHADING_TYPE, () -> APP_STATE.setActiveShadingType());
        UI_MODULE.addEvent(EventName.CHOOSE_MOUSE_MODE, () -> APP_STATE.setActiveStartAndEndPointMode());
        UI_MODULE.addEvent(EventName.UNDO, () -> CommandHistory.undo());
        UI_MODULE.addEvent(EventName.REDO, () -> CommandHistory.redo());
        UI_MODULE.addEvent(EventName.COPY, () -> CommandHistory.copy(this.SHAPE_LIST));
        UI_MODULE.addEvent(EventName.PASTE, () -> CommandHistory.paste(this.SHAPE_LIST));
        UI_MODULE.addEvent(EventName.DELETE, () -> CommandHistory.delete(this.SHAPE_LIST));
        UI_MODULE.addEvent(EventName.GROUP, () -> CommandHistory.group(this.SHAPE_LIST));
        UI_MODULE.addEvent(EventName.UNGROUP, () -> CommandHistory.ungroup(this.SHAPE_LIST));
    }
}

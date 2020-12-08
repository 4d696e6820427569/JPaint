package model.dialogs;

import model.ShapeShadingType;
import model.interfaces.IApplicationState;
import view.interfaces.IDialogChoice;

public class ChooseShadingTypeDialog implements IDialogChoice<ShapeShadingType> {
    private final IApplicationState APP_STATE;

    public ChooseShadingTypeDialog(IApplicationState applicationState) {
        this.APP_STATE = applicationState;
    }

    @Override
    public String getDialogTitle() {
        return "Shading Type";
    }

    @Override
    public String getDialogText() {
        return "Select a shading type from the menu below:";
    }

    @Override
    public ShapeShadingType[] getDialogOptions() {
        return ShapeShadingType.values();
    }

    @Override
    public ShapeShadingType getCurrentSelection() {
        return APP_STATE.getActiveShapeShadingType();
    }
}

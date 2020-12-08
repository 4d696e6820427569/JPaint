package model.dialogs;

import model.ShapeColor;
import model.interfaces.IApplicationState;
import view.interfaces.IDialogChoice;

public class ChooseSecondaryColorDialog implements IDialogChoice<ShapeColor> {

    private final IApplicationState APP_STATE;

    public ChooseSecondaryColorDialog(IApplicationState applicationState) {
        this.APP_STATE = applicationState;
    }

    @Override
    public String getDialogTitle() {
        return "Secondary Color";
    }

    @Override
    public String getDialogText() {
        return "Select a secondary color from the menu below:";
    }

    @Override
    public ShapeColor[] getDialogOptions() {
        return ShapeColor.values();
    }

    @Override
    public ShapeColor getCurrentSelection() {
        return APP_STATE.getActiveSecondaryColor();
    }
}

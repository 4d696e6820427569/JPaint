package model.dialogs;

import model.ShapeColor;
import model.ShapeShadingType;
import model.ShapeType;
import model.MouseMode;
import model.interfaces.IApplicationState;
import model.interfaces.IDialogProvider;
import view.interfaces.IDialogChoice;

public class DialogProvider implements IDialogProvider {
    private final IDialogChoice<ShapeType> CHOOSE_SHAPE_DIALOG;
    private final IDialogChoice<ShapeColor> CHOOSE_PRIMARY_COLOR_DIALOG;
    private final IDialogChoice<ShapeColor> CHOOSE_SECONDARY_DIALOG;
    private final IDialogChoice<ShapeShadingType> CHOOSE_SHADING_TYPE_DIALOG;
    private final IDialogChoice<MouseMode> CHOOSE_START_END_PT_DIALOG;
    private final IApplicationState APP_STATE;

    public DialogProvider(IApplicationState applicationState) {
        this.APP_STATE = applicationState;
        CHOOSE_SHAPE_DIALOG = new ChooseShapeDialog(this.APP_STATE);
        CHOOSE_PRIMARY_COLOR_DIALOG = new ChoosePrimaryColorDialog(this.APP_STATE);
        CHOOSE_SECONDARY_DIALOG = new ChooseSecondaryColorDialog(this.APP_STATE);
        CHOOSE_SHADING_TYPE_DIALOG = new ChooseShadingTypeDialog(this.APP_STATE);
        CHOOSE_START_END_PT_DIALOG = new ChooseStartAndEndPointModeDialog(this.APP_STATE);
    }

    @Override
    public IDialogChoice<ShapeType> getChooseShapeDialog() {
        return CHOOSE_SHAPE_DIALOG;
    }

    @Override
    public IDialogChoice<ShapeColor> getChoosePrimaryColorDialog() {
        return CHOOSE_PRIMARY_COLOR_DIALOG;
    }

    @Override
    public IDialogChoice<ShapeColor> getChooseSecondaryColorDialog() {
        return CHOOSE_SECONDARY_DIALOG;
    }

    @Override
    public IDialogChoice<ShapeShadingType> getChooseShadingTypeDialog() {
        return CHOOSE_SHADING_TYPE_DIALOG;
    }

    @Override
    public IDialogChoice<MouseMode> getChooseStartAndEndPointModeDialog() {
        return CHOOSE_START_END_PT_DIALOG;
    }
}

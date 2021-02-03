package io.github.therealmone.fireres.gui.controller.unheated.surface.thermocouple.groups.second.thermocouple.group;

import io.github.therealmone.fireres.core.config.SampleProperties;
import io.github.therealmone.fireres.gui.annotation.ChildController;
import io.github.therealmone.fireres.gui.annotation.ParentController;
import io.github.therealmone.fireres.gui.controller.AbstractController;
import io.github.therealmone.fireres.gui.controller.SampleContainer;
import io.github.therealmone.fireres.gui.controller.common.FunctionParamsController;
import io.github.therealmone.fireres.gui.controller.unheated.surface.UnheatedSurfacePaneController;
import javafx.fxml.FXML;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SecondThermocoupleGroupPaneController extends AbstractController implements SampleContainer {

    @FXML
    @ChildController
    private SecondThermocoupleGroupParamsController secondThermocoupleGroupParamsController;

    @FXML
    @ChildController
    private FunctionParamsController functionParamsController;

    @ParentController
    private UnheatedSurfacePaneController unheatedSurfacePaneController;

    @Override
    public SampleProperties getSampleProperties() {
        return unheatedSurfacePaneController.getSampleProperties();
    }

    @Override
    protected void initialize() {
        secondThermocoupleGroupParamsController.setSecondThermocoupleGroupPaneController(this);
        functionParamsController.setParentController(this);
    }

    @Override
    public void postConstruct() {
        secondThermocoupleGroupParamsController.postConstruct();
        functionParamsController.postConstruct();
    }
}
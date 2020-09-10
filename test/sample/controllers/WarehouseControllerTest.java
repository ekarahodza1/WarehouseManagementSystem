package sample.controllers;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import sample.dto.Product;
import sample.dto.Warehouse;
import sample.models.ProductModel;

import java.util.ResourceBundle;

@ExtendWith(ApplicationExtension.class)
class WarehouseControllerTest {
    Stage Stage;
    WarehouseController ctrl;

    @Start
    public void start (Stage stage) throws Exception {

        ProductModel model = ProductModel.getInstance();

        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/warehouse.fxml"), bundle);
        WarehouseController ctrl = new WarehouseController();
        loader.setController(ctrl);
        root = loader.load();
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.toFront();
        stage.show();
        Stage = stage;

    }

    @Test
    public void testWarehouseValidation(FxRobot robot) {
        robot.clickOn("#btnOk");

        TextField name = robot.lookup("#fieldName").queryAs(TextField.class);
        Background bg = name.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);

        robot.clickOn("#fieldName");
        robot.write("A3");
        robot.clickOn("#btnOk");

        name = robot.lookup("#fieldName").queryAs(TextField.class);
        bg = name.getBackground();
        colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("add8e6"))
                colorFound = true;
        assertTrue(colorFound);

        TextField amount = robot.lookup("#fieldLocation").queryAs(TextField.class);
        bg = amount.getBackground();
        colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);

        robot.clickOn("#fieldLocation");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("Zenica");

        robot.clickOn("#btnOk");

        assertFalse(Stage.isShowing());
    }


    @Test
    public void testCloseWarehouse(FxRobot robot) {

        robot.clickOn("#btnCancel");
        assertFalse(Stage.isShowing());
    }






}
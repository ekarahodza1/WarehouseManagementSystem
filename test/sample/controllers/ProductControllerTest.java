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
import sample.models.ProductModel;

import java.util.ResourceBundle;

@ExtendWith(ApplicationExtension.class)
class ProductControllerTest {
    Stage Stage;
    ProductController ctrl;

    @Start
    public void start (Stage stage) throws Exception {

        ProductModel model = ProductModel.getInstance();

        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/product.fxml"), bundle);
        ProductController productController = new ProductController(null, model.getAll());
        ctrl = productController;
        loader.setController(productController);
        root = loader.load();
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.toFront();
        stage.show();
        Stage = stage;

    }

    @Test
    public void testProductValidation(FxRobot robot) {
        robot.clickOn("#btnOk");

        TextField name = robot.lookup("#fieldName").queryAs(TextField.class);
        Background bg = name.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);

        robot.clickOn("#fieldName");
        robot.write("Pants");
        robot.clickOn("#btnOk");

        name = robot.lookup("#fieldName").queryAs(TextField.class);
        bg = name.getBackground();
        colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("add8e6"))
                colorFound = true;
        assertTrue(colorFound);

        TextField amount = robot.lookup("#fieldAmount").queryAs(TextField.class);
        bg = amount.getBackground();
        colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);

        robot.clickOn("#fieldAmount");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("20");

        TextField unitP = robot.lookup("#fieldUnitPrice").queryAs(TextField.class);
        bg = unitP.getBackground();
        colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);

        robot.clickOn("#fieldUnitPrice");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("100");

        robot.clickOn("#btnOk");

        TextField fieldPrice = robot.lookup("#fieldPrice").queryAs(TextField.class);
        assertEquals("2000.0", fieldPrice.getText());

        TextField date = robot.lookup("#dateAdded").queryAs(TextField.class);
        bg = amount.getBackground();
        colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertFalse(colorFound);

        robot.clickOn("#dateAdded");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("2020-05-05");

        robot.clickOn("#btnOk");

        assertFalse(Stage.isShowing());
    }


    @Test
    public void testCloseProduct(FxRobot robot) {

        robot.clickOn("#btnCancel");
        assertFalse(Stage.isShowing());
    }

    @Test
    public void testReturnProduct(FxRobot robot) {

        robot.clickOn("#fieldName");
        robot.write("Acer Notebook");
        robot.clickOn("#fieldAmount");
        robot.write("10");
        robot.clickOn("#fieldUnitPrice");
        robot.write("500");
        robot.clickOn("#choiceType");
        robot.clickOn("ELECTRONICS");
        robot.clickOn("#dateAdded");
        robot.write("2020-05-05");

        robot.clickOn("#btnOk");

        Product product = ctrl.getProduct();
        assertEquals("Acer Notebook", product.getName());
        assertEquals(5000.0, product.getPrice());

    }




}
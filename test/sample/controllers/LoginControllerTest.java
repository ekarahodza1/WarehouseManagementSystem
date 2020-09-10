package sample.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import sample.models.ProductModel;

import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class LoginControllerTest {

    javafx.stage.Stage Stage;

    @Start
    public void start (Stage stage) throws Exception {

        ProductModel model = ProductModel.getInstance();

        Parent root = null;
        ResourceBundle bundle = ResourceBundle.getBundle("translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"), bundle);
        LoginController loginController = new LoginController();
        loader.setController(loginController);
        root = loader.load();
        stage.setTitle("Login");
        stage.setScene(new Scene(root, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        Stage = stage;

    }

    @Test
    public void testLoginValidation(FxRobot robot) {

        TextField name = robot.lookup("#fieldName").queryAs(TextField.class);
        Background bg = name.getBackground();
        boolean colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertFalse(colorFound);

        robot.clickOn("#fieldName");
        robot.write("esma");
        robot.clickOn("#btnNext");

        name = robot.lookup("#fieldName").queryAs(TextField.class);
        bg = name.getBackground();
        colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);

        robot.clickOn("#fieldName");
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.press(KeyCode.BACK_SPACE).release(KeyCode.BACK_SPACE);
        robot.write("user");
        robot.clickOn("#btnNext");

        TextField amount = robot.lookup("#fieldPassword").queryAs(TextField.class);
        bg = amount.getBackground();
        colorFound = false;
        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);

        robot.clickOn("#fieldPassword");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("1234");

        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("ffb6c1"))
                colorFound = true;
        assertTrue(colorFound);

        robot.clickOn("#btnNext");

        robot.clickOn("#fieldPassword");
        robot.press(KeyCode.CONTROL).press(KeyCode.A).release(KeyCode.A).release(KeyCode.CONTROL);
        robot.write("0000");

        for (BackgroundFill bf : bg.getFills())
            if (bf.getFill().toString().contains("add8e6"))
                colorFound = true;
        assertTrue(colorFound);

        robot.clickOn("#btnNext");

        assertFalse(Stage.isShowing());
    }

}
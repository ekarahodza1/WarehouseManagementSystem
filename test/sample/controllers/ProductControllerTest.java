package sample.controllers;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;
import static org.junit.jupiter.api.Assertions.*;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.util.ResourceBundle;

@ExtendWith(ApplicationExtension.class)
class ProductControllerTest {

    @Start
    public void start (Stage stage) throws Exception {
        ResourceBundle bundle = ResourceBundle.getBundle("translation");
        Parent mainNode = FXMLLoader.load(getClass().getResource("/fxml/product.fxml"), bundle);
        stage.setScene(new Scene(mainNode, USE_COMPUTED_SIZE, USE_COMPUTED_SIZE));
        stage.show();
        stage.toFront();
    }

//    @Test
//    public void testButtonClick (FxRobot robot) {
//        Button prijavaBtn = robot.lookup("#prijavaBtn").queryAs(Button.class);
//        robot.clickOn("#loginField");
//        robot.write("anonymous");
//        robot.clickOn("#prijavaBtn");
//        assertEquals("anonymous", prijavaBtn.getText());
//    }


}
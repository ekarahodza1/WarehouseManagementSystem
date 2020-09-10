package sample.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class MainControllerTest {
    Stage Stage;
    MainController ctrl;

    @Start
    public void start (Stage stage) throws Exception {



        ResourceBundle bundle = ResourceBundle.getBundle("translation");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"), bundle);
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"), bundle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctrl = new MainController();
        stage.setTitle("Warehouse management system");
        stage.setScene(new Scene(root, 900, 500));
        stage.setResizable(false);
        stage.show();
        stage.toFront();
        Stage = stage;
    }

    @Test
    @Order(1)
    public void testTableView(FxRobot robot) {
        TableView tableViewProducts = robot.lookup("#tableViewStorage").queryAs(TableView.class);
        assertEquals(12, tableViewProducts.getItems().size());
    }

    @Test
    @Order(2)
    public void testDeleteProduct(FxRobot robot) {

        robot.clickOn("Oranges");
        robot.clickOn("#btnDeleteProduct");

        robot.lookup(".dialog-pane").tryQuery().isPresent();

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
        robot.clickOn(okButton);

        TableView tableViewProducts = robot.lookup("#tableViewStorage").queryAs(TableView.class);
        assertEquals(11, tableViewProducts.getItems().size());

    }

    @Test
    @Order(3)
    public void testFilter(FxRobot robot){
        robot.clickOn("#choiceType");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);

        TableView tableViewProducts = robot.lookup("#tableViewStorage").queryAs(TableView.class);
        assertEquals(4, tableViewProducts.getItems().size());

        robot.clickOn("#choiceWarehouse");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);

        assertEquals(3, tableViewProducts.getItems().size());

        robot.clickOn("#choiceType");
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.DOWN);
        robot.type(KeyCode.ENTER);

        assertEquals(1, tableViewProducts.getItems().size());

    }


}
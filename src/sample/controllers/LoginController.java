package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.UserModel;


import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

public class LoginController {

    public TextField fieldName;
    public PasswordField fieldPassword;
    private UserModel model;


    public LoginController(){
        model = UserModel.getInstance();
    }

    @FXML
    public void initialize(){

    }

    public void clickNext (ActionEvent actionEvent){
        String username = fieldName.getText();
        String password = fieldPassword.getText();
        Map<String, String> map = model.getUsers();

        if (map.get(username).matches(password)) {
            //Locale.setDefault(new Locale("bs", "BA"));

            Stage stage1 = (Stage) fieldName.getScene().getWindow();
            stage1.close();

            Stage stage = new Stage();
            ResourceBundle bundle = ResourceBundle.getBundle("translation");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"), bundle);
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"), bundle);
            } catch (IOException e) {
                e.printStackTrace();
            }
            stage.setTitle("Warehouse management system");
            stage.setScene(new Scene(root, 900, 500));
            stage.setResizable(false);
            stage.show();
        }
        else {
            fieldName.getStyleClass().removeAll("fieldCorrect");
            fieldName.getStyleClass().add("fieldIncorrect");
            fieldPassword.getStyleClass().removeAll("fieldCorrect");
            fieldPassword.getStyleClass().add("fieldIncorrect");
            return;
        }

    }


}

package sample.other;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.MainController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
//        primaryStage.setTitle("Warehouse management system");
//        primaryStage.setScene(new Scene(root, 900, 500));
//        primaryStage.setResizable(false);
//        primaryStage.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        MainController ctrl = new MainController();
        loader.setController(ctrl);
        Parent root = loader.load();
        primaryStage.setTitle("Warehouse management system");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Program.fxml"));
            Parent root = fxmlLoader.load();
            Program controller = fxmlLoader.<Program>getController();
            controller.initializeGUI();
            Stage stage = new Stage();
            String title = String.format("Holiday Maker");
            stage.setTitle(title);
            stage.setScene(new Scene(root, 1080 , 800));
            stage.setResizable(false);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }





        /*

        Parent root = FXMLLoader.load(getClass().getResource("Program.fxml"));
        primaryStage.setTitle("Holidaymaker");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}

package controllers;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    private static Stage stg;
    private Parent root;

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        try {
            stg = primaryStage;
            primaryStage.setResizable(false);

            root = FXMLLoader.load(getClass().getResource("MainPage.fxml"));

            primaryStage.setScene(new Scene(root, 700, 500));
            primaryStage.setTitle("Main Page");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void changeScene(String fxml, String title, int width, int height) throws IOException {
        stg.close();
        stg.setResizable(false);
        Stage newStage = new Stage();
        newStage.show();

        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        newStage.setTitle(title);
        newStage.setScene(new Scene(pane, width, height));
        stg = newStage;
    }
}

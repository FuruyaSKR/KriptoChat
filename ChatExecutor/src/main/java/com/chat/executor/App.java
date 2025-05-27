package com.chat.executor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/fxml/login.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);
        scene.getStylesheets().add(App.class.getResource("/styles/login.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Kripto Chat - Login");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

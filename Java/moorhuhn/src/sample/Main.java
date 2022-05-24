package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        Game root = new Game(1024, 720, "sample/bg.png");
        //Pridať "javafx.media" do --add-modules vo VM options!
        primaryStage.setTitle("Moorhuhn");
        primaryStage.setScene(new Scene(root, 1024, 720));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

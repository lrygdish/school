package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();

        primaryStage.setTitle("Balóniky");
        primaryStage.setScene(new Scene(root, 1024, 768));
        root.getScene().setFill(Color.LIGHTGRAY);
        root.getScene().fillProperty();

        Timeline t = new Timeline(new KeyFrame(Duration.millis(1000), evt ->
                root.getChildren().add(new Balon(root))));
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

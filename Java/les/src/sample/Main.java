package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        VBox box = new VBox();
        primaryStage.setScene(new Scene(root, 800, 600));

        Button strom = new Button("Strom");
        strom.setOnAction(evt -> root.getChildren().add(new Strom(root)));
        Button krik = new Button("Krík");
        krik.setOnAction(evt -> root.getChildren().add(new Krik(root)));
        Button kvetina = new Button("Kvetina");
        kvetina.setOnAction(evt -> root.getChildren().add(new Kvetina(root)));

        box.getChildren().addAll(strom, krik, kvetina);
        root.getChildren().addAll(box);
        primaryStage.setTitle("Les");
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

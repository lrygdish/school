package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        /*
           |==================================================================================================================================|
           | File -> Project Structure -> Libraries -> pridať "webcam-capture-0.3.12.jar", "bridj-0.7.0.jar" a "slf4j-api-1.7.2.jar" z ./lib! |
           |==================================================================================================================================|
         */


        Group root = new Group();

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Pocet rozdielov");
        BarChart barChart = new BarChart(xAxis, yAxis);
        barChart.setLegendVisible(false);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(barChart);

        Snimanie s = new Snimanie();
        s.start();
        Porovnavanie p = new Porovnavanie();
        p.start();

        Thread graf = new Thread(new Runnable() {

            XYChart.Series dataSeries1;
            int i = 0;

            @Override
            public void run() {
                while(true) {
                    try {
                        Platform.runLater(() -> {
                            dataSeries1 = new XYChart.Series();
                            dataSeries1.setName("" + i++);
                            int h = p.getPocet();
                            dataSeries1.getData().add(new XYChart.Data("", h));
                            if(h != 0)
                                barChart.getData().add(dataSeries1);
                        });
                        Thread.sleep(1000);
                    } catch(Exception e) {
                        System.out.println(e);
                    }
                }
            }
        });
        graf.start();

        root.getChildren().add(scrollPane);
        primaryStage.setTitle("Vlákna - Hard");
        primaryStage.setScene(new Scene(root, scrollPane.getPrefWidth(), scrollPane.getPrefHeight()));
        primaryStage.show();
        primaryStage.setOnCloseRequest(windowEvent -> {
            s.stop();
            p.stop();
            graf.stop();
        });
        primaryStage.setResizable(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

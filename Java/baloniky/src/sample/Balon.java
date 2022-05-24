package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Balon extends Canvas implements EventHandler<MouseEvent> {

    private static final int R = 40;
    private int pozicia;
    private int rychlost;
    private Color[] farby = {Color.ORANGERED, Color.ORANGE, Color.GREEN, Color.MEDIUMBLUE, Color.MEDIUMPURPLE};
    private Group root;
    private Timeline t;
    private GraphicsContext gc = getGraphicsContext2D();

    public Balon(Group root) {
        super(2 * R, 3 * R);
        addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        this.root = root;
        pozicia = (int) (Math.random() * (root.getScene().getWidth() - getWidth()));
        rychlost = 1 + (int) (Math.random() * 3);
        setLayoutY(root.getScene().getHeight());
        setLayoutX(pozicia);
        vykresli();
        t = new Timeline(new KeyFrame(Duration.millis(10), evt -> pohyb()));
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

    private void pohyb() {
        if(getLayoutY() <= 0)
            root.getChildren().remove(this);
        setLayoutY(getLayoutY() - rychlost);
    }

    private void vykresli() {
        gc.setFill(farby[(int) (Math.random() * 5)]);
        gc.setStroke(Color.BLACK);
        gc.fillOval(0, 0, 2 * R, 2 * R);
        gc.strokeLine(R, 2 * R, R, 3 * R);
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        root.getChildren().remove(this);
    }
}
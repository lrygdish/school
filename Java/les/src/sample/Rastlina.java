package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;

public class Rastlina extends Canvas {

    protected Group root;
    protected int stav = 1;
    protected Timeline t;
    protected GraphicsContext gc;
    protected int g = 0;
    private int pozX;
    private int pozY;

    public Rastlina(Group root) {
        super(40, 80);
        this.root = root;
        pozX = 20 + (int) (Math.random() * (root.getScene().getWidth() - (getWidth() + 20)));
        pozY = 20 + (int) (Math.random() * (root.getScene().getHeight() - (getHeight() + 20)));
        gc = getGraphicsContext2D();
        setLayoutX(pozX);
        setLayoutY(pozY);
        vykresli(g);
        t = new Timeline(new KeyFrame(Duration.millis(1000), evt -> rast()));
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

    protected void vykresli(int g) {

    }

    protected void rast() {
        if(stav < 5) {
            setHeight(getHeight() + 5);
            setWidth(getWidth() + 5);
            g += 5;
            stav++;
            vykresli(g);
        }
        else {
            stav = 1;
            g = 0;
            setHeight(80);
            setWidth(40);
            vykresli(g);
        }
    }
}

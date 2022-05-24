package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Strom extends Rastlina {

    public Strom(Group root) {
        super(root);
    }

    @Override
    protected void vykresli(int g) {
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.setFill(Color.BROWN);
        gc.fillRect(10 + g / 2, 40, 20, 80 + g);
        gc.setFill(Color.GREEN);
        gc.fillOval(0, 0, 40 + g, 45 + g);
    }
}

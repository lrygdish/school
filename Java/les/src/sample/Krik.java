package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Krik extends Rastlina {

    public Krik(Group root) {
        super(root);
    }

    @Override
    protected void vykresli(int g) {
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.setFill(Color.GREEN);
        gc.fillPolygon(new double[]{getWidth() / 2, 0, getWidth()}, new double[]{getHeight() / 2, getHeight(), getHeight()}, 3);
    }
}

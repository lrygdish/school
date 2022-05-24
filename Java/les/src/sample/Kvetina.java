package sample;

import javafx.scene.Group;
import javafx.scene.paint.Color;

public class Kvetina extends Rastlina {

    public Kvetina(Group root) {
        super(root);
    }

    @Override
    protected void vykresli(int g) {
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.setFill(Color.GREEN);
        gc.fillRect(12.5 + g / 4, 20, 15, 80 + g);
        gc.setFill(Color.RED);
        gc.fillOval(0, 0, 40 + g / 2, 25 + g / 2);
        gc.setFill(Color.GREEN);
        gc.fillPolygon(new double[]{0, 12.5 + g / 4, 12.5 + g / 3}, new double[]{30 + g / 2, 50 + g, 80 + g}, 3);
        gc.fillPolygon(new double[]{getWidth(), 25 - g / 4, 25 - g / 3}, new double[]{30 + g / 2, 50 + g, 80 + g}, 3);
    }
}

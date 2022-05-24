package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

import java.nio.file.Paths;

public class Sliepka extends ImageView {

    private Image[] sprites;
    private Image killed;
    double maxWidth, maxHeight, rychlost;
    int actImage;
    int stav = 0; //0 = živá, 1 = strelená; 2 = za okrajom
    private static int zasahy = 0;
    private AudioClip zomri = new AudioClip(new Media(Paths.get("src/sample/sliepka.wav").toUri().toString()).getSource());

    public Sliepka(String nazovSpritu, int pocetSpritov, double w, double h, double maxw, double maxh) {
        super();
        maxWidth = maxw;
        maxHeight = maxh;
        sprites = new Image[pocetSpritov];
        for(int i = 0; i < pocetSpritov; i++)
            sprites[i] = new Image("sample/" + nazovSpritu + i + ".png", w, h, false, false);
        killed = new Image("sample/dead.png", w, h, false, false);
        do
            rychlost = (int) (-8 + Math.random() * 11) * 40;
        while(rychlost == 0);
        if(rychlost < 0) {
            actImage = 2;
            setImage(sprites[actImage]);
        }
        else {
            actImage = 0;
            setImage(sprites[actImage]);
        }
        if(rychlost < 0)
            setLayoutX(maxWidth);
        else
            setLayoutX(1);
        setLayoutY(100 + (int) (Math.random() * maxHeight));
        setOnMousePressed(e -> onClick());
        zomri.setVolume(0.2);
    }

    public void Zmena(double deltaTime) {
        setLayoutX(getLayoutX() + rychlost * deltaTime);
        setLayoutY(getLayoutY() - 5 + (int) (Math.random() * 11));
        if((getLayoutX() < 0) || (getLayoutX() > maxWidth) || (getLayoutY() < 0) || (getLayoutY() > maxHeight - 177))
            stav = 2;
        vykresli();
    }

    private void nextImage() {
        switch(actImage) {
            case 0:
                actImage = 1;
                break;
            case 1:
                actImage = 0;
                break;
            case 2:
                actImage = 3;
                break;
            case 3:
                actImage = 2;
                break;
        }
    }

    private void vykresli() {
        nextImage();
        if(getImage() == killed)
            stav = 2;
        if(stav == 0)
            setImage(sprites[actImage]);
        if(stav == 1)
            setImage(killed);
        if(stav == 2)
            setImage(null);
    }

    private void onClick() {
        stav = 1;
        zasahy++;
        zomri.play();
    }

    public double getStav() {
        return stav;
    }

    public int getZasahy() {
        return zasahy;
    }
}

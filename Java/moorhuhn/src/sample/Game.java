package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;

public class Game extends Group {

    final int SPRITESIZE = 55;
    final int maxSliepky = 15;
    private ImageView background;
    private LinkedList<Sliepka> zoznam;
    private double maxWidth, maxHeight;
    AnimationTimer t;
    long prevDelta = 0;
    Image bg;
    Text zasahy = new Text(5, 710, "Zásahy: ");
    Text tvystrely = new Text(zasahy.getX(), zasahy.getY() - 30, "Výstrely: ");
    private int z = 0;
    private int vystrely = 0;
    private MediaPlayer bgm = new MediaPlayer(new Media(Paths.get("src/sample/bgm.mp3").toUri().toString()));
    private AudioClip shot = new AudioClip(new Media(Paths.get("src/sample/gunshot.wav").toUri().toString()).getSource());

    public Game(int w, int h, String pozadie) {
        maxWidth = w;
        maxHeight = h;
        bg = new Image(pozadie, w, h, false, false);
        background = new ImageView(bg);
        getChildren().add(background);

        zoznam = new LinkedList<>();

        t = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(l - prevDelta >= 35_000_000) {
                    update(l - prevDelta);
                    prevDelta = l;
                }
            }
        };
        t.start();

        bgm.setOnEndOfMedia(() -> {
            bgm.seek(Duration.ZERO);
            bgm.play();
        });
        bgm.setVolume(0.1);
        shot.setVolume(0.2);
        bgm.play();

        setCursor(Cursor.CROSSHAIR);
        setOnMousePressed(e -> vystrel());

        zasahy.setFont(new Font(25));
        zasahy.setFill(Color.LAVENDER);
        tvystrely.setFont(zasahy.getFont());
        tvystrely.setFill(zasahy.getFill());
    }

    public void update(double deltaTime) {
        novaSliepka();
        pohyb(deltaTime / 1000000000);
        vymazSliepky();
        refresh();
        zabiteSliepky(z);
    }

    private void vystrel() {
        vystrely++;
        shot.play();
    }

    public void zabiteSliepky(int z) {
        tvystrely.setText("Výstrely: " + vystrely);
        zasahy.setText("Zásahy: " + z);
        getChildren().removeAll(tvystrely, zasahy);
        getChildren().addAll(tvystrely, zasahy);
    }

    private void novaSliepka() {
        if(zoznam.size() < maxSliepky)
            if(Math.random() < 0.1) {
                Sliepka sliepka = new Sliepka("sliepka", 4, SPRITESIZE, SPRITESIZE, maxWidth, maxHeight);
                zoznam.add(sliepka);
                getChildren().add(sliepka);
            }
    }

    private void refresh() {
        if(zoznam.isEmpty()) {
            getChildren().remove(background);
            getChildren().add(background);
        }
    }

    private void pohyb(double delta) {
        Iterator<Sliepka> it = zoznam.iterator();
        while(it.hasNext()) {
            Sliepka prvok = it.next();
            prvok.Zmena(delta);
        }
    }

    private void vymazSliepky() {
        Iterator<Sliepka> it = zoznam.iterator();
        while(it.hasNext()) {
            Sliepka prvok = it.next();
            if (prvok.getStav() == 2) {
                z = prvok.getZasahy();
                it.remove();
                getChildren().remove(prvok);
            }
        }
    }
}

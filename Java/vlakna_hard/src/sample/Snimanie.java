package sample;

import com.github.sarxos.webcam.Webcam;

import javax.imageio.ImageIO;
import java.io.File;

public class Snimanie extends Thread {

    private Webcam webcam;
    private File file;
    private int order = 0;

    public Snimanie() {
        super("Snímanie kamery");
        webcam = Webcam.getDefault();
        webcam.open();
    }

    @Override
    public void run() {
        while(true) {
            try {
                file = new File("captures/CaptureN.jpg");
                if(file.exists())
                    file.renameTo(new File("captures/CaptureO.jpg"));
                ImageIO.write(webcam.getImage(), "JPG", new File("captures/CaptureN.jpg"));
                Thread.sleep(100);
                file = new File("captures/CaptureO.jpg");
                file.renameTo(new File("archive/Capture" + order++ + ".jpg"));
            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }
}

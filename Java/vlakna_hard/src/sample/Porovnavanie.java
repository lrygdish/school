package sample;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.PixelGrabber;

public class Porovnavanie extends Thread {

    private int pocet = 0;
    private String file1;
    private String file2;

    public Porovnavanie() {
        super("Porovnávanie obrázkov");
        file1 = "captures/CaptureO.jpg";
        file2 = "captures/CaptureN.jpg";
    }

    public int getPocet() {
        return pocet;
    }

    @Override
    public void run() {
        while(true) {
            pocet = 0;
            // Load the images
            Image image1 = Toolkit.getDefaultToolkit().getImage(file1);
            Image image2 = Toolkit.getDefaultToolkit().getImage(file2);

            try {

                PixelGrabber grabImage1Pixels = new PixelGrabber(image1, 0, 0, -1,
                        -1, false);
                PixelGrabber grabImage2Pixels = new PixelGrabber(image2, 0, 0, -1,
                        -1, false);

                int[] image1Data = null;

                if (grabImage1Pixels.grabPixels()) {
                    int width = grabImage1Pixels.getWidth();
                    int height = grabImage1Pixels.getHeight();
                    //image1Data = new int[width * height];
                    image1Data = (int[]) grabImage1Pixels.getPixels();
                }

                int[] image2Data = null;

                if (grabImage2Pixels.grabPixels()) {
                    int width = grabImage2Pixels.getWidth();
                    int height = grabImage2Pixels.getHeight();
                    //image2Data = new int[width * height];
                    image2Data = (int[]) grabImage2Pixels.getPixels();
                }

                System.out.println("Pixels equal: "
                        + java.util.Arrays.equals(image1Data, image2Data));
                if(!java.util.Arrays.equals(image1Data, image2Data))
                    pocet++;
                if(grabImage1Pixels.getWidth() != grabImage2Pixels.getWidth())
                    pocet++;
                if(grabImage1Pixels.getHeight() != grabImage2Pixels.getHeight())
                    pocet++;
                Thread.sleep(1000);

            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
}

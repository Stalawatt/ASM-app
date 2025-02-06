import javax.swing.*;
import java.awt.*;

public class Main {

    private static Window window;
    private static PageManager pageManager;


    public static void main(String[] args) {
        // initialisation logic here

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        window = new Window();

        if (gd.isFullScreenSupported()) {
            window.frame.setUndecorated(true);
            gd.setFullScreenWindow(window.frame);
        }

        pageManager = new PageManager();
        pageManager.addPage(0, window);



    }
}
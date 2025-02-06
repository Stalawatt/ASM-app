import javax.swing.*;
import java.awt.*;

public class Main {

    private static Window window;
    private static PageManager pageManager;
    private static HomeScreen homescreen;


    public static void main(String[] args) throws Exception {
        // initialisation logic here

        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        SwingUtilities.invokeLater(() -> {

            // create pages
            homescreen = new HomeScreen();

            // add pages to pagemanager
            pageManager = new PageManager();
            pageManager.addPage(0, homescreen);

            window = new Window(1920,1080, pageManager);


            if (window.frame == null) {
                try {
                    throw new Exception("Frame init error");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }


        });


    }
}
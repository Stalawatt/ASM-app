import javax.swing.*;
import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Window {
        public JFrame frame;

        // Colours

        private Button LMC_button;

        public Window(int w, int h, PageManager pageManager) {


            frame = new JFrame();
            frame.setSize(w, h);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            this.LMC_button = new Button(100,100,150,100);

            this.start(pageManager);
        }

        public void renderFrame(PageManager pageManager) {
            // render logic goes here

            JPanel[] toRender = pageManager.getPage().getToRender();

            for (JPanel panel : toRender) {
                frame.add(panel);
            }

            frame.repaint();

        }

        public void start(PageManager pageManager) {
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

            Runnable gameLoop = () -> {
                this.renderFrame(pageManager);
            };

            executor.scheduleAtFixedRate(gameLoop, 0, 16, TimeUnit.MILLISECONDS);

        }


}

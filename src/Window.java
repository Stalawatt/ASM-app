import javax.swing.*;

public class Window {
        public JFrame frame;
        private final int width,height;

        public Window(int _width, int _height) {

            this.width = _width;
            this.height = _height;

            frame = new JFrame();
            frame.setSize(width, height );
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        }

        public void renderFrame() {
            // render logic goes here
        }


}

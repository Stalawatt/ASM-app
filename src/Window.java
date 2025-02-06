import javax.lang.model.type.NullType;
import javax.swing.*;

public class Window {
        public JFrame frame;

        private int width = 0;
        private int height = 0;

        public Window(int _width, int _height) {

            this.width = _width;
            this.height = _height;

            frame = new JFrame();
            frame.setSize(width, height );
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        }

        public Window() {
            frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        public void renderFrame() {
            // render logic goes here



        }


}

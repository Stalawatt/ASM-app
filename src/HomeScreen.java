import javax.swing.*;
import java.awt.*;
import java.util.Locale;

public class HomeScreen implements Page{

    // Variables for the homescreen

    private Button[] buttons;
    private JPanel background = new JPanel() {
        public void paint(Graphics g) {
            setBackground(Color.BLACK);
        }
    };

    private final Color bgColor = Color.decode("0x14101c");
    private final Color textColor = Color.decode("0xe7e4ed");

    public HomeScreen() {
        buttons = new Button[5];
        buttons[0] = new Button(0,0,150,100);

    }


    @Override
    public JPanel[] getToRender() {
        JPanel[] output = new JPanel[buttons.length + 1];

        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] != null) {
                output[i] = buttons[i].getSprite();
            }
        }
        output[buttons.length + 1] = background;
        return output;
    }

}

import javax.swing.*;
import java.awt.*;

public class HomeScreen {

    // Variables for the homescreen

    private Button[] buttons;

    private Color bgColor = Color.decode("0x14101c");
    private Color textColor = Color.decode("0xe7e4ed");

    public HomeScreen() {
        buttons = new Button[5];
        buttons[0] = new Button(0,0,150,100);

    }

    public void render(JFrame frame) {
        frame.setBackground(bgColor);
        for (Button button : buttons) {
            frame.add(button.getSprite());
        }
    }

}

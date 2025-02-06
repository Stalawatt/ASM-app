import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Button {

    public int x,y,w,h;

    public String text;

    private BufferedImage sprite;

    public Button(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;


    }

    public Button(String text, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
        this.text = text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public void loadSprite(String path) throws IOException {
        this.sprite = ImageIO.read(new File(path));
    }

    public JPanel getSprite() {
        if (sprite != null) {
            return new JPanel() {
                public void paint(Graphics g) {
                    g.drawImage(sprite, x, y, null);
                }
            };
        }
        else {
            return new JPanel() {
                public void paint(Graphics g) {
                    g.drawRect(x,y,w,h);
                }
            };
        }
    }

    public void onClick() {

    }

}

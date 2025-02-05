import javax.swing.*;
import java.awt.*;

public class Button {

    public int x,y,w,h;

    public String text;

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



}

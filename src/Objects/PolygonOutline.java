package Objects;

import javax.swing.*;
import java.awt.*;

public class PolygonOutline extends JPanel {

    private int[] xPoints, yPoints;
    private final int nPoints;
    private Color colour;


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        Polygon polygon = new Polygon(xPoints, yPoints, nPoints);

        g2d.setColor(colour);
        g2d.drawPolygon(polygon);
    }


    public PolygonOutline(int[] xPointsList, int[] yPointsList, int numPoints, Color outlineColor) {
        xPoints = xPointsList;
        yPoints = yPointsList;
        nPoints = numPoints;
        colour = outlineColor;
    }

    public PolygonOutline(int[] xPointsList, int[] yPointsList, Color outlineColor) {
        if (xPoints.length != yPoints.length) {
            throw new Error("xPoints.length != yPoints.length");
        }

        xPoints = xPointsList;
        yPoints = yPointsList;
        nPoints = xPoints.length;
        colour = outlineColor;

    }

    public void setColor(Color outlineColor) {
        this.colour = outlineColor;
    }





}

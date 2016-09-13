package strategyPat;

import java.awt.*;
import java.awt.geom.Ellipse2D;

/**
 * Created by HCH on 28-Aug-16.
 */
public class CEllipseDrawer implements DrawStrategy{

    private static CEllipseDrawer instance = new CEllipseDrawer();

    private CEllipseDrawer(){}

    public static CEllipseDrawer getInstance(){
        return instance;
    }

    public void draw(int x, int y, int width, int height,Boolean isSelected, Graphics2D g2d){
        Shape shape = new Ellipse2D.Double(x, y, width, height);
        g2d.draw(shape);
        if (isSelected) {
            g2d.fill(shape);
            drawHighlightSquares(g2d, shape.getBounds2D());
        }
    }

    public String toString(int x, int y, int width, int height){
        StringBuilder builder = new StringBuilder();
        builder.append("ellipse");
        builder.append(" "+ x);
        builder.append(" "+ y);
        builder.append(" "+ width);
        builder.append(" "+ height + "\n");
        return builder.toString();
    }

}


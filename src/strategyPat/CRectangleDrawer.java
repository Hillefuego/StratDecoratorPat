package strategyPat;

import java.awt.*;

/**
 * Created by HCH on 28-Aug-16.
 */
public class CRectangleDrawer implements DrawStrategy {

    private static CRectangleDrawer instance = new CRectangleDrawer();

    private CRectangleDrawer(){}

    public static CRectangleDrawer getInstance(){
        return instance;
    }

    public void draw(int x, int y, int width, int height,Boolean isSelected, Graphics2D g2d){
        Shape shape = new Rectangle(x, y, width, height);
        g2d.draw(shape);
        if (isSelected) {
            g2d.fill(shape);
            drawHighlightSquares(g2d, shape.getBounds2D());
        }
    }

    public String toString(int x, int y, int width, int height){
        StringBuilder builder = new StringBuilder();
        builder.append("rectangle");
        builder.append(" "+ x);
        builder.append(" "+ y);
        builder.append(" "+ width);
        builder.append(" "+ height + "\n");
        return builder.toString();
    }

}

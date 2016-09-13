package strategyPat;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by HCH on 28-Aug-16.
 */
public interface DrawStrategy {

    default void drawHighlightSquares(Graphics2D g2D, Rectangle2D r) {
        double x = r.getX();
        double y = r.getY();
        double w = r.getWidth();
        double h = r.getHeight();
        g2D.setPaint(Color.black);

        g2D.fill(new Rectangle.Double(x - 6.0, y - 6.0, 6.0, 6.0));
        g2D.fill(new Rectangle.Double(x + w + 1.0, y - 6.0, 6.0, 6.0));
        g2D.fill(new Rectangle.Double(x - 6.0, y + h + 1.0, 6.0, 6.0));
        g2D.fill(new Rectangle.Double(x + w + 1.0, y + h + 1.0, 6.0, 6.0));
    }

    String toString(int x, int y, int width, int height);
    void draw(int x, int y, int width, int height, Boolean isSelected, Graphics2D graphics2D);
}

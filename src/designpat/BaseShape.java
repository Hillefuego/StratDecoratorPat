package designpat;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * Created by HCH on 03-Jul-16.
 */
public interface BaseShape {
    int getHeight();
    int getWidth();
    Point getCenterPoint();
    void resize(Point currentPoint);
    void drag(Point currentPoint, Point oldPoint);
    void select();
    void unselect();
    void draw(Graphics2D g2d);
    boolean isSelected();
    boolean contains(Point currentPoint);
    String toString(int indent);
}

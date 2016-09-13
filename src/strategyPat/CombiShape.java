package strategyPat;

import designpat.BaseShape;
import visitorPat.ShapeVisitor;
import visitorPat.Visitable;
import java.awt.*;

/**
 * Created by HCH on 28-Aug-16.
 */
public class CombiShape implements BaseShape, Visitable {
    private DrawStrategy strategy;
    private boolean isSelected = false;
    private int x;
    private int y;
    private int width;
    private int height;

    public CombiShape(DrawStrategy strategy, int x, int y, int width, int height){
        this.strategy = strategy;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Point getCenterPoint(){
        return new Point(x,y);
    }

    public boolean isSelected(){
        return this.isSelected;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }

    public void resize(Point currentPoint){
        if(isSelected) {
            width = currentPoint.x - x;
            height = currentPoint.y - y;
        }
    }

    public void drag(Point currentPoint, Point oldPoint){
        Rectangle shapeBounds = new Rectangle(x, y, width, height);
        Point centerPoint = new Point(currentPoint.x - (width / 2), currentPoint.y - (height / 2));
        if (isSelected && shapeBounds.contains(x,y)) {
/*            x =  currentPoint.x - oldPoint.x;
            y =  currentPoint.y - oldPoint.y;*/
            x = centerPoint.x;
            y = centerPoint.y;
        }
    }
    public void select(){
        this.isSelected = !isSelected;
    }
    public void unselect(){
        this.isSelected = false;
    }


    public void draw(Graphics2D g2d) {
        strategy.draw(x, y, width, height, isSelected, g2d);
    }

    public boolean contains(Point currentPoint){
        Rectangle shapeBounds = new Rectangle(x, y, width, height);
        if(shapeBounds.contains(currentPoint))
            return true;
        else return false;
    }

    public String toString(int x, int y, int width, int height){
        return strategy.toString(x, y, width, height);
    }

    public String toString(int indent){
        return "";
    }

    public void accept(ShapeVisitor visitor){
        visitor.visit(this);
    }
}

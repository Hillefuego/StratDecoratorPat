package decoratorPat;

import designpat.BaseShape;

import java.awt.*;

/**
 * Created by HCH on 07-Sep-16.
 */
public class ShapeDecorator implements BaseShape {
    protected BaseShape baseShapeToBeDecorated;

    public ShapeDecorator(BaseShape baseShapeToBeDecorated){
        this.baseShapeToBeDecorated = baseShapeToBeDecorated;
    }

    public void resize(Point currentPoint){
        baseShapeToBeDecorated.resize(currentPoint);
    }

    public void drag(Point currentPoint, Point lastPoint){
        baseShapeToBeDecorated.drag(currentPoint, lastPoint);
    }

    public void select(){
        baseShapeToBeDecorated.select();
    }

    public void unselect(){
        baseShapeToBeDecorated.unselect();
    }
    public void draw(Graphics2D g2d){
        baseShapeToBeDecorated.draw(g2d);
    }

    public boolean isSelected(){
        return baseShapeToBeDecorated.isSelected();
    }

    public boolean contains(Point currentPoint){
        return baseShapeToBeDecorated.contains(currentPoint);
    }

    public String toString(int indent){
        return baseShapeToBeDecorated.toString(indent);
    }

    public Point getCenterPoint(){
        return baseShapeToBeDecorated.getCenterPoint();
    }

    public int getHeight(){
        return baseShapeToBeDecorated.getHeight();
    }

    public int getWidth(){
        return baseShapeToBeDecorated.getWidth();
    }

}

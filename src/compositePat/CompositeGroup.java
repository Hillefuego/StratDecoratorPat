package compositePat;

import decoratorPat.BottomOrnamentDecorator;
import decoratorPat.TopOrnamentDecorator;
import designpat.BaseShape;
import strategyPat.CombiShape;
import visitorPat.ShapeVisitor;
import visitorPat.Visitable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HCH on 01-Jun-16.
 */
public class CompositeGroup implements BaseShape, Visitable {
    private List<BaseShape> shapes = new ArrayList<>();
    private boolean isSelected = false;

    public CompositeGroup(){

    }

    public CompositeGroup(List<BaseShape> shapes){
        this.shapes = shapes;
    }

    public int getHeight(){
        int countX = 0;
        int totalY = 0;
        for(BaseShape baseShape : shapes){
            totalY += baseShape.getCenterPoint().getY();
            countX++;
        }
        return totalY / countX;
    }

    public int getWidth(){
        return 50;
    }

    public void add(BaseShape baseShape){
        shapes.add(baseShape);
    }

    public void remove(BaseShape baseShape){
        shapes.remove(baseShape);
    }

    @Override
    public void select() {
        if(shapes.size() > 0){
            shapes.stream().forEach(baseShape -> {
                baseShape.select();
            });
        }
        isSelected = true;
    }

    public List<BaseShape> getShapes(){
        return this.shapes;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public boolean contains(Point currentPoint) {
        if(shapes.size() > 0){
            shapes.stream().forEach(baseShape -> {
                if(baseShape.contains(currentPoint)) {
                    baseShape.select();
                }
            });
        }
        return false;
    }


    public String toString(int indent) {
        StringBuilder builder = new StringBuilder();
        builder.append("group "+shapes.size()+"\n");
        String whitespace ="";
        for(int i = 0; i< indent; i++){
            whitespace+=" ";
        }
        for (BaseShape baseShape:shapes){
            if(baseShape instanceof CompositeGroup)
                builder.append(whitespace+baseShape.toString(indent+2));
            else if(baseShape instanceof CombiShape) {
                CombiShape combiShape = (CombiShape)baseShape;
                builder.append(whitespace + combiShape.toString(combiShape.getX(),combiShape.getY(),combiShape.getWidth(),combiShape.getHeight()));
            }else if(baseShape instanceof TopOrnamentDecorator){
                TopOrnamentDecorator decoratorShape = (TopOrnamentDecorator)baseShape;
                if(decoratorShape.getBaseShape() instanceof CompositeGroup) {
                    builder.append(whitespace + baseShape.toString(indent + 2));
                    builder.append(whitespace + decoratorShape.getDescription());
                }
                else if(decoratorShape.getBaseShape() instanceof CombiShape) {
                    CombiShape combiShape = (CombiShape) decoratorShape.getBaseShape();
                    builder.append(whitespace + combiShape.toString(combiShape.getX(), combiShape.getY(), combiShape.getWidth(), combiShape.getHeight()));
                    builder.append(whitespace + decoratorShape.getDescription());
                }
            }
            else if(baseShape instanceof BottomOrnamentDecorator) {
                BottomOrnamentDecorator decoratorShape = (BottomOrnamentDecorator) baseShape;
                if (decoratorShape.getBaseShape() instanceof CompositeGroup) {
                    builder.append(whitespace + baseShape.toString(indent + 2));
                    builder.append(whitespace + decoratorShape.getDescription());
                } else if (decoratorShape.getBaseShape() instanceof CombiShape) {
                    CombiShape combiShape = (CombiShape) decoratorShape.getBaseShape();
                    builder.append(whitespace + combiShape.toString(combiShape.getX(), combiShape.getY(), combiShape.getWidth(), combiShape.getHeight()));
                    builder.append(whitespace + decoratorShape.getDescription());
                }
            }
        }
        return builder.toString();
    }

    @Override
    public void resize(Point currentPoint) {
        if(shapes.size() > 0){
            shapes.stream().forEach(baseShape -> {
                baseShape.resize(currentPoint);
            });
        }
    }

    @Override
    public void drag(Point currentPoint, Point lastPoint) {
        if(shapes.size() > 0){
            shapes.stream().forEach(baseShape -> {
                baseShape.drag(currentPoint, lastPoint);
            });
        }
    }

    @Override
    public void unselect() {
        if(shapes.size() > 0){
            shapes.stream().forEach(baseShape -> {
                baseShape.unselect();
            });
        }
        isSelected = false;
    }

    @Override
    public void draw(Graphics2D g2d) {
        if(shapes.size() > 0){
            shapes.stream().forEach(baseShape -> {
                baseShape.draw(g2d);
            });
        }
    }

    public void accept(ShapeVisitor visitor){
        visitor.visit(this);
    }

    public Point getCenterPoint(){
        if(shapes.size()>0)
            return shapes.get(0).getCenterPoint();
        return new Point(50,50);
    }
}

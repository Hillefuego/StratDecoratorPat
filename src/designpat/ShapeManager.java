package designpat;

import compositePat.CompositeGroup;
import decoratorPat.BottomOrnamentDecorator;
import decoratorPat.TopOrnamentDecorator;
import visitorPat.MoveVisitor;
import visitorPat.ResizeVisitor;
import visitorPat.ShapeVisitor;

import java.awt.*;
import java.util.Iterator;
import java.util.List;

/**
 * Created by HCH on 01-Jun-16.
 */
public class ShapeManager {
    private CompositeGroup shapes = new CompositeGroup();
    private CompositeGroup selectedGroup = null;
    private CompositeGroup parentGroup = null;
    private CompositeGroup childGroup = null;

    public CompositeGroup getChildGroup(){
        return childGroup;
    }

    public void setChildGroup(CompositeGroup group){
        childGroup = group;
    }

    public CompositeGroup getParentGroup(){
        return parentGroup;
    }

    public void setParentGroup(CompositeGroup group){
        parentGroup = group;
    }

    public void addShape(BaseShape shape){
        shapes.add(shape);
    }

    public void setShapes(CompositeGroup shapes){
        this.shapes = shapes;
    }

    public void removeShape() {
        if(shapes.getShapes().size()>0)
            shapes.getShapes().remove(shapes.getShapes().size()-1);
    }

    public void addNewGroup(List<BaseShape> selectedShapes){
        CompositeGroup newGroup = new CompositeGroup(selectedShapes);
        shapes.add(newGroup);
        selectedShapes.stream().forEach((baseShape -> shapes.remove(baseShape)));
    }

    public void removeGroup() {
        shapes.getShapes().remove(shapes.getShapes().size()-1);
    }

    public void addChildGroup(CompositeGroup parentGroup, CompositeGroup childGroup){
        if(parentGroup != null && childGroup != null) {
            parentGroup.add(childGroup);
            shapes.remove(childGroup);
        }
    }

    public void removeChildGroup(CompositeGroup parentGroup, CompositeGroup childGroup){
        parentGroup.remove(childGroup);
    }

    public void selectShapes(Point currentPoint) {
        //unselectShapes();
        if(toolboxControls.select == true) {
            shapes.getShapes().stream().forEach(baseShape -> {
                if (baseShape.contains(currentPoint)) {
                    baseShape.select();
                }
            });
        }
/*        if(toolboxControls.selectGroup == true){
            selectedGroup = setNextGroup();
            unselectShapes();
            if(selectedGroup != null){
                selectedGroup.select();
            }
        }else if(toolboxControls.select == true) {
        }*/
    }

    public void selectNextGroup(){
        selectedGroup = getNextGroup();
        if(selectedGroup != null)
            selectedGroup.select();
    }


    public CompositeGroup getNextGroup(){
        if(shapes.getShapes().size() > 0) {
            if (selectedGroup != null) {
                int currentIndex = shapes.getShapes().indexOf(selectedGroup);
                if (shapes.getShapes().indexOf(selectedGroup) == shapes.getShapes().size() - 1) {
                    if (shapes.getShapes().get(0) instanceof CompositeGroup) {
                        return (CompositeGroup)shapes.getShapes().get(0);
                    }
                    return null;
                }
                if (shapes.getShapes().get(0) instanceof CompositeGroup) {
                    return (CompositeGroup) shapes.getShapes().get(currentIndex + 1);
                }
            } else if (selectedGroup == null) {
                for(BaseShape baseShape : shapes.getShapes()){
                    if(baseShape instanceof CompositeGroup){
                        return (CompositeGroup)baseShape;
                    }
                }
            }
            return null;        // Return null if no group has been found
        }
        return null;        // Return null if no group has been found
    }

    public void unselectShapes (){
        shapes.getShapes().stream().forEach((baseShape ->{
            baseShape.unselect();
        }));
    }

    public CompositeGroup getSelectedGroup(){
        return selectedGroup;
    }

    public List<BaseShape> getShapes(){
        return this.shapes.getShapes();
    }

    public void drawShapes(Graphics2D g2d) {
        if(shapes.getShapes().size() != 0) {
            shapes.getShapes().stream().forEach(baseShape -> {
                if(baseShape != null)
                    baseShape.draw(g2d);
            });
        }
    }


    public void clear(){
        shapes.getShapes().clear();
    }

    public void dragShapes(Point currentPoint, Point lastPoint){
        ShapeVisitor moveVisitor = new MoveVisitor(currentPoint, lastPoint);
        shapes.getShapes().stream().forEach(baseShape->
                moveVisitor.visit(baseShape)
        );
    }

    public void resizeShape(Point currentPoint, Point lastPoint){
        ShapeVisitor resizeVisitor = new ResizeVisitor(currentPoint);
        shapes.getShapes().stream().forEach((baseShape -> resizeVisitor.visit(baseShape)));
    }

    public String printAll(){
        StringBuilder builder = new StringBuilder();
        /*shapes.getShapes().stream().forEach((baseShape -> {
            builder.append(baseShape.toString());
        }));*/
        builder.append(shapes.toString(2));
        return builder.toString();
    }

    public void setOrnament(String side, String comment){
        BaseShape baseShapeOld = null;
        BaseShape baseShapeNew = null;
        Iterator<BaseShape> iter = shapes.getShapes().iterator();
        while(iter.hasNext()){
            baseShapeOld = iter.next();
            if(baseShapeOld.isSelected()) {
                if(side.equals("Top")) {
                    baseShapeNew = new TopOrnamentDecorator(baseShapeOld);
                    ((TopOrnamentDecorator) baseShapeNew).setDescription(comment);
                }
                if(side.equals("Bottom")){
                    baseShapeNew = new BottomOrnamentDecorator(baseShapeOld);
                    ((BottomOrnamentDecorator) baseShapeNew).setDescription(comment);
                }
                if(baseShapeNew != null && baseShapeOld != null)
                    break;
            }
        }
        if(baseShapeNew != null && baseShapeOld != null) {
            shapes.add(baseShapeNew);
            shapes.remove(baseShapeOld);
        }
    }

}

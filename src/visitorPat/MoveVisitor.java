package visitorPat;

import FileIO.FileWriter;
import designpat.BaseShape;

import java.awt.*;

/**
 * Created by HCH on 26-Aug-16.
 */
public class MoveVisitor implements ShapeVisitor {
    Point location;
    Point prevLocation;

    public MoveVisitor(Point p, Point pOld){
        location = p;
        prevLocation = pOld;
    }
    public void visit(BaseShape baseShape){
        baseShape.drag(location, prevLocation);
    }

    public void visit(FileWriter fileWriter){}
}

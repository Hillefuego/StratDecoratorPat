package visitorPat;


import FileIO.FileWriter;
import designpat.BaseShape;

import java.awt.*;

/**
 * Created by HCH on 26-Aug-16.
 */
public class ResizeVisitor implements ShapeVisitor {

    Point location;
    public ResizeVisitor(Point p){
        location = p;
    }

    public void visit(BaseShape baseShape){
        baseShape.resize(location);
    }
    public void visit(FileWriter fileWriter){}
}

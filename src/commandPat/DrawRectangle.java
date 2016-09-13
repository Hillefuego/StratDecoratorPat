package commandPat;

import designpat.BaseShape;
import designpat.ShapeManager;
import strategyPat.CRectangleDrawer;
import strategyPat.CombiShape;


import java.awt.*;


/**
 * Created by HCH on 01-Jun-16.
 */
public class DrawRectangle implements Command {
    private ShapeManager shapeMan;
    private BaseShape baseShape;

    public DrawRectangle(Point lastPoint, Point currentPoint, ShapeManager shapeMan){
        this.shapeMan = shapeMan;
        this.baseShape = new CombiShape(CRectangleDrawer.getInstance(),lastPoint.x,lastPoint.y, Math.abs(currentPoint.x - lastPoint.x), Math.abs(currentPoint.y - lastPoint.y));
    }

    @Override
    public void execute() {
        shapeMan.addShape(baseShape);
    }

    @Override
    public void undo() {
        shapeMan.removeShape();
    }
}

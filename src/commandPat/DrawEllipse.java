package commandPat;

import designpat.BaseShape;
import designpat.ShapeManager;
import strategyPat.CEllipseDrawer;
import strategyPat.CombiShape;

import java.awt.*;

/**
 * Created by HCH on 01-Jun-16.
 */
public class DrawEllipse implements Command{
    private ShapeManager shapeManager;
    private BaseShape baseShape;


    public DrawEllipse(Point lastPoint, Point currentPoint, ShapeManager shapeManager){
        this.shapeManager = shapeManager;
        this.baseShape = new CombiShape(CEllipseDrawer.getInstance(),lastPoint.x, lastPoint.y, Math.abs(currentPoint.x - lastPoint.x), Math.abs(currentPoint.y - lastPoint.y));
    }

    @Override
    public void execute() {
        shapeManager.addShape(baseShape);
    }

    @Override
    public void undo() {
        shapeManager.removeShape();
    }
}

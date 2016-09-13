package commandPat;

import designpat.ShapeManager;
import java.awt.*;


/**
 * Created by HCH on 01-Jun-16.
 */
public class ResizeShape implements Command{
    private ShapeManager shapeMan;
    private Point currentPoint;
    private Point lastPoint;

    public ResizeShape(ShapeManager shapeMan, Point currentPoint, Point lastPoint){
        this.shapeMan = shapeMan;
        this.currentPoint = currentPoint;
        this.lastPoint = lastPoint;
    }

    @Override
    public void execute() {
        shapeMan.resizeShape(currentPoint, lastPoint);
    }

    @Override
    public void undo() {
        shapeMan.resizeShape(lastPoint, currentPoint);
    }
}

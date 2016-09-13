package commandPat;

import designpat.ShapeManager;

import java.awt.*;

/**
 * Created by HCH on 01-Jun-16.
 */
public class SelectShapes implements Command {
    private ShapeManager shapeMan;
    private Point currentPoint;

    public SelectShapes(Point currentPoint, ShapeManager shapeMan){
        this.shapeMan = shapeMan;
        this.currentPoint = currentPoint;
    }

    @Override
    public void execute() {
        shapeMan.selectShapes(currentPoint);
    }

    @Override
    public void undo() {shapeMan.unselectShapes();
    }
}

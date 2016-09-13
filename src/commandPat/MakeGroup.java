package commandPat;

import designpat.BaseShape;
import designpat.ShapeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HCH on 03-Jun-16.
 */
public class MakeGroup implements Command {
    private ShapeManager shapeMan;
    private List<BaseShape> selectedShapes = new ArrayList<>();

    public MakeGroup(ShapeManager shapeManager){
        this.shapeMan = shapeManager;
        shapeMan.getShapes().stream().forEach((baseShape -> {
            if(baseShape.isSelected()){
                selectedShapes.add(baseShape);
            }
        }));
    }

    @Override
    public void execute() {
        shapeMan.addNewGroup(selectedShapes);
    }

    @Override
    public void undo() {
        shapeMan.removeGroup();
    }
}

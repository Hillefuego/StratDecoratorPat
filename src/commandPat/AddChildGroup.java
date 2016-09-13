package commandPat;


import compositePat.CompositeGroup;
import compositePat.Group;
import designpat.BaseShape;
import designpat.ShapeManager;

/**
 * Created by HCH on 15-Jun-16.
 */
public class AddChildGroup implements Command {
    private ShapeManager shapeMan;
    private CompositeGroup parentGroup;
    private CompositeGroup childGroup;

    public AddChildGroup(ShapeManager shapeManager){
        this.shapeMan = shapeManager;
        this.parentGroup = shapeManager.getParentGroup();
        this.childGroup = shapeManager.getChildGroup();
    }

    @Override
    public void execute() {
        shapeMan.addChildGroup(parentGroup, childGroup);
    }

    @Override
    public void undo() {
        shapeMan.removeChildGroup(parentGroup, childGroup);
    }
}

package visitorPat;

import FileIO.FileWriter;
import designpat.BaseShape;

/**
 * Created by HCH on 26-Aug-16.
 */
public interface ShapeVisitor {
    public void visit(BaseShape baseShape);
    public void visit(FileWriter fileWriter);
}

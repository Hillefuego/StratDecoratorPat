package decoratorPat;

import designpat.BaseShape;

import java.awt.*;

/**
 * Created by HCH on 12-Sep-16.
 */
public class BottomOrnamentDecorator extends ShapeDecorator {

    private String description = "ornament bottom \n";

    public BottomOrnamentDecorator(BaseShape baseShape){
        super(baseShape);
    }

    public void setDescription(String description){
        this.description = "ornament bottom \"" + description + "\"\n";
    }

    public void setFullDescription(String description){
        this.description = description + "\n";
    }

    public String getDescription(){
        return this.description;
    }

    public BaseShape getBaseShape(){
        return super.baseShapeToBeDecorated;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append(super.baseShapeToBeDecorated.toString());
        builder.append(description+"\n");
        return builder.toString();
    }

    @Override
    public void draw(Graphics2D g2d){
        g2d.drawString(this.description,super.baseShapeToBeDecorated.getCenterPoint().x, super.baseShapeToBeDecorated.getCenterPoint().y + super.baseShapeToBeDecorated.getHeight()+10);
        baseShapeToBeDecorated.draw(g2d);
    }
}

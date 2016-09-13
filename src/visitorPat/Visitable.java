package visitorPat;

/**
 * Created by HCH on 26-Aug-16.
 */
public interface Visitable {
    public void accept(ShapeVisitor visitor);
}

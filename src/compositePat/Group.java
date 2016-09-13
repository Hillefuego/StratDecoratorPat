package compositePat;

import designpat.BaseShape;

import java.util.List;

/**
 * Created by HCH on 01-Jun-16.
 */
public interface Group extends BaseShape {
    void add(Group group);

    void remove(Group group);

    List<BaseShape> getShapes();
}

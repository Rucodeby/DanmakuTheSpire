package danmaku.interfaces;

import com.badlogic.gdx.math.Polygon;

public interface ICollidable extends IEntity{

    Polygon getBounds();
    void setBounds(float[] vertices);

    int getContactFilter();
    void setContactFilter(int value);

    int getContactCategory();
    void setContactCategory(int value);

    void doCollision(ICollidable other);

}

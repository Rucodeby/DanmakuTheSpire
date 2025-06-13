package danmaku.interfaces;

import com.badlogic.gdx.math.Polygon;

import java.util.ArrayList;

public interface ICollidable extends IEntity{

    Polygon getHitbox();
    void setHitbox(float[] vertices);

    CollisionType getContactFilter();
    void setContactFilter(CollisionType value);

    ArrayList<CollisionType> getContactCategory();
    void setContactCategory(CollisionType[] value);

    void doCollision(ICollidable other);

    public static enum CollisionType {
        NONE,
        PLAYER,
        ENEMY,
        BOSS,
        PLAYER_BULLET,
        ENEMY_BULLET,
        RESOURCE
    }

}

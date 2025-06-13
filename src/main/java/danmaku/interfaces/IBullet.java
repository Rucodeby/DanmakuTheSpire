package danmaku.interfaces;

public interface IBullet extends ICollidable {

    default void delete() {
        // TODO: Deletion from quadTree
    }

    void erase();

    int getDamage();
    void setDamage(int value);

}

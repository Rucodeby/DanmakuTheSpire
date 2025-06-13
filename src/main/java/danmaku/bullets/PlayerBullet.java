package danmaku.bullets;

import danmaku.interfaces.IBullet;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class PlayerBullet implements IBullet {

    protected CollisionType contactFilter = CollisionType.PLAYER_BULLET;
    protected ArrayList<CollisionType> contactCategory = new ArrayList<>(Arrays.asList(CollisionType.ENEMY, CollisionType.BOSS));
    protected int damage = 1;

    public CollisionType getContactFilter() {
        return this.contactFilter;
    };

    public void setContactFilter(CollisionType value) {
        this.contactFilter = value;
    };

    public ArrayList<CollisionType> getContactCategory() {
        return this.contactCategory;
    };

    public void setContactCategory(CollisionType[] value) {
        this.contactCategory = new ArrayList<CollisionType>(Arrays.asList(value));
    };

    public int getDamage() {
        return damage;
    };

    public void setDamage(int value) {
        damage = value;
    };

}

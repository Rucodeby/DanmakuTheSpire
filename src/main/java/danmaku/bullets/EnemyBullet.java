package danmaku.bullets;

import danmaku.interfaces.ICollidable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public abstract class EnemyBullet {
    protected ICollidable.CollisionType contactFilter = ICollidable.CollisionType.ENEMY_BULLET;
    protected ArrayList<ICollidable.CollisionType> contactCategory = new ArrayList<>(Collections.singletonList(ICollidable.CollisionType.PLAYER));
    protected int damage = 1;

    public ICollidable.CollisionType getContactFilter() {
        return this.contactFilter;
    }

    public void setContactFilter(ICollidable.CollisionType value) {
        this.contactFilter = value;
    }

    public ArrayList<ICollidable.CollisionType> getContactCategory() {
        return this.contactCategory;
    }

    public void setContactCategory(ICollidable.CollisionType[] value) {
        this.contactCategory = new ArrayList<ICollidable.CollisionType>(Arrays.asList(value));
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int value) {
        damage = value;
    }

}

package danmaku.collectables;

import danmaku.interfaces.ICollidable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public abstract class AbstractCollectable implements ICollidable {

    protected CollisionType contactFilter = CollisionType.RESOURCE;
    protected ArrayList<CollisionType> contactCategory = new ArrayList<>(Collections.singletonList(CollisionType.PLAYER));

    @Override
    public CollisionType getContactFilter() {
        return this.contactFilter;
    }

    @Override
    public void setContactFilter(CollisionType value) {
        this.contactFilter = value;
    }

    @Override
    public ArrayList<CollisionType> getContactCategory() {
        return this.contactCategory;
    }

    @Override
    public void setContactCategory(CollisionType[] value) {
        this.contactCategory = new ArrayList<CollisionType>(Arrays.asList(value));
    }

    protected void move() {
        // TODO - Write movement logic there with Vector2 and seeded/pure random
    }

}

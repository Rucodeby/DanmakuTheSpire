package danmaku.collectables;

import com.badlogic.gdx.math.Polygon;
import danmaku.dungeons.AbstractDungeon;
import danmaku.interfaces.ICollidable;

public class PowerCollectable extends AbstractCollectable {

    public static int addValue = 1;

    @Override
    public Polygon getHitbox() {
        return null;
    }

    @Override
    public void setHitbox(float[] vertices) {

    }

    @Override
    public void doCollision(ICollidable other) {
        AbstractDungeon.player.power += addValue;
        // TODO remove this from QuadTree
    }

    @Override
    public float getX() {
        return 0;
    }

    @Override
    public float getY() {
        return 0;
    }

    @Override
    public float getCX() {
        return 0;
    }

    @Override
    public float getCY() {
        return 0;
    }

    @Override
    public float getWidth() {
        return 0;
    }

    @Override
    public float getHeight() {
        return 0;
    }

    @Override
    public float update() {
        return 0;
    }

    @Override
    public float render() {
        return 0;
    }
}

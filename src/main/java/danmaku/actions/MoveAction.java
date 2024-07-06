package danmaku.actions;

import danmaku.bullets.AbstractBullet;
import danmaku.dungeons.AbstractDungeon;

import java.util.Collection;

public abstract class MoveAction {

    public abstract void update();

    public static void addBullet(AbstractBullet b) {
        AbstractDungeon.danmakuManager.bullets.add(b);
    }

    public static void addBullets(Collection<? extends AbstractBullet> ar) {
        AbstractDungeon.danmakuManager.bullets.addAll(ar);
    }
}

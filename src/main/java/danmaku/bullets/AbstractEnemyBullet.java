package danmaku.bullets;

import danmaku.actions.MoveAction;

public abstract class AbstractEnemyBullet extends AbstractBullet{
    public AbstractEnemyBullet(String texture, float x, float y, MoveAction move) {
        super(true, texture, x, y, move);
    }
}

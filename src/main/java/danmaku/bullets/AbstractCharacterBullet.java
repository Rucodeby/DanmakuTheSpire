package danmaku.bullets;

import danmaku.actions.MoveAction;

public abstract class AbstractCharacterBullet extends AbstractBullet {

    public AbstractCharacterBullet(String texture, float x, float y, MoveAction move) {
        super(false, texture, x, y, move);
    }
}

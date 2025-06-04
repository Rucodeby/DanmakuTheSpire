package danmaku.bullets;


public abstract class AbstractEnemyBullet extends AbstractBullet{
    public AbstractEnemyBullet(String texture, float x, float y) {
        super(true, texture, x, y);
    }
}
